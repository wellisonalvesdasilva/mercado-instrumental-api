package br.com.shopdosmusicos.controller.admin.anuncio.schema;

import jakarta.validation.constraints.NotNull;

public record CategoriaUpd(
		
	@NotNull
	String nome,
	
	@NotNull
	String rgbColor,
	
	@NotNull
	Boolean ativo
	
) {
	
}
