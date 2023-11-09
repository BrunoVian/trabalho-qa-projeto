package br.com.SecurityProfit.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;


@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.SecurityProfit.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(apiKey()))
                .consumes(new HashSet<>(Arrays.asList("multipart/form-data")))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Security Profit",
                "Projeto Integrador - Curso Superior em Análise e Desenvolvimento de Sistemas",
                "1.0",
                "",
                new springfox.documentation.service.Contact("Bruno Vian | Wyllian Martinez", "URL do seu site", ""),
                "",
                "",
                Collections.emptyList()
        );
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(defaultAuth()))
                .build();
    }

    private SecurityReference defaultAuth() {
        return SecurityReference.builder()
                .scopes(new springfox.documentation.service.AuthorizationScope[0])
                .reference("Authorization")
                .build();
    }
}