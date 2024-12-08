package com.backend.allreva.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityRequirement developerRequirement = new SecurityRequirement().addList("USER");

        Server server1 = new Server();
        server1.setUrl("https://allreva.shop");

        Server server2 = new Server();
        server2.setUrl("http://localhost:8080");

        return new OpenAPI()
                .info(new Info().title("AllReva"))
                .servers(List.of(server1, server2))
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
