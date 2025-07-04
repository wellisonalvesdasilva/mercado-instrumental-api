package br.com.mercadoinstrumental.controller.lytex.webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.client.webhook.rws.schema.LiquidateInvoiceWebhookReq;
import br.com.mercadoinstrumental.manager.admin.anuncio.AnuncioManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Tag(name = "Webhook::Lytex")
@RestController
@RequestMapping("/webhooks")
public class WebhookLytexController {

	@Autowired
	private AnuncioManager anuncioManager;

    @PostMapping("/lytex")
	public ResponseEntity<Void> liquidar(@Valid @NotNull @RequestBody LiquidateInvoiceWebhookReq req) {
		anuncioManager.finalizeInvoicePayment(req);
		return ResponseEntity.ok().build();
	}

}
