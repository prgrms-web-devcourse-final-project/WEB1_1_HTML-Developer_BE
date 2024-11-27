package com.backend.allreva.common.config;

import com.backend.allreva.auth.util.JwtTestTokenInitializer;
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

    private final JwtTestTokenInitializer jwtTestTokenInitializer;

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme userScheme = createSecurityScheme("USER", jwtTestTokenInitializer.getToken("USER"));
        SecurityScheme adminScheme = createSecurityScheme("ADMIN", jwtTestTokenInitializer.getToken("ADMIN"));
        SecurityScheme guestScheme = createSecurityScheme("GUEST", jwtTestTokenInitializer.getToken("GUEST"));

        SecurityRequirement userRequirement = new SecurityRequirement().addList("USER");
        SecurityRequirement adminRequirement = new SecurityRequirement().addList("ADMIN");
        SecurityRequirement guestRequirement = new SecurityRequirement().addList("GUEST");

        return new OpenAPI()
                .info(new Info().title("AllReva"))
                .components(new Components()
                        .addSecuritySchemes("USER", userScheme)
                        .addSecuritySchemes("ADMIN", adminScheme)
                        .addSecuritySchemes("GUEST", guestScheme))
                .addSecurityItem(userRequirement)
                .addSecurityItem(adminRequirement)
                .addSecurityItem(guestRequirement);
    }

    private SecurityScheme createSecurityScheme(String role, String token) {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization")
                .description(String.format("%s Token: %s", role, token))
                .in(SecurityScheme.In.HEADER);
    }
}
