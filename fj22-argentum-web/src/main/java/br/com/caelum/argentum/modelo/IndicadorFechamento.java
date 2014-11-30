package br.com.caelum.argentum.modelo;

import br.com.caelum.argentum.interfaces.Indicador;
import br.com.caelum.argentum.negocio.SerieTemporal;

public class IndicadorFechamento implements Indicador {

	@Override
	public double calcula(int posicao, SerieTemporal serie) {
		
		return serie.getCandle(posicao).getFechamento();
	}

}
