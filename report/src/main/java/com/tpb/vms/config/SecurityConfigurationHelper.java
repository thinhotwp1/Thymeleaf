/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.config;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.tpb.vms.security.AuthorAccessDecisionVoter;
import com.tpb.vms.security.TokenProvider;

/**
 *
 * @author Viet Do-Van
 */
@Configuration
@Slf4j
public class SecurityConfigurationHelper {

    @Bean
    public AccessDecisionManager accessDecisionManager(TokenProvider tokenProvider, ModelMapper modelMapper) {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                        new WebExpressionVoter(),
                        new RoleVoter(),
                        new AuthenticatedVoter(),
                        new AuthorAccessDecisionVoter(tokenProvider, modelMapper)
                );
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public CorsFilter corsFilter(CorsProperties corsProperties) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = getCorsConfiguration(corsProperties);

        List<String> allowedOrigins = config.getAllowedOrigins();
        if (allowedOrigins != null && !allowedOrigins.isEmpty()) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
            source.registerCorsConfiguration("/*/api/**", config);
            source.registerCorsConfiguration("/services/*/api/**", config);
            source.registerCorsConfiguration("/*/management/**", config);
        }
        return new CorsFilter(source);
    }

    private CorsConfiguration getCorsConfiguration(CorsProperties corsProperties) {
        CorsConfiguration config = new CorsConfiguration();
        if (StringUtils.isNoneBlank(corsProperties.getAllowedOrigins())) {
            config.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        }
        if (StringUtils.isNoneBlank(corsProperties.getAllowedMethods())) {
            config.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        }
        if (StringUtils.isNoneBlank(corsProperties.getAllowedHeaders())) {
            config.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        }
        if (StringUtils.isNoneBlank(corsProperties.getExposedHeaders()) && !StringUtils.equalsIgnoreCase("*", corsProperties.getExposedHeaders())) {
            config.setExposedHeaders(Arrays.asList(corsProperties.getExposedHeaders().split(",")));
        }
        config.setAllowCredentials(corsProperties.getAllowCredentials());
        config.setMaxAge(corsProperties.getMaxAge());
        return config;
    }

}
