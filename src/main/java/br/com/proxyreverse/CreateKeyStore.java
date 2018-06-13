package br.com.proxyreverse;

import java.io.FileOutputStream;
import java.security.KeyStore;

public class CreateKeyStore {
	
	public CreateKeyStore() {
		
	}

	public static void main(String[] args) {

		try {

			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] password = "password".toCharArray();
			ks.load(null, password);
			FileOutputStream fos = new FileOutputStream("/Users/matheus/keystore.jks");

			ks.store(fos, password);
			fos.close();

		}

		catch (Exception e) {
			e.printStackTrace();

		}

	}
}
