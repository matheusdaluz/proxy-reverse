package br.com.proxyreverse.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

	@Autowired
	private Environment env;

	public String getPathKeyStore() {
		return env.getProperty("keystore");
	}

	public String getPathCertificados() {
		return env.getProperty("certificados");
	}

}
