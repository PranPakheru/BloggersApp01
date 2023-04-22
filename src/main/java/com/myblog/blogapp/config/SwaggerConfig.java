package com.myblog.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){

        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
                .securitySchemes(Arrays.asList(apiKeys())).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
        return docket;
    }

    private ApiInfo getInfo(){

        return new ApiInfo("BloggerApp : Backend api documentation", "This documentation is created by Pranav",
                "1.0", "Terms of service", new Contact("Pranav", "", "pranav.shahu.ps@gmail.com"),
                "License of api", "License url", Collections.emptyList());
    }


    //below 3 things will be passed into the api abve.
    private List<SecurityContext> securityContexts(){

        List<SecurityContext> secContexts = Arrays.asList(SecurityContext.builder().securityReferences(securityReference()).build());
        return secContexts;
    }

    private List<SecurityReference> securityReference(){

        AuthorizationScope scope = new AuthorizationScope("global", "Access Everything");
        List<SecurityReference> jwt = Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[]{scope}));
        return jwt;
    }

    private ApiKey apiKeys(){

        ApiKey apiKey = new ApiKey("JWT", "Authorization", "beared");
        return apiKey;
    }
}
