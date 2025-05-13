package br.com.mercadoinstrumental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MercadoInstrumentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadoInstrumentalApplication.class, args);
	}

}
