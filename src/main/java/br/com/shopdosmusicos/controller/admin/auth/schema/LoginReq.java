package br.com.shopdosmusicos.controller.admin.auth.schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginReq(
		
		@NotEmpty
		@NotNull
		String email,
		
		@NotEmpty
		@NotNull
		String senha
) {
}
