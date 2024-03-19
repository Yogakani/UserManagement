package com.yoga.usermanagement.service;

import com.yoga.usermanagement.domain.response.MobileNumLookupResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VeriphoneService {

    @Value("${veriphone.key}")
    private String key;

    private WebClient webClient;

    @PostConstruct
    public void createWebClient() {
        String baseUrl = "https://api.veriphone.io/";
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public MobileNumLookupResponse verify(long phone) {

        return this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v2/verify")
                            .queryParam("phone", phone)
                            .queryParam("key", key)
                            .build())
                    .retrieve()
                    .bodyToMono(MobileNumLookupResponse.class)
                    .onErrorResume(e -> Mono.empty())
                    .block();
    }
}
