package br.com.mercadoinstrumental.client.rws;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.mercadoinstrumental.client.rws.schema.RwsLytexAuthReq;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexAuthResponse;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentReq;
import br.com.mercadoinstrumental.client.rws.schema.RwsLytexPaymentResponse;

@FeignClient(name = "RwsLytexClient", url = "${clients.rws-lytex.url}")
public interface RwsLytexClient {

    @PostMapping("/auth/obtain_token")
    RwsLytexAuthResponse obtainToken(@RequestBody RwsLytexAuthReq req);

    @PostMapping("/payment_links")
    RwsLytexPaymentResponse createPayment(@RequestBody RwsLytexPaymentReq req,  @RequestHeader("Authorization") String token);
}
