package br.com.caelum.argentum.modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.argentum.schedule.AtualizaDadosJob;

public class CandlestickFactory {
	public final static String HORA = "hora";
	public final static String DIA = "dia";
	Logger log;
	public CandlestickFactory(){
		log = LoggerFactory.getLogger(CandlestickFactory.class);
	}
	public Candle constroiCandleParaData(Calendar data,
			List<Negociacao> negocios) {
		
		double maximo = Double.MIN_VALUE;
		double minimo = Double.MAX_VALUE;
		double volume = 0;

		for (Negociacao negocio : negocios) {
			volume += negocio.getVolume();

			double preco = negocio.getPreco();
			if (preco > maximo) {
				maximo = preco;
			}
			if (preco < minimo) {
				minimo = preco;
			}
		}

		double abertura = negocios.isEmpty() ? 0 : negocios.get(0).getPreco();
		double fechamento = negocios.isEmpty() ? 0 : negocios.get(
				negocios.size() - 1).getPreco();

		return new Candle(abertura, fechamento, minimo, maximo, volume, data);
	}

	public List<Candle> constroiCandles(List<Negociacao> todasNegociacoes) {
		List<Candle> candles = new ArrayList<Candle>();
		//log.debug("Quant Negociacao "+ todasNegociacoes.size());
		List<Negociacao> negociacoesDoDia = new ArrayList<Negociacao>();
		if (!todasNegociacoes.isEmpty()) {
			Calendar dataAtual = todasNegociacoes.get(0).getData();
			
			for (Negociacao negociacao : todasNegociacoes) {
				// se não for mesmo dia, fecha candle e reinicia variáveis
				//log.debug("Data Atual "+ dataAtual.getTime());
				//log.debug("Data Negociacao "+ negociacao.getData().getTime());
				if (!negociacao.isMesmoMinuto(dataAtual)) {
					log.debug("Trocou de Hora");
					criaEGuardaCandle(candles, negociacoesDoDia, dataAtual);
					negociacoesDoDia = new ArrayList<Negociacao>();
					dataAtual = negociacao.getData();
				}
				//log.debug("Acumulou - Quant negociacoesDoDia "+negociacoesDoDia.size());
				negociacoesDoDia.add(negociacao);
			}
			// adiciona último candle
			criaEGuardaCandle(candles, negociacoesDoDia, dataAtual);
		}
		//log.debug("Quant candles "+ candles.size());
		return candles;
	}

	private void criaEGuardaCandle(List<Candle> candles,
			List<Negociacao> negociacoesDoDia, Calendar dataAtual) {
		Candle candleDoDia = constroiCandleParaData(dataAtual, negociacoesDoDia);
		candles.add(candleDoDia);
	}

	public List<Candle> constroiCandles(List<Negociacao> todasNegociacoes,
			final String intervalo) {
		List<Candle> candles = new ArrayList<Candle>();

		List<Negociacao> negociacoesDoDia = new ArrayList<Negociacao>();
		Calendar dataAtual = todasNegociacoes.get(0).getData();

		for (Negociacao negociacao : todasNegociacoes) {
			// se não for mesmo dia, fecha candle e reinicia variáveis
			if (intervalo.equals(DIA)) {
				if (!negociacao.isMesmoDia(dataAtual)) {
					criaEGuardaCandle(candles, negociacoesDoDia, dataAtual);
					// negociacoesDoDia = new ArrayList<Negociacao>();
					dataAtual = negociacao.getData();
				}
			} else if (intervalo.equals(HORA)) {
				if (!negociacao.isMesmaHora(dataAtual)) {
					criaEGuardaCandle(candles, negociacoesDoDia, dataAtual);
					// negociacoesDoDia = new ArrayList<Negociacao>();
					dataAtual = negociacao.getData();
				}
			}
			negociacoesDoDia.add(negociacao);
		}
		criaEGuardaCandle(candles, negociacoesDoDia, dataAtual);

		return candles;
	}
}
