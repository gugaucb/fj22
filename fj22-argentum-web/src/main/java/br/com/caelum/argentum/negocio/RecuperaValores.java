package br.com.caelum.argentum.negocio;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class RecuperaValores {
	public String recupera() throws IOException {
			URL url = new URL(
					"http://www.bmfbovespa.com.br/Pregao-Online/ExecutaAcaoCarregarDadosPapeis.asp?CodDado=petr4");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxyout01-trf1.trf1.gov.br", 8081));
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.connect();
			InputStream resultado = connection.getInputStream();
			java.util.Scanner s = new java.util.Scanner(resultado)
					.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
	}
}
