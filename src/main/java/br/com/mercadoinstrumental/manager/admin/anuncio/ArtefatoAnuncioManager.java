package br.com.mercadoinstrumental.manager.admin.anuncio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

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
import br.com.mercadoinstrumental.manager.exception.BusinessException;
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
        
    	ArtefatoAnuncio artefato = artefatoAnuncioRepository.findById(idArtefato)
				.orElseThrow(BusinessException.from("Anuncio.1000", "ArtefatoAnuncio não encontrado para o id informado."));
	
    	if (arquivo == null) {
            throw new IllegalArgumentException("Nenhum arquivo enviado.");
        }

        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio.");
        }

        try {
            String nomeOriginal = arquivo.getOriginalFilename();
            String nomeArquivo = UUID.randomUUID() + "_" + nomeOriginal;
            Path diretorioPath = Paths.get(diretorioUpload);
            Files.createDirectories(diretorioPath);

            Path caminhoArquivo = diretorioPath.resolve(nomeArquivo);

            if (Boolean.TRUE.equals(artefato.getMiniatura())) {
                Thumbnails.of(arquivo.getInputStream())
                          .size(200, 200)
                          .toFile(caminhoArquivo.toFile());
            } else {
                arquivo.transferTo(caminhoArquivo.toFile());
            }

            String srcDir = diretorioUpload + "/" + nomeArquivo;
            
            artefato.setSrcDocumento(srcDir);
            return artefatoAnuncioRepository.save(artefato);

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
