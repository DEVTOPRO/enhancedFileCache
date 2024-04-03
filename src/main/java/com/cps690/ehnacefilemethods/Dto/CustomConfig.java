package com.cps690.ehnacefilemethods.Dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@ConfigurationProperties(prefix = "custom")
@Data
public class CustomConfig {
 private String key;
 
}
