package br.com.mercadoinstrumental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.client.rws.RwsIbgeClient;
import br.com.mercadoinstrumental.client.rws.schema.RwsListEstadoResponse;
import br.com.mercadoinstrumental.client.rws.schema.RwsListMunicipioResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//@Tag(name = "Ibge::Ibge")
@RestController
@RequestMapping("ibge-dados")
public class IbgeController {

	@Autowired
	private RwsIbgeClient client;

	@GetMapping("/estados")
	public ResponseEntity<List<RwsListEstadoResponse>> getEstados() {
		List<RwsListEstadoResponse> estados = client.getEstados();
		return ResponseEntity.ok(estados);
	}

	
	@GetMapping("/municipios/{estado}")
	public ResponseEntity<List<RwsListMunicipioResponse>> getMunicipios(@PathVariable String estado) {
	    List<RwsListMunicipioResponse> municipios;

	    if (estado == null || "null".equals(estado) || estado.isBlank()) {
	        municipios = client.getAllMunicipios();
	    } else {
	        municipios = client.getMunicipiosByUf(estado);
	    }

	    return ResponseEntity.ok(municipios);
	}


}
