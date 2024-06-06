package com.example.votacao;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	
    @Bean
    OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Votação Sicredi")
                                 .description("API para votação de pautas do Sicredi")
                                 .version("1.0"));
    }
}