package br.com.proxyreverse.client;

import java.net.URL;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpsClient {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpsClient.class);
		
	public static void main(String[] args) {
			
		HttpsURLConnection connection = null;
		try {
			URL url = new URL("https://www.google.com");
			connection = (HttpsURLConnection) url.openConnection();

			SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);

			logger.debug("HTTP Response Code " + connection.getResponseCode());
			logger.info("HTTP Response Message " + connection.getResponseMessage());
			logger.info("HTTP Content Length " + connection.getContentLength());
			logger.info("HTTP Content Type " + connection.getContentType());
			logger.info("HTTP Cipher Suite " + connection.getCipherSuite());

			Certificate[] serverCertificate = connection.getServerCertificates();

			for (Certificate certificate : serverCertificate) {
				logger.info("\n");

				logger.info("Certificate Type " + certificate.getType());
				logger.info("Cert Hash Code : " + certificate.hashCode());
				logger.info("Cert Public Key Algorithm : " + certificate.getPublicKey().getAlgorithm());
				logger.info("Cert Public Key Format : " + certificate.getPublicKey().getFormat());

				if (certificate instanceof X509Certificate) {
					X509Certificate x509cert = (X509Certificate) certificate;

					Principal principal = x509cert.getSubjectDN();
					logger.info("Certificate Subject DN " + principal.getName());

					principal = x509cert.getIssuerDN();
					logger.info("Certificate IssuerDn " + principal.getName());
				}
			}

			connection.disconnect();

		} catch (Exception e) {
			if (connection != null) {
				connection.disconnect();
			}
			logger.error(e.getMessage());
		}
	}

	private static SSLSocketFactory getFactorySimple() throws Exception {
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, null, null);
		return context.getSocketFactory();
	}

}
