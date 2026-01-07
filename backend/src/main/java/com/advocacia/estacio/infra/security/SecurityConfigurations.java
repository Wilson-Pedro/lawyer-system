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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.cors(cors -> {})
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frame -> frame.disable()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/h2-console/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/atores/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/atores/**").hasRole("ADMIN")

						.requestMatchers(HttpMethod.POST, "/advogados/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/advogados/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/advogados/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/advogados/buscarId/email/{email}").hasRole("ADVOGADO")

						.requestMatchers(HttpMethod.POST, "/assistidos/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/assistidos/buscar/{nome}").hasRole("ADMIN")

						//.requestMatchers(HttpMethod.GET, "/demandas/**").hasRole("ESTAGIARIO")
						.requestMatchers(HttpMethod.PATCH, "/demandas/{demandaId}/change").hasRole("PROFESSOR")
						.requestMatchers(HttpMethod.POST, "/demandas/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/demandas/estagiario/{estagiarioId}").hasRole("ESTAGIARIO")
						.requestMatchers(HttpMethod.GET, "/demandas/advogado/{advogadoId}").hasRole("ADVOGADO")
						.requestMatchers(HttpMethod.GET, "/demandas/status/{demandaStatus}").hasRole("ESTAGIARIO")
						.requestMatchers("/demandas/responde/**").hasRole("PROFESSOR")

						.requestMatchers(HttpMethod.POST, "/estagiarios/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/estagiarios/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/estagiarios/buscar/{nome}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/estagiarios/buscarId/email/{email}").permitAll()

						.requestMatchers(HttpMethod.POST, "/movimentos/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/movimentos/buscar/{numeroDoProcesso}").hasRole("ADMIN")

						.requestMatchers(HttpMethod.POST, "/processos/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/statusDoProcesso/{processoStatus}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/buscar/{numeroDoProcesso}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/numeroDoProcesso/{numeroDoProcesso}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/processos/{id}").hasRole("ADMIN")
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
	
	@Bean
	WebMvcConfigurer corsConfig() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000")
				.allowedMethods("*")
				.allowCredentials(true);
			}
		};
	}
}
