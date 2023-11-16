/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.modules.report.config;

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
@ConfigurationProperties(prefix = "report", ignoreUnknownFields = false)
@Component
@Slf4j
@ToString
public class ReportProperties {

    private String baseUrl;

    @PostConstruct
    public void postConstruct() {
        log.info(this.toString());
    }
}
