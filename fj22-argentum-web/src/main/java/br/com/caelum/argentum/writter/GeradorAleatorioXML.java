package br.com.caelum.argentum.writter;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import br.com.caelum.argentum.modelo.Negociacao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GeradorAleatorioXML {
	public static final String FILE = "bovespaPTR4.xml";
	public void gerarXMLNegociacoes(List<Negociacao> negociacoes) throws FileNotFoundException{
		XStream stream = new XStream(new DomDriver());
		stream.alias("negociacao", Negociacao.class);
		stream.setMode(XStream.NO_REFERENCES);
		PrintStream out = new PrintStream(FILE);
		out.println(stream.toXML(negociacoes));
		out.close();
	}

}
