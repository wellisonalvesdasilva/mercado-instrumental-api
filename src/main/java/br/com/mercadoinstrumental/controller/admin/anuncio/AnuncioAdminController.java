package br.com.mercadoinstrumental.controller.admin.anuncio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.controller.admin.anuncio.schema.AnuncioFilter;
import br.com.mercadoinstrumental.controller.admin.anuncio.schema.AnuncioReq;
import br.com.mercadoinstrumental.controller.admin.anuncio.schema.AnuncioResponse;
import br.com.mercadoinstrumental.controller.admin.anuncio.schema.AnuncioUpd;
import br.com.mercadoinstrumental.controller.commom.schema.ResponsePagedCommom;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.manager.admin.anuncio.AnuncioManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Anuncio::Anuncio")
@RestController
@RequestMapping("anuncios")
public class AnuncioAdminController {

	@Autowired
	private AnuncioManager anuncioManager;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<Long> createAnuncio(@Valid @RequestBody AnuncioReq req) {
		Anuncio anuncio = anuncioManager.createAnuncio(req);
		return ResponseEntity.ok(anuncio.getId());
	}
	
	@PutMapping(path = { "{idAnuncio}" })
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<Long> updateAnuncio(@Valid 
			@PathVariable(required=true) Long idAnuncio,
			@RequestBody AnuncioUpd upd) {
		Anuncio anuncio = anuncioManager.updateAnuncio(idAnuncio, upd);
		return ResponseEntity.ok(anuncio.getId());
	}
	

	@DeleteMapping(path = { "{idAnuncio}" })
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<Long> deleteAnuncio(
			@PathVariable(required=true) Long idAnuncio) {
		anuncioManager.deleteAnuncio(idAnuncio);
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping(path = { "{idAnuncio}" })
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<AnuncioResponse> findAnuncioById(
			@PathVariable(required=true) Long idAnuncio) {
		AnuncioResponse anuncio = anuncioManager.findAnuncioById(idAnuncio);
		return ResponseEntity.ok(anuncio);
	}
	

	@GetMapping
	@PreAuthorize("hasAnyRole('ANUNCIANTE', 'ADMINISTRADOR')")
	public ResponseEntity<ResponsePagedCommom<AnuncioResponse>> findAllAnuncio(
			@Valid AnuncioFilter filtros) {
		return ResponseEntity.ok(anuncioManager.findAllAnuncioPaged(filtros));
	}
	
	@GetMapping("hasAnuncioFree")
	@PreAuthorize("hasAnyRole('ANUNCIANTE', 'ADMINISTRADOR')")
	public ResponseEntity<Boolean> userHasOrHadAnuncioFree() {
		return ResponseEntity.ok(anuncioManager.userHasOrHadAnuncioFree());
	}

}
