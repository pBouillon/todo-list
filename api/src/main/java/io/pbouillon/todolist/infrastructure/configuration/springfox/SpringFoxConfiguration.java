package io.pbouillon.todolist.infrastructure.configuration.springfox;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Configuration class to configure the Swagger UI and the Swagger documentation
 */
@Configuration
public class SpringFoxConfiguration {

    /**
     * Package in which the controllers are defined
     */
    private final static String controllersPackage = "io.pbouillon.todolist.presentation.controllers";

    /**
     * Expose controllers and endpoints to be displayed by Swagger
     * @return The associated Java Bean
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(controllersPackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Specify the Swagger UI information do be displayed
     * @return The associated Java Bean
     */
    private ApiInfo apiInfo() {
        Contact author = new Contact("Pierre Bouillon", "https://pbouillon.github.io", "PierreBouillon@duck.com");

        return new ApiInfo(
                "TODO list REST API",
                "Simple and unambitious TODO list API.",
                "API TOS",
                "Terms of service",
                author,
                "License of API", "about:blank", Collections.emptyList());
    }

}
