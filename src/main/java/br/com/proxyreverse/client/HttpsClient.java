package br.com.proxyreverse.client;

import java.net.URL;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class HttpsClient {

	private static List<String> targetURL = new ArrayList<String>();

	public static void main(String[] args) {
		targetURL.add("https://www.google.com");
		
		
		for (String httpURL : targetURL) {
			HttpsURLConnection connection = null;
			try {
				System.out.println("Try to connect to the URL " + httpURL + " ...");
				URL url = new URL(httpURL);
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
					System.out.println("Certificate Type " + certificate.getType());

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
	}

	private static SSLSocketFactory getFactorySimple() throws Exception {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, null, null);
		return context.getSocketFactory();
	}

}
