package br.com.proxyreverse.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.UrlValidator;
import org.cryptacular.util.CertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.proxyreverse.manager.KeyStoreManager;

@WebServlet(urlPatterns = "")
public class HttpsClient extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(HttpsClient.class);
	private static final long serialVersionUID = 6644332178282070109L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		String path = request.getParameter("path");

		if (path.isEmpty()) {
			response.setStatus(400);
			writer.println("O parametro é obrigatorio.");
		}

		if (invalidUrl(path)) {
			response.setStatus(400);
			writer.println("Problema na url.");
		}

		makeConnectionAndValidate(request.getRequestURL().toString(), response, path, writer);
	}

	public void makeConnectionAndValidate(String path, HttpServletResponse response, String caminho, PrintWriter writer)
			throws IOException {
		HttpsURLConnection connection = null;
		List<String> listAlias = new ArrayList<String>();

		try {
			URL url = new URL(caminho);
			connection = (HttpsURLConnection) url.openConnection();

			SSLSocketFactory sslSocketFactory = getFactory();
			connection.setSSLSocketFactory(sslSocketFactory);

			connection.connect();

			Certificate[] serverCertificate = connection.getServerCertificates();

			if (serverCertificate.length == 0) {
				response.setStatus(204);
				writer.println("Nenhum certificado encontrado.");
			}

			for (Certificate certificate : serverCertificate) {
				if (certificate instanceof X509Certificate) {
					X509Certificate x509cert = (X509Certificate) certificate;
					listAlias.add(CertUtil.subjectCN(x509cert));
				}
			}

			X509Certificate cert = KeyStoreManager.verifyCertificate(listAlias);

			if (cert == null) {
				response.setStatus(401);
				writer.println("Certificado não permitido.");
			} else {
				response.setStatus(301);
				redirectToServer("https://www.google.com", response);
			}

			connection.disconnect();

		} catch (Exception e) {
			if (connection != null) {
				connection.disconnect();
			}
			logger.error(e.getMessage());
		}
	}

	private SSLSocketFactory getFactory() throws Exception {
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, null, null);
		return context.getSocketFactory();
	}

	private Boolean invalidUrl(String url) {

		String[] schemes = { "https" };
		UrlValidator urlValidator = new UrlValidator(schemes);

		if (urlValidator.isValid(url)) {
			return false;
		} else {
			return true;
		}

	}

	private void redirectToServer(String serverName, HttpServletResponse response) {
		try {
			response.sendRedirect(serverName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
