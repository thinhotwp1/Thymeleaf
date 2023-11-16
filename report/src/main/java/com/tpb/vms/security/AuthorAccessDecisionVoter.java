/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.security;

import io.jsonwebtoken.Claims;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.modelmapper.ModelMapper;

/**
 *
 * @author Viet Do-Van
 */
@Slf4j
public class AuthorAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;

    public AuthorAccessDecisionVoter(TokenProvider tokenProvider, ModelMapper modelMapper) {
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        try {
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            String contextPath = filterInvocation.getHttpRequest().getContextPath();
            String requestURI = filterInvocation.getHttpRequest().getRequestURI();
            String method = filterInvocation.getHttpRequest().getMethod();
            log.debug("requestURI:{},method:{}", requestURI, method);

            if (permittedAll(attributes)) {
                log.debug("requestURI:{},method:{} - permittedAll", requestURI, method);
                return ACCESS_GRANTED;
            }
            String token = this.tokenProvider.resolveToken(filterInvocation.getHttpRequest());
            Claims claims = this.tokenProvider.parseClaimsJwt(token);
            //List scopes = (List) claims.get(TokenProvider.SCOPE_KEY);
            List scopes = tokenProvider.getScopes(token);
            
            List<Permission> grantedPermissionLst = (List<Permission>) scopes.stream().map(x -> modelMapper.map(x, Permission.class)).collect(Collectors.toList());
            long countMatchedGrantedPermission = 0;
            
            if (Objects.nonNull(grantedPermissionLst)) {
                countMatchedGrantedPermission = grantedPermissionLst.parallelStream()
                        .filter(permission
                                -> antPathMatcher.match(String.format("%s%s", contextPath, permission.getPath()), requestURI)
                        && (StringUtils.isEmpty(permission.getMethod()) || StringUtils.equalsIgnoreCase(permission.getMethod(), method))
                        )
                        .count();
            }
            
			/*
			 * if (Objects.nonNull(grantedPermissionLst)) { countMatchedGrantedPermission =
			 * grantedPermissionLst.parallelStream() .filter(permission ->
			 * antPathMatcher.match(permission.getPath(), requestURI) &&
			 * (StringUtils.isEmpty(permission.getMethod()) ||
			 * StringUtils.equalsIgnoreCase(permission.getMethod(), method)) ) .count(); }
			 */

            log.debug("requestURI:{},method:{} - countMatchedGrantedPermission:{}", requestURI, method, countMatchedGrantedPermission);
            if (countMatchedGrantedPermission > 0) {
                return ACCESS_GRANTED;
            }
            return ACCESS_DENIED;
        } catch (Exception e) {
            return ACCESS_DENIED;
        }
    }

    private Boolean permittedAll(Collection<ConfigAttribute> attributes) {
        return attributes.stream().filter((ConfigAttribute x) -> StringUtils.equals("permitAll", x.getAttribute())).count() > 0;
    }
}
