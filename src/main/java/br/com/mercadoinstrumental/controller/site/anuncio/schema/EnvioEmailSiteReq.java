package br.com.mercadoinstrumental.controller.site.anuncio.schema;

public record EnvioEmailSiteReq(

	Long idAnuncio,
	
	String nome,
	
	String email,
	
	String whatsApp,
	
	String mensagem

) {

}
