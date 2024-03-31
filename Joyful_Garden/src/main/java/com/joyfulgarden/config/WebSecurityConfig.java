package com.joyfulgarden.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.joyfulgarden.service.AuthMembersDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Bean
	public AuthMembersDetailsService membersDetailService() {
		return new AuthMembersDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bcEncrypt, AuthMembersDetailsService membersDetailService) throws Exception{
		return http
			   .getSharedObject(AuthenticationManagerBuilder.class)
			   .userDetailsService(membersDetailService)
			   .passwordEncoder(bcEncrypt)
			   .and()
			   .build();
	}


	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http
		  .authorizeHttpRequests()
		  
		  .requestMatchers(HttpMethod.GET,"/admin/**").permitAll()
		  .requestMatchers(HttpMethod.GET,"/user/**").permitAll()
		  .requestMatchers(HttpMethod.GET).permitAll()
		  
		  .requestMatchers(HttpMethod.POST,"/admin/**").permitAll()
		  .requestMatchers(HttpMethod.POST,"/user/**").permitAll()
		  .requestMatchers(HttpMethod.POST).permitAll()
		  
		  .requestMatchers(HttpMethod.PUT,"/admin/**").permitAll()
		  .requestMatchers(HttpMethod.PUT,"/user/**").permitAll()
		  .requestMatchers(HttpMethod.PUT).permitAll()
		  
		  .requestMatchers(HttpMethod.DELETE,"/admin/**").permitAll()
		  .requestMatchers(HttpMethod.DELETE,"/user/**").permitAll()
		  .requestMatchers(HttpMethod.DELETE).permitAll()
		  
		  .anyRequest().authenticated()
		  .and()
		  .rememberMe().tokenValiditySeconds(86400).key("rememberMe-key")
		  .and()
		  .csrf().disable()
		  .cors().disable()
		  .formLogin().loginPage("/user/loginPage")
		  .defaultSuccessUrl("/homePage")
		  .and()
		  .build();
	
        		
        		
    }
}

