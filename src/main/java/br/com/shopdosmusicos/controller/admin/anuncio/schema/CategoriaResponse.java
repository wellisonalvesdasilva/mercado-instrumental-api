package br.com.shopdosmusicos.controller.admin.anuncio.schema;

import jakarta.validation.constraints.NotNull;

public record CategoriaResponse(
		
	@NotNull
	Long id,
		
	@NotNull
	String nome,
	
	@NotNull
	String rgbColor,
	
	@NotNull
	Boolean ativo
	
) {
	
}
