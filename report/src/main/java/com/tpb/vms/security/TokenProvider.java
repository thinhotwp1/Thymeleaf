/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpb.LibEncrypt.LibEncrypt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Viet Do-Van
 */
@Slf4j
@Component
public class TokenProvider {

    
    public Claims parseClaimsJwt(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        int signatureIndex = token.lastIndexOf('.');
        if (signatureIndex <= 0) {
            return null;
        }
        
        //CongPT check neu token chua duoc sign thi ko cho qua
        String signature = token.substring(signatureIndex+1,token.length());
        if(signature.isEmpty())
        {
        	return null;
        }
        
        String withoutSignature = token.substring(0, signatureIndex + 1);
        Jwt<Header, Claims> untrusted = Jwts.parserBuilder().build().parseClaimsJwt(withoutSignature);

        //CongPT check neu thuat toan la none thi ko cho qua
        if(untrusted.getHeader().get("alg").toString().equalsIgnoreCase("none"))
        {
        	return null;
        }
        
        return untrusted.getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isEmpty(bearerToken)) {
            return null;
        }

        if (!(org.springframework.util.StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))) {
            return null;
        }
        return bearerToken.substring(7);
    }

    public Authentication getAuthentication(String token) {
        if (Objects.isNull(token) || StringUtils.isEmpty(token)) {
            return null;
        }
        Claims claims = this.parseClaimsJwt(token);
        List<String> authorityLst = (List<String>) claims.get(AUTHORITIES_KEY);
        List<? extends GrantedAuthority> authorities = authorityLst.parallelStream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), StringUtils.EMPTY, authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    
    public List getScopes(String token) {
        try {
            if (Objects.isNull(token) || StringUtils.isEmpty(token)) {
                return Collections.emptyList();
            }

            Claims claims = this.parseClaimsJwt(token);
            String base64Scopes = (String) claims.get(TokenProvider.SCOPE_KEY);
            byte[] compScopes = Base64.getDecoder().decode(base64Scopes.getBytes());

            // Decompress the bytes
            Inflater inflater = new Inflater();
            inflater.setInput(compScopes);

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(compScopes.length)) {
                byte[] buffer = new byte[1024];
                while (!inflater.finished()) {
                    int count = inflater.inflate(buffer);
                    outputStream.write(buffer, 0, count);
                }
                byte[] scopes = outputStream.toByteArray();

                ObjectMapper objectMapper = new ObjectMapper();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(scopes);
                return objectMapper.readValue(inputStream, List.class);
            }
        } catch (DataFormatException | UnsupportedEncodingException ex) {
            log.trace(ex.toString(), ex);
            return Collections.emptyList();
        } catch (IOException ex) {
            log.trace(ex.toString(), ex);
            return Collections.emptyList();
        }
    }

    public static final String AUTHORITIES_KEY = "auth";
    public static final String SCOPE_KEY = "scope";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_TOKEN = "access_token";
}
