package br.com.mercadoinstrumental.client.rws;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mailgunClient", url = "https://api.mailgun.net/v3")
public interface MailgunClient {

	@PostMapping(value = "/{domain}/messages", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	ResponseEntity<String> enviarEmail(
	    @RequestHeader("Authorization") String authorization,
	    @PathVariable("domain") String domain,
	    @RequestParam MultiValueMap<String, String> form
	);
}
