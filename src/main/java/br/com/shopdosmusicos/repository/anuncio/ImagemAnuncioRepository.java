package br.com.shopdosmusicos.repository.anuncio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.shopdosmusicos.domain.model.anuncio.ImagemAnuncio;

public interface ImagemAnuncioRepository extends JpaRepository<ImagemAnuncio, Long> {

}