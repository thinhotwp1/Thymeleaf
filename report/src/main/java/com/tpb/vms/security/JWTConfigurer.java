/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tpb.vms.client.TokenClient;

/**
 *
 * @author Viet Do-Van
 */
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final TokenProvider tokenProvider;
	private final TokenClient tokenClient;

	public JWTConfigurer(TokenProvider tokenProvider, TokenClient tokenClient) {
		this.tokenProvider = tokenProvider;
		this.tokenClient = tokenClient;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		JWTFilter customFilter = new JWTFilter(tokenProvider, tokenClient);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
