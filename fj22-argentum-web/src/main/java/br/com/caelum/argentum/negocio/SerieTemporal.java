package br.com.caelum.argentum.negocio;

import java.util.List;

import br.com.caelum.argentum.modelo.Candle;

public class SerieTemporal {
	private final List<Candle> candles;

	public SerieTemporal(List<Candle> candles) {
		this.candles = candles;
	}

	public Candle getCandle(int index) {
		return candles.get(index);
	}
	
	public int getTotal(){
		return this.candles.size();
	}
	
}
