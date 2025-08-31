package br.com.mercadoinstrumental.repository.anuncio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoPlanoEnum;
import br.com.mercadoinstrumental.model.usuario.Usuario;
import feign.Param;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

	Page<Anuncio> findAll(Specification<Anuncio> filtrosCustomizados, Pageable pageable);

	Boolean existsByUsuarioAndTipoPlano(Usuario usuario, TipoPlanoEnum gratis);

	Boolean existsByTipoPlanoAndUsuario_Email(TipoPlanoEnum tipoPlano, String email);

	List<Anuncio> findAllByDataHoraExpiracaoLessThanEqual(LocalDateTime hoje);

	Anuncio findByIdPagamentoLytex(String invoiceId);

	List<Anuncio> findAllByUsuario_Id(Long idUsuario);
	
	List<Anuncio> findAllByUsuario_HashDeQuemIndicou(String hashDeQuemIndicou);

	@Query("""
			    SELECT COALESCE(SUM(a.valorPagoPlano) * 0.2, 0)
			    FROM Anuncio a
			    WHERE a.tipoPlano <> 'GRATIS'
			      AND a.status IN ('PUBLICADO', 'EXPIRADO')
			      AND a.numeroTransacaoPagamento IS NULL
			      AND (:hashDeQuemIndicou IS NULL OR a.usuario.hashDeQuemIndicou = :hashDeQuemIndicou)
			""")
	BigDecimal somatorioPlanosPagosSemTransacao(@Param("hashIndicador") String hashDeQuemIndicou);
	
	
	@Query("""
		    SELECT COALESCE(SUM(a.valorPagoPlano) * 0.2, 0)
		    FROM Anuncio a
		    WHERE a.numeroTransacaoPagamento IS NOT NULL
		      AND (:hashDeQuemIndicou IS NULL OR a.usuario.hashDeQuemIndicou = :hashDeQuemIndicou)
		""")
BigDecimal somatorioPlanosPagosComTransacao(@Param("hashIndicador") String hashDeQuemIndicou);

	@Query("SELECT COUNT(a) FROM Anuncio a WHERE a.usuario.id = :usuarioId")
	long countByUsuarioId(Long usuarioId);
}