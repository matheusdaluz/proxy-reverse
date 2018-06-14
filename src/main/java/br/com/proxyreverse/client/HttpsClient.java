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

import org.cryptacular.util.CertUtil;

import br.com.proxyreverse.manager.KeyStoreManager;

@WebServlet(urlPatterns = "")
public class HttpsClient extends HttpServlet {

	private static final long serialVersionUID = 6644332178282070109L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		makeConnectionAndValidate(request.getRequestURL().toString(), response);
	}

	public void makeConnectionAndValidate(String path, HttpServletResponse response) throws IOException {
		HttpsURLConnection connection = null;
		List<String> listAlias = new ArrayList<String>();
		PrintWriter writer = response.getWriter();

		try {
			URL url = new URL(path);
			connection = (HttpsURLConnection) url.openConnection();
			connection.connect();

			SSLSocketFactory sslSocketFactory = getFactory();
			connection.setSSLSocketFactory(sslSocketFactory);

			Certificate[] serverCertificate = connection.getServerCertificates();

			if (serverCertificate.length == 0) {
				writer.println("Nenhum certificado encontrado.");
			}

			for (Certificate certificate : serverCertificate) {

				if (certificate instanceof X509Certificate) {
					X509Certificate x509cert = (X509Certificate) certificate;
					listAlias.add(CertUtil.subjectCN(x509cert));
				}

			}

			Certificate cert = KeyStoreManager.verifyCertificate(listAlias, writer);
			connection.disconnect();

		} catch (ClassCastException e) {
			writer.println("permito apenas requests https.");
		} catch (Exception e) {
			if (connection != null) {
				connection.disconnect();
			}
			writer.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

	}

	private static SSLSocketFactory getFactory() throws Exception {
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, null, null);
		return context.getSocketFactory();
	}

}
