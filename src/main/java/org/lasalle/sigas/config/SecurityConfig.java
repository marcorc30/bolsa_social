package org.lasalle.sigas.config;

import org.lasalle.sigas.security.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
//		auth.inMemoryAuthentication()
//			.withUser("social@lasalle.org.br").password("123456").roles("SAS")
//		.and()
//			.withUser("usuario@gmail.com").password("123456").roles("USER");
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
			
		
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring()
			.antMatchers("/layout/**")
			.antMatchers("/images/**")
			.antMatchers("/stylesheets/**")
			.antMatchers("/javascripts/**")
			.antMatchers("/solicitante/acesso")
			.antMatchers("/solicitante/alunoRede")
			.antMatchers("/solicitante/alunoExterno")
			.antMatchers("/solicitante/termosEdital")
			.antMatchers("/solicitante/pesquisarAluno")
			.antMatchers("/solicitante/disparaMensagem")
			.antMatchers("/solicitante/cadastrarAlunoExterno")
			.antMatchers("/usuarios/altera-senha-externa/**")
//			.antMatchers("/usuarios/altera-senha")
			.antMatchers("/sucesso")
			.antMatchers("/alterar-senha-login")
			.antMatchers("/usuarios/solicitar-alteracao-senha");
		
		
		
		
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/usuarios/novo").hasRole("CADASTRAR_USUARIO")
			.antMatchers("/processos/novo").hasRole("CADASTRAR_PROCESSO_SELETIVO")
			.antMatchers("/detalhesProcessos/novo").hasRole("CADASTRAR_SERIES_PROCESSO_SELETIVO")
			.antMatchers("/candidatos/novo").hasRole("CADASTRAR_CANDIDATO")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/", true)
			.and()
		.exceptionHandling()
			.accessDeniedPage("/403")
			.and()
		.csrf().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 	
}
