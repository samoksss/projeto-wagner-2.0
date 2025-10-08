package com.turmaa.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// NOVO: Import necessário para a anotação @EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.turmaa.helpdesk.security.JWTAuthenticationFilter;
import com.turmaa.helpdesk.security.JWTAuthorizationFilter; 
import com.turmaa.helpdesk.security.JWTUtil;

// NOVO: Habilita o uso de @PreAuthorize (Autorização)
@EnableGlobalMethodSecurity(prePostEnabled = true) 
@Configuration 
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**", "/login" };

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JWTUtil jwtUtil;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable();
		
		// 🚨 LINHA CRÍTICA PARA O H2 CONSOLE: Permite que o console seja exibido em um iframe
        http.headers().frameOptions().disable();
		
		// 1. Define as rotas públicas (H2 e Login) e protege o resto
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
		.anyRequest().authenticated();
		
		// 2. Adiciona os filtros JWT
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		
		// 3. Define a sessão como STATELESS (sem estado)
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Define o serviço para carregar o usuário e o encoder de senha
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}