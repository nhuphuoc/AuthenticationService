package org.example.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] AUTH_WHITELIST = {
					"/swagger-resources",
					"/swagger-resources/**",
					"/configuration/ui",
					"/configuration/security",
					"/swagger-ui.html",
					"/webjars/**",
					"/v3/api-docs/**",
					"/api/public/**",
					"/api/public/authenticate",
					"/actuator/*",
					"/swagger-ui/**",
					"/book-lib/api/public/**",
					"/auth/**",
					"/internal/**"
	};
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
						.csrf(csrf -> csrf.disable())
						.authorizeRequests(authorizeRequests ->
										authorizeRequests
														.requestMatchers(AUTH_WHITELIST).permitAll() // Allow access to auth endpoints
														.anyRequest().authenticated() // Protect other endpoints
						)
						.sessionManagement(sessionManagement ->
										sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session management
						);

		return http.build();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder =
						http.getSharedObject(AuthenticationManagerBuilder.class);
		return authenticationManagerBuilder.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}