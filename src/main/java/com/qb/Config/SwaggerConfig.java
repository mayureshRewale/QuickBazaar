package com.qb.Config;

//import org.reactivestreams.Publisher;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.config.ResourceHandlerRegistry;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
//@Configuration
public class SwaggerConfig {

    private String swagger2BasePackage = "com.qb";

//    private String swagger2ApiTitle = "QB APIs";
//
//    private String swagger2ApiDescription = "QB APIs made for communication with backend";
//
//    private String swagger2ContactName = "QuickBazaar";
//
//    private String swagger2ContactUrl = "";
//
//    private String swagger2ContactEmail = "";
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/swagger-ui.html**")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .genericModelSubstitutes(Mono.class, Flux.class, Publisher.class)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(this.swagger2BasePackage))
//                .paths(PathSelectors.any())
//                .build().apiInfo(apiEndPointsInfo());
//    }
//
//    private ApiInfo apiEndPointsInfo() {
//        return new ApiInfoBuilder().title(this.swagger2ApiTitle)
//                .description(this.swagger2ApiDescription)
//                .contact(new Contact(this.swagger2ContactName, this.swagger2ContactUrl, this.swagger2ContactEmail))
//                .build();
//    }

}
