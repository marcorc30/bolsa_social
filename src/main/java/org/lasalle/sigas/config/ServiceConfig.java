package org.lasalle.sigas.config;

import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.service.UnidadeService;
import org.lasalle.sigas.storage.local.ArquivoStorage;
import org.lasalle.sigas.storage.local.ArquivoStorageLocal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@ComponentScan(basePackageClasses = UnidadeService.class)
public class ServiceConfig {

	@Bean
	public ArquivoStorage arquivoStorage() {
		return new ArquivoStorageLocal();
	}

	@Bean
//	@Scope(
//		value= WebApplicationContext.SCOPE_SESSION,
//		proxyMode = ScopedProxyMode.TARGET_CLASS)
	@SessionScope
	public DadosIniciais dadosIniciais() {
		return new DadosIniciais();
	}
}
