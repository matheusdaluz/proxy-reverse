package br.com.proxyreverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan 
public class ProxyReverseDesafioApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ProxyReverseDesafioApplication.class, args);
	}

}
