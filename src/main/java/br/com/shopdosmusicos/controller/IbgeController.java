package br.com.shopdosmusicos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.shopdosmusicos.client.rws.RwsIbgeClient;
import br.com.shopdosmusicos.client.rws.schema.RwsListEstadoResponse;
import br.com.shopdosmusicos.client.rws.schema.RwsListMunicipioResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ibge::Ibge")
@RestController
@RequestMapping("/ibge-dados")
public class IbgeController {

	@Autowired
	private RwsIbgeClient client;

	@PostMapping("/estados")
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<List<RwsListEstadoResponse>> getEstados() {
		List<RwsListEstadoResponse> estados = client.getEstados();
		return ResponseEntity.ok(estados);
	}

	@PostMapping("/municipios/{estado}")
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<List<RwsListMunicipioResponse>> getMunicipios(@PathVariable String estado) {
		List<RwsListMunicipioResponse> municipios = client.getMunicipiosByUf(estado);
		return ResponseEntity.ok(municipios);
	}

}
