package it.globus.finaudit.config;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
            .components(new Components()
              .addSecuritySchemes(securitySchemeName, createSecurityScheme()))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .info(new Info()
              .title("Finance audit API")
              .version("1.0")
              .description("Описание API"));
    }

    private SecurityScheme createSecurityScheme() {
      return new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");
    }
}
