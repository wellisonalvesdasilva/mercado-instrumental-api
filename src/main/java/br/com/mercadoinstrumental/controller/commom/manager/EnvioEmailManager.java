package br.com.mercadoinstrumental.controller.commom.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EnvioEmailManager {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${empresa.nome}")
	public String nomeEmpresa;

	
	public void enviarEmailHtml(List<String> destinatarios, String titulo, String corpoHtml) {

	    if (destinatarios == null || destinatarios.isEmpty()) {
	        throw new IllegalArgumentException("A lista de destinatários não pode ser nula ou vazia.");
	    }

	    for (String destinatario : destinatarios) {
	        try {
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

	            helper.setFrom("suporte@mercadoinstrumental.com.br", "Mercado Instrumental");

	            helper.setTo(destinatario);
	            helper.setSubject(titulo);
	            helper.setText(corpoHtml, true); // o segundo parâmetro 'true' indica que o texto é HTML

	            mailSender.send(message);
	        } catch (Exception e) {
	            System.err.println("Erro ao enviar e-mail HTML para " + destinatario + ": " + e.getMessage());
	        }
	    }
	}

	
}
