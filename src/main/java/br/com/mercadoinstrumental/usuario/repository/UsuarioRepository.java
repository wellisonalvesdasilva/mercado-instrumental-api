package br.com.mercadoinstrumental.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mercadoinstrumental.model.usuario.Usuario;
import feign.Param;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Boolean existsByEmail(String email);

	@Transactional(readOnly = true)
	Optional<Usuario> findByEmailAndAtivo(String email, Boolean ativo);

	Optional<Usuario> findByPalavraChaveTemp(String palavraPasse);

	@Query("SELECT u FROM Usuario u WHERE :codigo IN elements(u.perfil)")
	List<Usuario> findByTipoPerfil(Integer codigo);

	Page<Usuario> findAll(Specification<Usuario> filtrosCustomizados, Pageable pageable);

	Optional<Usuario> findByHashDeQuemIndicou(String hashDeQuemIndicou);

	Optional<Usuario> findByHashPropria(String hashPropria);

	@Query("""
		    SELECT COUNT(u)
		    FROM Usuario u
		    WHERE (:hashDeQuemIndicou IS NULL OR u.hashDeQuemIndicou = :hashDeQuemIndicou)
		""")
		Long countUsuariosComHashIndicador(@Param("hashDeQuemIndicou") String hashDeQuemIndicou);


	@Query("""
			    SELECT COUNT(DISTINCT u)
			    FROM Usuario u
			    JOIN u.anuncios a
			    WHERE a.tipoPlano <> 'GRATIS'
			      AND (:hashUsuarioLogado IS NULL OR u.hashDeQuemIndicou = :hashUsuarioLogado)
			""")
	Long countUsuariosComAnuncioProPorIndicador(String hashUsuarioLogado);

	Usuario findById(Long idUsuario);

	long countByHashDeQuemIndicou(String hashPropria);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
		       "FROM Usuario u JOIN u.anuncios a " +
		       "WHERE u.hashDeQuemIndicou = :hashPropria AND a.numeroTransacaoPagamento IS NULL")
		boolean existsByHashDeQuemIndicouAndAnunciosNumeroTransacaoPagamentoIsNull(String hashPropria);
}