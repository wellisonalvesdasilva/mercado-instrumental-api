package br.com.mercadoinstrumental.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.mercadoinstrumental.manager.admin.anuncio.AnuncioManager;

@Component
public class AnuncioScheduler {

	@Autowired
	AnuncioManager anuncioManager;

	@Scheduled(cron = "0 0 0 * * *")
	public void executarExpiracaoDiaria() {
		anuncioManager.expirarAnuncios();
	}

}
