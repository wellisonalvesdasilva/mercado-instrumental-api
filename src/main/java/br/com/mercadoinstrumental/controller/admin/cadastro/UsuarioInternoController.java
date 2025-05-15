package br.com.mercadoinstrumental.controller.admin.cadastro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.controller.admin.usuario.schema.UsuarioAlteracaoSenhaResponse;
import br.com.mercadoinstrumental.manager.admin.anuncio.UsuarioManager;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuario::DadosCadastrais")
@RestController
@RequestMapping("/usuariosInterno")
public class UsuarioInternoController {

    @Autowired
    private UsuarioManager usuarioManager;

	@GetMapping
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<UsuarioAlteracaoSenhaResponse> buscarDadosUsuarioLogado() {
		UsuarioAlteracaoSenhaResponse usuario = usuarioManager.buscarDadosUsuarioLogado();
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<Void> alterarSenha(
			@RequestParam String senhaAtual,
			@RequestParam String novaSenha) {
		usuarioManager.alterarSenha(senhaAtual, novaSenha);
        return ResponseEntity.ok().build();
	}

  }
