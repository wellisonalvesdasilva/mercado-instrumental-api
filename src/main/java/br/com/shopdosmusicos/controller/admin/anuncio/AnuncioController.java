package br.com.shopdosmusicos.controller.admin.anuncio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.shopdosmusicos.controller.admin.anuncio.schema.AnuncioReq;
import br.com.shopdosmusicos.domain.model.anuncio.Anuncio;
import br.com.shopdosmusicos.manager.anuncio.AnuncioManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Anuncio::Anuncio")
@RestController
@RequestMapping("anuncios")
public class AnuncioController {

	@Autowired
	private AnuncioManager anuncioManager;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Long> createAnuncio(@Valid @RequestBody AnuncioReq req) {
		Anuncio anuncio = anuncioManager.createAnuncio(req);
		return ResponseEntity.ok(anuncio.getId());
	}
	
	/*@PutMapping(path = { "{idCategoria}" })
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Long> updateCategoria(@Valid 
			@PathVariable(required=true) Long idCategoria,
			@RequestBody CategoriaUpd upd) {
		InstrumentoMusical categoria = categoriaManager.updateCategoria(idCategoria, upd);
		return ResponseEntity.ok(categoria.getId());
	}
	
	@DeleteMapping(path = { "{idCategoria}" })
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<Long> deleteCategoria(@PathVariable(required=true) Long idCategoria) {
		categoriaManager.deleteCategoria(idCategoria);
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping(path = { "{idCategoria}" })
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<CategoriaResponse> findCategoriaById(
			@PathVariable(required=true) Long idCategoria) {
		CategoriaResponse categoria = categoriaManager.findCategoriaById(idCategoria);
		return ResponseEntity.ok(categoria);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<ResponsePagedCommom<CategoriaResponse>> findAllCategoria(
			@Valid CategoriaFilter filtros) {
		return ResponseEntity.ok(categoriaManager.findAllCategoria(filtros));
	}*/
	
}
