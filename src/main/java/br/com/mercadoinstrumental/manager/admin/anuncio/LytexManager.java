package br.com.mercadoinstrumental.manager.admin.anuncio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.mercadoinstrumental.client.rws.RwsLytexClient;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexAuthReq;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexAuthResponse;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentItemReq;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentPaymentItemReq;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentRedirectReq;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentReq;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentResponse;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentTypeBoletoReq;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentTypeCreditCardReq;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentTypePixReq;
import br.com.mercadoinstrumental.controller.commom.manager.EnvioEmailManager;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.repository.anuncio.AnuncioRepository;
import jakarta.transaction.Transactional;

@Service
@Validated
public class LytexManager {

	@Autowired
	private RwsLytexClient client;

	@Autowired
	private AnuncioRepository anuncioRepository;

	@Autowired
	private EnvioEmailManager envioEmailManager;

	@Value("${clients.rws-lytex.id}")
	private String clientId;

	@Value("${clients.rws-lytex.secret}")
	private String secret;

	@Value("${clients.rws-lytex.urlBase}")
	private String urlBaseLytex;

	@Value("${aplicacao-web}")
	private String urlSite;

	@Transactional
	public void createPaymentLytex(Anuncio anuncio) {
		RwsLytexAuthResponse resultToken = client.obtainToken(new RwsLytexAuthReq(clientId, secret));
		RwsLytexPaymentResponse resultPayment = client.createPayment(createRequestLytexPayment(anuncio), "Bearer " + resultToken.getAccessToken());
		anuncio.setIdPagamentoLytex(resultPayment.get_id());
		anuncio.setHashIdPagamentoLytex(resultPayment.get_hashId());
		anuncioRepository.save(anuncio);
		enviarLinkPorEmailAsync(anuncio);
	}

	private RwsLytexPaymentReq createRequestLytexPayment(Anuncio anuncio) {
		List<RwsLytexPaymentPaymentItemReq> items = List.of(new RwsLytexPaymentPaymentItemReq(anuncio.getTitulo(), anuncio.getTipoPlano().getPrice(), String.valueOf(1)));
		RwsLytexPaymentItemReq paymentMethods = new RwsLytexPaymentItemReq(new RwsLytexPaymentTypePixReq(true), new RwsLytexPaymentTypeBoletoReq(true), new RwsLytexPaymentTypeCreditCardReq(false, 0));
		RwsLytexPaymentRedirectReq redirect = new RwsLytexPaymentRedirectReq(String.format(urlSite + "/" + "%s", anuncio.getId()), true);
		RwsLytexPaymentReq dto = new RwsLytexPaymentReq(anuncio.getTitulo(), anuncio.getTipoPlano().getPrice(), anuncio.getId().toString(), items, paymentMethods, redirect);
		return dto;
	}

	
	@Async
	private void enviarLinkPorEmailAsync(Anuncio anuncio) {
		
		String linkPagamento = urlBaseLytex + "/" + anuncio.getHashIdPagamentoLytex();
		String corpo = String.format(
				"""
						<html>
						<body style="font-family: Arial, sans-serif; color: #333; line-height: 1.6;">
						    <h2 style="color: #007bff;">Pagamento do seu anúncio</h2>
						    <p>Olá <strong>%s</strong>,</p>
						    <p>Seu anúncio <strong>“%s”</strong> está quase no ar! Para ativá-lo, finalize o pagamento.</p>
						    <p>Você pode pagar via <strong>Pix</strong>, <strong>Cartão de Crédito</strong> ou <strong>Boleto Bancário</strong>. É simples, rápido e seguro.</p>
						    <p><em>Importante:</em> após o pagamento, o sistema levará algumas horas para reconhecer e, em seguida, seu anúncio será enviado para revisão pelos administradores.</p>
						    <p>Somente após a aprovação deles, o anúncio será publicado efetivamente no site.</p>
						    <p style="text-align: center; margin: 30px 0;">
						        <a href="%s" target="_blank"
						           style="background-color: #28a745; color: white; padding: 12px 24px;
						                  text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 16px;">
						            Clique aqui para pagar agora
						        </a>
						    </p>
						    <p>Se tiver qualquer dúvida, fale com a gente:
						        <a href="mailto:mercadoinstrumental@gmail.com">mercadoinstrumental@gmail.com</a>
						    </p>
						    <hr style="border: none; border-top: 1px solid #eee; margin-top: 40px;">
						    <p style="font-size: 12px; color: #999;">Mercado Instrumental - Seu marketplace de instrumentos musicais</p>
						</body>
						</html>
						""",
				anuncio.getUsuario().getNome(), anuncio.getTitulo(), linkPagamento);

		envioEmailManager.enviarEmailHtml(List.of(anuncio.getUsuario().getEmail()), "Finalize o pagamento do seu anúncio - Mercado Instrumental", corpo);
	}

}
