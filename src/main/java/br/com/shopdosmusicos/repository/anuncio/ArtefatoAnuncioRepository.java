package br.com.shopdosmusicos.repository.anuncio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.shopdosmusicos.domain.model.anuncio.Anuncio;
import br.com.shopdosmusicos.domain.model.anuncio.ArtefatoAnuncio;

public interface ArtefatoAnuncioRepository extends JpaRepository<ArtefatoAnuncio, Long> {

	List<ArtefatoAnuncio> findAllByAnuncio(Anuncio anuncio);
	
	ArtefatoAnuncio findByAnuncioAndMiniatura(Anuncio anuncio, Boolean miniatura);

}