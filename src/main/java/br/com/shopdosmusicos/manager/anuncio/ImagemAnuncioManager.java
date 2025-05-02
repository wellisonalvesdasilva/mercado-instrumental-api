package br.com.shopdosmusicos.manager.anuncio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import br.com.shopdosmusicos.controller.admin.imagem.schema.ImagemAnuncioResponse;
import br.com.shopdosmusicos.controller.commom.schema.RwsDocumentoResponse;
import br.com.shopdosmusicos.repository.anuncio.AnuncioRepository;
import br.com.shopdosmusicos.repository.anuncio.ImagemAnuncioRepository;
import jakarta.validation.constraints.NotNull;

@Service
@Validated
public class ImagemAnuncioManager {

	@Autowired
	private AnuncioRepository anuncioRepository;
	
	@Autowired
	private ImagemAnuncioRepository imagemAnuncioRepository;


	public Boolean upload(@NotNull Long idAnuncio, @NotNull MultipartFile[] arquivo) {
		// TODO
		return null;
	}


	public RwsDocumentoResponse download(@NotNull Long idArtefato) {
		// TODO:
		return null;
	}


	public List<ImagemAnuncioResponse> findAllByAnuncio(Long idAnuncio) {
		return null;
		
	}

}
