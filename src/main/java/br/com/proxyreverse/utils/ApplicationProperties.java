package br.com.proxyreverse.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class ApplicationProperties {

	 
	private String pathKeyStore;

	public String getPathKeyStore() {
		return pathKeyStore;
	}

	public void setPathKeyStore(String pathKeyStore) {
		this.pathKeyStore = pathKeyStore;
	}

}
