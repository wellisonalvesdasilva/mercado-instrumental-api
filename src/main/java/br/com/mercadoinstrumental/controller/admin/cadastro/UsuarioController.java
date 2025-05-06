package br.com.mercadoinstrumental.controller.admin.cadastro;
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

@Tag(name = "Usuario::Cadastro")
@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioManager usuarioManager;

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody UsuarioReq request) {
        Usuario usuario = usuarioManager.createUsuario(request);
        return ResponseEntity.ok(usuario.getId());
    }

    @PostMapping("/activate/{email}")
    public ResponseEntity<Void> activateUser(
            @PathVariable String email,
            @RequestParam String keyword) {
        usuarioManager.activeUser(email, keyword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-recovery/request/{email}")
    public ResponseEntity<Void> requestPasswordRecovery(@PathVariable String email) {
        usuarioManager.requestPasswordRecovery(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-recovery/confirm/{email}")
    public ResponseEntity<Void> confirmPasswordRecovery(
    		@PathVariable String email,
    		@RequestParam String newPassword,
    		@RequestParam String palavraPasse) {
        usuarioManager.confirmPasswordRecovery(email, newPassword, palavraPasse);
        return ResponseEntity.ok().build();
    }

  }
