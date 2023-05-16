package be.pxl.paj.testing.config;

import be.pxl.paj.testing.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	private JwtRequestFilter jwtRequestFilter;

	public SecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
		this.jwtRequestFilter = jwtRequestFilter;
	}


	@Bean
	public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
		return  http
				.csrf()
				.disable()
				.authorizeHttpRequests()
				.requestMatchers("/auth")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				//.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).build()				;

	}
}
