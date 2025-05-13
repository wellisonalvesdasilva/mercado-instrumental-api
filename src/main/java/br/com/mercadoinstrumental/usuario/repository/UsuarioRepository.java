package br.com.mercadoinstrumental.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mercadoinstrumental.model.usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Boolean existsByEmail(String email);
	
	@Transactional(readOnly=true)
	Optional<Usuario> findByEmailAndAtivo(String email, Boolean ativo);
		
	Optional<Usuario> findByPalavraChaveTemp(String palavraPasse);
	
}