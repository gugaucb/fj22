package br.com.caelum.argentum.negocio;

import java.io.IOException;
import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.argentum.interfaces.Indicador;
import br.com.caelum.argentum.mock.GeradorNegociacaoPorMinuto;
import br.com.caelum.argentum.modelo.Candle;
import br.com.caelum.argentum.modelo.CandlestickFactory;
import br.com.caelum.argentum.modelo.Filas;
import br.com.caelum.argentum.modelo.IndicadorFechamento;
import br.com.caelum.argentum.modelo.MediaMovelSimples;
import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.schedule.AtualizaDadosJob;

public class AtualizarDados {
	private SimpleRegression simpleRegression = new SimpleRegression();
	private int indexUltimaNegociacao = 0;
	private int indexUltimaMedia = 0;
	private int indexUltimaCandle = 0;
	private MediaMovelSimples mms;
	Logger log;
    

	public AtualizarDados() {
		super();
		log = LoggerFactory.getLogger(AtualizaDadosJob.class);
	}

	public void execute()
			 {
			List<Negociacao> negociacoes = Filas.getInstance().getNegociacoes().getItensFila();
			List<Double> medias = Filas.getInstance().getMediaSimples().getItensFila();
			List<Candle> candles = Filas.getInstance().getCandleStick().getItensFila();
			List<Double> previsoes = Filas.getInstance().getPrevisao().getItensFila();
		
			log.info("------- Atualizando ----------------------");
			List<Negociacao> lista = listar();
			log.info("Verificando pré-requisitos");
			log.info("listar() está nula? "+(lista==null));
			indexUltimaNegociacao = !negociacoes.isEmpty()?negociacoes.size()-1:0;
			indexUltimaMedia = !medias.isEmpty()?medias.size()-1:0;
			indexUltimaCandle = !candles.isEmpty()?candles.size()-1:0;
			log.info("indexUltimaNegociacao "+indexUltimaNegociacao);
			log.info("indexUltimaMedia "+indexUltimaMedia);
			log.info("indexUltimaCandle "+indexUltimaCandle);
			log.info("------- Atualizando ListaNegociacao ----------------------");
			
			atualizarListaNegociacao(lista, indexUltimaNegociacao);
			if(negociacoes.size()>=2){
				log.info("------- Atualizando ListaCandles ----------------------");
				atualizarListaCandles(negociacoes, indexUltimaCandle);
				if(candles.size()>=2){
					log.info("------- Atualizando ListaMedia ----------------------");
					atualizarListaMedia(candles, indexUltimaCandle);
				}
				if(medias.size()>=2){
					log.info("------- Atualizando ListaPredict ----------------------");
					atualizarListaPredict(negociacoes, medias,indexUltimaMedia);
				}
			}
			log.debug("Atualizando Grafico...");
			Filas.getInstance().atualizarGrafico();
			
			printListDouble(previsoes);
			printListDouble(medias);
			printListNegociacao(negociacoes);
			log.info("Quant. Negociacao "+ negociacoes.size());
			log.info("Quant Candles Fila "+ candles.size());
			log.info("Quant Media Fila "+ medias.size());
			log.info("Quant Previsao Fila "+ previsoes.size());
			log.debug("------- Finishing Job----------------------");
	}

	private void atualizarListaPredict(List<Negociacao> negociacoes,
			List<Double> medias, int indexUltimaMedia) {
		Predict predict = new Predict();
		if(medias.size()>3)
			predict.carregar(negociacoes, medias,indexUltimaMedia);
		for (int i = indexUltimaMedia; i < medias.size(); i++) {
			
			predict.add(negociacoes.get(negociacoes.size()-i).getPreco(), medias.get(i));
			Filas.getInstance().getPrevisao().inserir(predict.calcula(medias.get(medias.size()-1)));
		}
	}

	private List<Negociacao> listar() {
		return GeradorNegociacaoPorMinuto.getInstance().gerarNegociacao();
		//return new ClienteWebService().getNegociacoes();
		/*try {
			
			return new NegociacaoBovespaFactory()
					.constroiNegociacoesNovo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}*/
	}

	private double getPredict(double media, double preco) {
		simpleRegression.addData(media, preco);
		return simpleRegression.predict(media);
	}

	private double getPredict(List<Negociacao> negociacoes) {
		List<Double> medias = Filas.getInstance().getMediaSimples()
				.getItensFila();

		for (int i = 0; i <= medias.size() - 1; i++) {
			if (medias.size() > i && negociacoes.size() > i) {
				double media = medias.get(i);
				double preco = negociacoes.get(i).getPreco();
				simpleRegression.addData(media, preco);
			}
 
		}

		return simpleRegression.predict(medias.get(medias.size() - 1));
	}

	private void printListDouble(List<Double> lista) {
		System.out.print(lista.getClass().getName() + " ");
		String temp = new String();
		for (Double t : lista) {
			temp+=t.toString() + ", ";
		}
		log.debug(temp);
		System.out.println("");
	}

	private void printListNegociacao(List<Negociacao> lista) {
		System.out.print(lista.getClass().getName() + " ");
		String temp = new String();
		for (Negociacao t : lista) {
			temp+=t.toString() + ", ";
		}
		log.debug(temp);
		System.out.println("");
	}
	
	private void atualizarListaNegociacao(List<Negociacao> lista, int indexUltimaNegociacao){
		if(lista!=null){
			for (int i = indexUltimaNegociacao; i < lista.size()-1; i++) {
				Filas.getInstance().getNegociacoes().inserir(lista.get(i));
			}
		}
	}
	
	private void atualizarListaCandles(List<Negociacao> lista, int indexUltimaCandle){
		if(lista!=null){
			List<Candle> candles = new CandlestickFactory().constroiCandles(lista);
			for (int i = indexUltimaCandle; i < candles.size()-1; i++) {
				Filas.getInstance().getCandleStick().inserir(candles.get(i));
			}
		}
	}
	
	private void atualizarListaMedia(List<Candle> lista, int indexUltimaCandle){
		if(lista!=null){
			log.info("atualizarListaMedia indexUltimaNegociacao "+ indexUltimaNegociacao);
			log.info("atualizarListaMedia lista.size()-1 "+ (lista.size()-1));
			for (int i = indexUltimaCandle; i < lista.size()-1; i++) {
				SerieTemporal serie = new SerieTemporal(lista.subList(i, lista.size()-1));
				Filas.getInstance().getMediaSimples().inserir(calcularIndicador(serie, 2, serie.getTotal()-1));
			}
		}
	}
	
	private double calcularIndicador(SerieTemporal serie, int comeco, int fim){
		Indicador indicador = new MediaMovelSimples(new IndicadorFechamento());
		log.debug("comeco "+ comeco);
		log.debug("fim "+ fim);
		log.debug("Quant serie "+ serie.getTotal());
		for (int i = comeco; i < fim; i++) {
			return indicador.calcula(i, serie);
		}
		return 0;
		
	}
	

}
