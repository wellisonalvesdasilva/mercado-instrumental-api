package br.com.mercadoinstrumental.controller.admin.indicacao.schema;

import java.time.LocalDate;

import br.com.mercadoinstrumental.controller.schema.geral.EnumResponse;

public record IndicacaoResponse(

		Long id,

		String nome,
		
		LocalDate dataCadastro,
		
		Long quantidadeIndicacoes,
		
		Long quantidadeAnunciosPublicados,
		
		Boolean hasComissaoPendente,
		
		String chavePix,
		
		EnumResponse tipoChavePix,
		
		String whats

) {

}
