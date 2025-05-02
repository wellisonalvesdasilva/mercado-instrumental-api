package br.com.shopdosmusicos.controller.admin.anuncio.schema;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.shopdosmusicos.controller.schema.geral.EnumResponse;

public record AnuncioResponse(

		String titulo,

		String descricao,

		EnumResponse tipo,

		EnumResponse marca,

		Long idMunicipioIbge,

		BigDecimal valor,

		Boolean novo,
		
		Boolean ativo,
		
		LocalDate dataPublicacao

) {

}
