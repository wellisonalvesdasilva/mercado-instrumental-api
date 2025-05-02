package br.com.shopdosmusicos.repository.anuncio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.shopdosmusicos.domain.model.anuncio.Anuncio;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

	Page<Anuncio> findAll(Specification<Anuncio> filtrosCustomizados, PageRequest of);

}