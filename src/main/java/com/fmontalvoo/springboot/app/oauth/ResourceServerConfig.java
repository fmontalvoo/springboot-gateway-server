package com.fmontalvoo.springboot.app.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
//import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//@Configuration
//@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/v1/oauth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/productos", "/api/v1/items").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/usuarios/{id}", "/api/v1/productos/{id}", "/api/v1/items/{id}").hasAnyRole("ADMIN", "USER")
				.antMatchers("/api/v1/usuarios/**", "/api/v1/productos/**", "/api/v1/items/**").hasRole("ADMIN")
				.anyRequest().authenticated();
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("0a780975e7e3e9baf1f9b616b34fd542");
		return converter;
	}

}
