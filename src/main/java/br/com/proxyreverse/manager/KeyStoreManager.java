package br.com.proxyreverse.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.List;

public class KeyStoreManager {

	public static KeyStoreManager instace;

	public static synchronized KeyStoreManager getInstance() {
		if (instace == null)
			instace = new KeyStoreManager();
		return instace;
	}

	public KeyStore createKeyStore() throws Exception {

		File file = new File("KeyStore");

		KeyStore keyStore = KeyStore.getInstance("JKS");
		if (file.exists()) {
			keyStore.load(new FileInputStream(file), "globodesafio".toCharArray());
		} else {
			keyStore.load(null, null);
			keyStore.store(new FileOutputStream(file), "globodesafio".toCharArray());
		}
		return keyStore;
	}

	public static Certificate verifyCertificate(List<String> listAlias, PrintWriter writer) throws Exception {

		KeyStore keystore = KeyStoreManager.getInstance().createKeyStore();
		Certificate cert = null;

		for (String alias : listAlias) {
			if (keystore.containsAlias(alias)) {
				cert = keystore.getCertificate(alias);
			} else {
				writer.println("Certificado não permitido.");
			}
		}

		return cert;
	}

}
