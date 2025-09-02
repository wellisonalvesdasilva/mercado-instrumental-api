package br.com.mercadoinstrumental.controller.commom.manager;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.mercadoinstrumental.client.rws.MailgunClient;

@Service
public class EnvioEmailManager {

    @Value("${mailgun.api.key}")
    private String mailgunApiKey;

    @Value("${mailgun.domain}")
    private String mailgunDomain;

    @Value("${mailgun.sender}")
    private String remetente;

    private final MailgunClient mailgunClient;

    public EnvioEmailManager(MailgunClient mailgunClient) {
        this.mailgunClient = mailgunClient;
    }

    public void enviarEmailHtml(List<String> destinatarios, String titulo, String corpoHtml) {
        if (destinatarios == null || destinatarios.isEmpty()) {
            throw new IllegalArgumentException("A lista de destinatários não pode ser nula ou vazia.");
        }

        String authHeader = "Basic " + Base64.getEncoder().encodeToString(("api:" + mailgunApiKey).getBytes(StandardCharsets.UTF_8));
        for (String destinatario : destinatarios) {
            try {
                MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
                form.add("from", "Mercado Instrumental <" + remetente + ">");
                form.add("to", destinatario);
                form.add("subject", titulo);
                form.add("html", corpoHtml);

                ResponseEntity<String> response = mailgunClient.enviarEmail(authHeader, mailgunDomain, form);

                if (!response.getStatusCode().is2xxSuccessful()) {
                    System.err.println("Falha ao enviar e-mail para " + destinatario + ". Código: " + response.getStatusCode());
                }

            } catch (Exception e) {
                System.err.println("Erro ao enviar e-mail para " + destinatario + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
