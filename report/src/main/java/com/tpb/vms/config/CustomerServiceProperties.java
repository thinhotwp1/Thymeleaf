/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.config;

import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author Viet Do-Van
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "customer-service", ignoreUnknownFields = false)
@Component
@Slf4j
@ToString
public class CustomerServiceProperties {

    private String scheme;
    private String host;
    private String port;
    private String path;

    private String serviceVersion;
    private String sourceAppId;
    private String targetAppId;
    private String userId;
    @ToString.Exclude
    private String userPassword;

    @PostConstruct
    public void postConstruct() {
        log.info(this.toString());
    }
}
