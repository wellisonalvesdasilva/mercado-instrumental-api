package br.com.mercadoinstrumental.controller.site.anuncio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.controller.commom.schema.ResponsePagedCommom;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioListSiteResponse;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioSiteFilter;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioSiteResponse;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.manager.site.anuncio.AnuncioSiteManager;
import br.com.mercadoinstrumental.repository.anuncio.AnuncioRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Anuncio::Site")
@RestController
@RequestMapping("anuncios-site")
public class AnuncioSiteController {

	@Autowired
	private AnuncioSiteManager anuncioManager;
	
	@Autowired
	private AnuncioRepository anuncioRepository;

	@GetMapping
	public ResponseEntity<ResponsePagedCommom<AnuncioListSiteResponse>> findAllAnuncio(
			@Valid AnuncioSiteFilter filtros,
			HttpServletRequest request) {
		return ResponseEntity.ok(anuncioManager.findAllAnuncioPaged(filtros, request));
	}
	
	@GetMapping(path = { "{idAnuncio}" })
	public ResponseEntity<AnuncioSiteResponse> findAnuncioById(
			@PathVariable(required=true) Long idAnuncio, HttpServletRequest request) {
		Anuncio anuncio = anuncioRepository.findById(idAnuncio).orElseThrow();
		AnuncioSiteResponse response = anuncioManager.findDetailAnuncio(anuncio, request);
		return ResponseEntity.ok(response);
	}
	

	
}
