package br.com.mercadoinstrumental.controller.admin.anuncio.schema;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.mercadoinstrumental.controller.schema.geral.EnumResponse;

public record AnuncioResponse(

		Long id,

		String titulo,

		String descricao,

		EnumResponse tipo,

		EnumResponse marca,

		String estado,

		String municipio,

		BigDecimal valor,

		Boolean novo,

		Boolean ativo,

		LocalDate dataPublicacao,

		EnumResponse status,

		Long quantidadeAcesso,

		EnumResponse tipoPlano,

		String urlBaseLytex,

		String idPagamentoLytex,

		String hashIdPagamentoLytex

) {

}
