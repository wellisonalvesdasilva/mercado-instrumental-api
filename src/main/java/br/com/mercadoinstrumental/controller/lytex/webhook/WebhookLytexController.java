package br.com.mercadoinstrumental.controller.lytex.webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.client.webhook.rws.schema.LiquidateInvoiceWebhookReq;
import br.com.mercadoinstrumental.controller.commom.schema.ResponsePagedCommom;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioListSiteResponse;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioSiteFilter;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioSiteResponse;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.EnvioEmailSiteReq;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.manager.admin.anuncio.AnuncioManager;
import br.com.mercadoinstrumental.repository.anuncio.AnuncioRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Webhook::Lytex")
@RestController
@RequestMapping("webhook")
public class WebhookLytexController {

	@Autowired
	private AnuncioManager anuncioManager;

	@PostMapping
	public ResponseEntity<Void> liquidar(@RequestBody LiquidateInvoiceWebhookReq req) {
		anuncioManager.finalizeInvoicePayment(req);
		return ResponseEntity.ok().build();
	}

}
