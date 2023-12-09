package com.example.lesson362;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class PaymentsProxy {

    private final RestTemplate restTemplate;

    @Value("${name.service.url}")
    private String paymentServiceUrl;

    public PaymentsProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Payment createPayment(Payment payment){
        String uri = paymentServiceUrl + "/payment";
        HttpHeaders headers = new HttpHeaders();
        headers.add("requestId", UUID.randomUUID().toString());
        HttpEntity<Payment> paymentHttpEntity = new HttpEntity<>(payment, headers);
        ResponseEntity<Payment> response = restTemplate.exchange(uri,
                HttpMethod.POST, paymentHttpEntity, Payment.class);
        return response.getBody();
    }
}
