package br.com.mercadoinstrumental.controller.admin.auth.schema;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProfileResponse(
		
		@NotEmpty
		@NotNull
		String nome,
		
		@NotEmpty
		@NotNull
		String email,
		
		@NotEmpty
		@NotNull
		Collection<? extends GrantedAuthority> perfis
) {

}
