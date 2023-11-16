/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.config;

import com.tpb.vms.client.TokenClient;
import com.tpb.vms.security.JWTConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.tpb.vms.security.TokenProvider;

/**
 *
 * @author Viet Do-Van
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final AccessDecisionManager accessDecisionManager;
	private final CorsFilter corsFilter;
    private final TokenProvider tokenProvider;
    private final TokenClient tokenClient;
    
    public SecurityConfiguration(
    		AccessDecisionManager accessDecisionManager,
            CorsFilter corsFilter,
            TokenProvider tokenProvider,
            TokenClient tokenClient) {
    	this.accessDecisionManager = accessDecisionManager;
        this.corsFilter = corsFilter;
        this.tokenProvider = tokenProvider;
        this.tokenClient = tokenClient;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/h2-console/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**")
                .antMatchers("/api/register")
                .antMatchers("/api/report")
                .antMatchers("/api/activate")
                .antMatchers("/api/logout")
                .antMatchers("/api/authenticate")
                .antMatchers("/api/account/reset-password/init")
                .antMatchers("/api/account/reset-password/finish");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .and().headers().frameOptions().disable()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/api/**")
                /*.authenticated()
                .accessDecisionManager(accessDecisionManager)
                .and().apply(securityConfigurerAdapter());*/
                .anonymous();
		 
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider,tokenClient);
    }

}
