package br.com.shopdosmusicos.controller.admin.anuncio;

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

import br.com.shopdosmusicos.controller.admin.imagem.schema.ImagemAnuncioResponse;
import br.com.shopdosmusicos.controller.commom.schema.RwsDocumentoResponse;
import br.com.shopdosmusicos.manager.anuncio.ImagemAnuncioManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;

@Tag(name = "Anuncio::ImagemAnuncio")
@RestController
@RequestMapping("imagens-anuncio")
public class ImagemAnuncioController {


	@Autowired
	private ImagemAnuncioManager imagemAnuncioManager;
	
	
    @GetMapping(path = {"{idAnuncio}"})
	@PreAuthorize("hasAnyRole('ANUNCIANTE', 'ADMINISTRADOR')")
    public ResponseEntity<List<ImagemAnuncioResponse>> findAllByAnuncio(
    		@PathVariable Long idAnuncio) {
        return ResponseEntity.ok(imagemAnuncioManager.findAllByAnuncio(idAnuncio));
    }
    
	@PostMapping(path = "{idAnuncio}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasAnyRole('ANUNCIANTE', 'ADMINISTRADOR')")
	public ResponseEntity<Boolean> uploadDocumento(
			@PathVariable @NotNull Long idAnuncio,
			@RequestParam @NotNull MultipartFile[] arquivo) {
		return ResponseEntity.ok(imagemAnuncioManager.upload(idAnuncio, arquivo));
	}
	    
	
    @PostMapping(path = "{idAnuncio}/download/{idArtefato}")
    @PreAuthorize("hasAnyRole('ANUNCIANTE', 'ADMINISTRADOR')")
    public ResponseEntity<RwsDocumentoResponse> downloadDocumento(@PathVariable @NotNull Long idArtefato) {
        return ResponseEntity.ok(imagemAnuncioManager.download(idArtefato));
    }


	
}
