package br.com.proxyreverse.client;

import java.net.URL;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class HttpsClient {

	public static void main(String[] args) {

		HttpsURLConnection connection = null;
		try {
			URL url = new URL("https://www.google.com");
			connection = (HttpsURLConnection) url.openConnection();

			SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);

			System.out.println("HTTP Response Code " + connection.getResponseCode());
			System.out.println("HTTP Response Message " + connection.getResponseMessage());
			System.out.println("HTTP Content Length " + connection.getContentLength());
			System.out.println("HTTP Content Type " + connection.getContentType());
			System.out.println("HTTP Cipher Suite " + connection.getCipherSuite());

			Certificate[] serverCertificate = connection.getServerCertificates();

			for (Certificate certificate : serverCertificate) {
				System.out.println("\n");

				System.out.println("Certificate Type " + certificate.getType());
				System.out.println("Cert Hash Code : " + certificate.hashCode());
				System.out.println("Cert Public Key Algorithm : " + certificate.getPublicKey().getAlgorithm());
				System.out.println("Cert Public Key Format : " + certificate.getPublicKey().getFormat());

				if (certificate instanceof X509Certificate) {
					X509Certificate x509cert = (X509Certificate) certificate;

					Principal principal = x509cert.getSubjectDN();
					System.out.println("Certificate Subject DN " + principal.getName());

					principal = x509cert.getIssuerDN();
					System.out.println("Certificate IssuerDn " + principal.getName());
				}
			}

			connection.disconnect();

		} catch (Exception e) {
			if (connection != null) {
				connection.disconnect();
			}
			System.out.println(e.getMessage());
		}
	}

	private static SSLSocketFactory getFactorySimple() throws Exception {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, null, null);
		return context.getSocketFactory();
	}

}
