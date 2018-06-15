package br.com.proxyreverse.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyStoreManager {
	
	private static final Logger logger = LoggerFactory.getLogger(KeyStoreManager.class);	
	public static KeyStoreManager instace;

	public static synchronized KeyStoreManager getInstance() {
		if (instace == null)
			instace = new KeyStoreManager();
		return instace;
	}

	public KeyStore createKeyStore() throws Exception {

		File file = new File("keystore.p12");

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		if (file.exists()) {
			keyStore.load(new FileInputStream(file), "globodesafio".toCharArray());
		} else {
			keyStore.load(null, null);
			keyStore.store(new FileOutputStream(file), "globodesafio".toCharArray());
		}
		return keyStore;
	}

	public static X509Certificate verifyCertificate(List<String> listAlias) throws Exception {

		KeyStore keystore = KeyStoreManager.getInstance().createKeyStore();
		X509Certificate cert = null;

		for (String alias : listAlias) {
			if (keystore.containsAlias(alias)) {
				cert = (X509Certificate) keystore.getCertificate(alias);
				logger.info("Certificado validado;");
			} else {
				logger.warn("Certificado não permitido.");
			}
		}

		return cert;
	}

}
