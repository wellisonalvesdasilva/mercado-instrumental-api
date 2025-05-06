package br.com.mercadoinstrumental.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mercadoinstrumental.model.usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	//@Transactional(readOnly=true)
	//Usuario findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
	@Transactional(readOnly=true)
	Optional<Usuario> findByEmail(String email);
	
}