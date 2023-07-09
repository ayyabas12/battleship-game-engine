package com.nl.abn.bt.config.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket component() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Login verify and country details")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nl.abn.bt"))
                .paths(PathSelectors.ant("/api/frontend-service/**"))
                .build().apiInfo(componentInfo());
    }

    private ApiInfo componentInfo() {
        return new ApiInfoBuilder().title("This controller get and save game details")
                .description("This controller used to verify the game details")
                .version("0.0.1")
                .build();
    }



}