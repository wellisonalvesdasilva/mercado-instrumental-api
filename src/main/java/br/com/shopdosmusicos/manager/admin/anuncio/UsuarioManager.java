package br.com.shopdosmusicos.manager.admin.anuncio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.shopdosmusicos.controller.admin.usuario.schema.UsuarioReq;
import br.com.shopdosmusicos.manager.exception.BusinessException;
import br.com.shopdosmusicos.model.usuario.Usuario;
import br.com.shopdosmusicos.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class UsuarioManager {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario createUsuario(@Valid UsuarioReq req) {
		validations(req);
		Usuario usuario = new Usuario(req.nome(), req.email(), req.senha(), req.whats());
		usuario = usuarioRepository.save(usuario);
		sendActivationEmail(usuario);
		return usuario;
	}
	

	@Transactional
	public void activeUser(String email, String palavraChave) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario.getPalavraChaveTemp().equals(palavraChave)) {
			usuario.setAtivo(true);
			usuario.setPalavraChaveTemp(null);
		}
	}

	public void sendActivationEmail(Usuario usuario) {
		// TODO: Implement
	}



	@Transactional
	public void requestPasswordRecovery(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		usuario.gerarNovaSenhaTemporaria();
		usuarioRepository.save(usuario);
		// TODO: enviar email com nova senha.
	}

	@Transactional
	public void confirmPasswordRecovery(String email, String newPassword, String palavraPasse) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario.getPalavraChaveTemp().equals(palavraPasse)) { 
			usuario.setSenha(newPassword);
		}
		
		usuarioRepository.save(usuario);
	}

	private void validations(@Valid UsuarioReq req) {
	    boolean emailJaCadastrado = usuarioRepository.existsByEmail(req.email());
	    if (emailJaCadastrado) {
	        throw new BusinessException("Já existe um usuário cadastrado com o e-mail informado. Use a opção 'Esqueci minha senha' para redefinir a senha.");
	    }
	}


}
