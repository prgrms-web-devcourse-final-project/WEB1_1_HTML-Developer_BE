package com.backend.allreva.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Value("${url.back.protocol}")
    private String protocol;
    @Value("${url.back.domain}")
    private String domain;

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityRequirement developerRequirement = new SecurityRequirement().addList("USER");

        Server server1 = new Server();
        server1.setUrl(MessageFormat.format("{0}://{1}", protocol, domain));

        return new OpenAPI()
                .info(new Info().title("Allreva"))
                .servers(List.of(server1))
                .components(new Components()
                        .addSecuritySchemes("USER", createSecurityScheme()))
                .addSecurityItem(developerRequirement);
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization")
                .description("Token: oauth2 로그인을 통해 얻은 토큰을 입력하세요.")
                .in(SecurityScheme.In.HEADER);
    }
}
