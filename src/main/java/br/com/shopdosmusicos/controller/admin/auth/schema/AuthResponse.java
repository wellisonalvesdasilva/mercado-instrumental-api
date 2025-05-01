package br.com.shopdosmusicos.controller.admin.auth.schema;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public record AuthResponse(
		
		String usuario,
		
		String token,
		
		Collection<? extends GrantedAuthority> perfis
) {

}
