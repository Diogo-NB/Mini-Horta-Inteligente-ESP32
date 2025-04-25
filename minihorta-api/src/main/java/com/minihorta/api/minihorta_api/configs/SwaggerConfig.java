package com.minihorta.api.minihorta_api.configs;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	GroupedOpenApi swaggerOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("public")
			.pathsToMatch("/**")
			.build();
	}
}
