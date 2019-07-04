package com.powerreviews.project;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!prod")
public class SwaggerConfig {
    @SuppressWarnings("unchecked")
    @Bean
    public Docket api() {

        final ApiInfoBuilder infoBuilder = new ApiInfoBuilder();
        infoBuilder.title("PowerReviews - Restaurants API");

        final Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(infoBuilder.build()).select().apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(Predicates.or(PathSelectors.regex("/error"), PathSelectors.regex("/health.*"),
                        PathSelectors.regex("/info")))) // Exclude Spring error controllers
                .build();
        return docket;
    }
}
