package br.com.proxyreverse.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import br.com.proxyreverse.utils.ApplicationProperties;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

public class KeyStoreManager {

	public static KeyStoreManager instace;

	public static synchronized KeyStoreManager getInstance() {
		if (instace == null)
			instace = new KeyStoreManager();
		return instace;
	}

	public KeyStore createKeyStore() throws Exception {
		
		ApplicationProperties properties = new ApplicationProperties();
		File file = new File(properties.getPathKeyStore());

		KeyStore keyStore = KeyStore.getInstance("JKS");
		if (file.exists()) {
			keyStore.load(new FileInputStream(file), "123456".toCharArray());
		} else {
			keyStore.load(null, null);
			keyStore.store(new FileOutputStream(file), "123456".toCharArray());
		}
		return keyStore;
	}

	public void includeCertificate() throws Exception {

		CertAndKeyGen certGen = new CertAndKeyGen("RSA", "SHA256WithRSA", null);
		certGen.generate(2048);
		long validSecs = (long) 365 * 24 * 60 * 60;
		X509Certificate cert = certGen.getSelfCertificate(new X500Name("CN=My Application,O=My Organisation,L=My City,C=DE"), validSecs);

		KeyStore keyStore = KeyStoreManager.getInstance().createKeyStore();
		keyStore.setKeyEntry("tomcat", certGen.getPrivateKey(), null, new X509Certificate[] { cert });
	}

}
