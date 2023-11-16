package com.tpb.vms.config;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth-service", ignoreUnknownFields = false)
@Component
@Slf4j
@ToString
public class BranchServiceProperty {
	private String scheme;
	private String host;
	private String port;
	private String path;

	@PostConstruct
	public void postConstruct() {
		log.info(this.toString());
	}
}
