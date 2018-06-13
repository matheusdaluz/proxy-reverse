package br.com.proxyreverse.client;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class SNIHostnameVerifier implements HostnameVerifier {

	public SNIHostnameVerifier() {
	  }

	@Override
	public boolean verify(String hostname, SSLSession session) {

		
		return true;
	}

}
