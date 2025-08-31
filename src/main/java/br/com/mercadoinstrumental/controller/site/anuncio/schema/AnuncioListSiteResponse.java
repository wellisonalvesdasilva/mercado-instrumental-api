package br.com.mercadoinstrumental.controller.site.anuncio.schema;

import java.math.BigDecimal;

import br.com.mercadoinstrumental.controller.schema.geral.EnumResponse;

public record AnuncioListSiteResponse(
		
		Long idAnuncio,
		
		String srcMiniatura,

		Boolean isUsado,

		EnumResponse marca,
		
		String titulo,
		
		BigDecimal valor,
		
		String uf,
		
		String municipio,
		
		String descricao,
		
		Long quantidadeAcesso,
		
		Boolean isDestaque
		
) {

}
