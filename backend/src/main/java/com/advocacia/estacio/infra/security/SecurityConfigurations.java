package com.advocacia.estacio.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/atores/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/advogados/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/advogados/buscar/{nome}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/assistidos/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/assistidos/buscar/{nome}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/demandas/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/estagiarios/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/estagiarios/buscar/{nome}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/movimentos/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/movimentos/buscar/{numeroDoProcesso}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/processos/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/statusDoProcesso/{processoStatus}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/buscar/{numeroDoProcesso}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/numeroDoProcesso/{numeroDoProcesso}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/processos/{id}").hasRole("ADMIN")
						//.requestMatchers(HttpMethod.POST, "/estagiarios").hasRole("ADMIN")
						.anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passworddEncoder() {
		return new BCryptPasswordEncoder();
	}
}
