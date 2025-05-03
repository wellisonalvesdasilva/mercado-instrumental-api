package br.com.shopdosmusicos.controller.admin.imagem.schema;

import java.time.LocalDate;

public record ArtefatoAnuncioResponse(
		
		Long idArtefato,
		
		LocalDate dataUpload,
		
		String srcDir
		
) {

}
