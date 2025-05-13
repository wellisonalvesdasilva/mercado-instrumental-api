package br.com.mercadoinstrumental.controller.admin.anuncio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.mercadoinstrumental.controller.admin.imagem.schema.ArtefatoAnuncioResponse;
import br.com.mercadoinstrumental.controller.commom.schema.RwsArtefatoResponse;
import br.com.mercadoinstrumental.domain.model.anuncio.ArtefatoAnuncio;
import br.com.mercadoinstrumental.manager.admin.anuncio.ArtefatoAnuncioManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;

@Tag(name = "Anuncio::ImagemAnuncio")
@RestController
@RequestMapping("imagens")
public class ArtefatoAnuncioController {


	@Autowired
	private ArtefatoAnuncioManager artefatoAnuncioManager;
	
	
    @GetMapping(path = {"{idAnuncio}"})
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
    public ResponseEntity<List<ArtefatoAnuncioResponse>> findAllByAnuncio(
    		@PathVariable Long idAnuncio) {
        return ResponseEntity.ok(artefatoAnuncioManager.findAllByAnuncio(idAnuncio));
    }
    
	@PostMapping(path = "{idArtefato}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<Long> uploadArtefato(
			@PathVariable @NotNull Long idArtefato,
			@RequestParam @NotNull MultipartFile arquivo) {
		ArtefatoAnuncio imagemAnuncio = artefatoAnuncioManager.upload(idArtefato, arquivo);
		return ResponseEntity.ok(imagemAnuncio.getId());
	}
	    
	
    @PostMapping(path = "{idAnuncio}/download/{idArtefato}")
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
    public ResponseEntity<RwsArtefatoResponse> downloadArtefato(@PathVariable @NotNull Long idArtefato) {
        return ResponseEntity.ok(artefatoAnuncioManager.download(idArtefato));
    }


	
}
