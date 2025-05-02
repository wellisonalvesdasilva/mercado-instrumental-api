/*package br.com.shopdosmusicos.client.rws;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import br.com.bancoamazonia.sigaf.client.rws.data.*;

@FeignClient(name = "RwsIbgeClient", url = "${clients.rws-ibge.url}")
public interface RwsIbgeClient {

	@GetMapping("municipio/{sigla}")
	RwsProcesso getProcesso(@PathVariable String sigla);

	
}*/