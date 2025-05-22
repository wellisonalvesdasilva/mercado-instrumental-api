package br.com.mercadoinstrumental.manager.admin.anuncio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import br.com.mercadoinstrumental.controller.admin.imagem.schema.ArtefatoAnuncioResponse;
import br.com.mercadoinstrumental.controller.commom.schema.RwsArtefatoResponse;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.ArtefatoAnuncio;
import br.com.mercadoinstrumental.exceptions.BusinessException;
import br.com.mercadoinstrumental.repository.anuncio.AnuncioRepository;
import br.com.mercadoinstrumental.repository.anuncio.ArtefatoAnuncioRepository;
import jakarta.validation.constraints.NotNull;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Validated
public class ArtefatoAnuncioManager {

	@Autowired
	private AnuncioRepository anuncioRepository;
	
	@Autowired
	private ArtefatoAnuncioRepository artefatoAnuncioRepository;

	
    @Value("${upload.imagem.diretorio}")
    private String diretorioUpload;
    

    @Transactional
    public ArtefatoAnuncio upload(@NotNull Long idArtefato, @NotNull MultipartFile arquivo) {

        if (arquivo == null) {
            throw new IllegalArgumentException("Nenhum arquivo enviado.");
        }

        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio.");
        }

        String contentType = arquivo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("O arquivo não é uma imagem válida.");
        }

        ArtefatoAnuncio artefato = artefatoAnuncioRepository.findById(idArtefato)
                .orElseThrow(() -> new BusinessException("Anuncio.1000", "ArtefatoAnuncio não encontrado para o id informado."));

        try {

            if (artefato.getSrcDocumento() != null && !artefato.getSrcDocumento().isEmpty()) {
                Path caminhoArquivoAntigo = Paths.get(artefato.getSrcDocumento());
                try {
                    Files.deleteIfExists(caminhoArquivoAntigo);
                } catch (IOException e) {
                    throw new RuntimeException("Erro ao excluir arquivo antigo.", e);
                }
            }

            String nomeOriginal = arquivo.getOriginalFilename();
            if (nomeOriginal == null) {
                throw new IllegalArgumentException("Nome do arquivo não encontrado.");
            }

            String nomeArquivo = UUID.randomUUID() + "_" + nomeOriginal;
            Path diretorioPath = Paths.get(diretorioUpload);
            Files.createDirectories(diretorioPath);

            Path caminhoArquivo = diretorioPath.resolve(nomeArquivo);

            try (InputStream inputStream = arquivo.getInputStream()) {
                BufferedImage imagemOriginal = ImageIO.read(inputStream);
                if (imagemOriginal == null) {
                    throw new IllegalArgumentException("Arquivo enviado não é uma imagem válida.");
                }

                int larguraOriginal = imagemOriginal.getWidth();
                int alturaOriginal = imagemOriginal.getHeight();

                // if (larguraOriginal < 740 || alturaOriginal < 540) {
                   // throw new IllegalArgumentException("A imagem precisa ter pelo menos 740x540 pixels.");
                // }

                int larguraDesejada = 740;
                int alturaDesejada = 540;
                
                if (Boolean.TRUE.equals(artefato.getMiniatura())) {
                    try {
                        Thumbnails.of(imagemOriginal)
                                .size(343, 197)
                                .keepAspectRatio(true)
                                .toFile(caminhoArquivo.toFile());
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao gerar miniatura", e);
                    }
                } else {
                    try {
                        Thumbnails.of(imagemOriginal)
                                .size(larguraDesejada, alturaDesejada)
                                .keepAspectRatio(true)
                                .toFile(caminhoArquivo.toFile());
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao redimensionar imagem", e);
                    }
                }

                String srcDir = diretorioUpload + "/" + nomeArquivo;
                artefato.setSrcDocumento(srcDir);

                return artefatoAnuncioRepository.save(artefato);

            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar a imagem", e);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo", e);
        }
    }






    public RwsArtefatoResponse download(@NotNull Long idArtefato) {
    	
    	ArtefatoAnuncio imagem = artefatoAnuncioRepository.findById(idArtefato)
				.orElseThrow(BusinessException.from("Anuncio.1000", "Artefato não encontrado para o id informado."));
	
    	String caminho = imagem.getSrcDocumento();
        File arquivo = new File(caminho);

        if (!arquivo.exists()) {
            throw new RuntimeException("Arquivo não encontrado no caminho: " + caminho);
        }

        try {
            byte[] bytes = Files.readAllBytes(arquivo.toPath());
            String base64 = Base64.getEncoder().encodeToString(bytes);

            String tipoMime = Files.probeContentType(arquivo.toPath());

            return new RwsArtefatoResponse(arquivo.getName(), base64, tipoMime);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo para Base64", e);
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
