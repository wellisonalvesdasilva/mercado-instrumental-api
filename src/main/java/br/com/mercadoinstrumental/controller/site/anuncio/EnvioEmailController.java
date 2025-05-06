package br.com.mercadoinstrumental.controller.site.anuncio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.controller.site.anuncio.schema.EnvioEmailSiteReq;
import br.com.mercadoinstrumental.manager.site.anuncio.AnuncioSiteManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "DisparoEmail::Site")
@RestController
@RequestMapping("/disparo-email-site")
public class EnvioEmailController {

	@Autowired
	private AnuncioSiteManager anuncioManager;

    @PostMapping
    public ResponseEntity<Void> envioMensagem(
    		@Valid @RequestBody EnvioEmailSiteReq req) {
        anuncioManager.envioMensagem(req);
        return ResponseEntity.ok().build();
    }

   
  }
