package br.com.caelum.argentum.testes;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import br.com.caelum.argentum.modelo.Candle;
import br.com.caelum.argentum.modelo.CandlestickFactory;
import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.negocio.NegociacaoBovespaFactory;
import br.com.caelum.argentum.negocio.RecuperaValores;

public class TestaCandleStickFactoryBovespa {
	private double predict = 0.0;
	private int ultimaPosicao = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestaCandleStickFactoryBovespa().iniciar();

	}

	private List<Negociacao> listar() {

		try {
			return new NegociacaoBovespaFactory().constroiNegociacoesNovo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private void iniciar() {
		new Thread() {
			@Override
			public void run() {

				try {
					while (true) {
						List<Negociacao> lista = listar();
						//System.out.println(ultimaPosicao);
						//System.out.println(lista.size());
						if (lista.size()-1 > ultimaPosicao) {
							Negociacao negociacao = lista
									.get(ultimaPosicao + 1);
							if (negociacao.getPreco() == predict) {
								System.out.println("Predict Igual");
							} else if (negociacao.getPreco() < predict) {
								System.out.println("Predict Maior "+(100-(negociacao.getPreco()/predict)*100)+"%");
							} else if (negociacao.getPreco() > predict) {
								System.out.println("Predict Menor "+(100-(predict/negociacao.getPreco())*100)+"%");
							}
							System.out.println(negociacao);
							System.out.println(predict);
							System.out.println();

							ultimaPosicao = lista.size() - 1;
							predict = getPredict(lista);
						}
						sleep(30000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	private double getPredict(List<Negociacao> negociacoes) {
		SimpleRegression simpleRegression = new SimpleRegression();
		for (Negociacao negociacao : negociacoes) {
			simpleRegression.addData(negociacao.getVolume(),
					negociacao.getPreco());
		}
		return simpleRegression.predict(negociacoes.get(negociacoes.size() - 1)
				.getVolume() + 100);
	}

}
