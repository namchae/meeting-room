package me.namchae.meetingroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket bookingGroup() {
        return buildGroup("예약 API", "me.namchae.meetingroom.booking", "예약 API", "회의실 예약 관련 API 정의");
    }

    private Docket buildGroup(String groupName, String packages, String apiName, String apiDesc) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(packages))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .apiInfo(new ApiInfo(apiName, apiDesc, "", "", ApiInfo.DEFAULT_CONTACT, "", "", new ArrayList<>()));
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null)
                .build();
    }
}