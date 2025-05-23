package br.com.mercadoinstrumental.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.mercadoinstrumental.model.usuario.Usuario;
import br.com.mercadoinstrumental.security.UserSS;
import br.com.mercadoinstrumental.usuario.repository.UsuarioRepository;

@Service
@Validated
public class SegurancaManager {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario obterUsuarioLogado() {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserSS) {
            UserSS userSS = (UserSS) authentication.getPrincipal();
            return usuarioRepository.findByEmailAndAtivo(userSS.getUsername(), true).orElse(null);
        }
        
        return null;
    }
}
