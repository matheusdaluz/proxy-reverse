package br.com.proxyreverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.proxyreverse.manager.KeyStoreManager;

@SpringBootApplication
public class ProxyReverseDesafioApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ProxyReverseDesafioApplication.class, args);
		KeyStoreManager.getInstance().createKeyStore();
	}
	
	
}
