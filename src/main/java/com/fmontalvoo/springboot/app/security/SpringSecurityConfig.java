package com.fmontalvoo.springboot.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity security) {
		return security.authorizeExchange().pathMatchers("/api/v1/oauth/**").permitAll()
				.pathMatchers(HttpMethod.GET, "/api/v1/productos", "/api/v1/items").permitAll()
				.pathMatchers(HttpMethod.GET, "/api/v1/usuarios/{id}", "/api/v1/productos/{id}",
						"/api/v1/items/{id}/{cantidad}")
				.hasAnyRole("ADMIN", "USER")
				.pathMatchers("/api/v1/usuarios/**", "/api/v1/productos/**", "/api/v1/items/**").hasRole("ADMIN")
				.anyExchange().authenticated().and().addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION).csrf()
				.disable().build();
	}

}
