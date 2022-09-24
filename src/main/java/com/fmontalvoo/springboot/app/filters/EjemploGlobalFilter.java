package com.fmontalvoo.springboot.app.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class EjemploGlobalFilter implements GlobalFilter {

	private static final Logger log = LoggerFactory.getLogger(EjemploGlobalFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Ejecucion pre fliter");
		exchange.getRequest().mutate().headers(header -> header.add("token", "1234567"));
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			log.info("Ejecucion post fliter");
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(value -> {
				exchange.getResponse().getHeaders().add("token", value);
			});
//			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "azul").build());
//			exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

}
