package br.com.mercadoinstrumental.repository.anuncio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.ArtefatoAnuncio;

public interface ArtefatoAnuncioRepository extends JpaRepository<ArtefatoAnuncio, Long> {

	List<ArtefatoAnuncio> findAllByAnuncioAndMiniatura(Anuncio anuncio, Boolean miniatura);
	
	ArtefatoAnuncio findByAnuncioAndMiniatura(Anuncio anuncio, Boolean miniatura);

	Boolean existsByAnuncioAndSrcDocumentoIsNull(Anuncio anuncio);

	List<ArtefatoAnuncio> findAllByAnuncio(Anuncio anuncio);
}