package com.project.TwitterClone.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class Appconfig {
	
	
	//for security configuration previously we use webSecurityconfigurerAdapter
	//but now it is deprecated in newer versions we make it more modular by providing 
	//securityfilterchain
	
	
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		//for managing the session and make it stateless
		.authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated()
				.anyRequest().permitAll())
		
		//ek cheez dhyan rakhna hai for making endpoints that it startv from /api/url other wise permit all 
		//ho jayega
		
		.addFilterBefore(new JwtTokenValidator(),BasicAuthenticationFilter.class)// 
		.csrf().disable()
		.cors().configurationSource(corsConfigurationSource()).and()
		
		//hmare backend ko kaun kaun se frontend url accesss kr ske ye batayega
		
		.httpBasic().and()
		.formLogin();
		
		return http.build();
	}
    
	private CorsConfigurationSource corsConfigurationSource() {
		
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cfg =new  CorsConfiguration();
				cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.setMaxAge(3600L);
				return cfg;
				
			}
		};
		
	}
	
	//it save the encrypted  password in the database
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
