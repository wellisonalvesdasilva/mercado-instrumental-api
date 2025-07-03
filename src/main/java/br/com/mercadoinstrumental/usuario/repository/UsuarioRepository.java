package br.com.mercadoinstrumental.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mercadoinstrumental.model.usuario.Usuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Boolean existsByEmail(String email);
	
	@Transactional(readOnly=true)
	Optional<Usuario> findByEmailAndAtivo(String email, Boolean ativo);
		
	Optional<Usuario> findByPalavraChaveTemp(String palavraPasse);

    @Query("SELECT u FROM Usuario u WHERE :codigo IN elements(u.perfil)")
    List<Usuario> findByTipoPerfil(Integer codigo);
	
}