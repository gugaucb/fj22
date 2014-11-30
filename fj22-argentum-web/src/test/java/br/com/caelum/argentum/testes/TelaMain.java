package br.com.caelum.argentum.testes;

import java.io.IOException;

import br.com.caelum.argentum.negocio.NegociacaoBovespaFactory;

public class TelaMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NegociacaoBovespaFactory negociacaoBovespaFactory = new NegociacaoBovespaFactory();
		try {
			negociacaoBovespaFactory.constroiNegociacoesNovo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
