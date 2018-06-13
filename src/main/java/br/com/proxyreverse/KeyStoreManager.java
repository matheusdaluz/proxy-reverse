package br.com.proxyreverse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;


public class KeyStoreManager {

	public static KeyStore createKeyStore() throws Exception {
		File file = new File("/Users/matheus/keyserverstore.keystore");
		KeyStore keyStore = KeyStore.getInstance("JKS");
		if (file.exists()) {
			keyStore.load(new FileInputStream(file), "123456".toCharArray());
		} else {
			keyStore.load(null, null);
			keyStore.store(new FileOutputStream(file), "123456".toCharArray());
		}
		return keyStore;
	}

	public static void includeCertificate() throws Exception {

		CertAndKeyGen certGen = new CertAndKeyGen("RSA", "SHA256WithRSA", null);
		certGen.generate(2048);
		long validSecs = (long) 365 * 24 * 60 * 60;
		X509Certificate cert = certGen.getSelfCertificate(new X500Name("CN=My Application,O=My Organisation,L=My City,C=DE"), validSecs);

		KeyStore keyStore = KeyStoreManager.createKeyStore();
		keyStore.setKeyEntry("tomcat", certGen.getPrivateKey(), null, new X509Certificate[] { cert });
	}

	public static void main(String args[]) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException,
			IOException, KeyStoreException, UnrecoverableEntryException {
		try {
			KeyStoreManager.includeCertificate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
