package br.com.shopdosmusicos.client.rws;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.shopdosmusicos.client.rws.schema.RwsListEstadoResponse;
import br.com.shopdosmusicos.client.rws.schema.RwsListMunicipioResponse;

@FeignClient(name = "RwsIbgeClient", url = "${clients.rws-ibge.url}")
public interface RwsIbgeClient {

	@GetMapping("localidades/estados?orderBy=nome")
	List<RwsListEstadoResponse> getEstados();

	@GetMapping("localidades/estados/{uf}/municipios?orderBy=nome")
	List<RwsListMunicipioResponse> getMunicipiosByUf(@PathVariable String uf);
	
}