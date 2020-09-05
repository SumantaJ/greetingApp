package com.greeting.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration extends WebMvcConfigurationSupport {
	
	@Value("${swagger.api.title}")
    private String title;

    @Value("${swagger.api.description}")
    private String description;

    @Value("${swagger.api.version}")
    private String version;

    @Value("${swagger.api.controller.basepackage}")
    private String basePackage;
    
    @Value("${swagger.ui.html.name}")
    private String htmlTemplate;
    
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.ant("/**")).build().apiInfo(metaData());
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(htmlTemplate).addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title(title).description(description).version(version).build();
	}
}
