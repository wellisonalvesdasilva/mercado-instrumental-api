package br.com.mercadoinstrumental.repository.anuncio;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoPlanoEnum;
import br.com.mercadoinstrumental.model.usuario.Usuario;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

	Page<Anuncio> findAll(Specification<Anuncio> filtrosCustomizados, Pageable pageable);

	Boolean existsByUsuarioAndTipoPlano(Usuario usuario, TipoPlanoEnum gratis);
	
	Boolean existsByTipoPlanoAndUsuario_Email(TipoPlanoEnum tipoPlano, String email);

	List<Anuncio> findAllByDataHoraExpiracaoLessThanEqual(LocalDateTime hoje);

	Anuncio findByIdPagamentoLytex(String invoiceId);
	
}