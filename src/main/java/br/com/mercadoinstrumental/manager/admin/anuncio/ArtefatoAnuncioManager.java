package br.com.mercadoinstrumental.manager.admin.anuncio;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import br.com.mercadoinstrumental.controller.admin.imagem.schema.ArtefatoAnuncioResponse;
import br.com.mercadoinstrumental.controller.commom.schema.RwsArtefatoResponse;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.ArtefatoAnuncio;
import br.com.mercadoinstrumental.exceptions.BusinessException;
import br.com.mercadoinstrumental.repository.anuncio.AnuncioRepository;
import br.com.mercadoinstrumental.repository.anuncio.ArtefatoAnuncioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import net.coobird.thumbnailator.Thumbnails;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Validated
public class ArtefatoAnuncioManager {

	@Autowired
	private AnuncioRepository anuncioRepository;
	
	@Autowired
	private ArtefatoAnuncioRepository artefatoAnuncioRepository;

	@Autowired
	private S3Client s3Client;
	
	@Value("${aws.region}")
	private String awsRegion;
	
    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    
    

    @Transactional
    public ArtefatoAnuncio upload(@NotNull Long idArtefato, @NotNull MultipartFile arquivo) {

        if (arquivo == null || arquivo.isEmpty()) {
            throw new BusinessException("Upload.1000", "Arquivo inválido ou vazio.");
        }

        String contentType = arquivo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BusinessException("Upload.1001", "O arquivo não é uma imagem válida.");
        }

        ArtefatoAnuncio artefato = artefatoAnuncioRepository.findById(idArtefato)
                .orElseThrow(() -> new BusinessException("Anuncio.1000", "ArtefatoAnuncio não encontrado para o id informado."));

        try (InputStream inputStream = arquivo.getInputStream()) {

            BufferedImage imagemOriginal = ImageIO.read(inputStream);
            if (imagemOriginal == null) {
                throw new BusinessException("Upload.1004", "Arquivo enviado não é uma imagem válida.");
            }

            int larguraOriginal = imagemOriginal.getWidth();
            int alturaOriginal = imagemOriginal.getHeight();

            int larguraMinima = 800;
            int alturaMinima = 600;

            if (larguraOriginal < larguraMinima || alturaOriginal < alturaMinima) {
                throw new BusinessException("Upload.1005",
                        String.format("A imagem deve ter no mínimo %dx%d pixels.", larguraMinima, alturaMinima));
            }

            BufferedImage imagemRedimensionada;
            if (Boolean.TRUE.equals(artefato.getMiniatura())) {
                imagemRedimensionada = Thumbnails.of(imagemOriginal)
                        .size(306, 211)
                        .keepAspectRatio(true)
                        .asBufferedImage();
            } else {
                imagemRedimensionada = Thumbnails.of(imagemOriginal)
                        .size(larguraMinima, alturaMinima)
                        .keepAspectRatio(true)
                        .asBufferedImage();
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            String extensao = Optional.ofNullable(arquivo.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(arquivo.getOriginalFilename().lastIndexOf('.') + 1))
                    .orElse("jpg");

            ImageIO.write(imagemRedimensionada, extensao, os);
            byte[] bytesImagem = os.toByteArray();

            String nomeOriginal = Optional.ofNullable(arquivo.getOriginalFilename()).orElse("imagem");
            String nomeArquivo = UUID.randomUUID() + "_" + nomeOriginal;
            String chaveS3 = nomeArquivo;

            if (artefato.getSrcDocumento() != null && !artefato.getSrcDocumento().isEmpty()) {
                try {
                    String chaveAntiga = artefato.getSrcDocumento().replaceFirst(".+\\.amazonaws\\.com/", "");
                    s3Client.deleteObject(builder -> builder.bucket(bucketName).key(chaveAntiga).build());
                } catch (Exception e) {
                    throw new BusinessException("Upload.1002", "Erro ao excluir arquivo antigo no S3.");
                }
            }

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(chaveS3)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(bytesImagem));

            String urlS3 = "https://" + bucketName + ".s3." + awsRegion + ".amazonaws.com/" + chaveS3;
            artefato.setSrcDocumento(urlS3);

            return artefatoAnuncioRepository.save(artefato);

        } catch (IOException e) {
            throw new BusinessException("Upload.1007", "Erro ao processar a imagem.", e);
        }
    }



    public RwsArtefatoResponse download(@NotNull Long idArtefato) {

        ArtefatoAnuncio imagem = artefatoAnuncioRepository.findById(idArtefato)
                .orElseThrow(() -> new BusinessException("Anuncio.1000", "Artefato não encontrado para o id informado."));

        String url = imagem.getSrcDocumento();

        if (url == null || url.isEmpty()) {
            throw new BusinessException("Download.1000", "URL da imagem não informada.");
        }

        String chave;
        try {
            java.net.URL s3Url = new java.net.URL(url);
            chave = s3Url.getPath().substring(1);
        } catch (Exception e) {
            throw new BusinessException("Download.1001", "URL da imagem inválida.");
        }

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(chave)
                    .build();

            ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);

            byte[] bytes = s3Object.readAllBytes();

            String base64 = Base64.getEncoder().encodeToString(bytes);

            String contentType = s3Object.response().contentType();
            if (contentType == null || contentType.isEmpty()) {
                contentType = "application/octet-stream";
            }

            String nomeArquivo = chave.substring(chave.lastIndexOf('/') + 1);

            return new RwsArtefatoResponse(nomeArquivo, base64, contentType);

        } catch (NoSuchKeyException e) {
            throw new BusinessException("Download.1002", "Arquivo não encontrado no S3.");
        } catch (IOException e) {
            throw new BusinessException("Download.1003", "Erro ao ler o arquivo do S3.");
        }
    }


    
    public List<ArtefatoAnuncioResponse> findAllByAnuncio(Long idAnuncio) {
    	
    	Anuncio anuncio = anuncioRepository.findById(idAnuncio)
				.orElseThrow(BusinessException.from("Anuncio.1000", "Anuncio não encontrado para o id informado."));
	
    	
        return artefatoAnuncioRepository.findAllByAnuncio(anuncio)
            .stream()
            .map(artefato -> new ArtefatoAnuncioResponse(
          		artefato.getNumero(),
                artefato.getId(),
                artefato.getSrcDocumento()))
            .toList();
    }


}
