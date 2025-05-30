package br.com.mercadoinstrumental.controller.admin.cadastro;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.controller.admin.usuario.schema.UsuarioReq;
import br.com.mercadoinstrumental.manager.admin.anuncio.UsuarioManager;
import br.com.mercadoinstrumental.model.usuario.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

//@Tag(name = "Usuario::Cadastro")
@RestController
@RequestMapping("/usuarios")
public class UsuarioExternoController {

    @Autowired
    private UsuarioManager usuarioManager;

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody UsuarioReq request) throws IOException {
        Usuario usuario = usuarioManager.createUsuario(request);
        return ResponseEntity.ok(usuario.getId());
    }

    @PostMapping("/activate/{palavraChave}")
    public ResponseEntity<Void> activateUser(
            @PathVariable String palavraChave) {
        usuarioManager.activeUser(palavraChave);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recuperacao-de-senha/pedido/{email}")
    public ResponseEntity<Void> requestPasswordRecovery(@PathVariable String email) {
        usuarioManager.requestPasswordRecovery(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirmacao-recuperacao-senha")
    public ResponseEntity<Void> confirmPasswordRecovery(
    		@RequestParam String senha,
    		@RequestParam String palavraPasse) {
        usuarioManager.confirmPasswordRecovery(senha, palavraPasse);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/alteracao-senha/{idUsuario}")
    public ResponseEntity<Void> confirmPasswordAlter(
    		@PathVariable Integer idUsuario,
    		@RequestParam String senha) {
        usuarioManager.confirmPasswordChange(idUsuario, senha);
        return ResponseEntity.ok().build();
    }

  }
