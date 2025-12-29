package com.example.demo.middle;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.ws.client.core.WebServiceTemplate;
@Configuration
public class SoapClientConfig {

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        return new WebServiceTemplate();
    }
}
