package br.com.mercadoinstrumental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShopDosMusicosInitialApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopDosMusicosInitialApplication.class, args);
	}

}
