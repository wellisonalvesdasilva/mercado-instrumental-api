package br.com.shopdosmusicos.controller.site.anuncio.schema;

import java.math.BigDecimal;

import br.com.shopdosmusicos.controller.schema.geral.EnumResponse;

public record AnuncioListSiteResponse(
		
		Long idAnuncio,
		
		String srcMiniatura,

		Boolean isUsado,

		EnumResponse marca,
		
		String titulo,
		
		BigDecimal valor,
		
		String uf,
		
		String municipio
		
) {

}
