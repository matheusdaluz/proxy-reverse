package br.com.proxyreverse.client;

import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.cryptacular.util.CertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.proxyreverse.exception.ValidadeCertificateException;
import br.com.proxyreverse.manager.KeyStoreManager;

public class HttpsClient {

	private static final Logger logger = LoggerFactory.getLogger(HttpsClient.class);

	public static void main(String[] args) {

		HttpsURLConnection connection = null;
		List<String> listHostname = new ArrayList<String>();

		try {
			URL url = new URL("https://twitter.com/");
			connection = (HttpsURLConnection) url.openConnection();
			connection.connect();

			SSLSocketFactory sslSocketFactory = getFactory();
			connection.setSSLSocketFactory(sslSocketFactory);

			Certificate[] serverCertificate = connection.getServerCertificates();

			if (serverCertificate.length == 0) {
				logger.error("Nenhum certificado encontrado.");
			}

			for (Certificate certificate : serverCertificate) {

				if (certificate instanceof X509Certificate) {
					X509Certificate x509cert = (X509Certificate) certificate;
					listHostname.add(CertUtil.subjectCN(x509cert));
				}

			}

			Certificate cert = KeyStoreManager.verifyCertificate(listHostname);
			connection.disconnect();

		} catch (Exception e) {
			if (connection != null) {
				connection.disconnect();
			}
			logger.error(e.getMessage());
		}
	}

	private static SSLSocketFactory getFactory() throws Exception {
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, null, null);
		return context.getSocketFactory();
	}

}
