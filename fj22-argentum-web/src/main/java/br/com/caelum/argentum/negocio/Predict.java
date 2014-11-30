package br.com.caelum.argentum.negocio;

import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import br.com.caelum.argentum.interfaces.Indicador;
import br.com.caelum.argentum.modelo.Negociacao;

public class Predict  {
	private SimpleRegression simpleRegression = new SimpleRegression();
	public void carregar(List<Negociacao> negociacoes, List<Double> medias, int inicio){
		for (int i = inicio-2; i <= medias.size()-1; i++) {
				double media = medias.get(i);
				double preco = negociacoes.get(i).getPreco();
				simpleRegression.addData(media, preco);
		}
	}
	
	public void add(double media, double preco){
		simpleRegression.addData(media, preco);
	}
	
	public double calcula(Double media){
		return simpleRegression.predict(media);
	}

}
