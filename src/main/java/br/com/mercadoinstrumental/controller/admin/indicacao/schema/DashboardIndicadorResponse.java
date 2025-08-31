package br.com.mercadoinstrumental.controller.admin.indicacao.schema;

import java.math.BigDecimal;

import br.com.mercadoinstrumental.controller.schema.geral.EnumResponse;

public record DashboardIndicadorResponse(

		String hash,
		
		BigDecimal valorAReceber,

		BigDecimal valorRecebido,
		
		Long qtdeIndicados,
		
		Long qtdeComAnunciosPro,
		
		String chavePix,
		
		EnumResponse tipoChavePix
		
		
) {

}
