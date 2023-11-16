/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.client;

import java.net.URI;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.tpb.vms.client.dto.TokenValidationDTO;
import com.tpb.vms.config.TokenServiceProperties;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Viet Do-Van
 */
@Service
@Slf4j
public class TokenClientImpl implements TokenClient {

    private final TokenServiceProperties tokenServiceProperties;
    private final RestTemplate restTemplate;
    private final UriComponents uriComponents;

    public TokenClientImpl(RestTemplate restTemplate, TokenServiceProperties tokenServiceProperties) {
        this.restTemplate = restTemplate;
        this.tokenServiceProperties = tokenServiceProperties;
        this.uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.tokenServiceProperties.getScheme())
                .host(this.tokenServiceProperties.getHost())
                .port(this.tokenServiceProperties.getPort())
                .path(this.tokenServiceProperties.getPath())
                .build()
                .encode();
    }

    @Override
    public boolean validateToken(String token) {
        TokenValidationDTO tokenValidationDTO = TokenValidationDTO.builder()
                .token(token)
                .clientKey(this.tokenServiceProperties.getClientKey())
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.set(API_KEY_HEADER, this.tokenServiceProperties.getClientKey());
        headers.set("Content-Type", "application/json");
        HttpEntity<TokenValidationDTO> httpEntity = new HttpEntity<>(tokenValidationDTO, headers);

        URI uri = uriComponents.toUri();
        log.debug("exchange - uri:{},method:{},requestBody:{}", uri, HttpMethod.POST.name(), tokenValidationDTO);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(uriComponents.toUri(), HttpMethod.POST, httpEntity, String.class);
        if (responseEntity == null) {
            return false;
        }
        log.debug("exchange - status:{}, responseBody:{} - uri:{},method:{},requestBody:{}", responseEntity.getStatusCode(), responseEntity.getBody(), uri, HttpMethod.POST.name(), tokenValidationDTO);
        return Objects.equals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

}
