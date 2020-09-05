package com.greeting.api.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Value("${com.greeting.api.allowed.origins}")
    private String allowedOrigins;

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Arrays.asList(allowedOrigins));
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
