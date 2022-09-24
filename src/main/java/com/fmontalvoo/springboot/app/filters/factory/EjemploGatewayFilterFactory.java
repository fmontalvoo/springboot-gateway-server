package com.fmontalvoo.springboot.app.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory
		extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {

	private static final Logger log = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
	}

	@Override
	public GatewayFilter apply(Configuracion config) {
		return (exchange, chain) -> {
			log.info("Ejecucion gateway pre fliter factory: ".concat(config.mensaje));
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {

				Optional.ofNullable(config.valorCookie).ifPresent(valor -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.nombreCookie, valor).build());
				});

				log.info("Ejecucion gateway post fliter factory: ".concat(config.mensaje));

			}));
		};
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("mensaje", "nombreCookie", "valorCookie");
	}

	public static class Configuracion {
		private String mensaje;
		private String nombreCookie;
		private String valorCookie;

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		public String getNombreCookie() {
			return nombreCookie;
		}

		public void setNombreCookie(String nombreCookie) {
			this.nombreCookie = nombreCookie;
		}

		public String getValorCookie() {
			return valorCookie;
		}

		public void setValorCookie(String valorCookie) {
			this.valorCookie = valorCookie;
		}

	}

}
