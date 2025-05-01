package br.com.shopdosmusicos.repository.anuncio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.shopdosmusicos.domain.model.anuncio.Anuncio;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

}