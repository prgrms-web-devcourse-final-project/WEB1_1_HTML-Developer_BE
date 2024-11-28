package com.backend.allreva.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityRequirement developerRequirement = new SecurityRequirement().addList("USER");

        return new OpenAPI()
                .info(new Info().title("AllReva"))
                .components(new Components()
                        .addSecuritySchemes("Auth", createSecurityScheme()))
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
