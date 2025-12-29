package com.example.demo.consumer;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/consumer/servers")
    public String consumeMiddleService() {
        return restTemplate.getForObject(
                "http://localhost:8081/middle/servers",
                String.class
        );
    }
}
