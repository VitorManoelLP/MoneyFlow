package com.moneyflow.gateway.service;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private static final int HOUR = 360000;
	String secretKey = "MxiWmFvKKdJ6APJWS57Es3CHCiRj76EGKUimExc9D6RfSkQYUbqHni7arKZVKg2tYMyrTVDBUy8RsohurxCESpd";
	long validityInMs = HOUR;

}