package com.greengear;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication

public class ApiGatwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatwayApplication.class, args);
	}

	
	// config the react interaction
	 @Bean
	     CorsWebFilter corsWebFilter() {
	        CorsConfiguration corsConfig = new CorsConfiguration();
	        corsConfig.setAllowedOriginPatterns(List.of("http://localhost:3000"));
	        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
	        corsConfig.setAllowedHeaders(List.of("*"));
	        corsConfig.setAllowCredentials(true); // Allows cookies/authentication

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", corsConfig);

	        return new CorsWebFilter(source);
	    }
	
	
}
