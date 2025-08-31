package br.com.mercadoinstrumental.controller.admin.indicacao.schema;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.mercadoinstrumental.controller.schema.geral.EnumResponse;

public record ComissaoIndicadorResponse(

		Long idAnuncio,
		
		String anuncio,
		
		EnumResponse tipoPlano,
		
		BigDecimal valorPlano,
		
		BigDecimal comissao,
		
		LocalDate dataPagamentoComissao,
		
		String numeroTransacaoPix,
		
		String situacaoAnuncio,
		
		String link
) {

}
