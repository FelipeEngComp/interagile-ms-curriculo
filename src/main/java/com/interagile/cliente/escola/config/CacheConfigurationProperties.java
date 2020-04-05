package com.interagile.cliente.escola.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "cache")
@Data
public class CacheConfigurationProperties {
	
	private long timeoutSeconds = 60;
    private Map<String, Long> cacheExpirations = new HashMap<>();
	
}
