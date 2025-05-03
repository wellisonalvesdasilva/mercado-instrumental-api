package br.com.shopdosmusicos.controller.site.anuncio.schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AnuncioSiteResponse(

		Long idAnuncio,

		String titulo,

		LocalDate dataPublicacao,

		Boolean isUsado,

		BigDecimal valor,

		AnuncioVendedorSiteResponse vendedor,

		Long quantidadeAcesso,

		String uf,

		String municipio,

		String descricao,
		
		List<String> srcsDir

) {

}
