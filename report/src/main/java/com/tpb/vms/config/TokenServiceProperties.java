package com.tpb.vms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth", ignoreUnknownFields = false)
@Component
@Slf4j
@ToString
public class TokenServiceProperties {
	private String scheme;
	private String host;
	private String port;
	private String path;
	private String clientKey;
}
