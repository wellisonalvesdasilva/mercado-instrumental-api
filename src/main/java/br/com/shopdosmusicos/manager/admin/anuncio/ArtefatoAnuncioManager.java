package br.com.shopdosmusicos.manager.admin.anuncio;

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

import br.com.shopdosmusicos.controller.admin.imagem.schema.ArtefatoAnuncioResponse;
import br.com.shopdosmusicos.controller.commom.schema.RwsArtefatoResponse;
import br.com.shopdosmusicos.domain.model.anuncio.Anuncio;
import br.com.shopdosmusicos.domain.model.anuncio.ArtefatoAnuncio;
import br.com.shopdosmusicos.manager.exception.BusinessException;
import br.com.shopdosmusicos.repository.anuncio.AnuncioRepository;
import br.com.shopdosmusicos.repository.anuncio.ArtefatoAnuncioRepository;
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
    public ArtefatoAnuncio upload(@NotNull Long idAnuncio, @NotNull Boolean isMiniatura, @NotNull MultipartFile[] arquivos) {
        
    	Anuncio anuncio = anuncioRepository.findById(idAnuncio)
				.orElseThrow(BusinessException.from("Anuncio.1000", "Anuncio não encontrado para o id informado."));
	
    	if (arquivos == null || arquivos.length == 0) {
            throw new IllegalArgumentException("Nenhum arquivo enviado.");
        }

        MultipartFile arquivo = arquivos[0];
        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio.");
        }

        try {
            String nomeOriginal = arquivo.getOriginalFilename();
            String nomeArquivo = UUID.randomUUID() + "_" + nomeOriginal;
            Path diretorioPath = Paths.get(diretorioUpload);
            Files.createDirectories(diretorioPath);

            Path caminhoArquivo = diretorioPath.resolve(nomeArquivo);

            if (Boolean.TRUE.equals(isMiniatura)) {
                Thumbnails.of(arquivo.getInputStream())
                          .size(200, 200)
                          .toFile(caminhoArquivo.toFile());
            } else {
                arquivo.transferTo(caminhoArquivo.toFile());
            }

            String srcDir = diretorioUpload + "/" + nomeArquivo;
            ArtefatoAnuncio imagemAnuncio = new ArtefatoAnuncio(anuncio, srcDir, isMiniatura);

            return artefatoAnuncioRepository.save(imagemAnuncio);

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
                artefato.getId(),
                artefato.getDataHoraUpload().toLocalDate(),
                artefato.getSrcDocumento()))
            .toList();
    }


}
