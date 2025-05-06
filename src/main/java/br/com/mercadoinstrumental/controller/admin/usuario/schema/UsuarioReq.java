package br.com.mercadoinstrumental.controller.admin.usuario.schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioReq(

		@NotNull
		@Size(min = 1, max = 100)
		String nome,

		@NotNull
		String
		email,

		@NotNull
		String
		whats,

		@NotNull
		String
		senha) {

}
