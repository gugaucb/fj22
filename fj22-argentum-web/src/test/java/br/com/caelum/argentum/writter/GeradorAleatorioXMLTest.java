package br.com.caelum.argentum.writter;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.negocio.NegociacaoBovespaFactory;

public class GeradorAleatorioXMLTest {

	@Test
	public void arquivoFoiCriado() throws IOException {
		GeradorAleatorioXML gerador = new GeradorAleatorioXML();
		try {
			NegociacaoBovespaFactory fabrica = new NegociacaoBovespaFactory();
			List<Negociacao> negociacoes = fabrica.constroiNegociacoesNovo();
			gerador.gerarXMLNegociacoes(negociacoes);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Falha na geração das Negociacoes");
			
		}
		Assert.assertTrue(new File(GeradorAleatorioXML.FILE).exists());
	}
	
	@Test
	public void gravouTodosAsNegociacoes() throws IOException {
		GeradorAleatorioXML gerador = new GeradorAleatorioXML();
		try {
			NegociacaoBovespaFactory fabrica = new NegociacaoBovespaFactory();
			List<Negociacao> negociacoes = fabrica.constroiNegociacoesNovo();
			gerador.gerarXMLNegociacoes(negociacoes);
			Assert.assertEquals(negociacoes.size(), fabrica.carregarNegociacaoExistentes().size());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Falha na geração das Negociacoes");
			
		}
	}
}
