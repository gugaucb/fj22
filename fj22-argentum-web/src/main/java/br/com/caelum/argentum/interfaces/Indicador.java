package br.com.caelum.argentum.interfaces;

import br.com.caelum.argentum.negocio.SerieTemporal;

public interface Indicador {
	public double calcula(int posicao, SerieTemporal serie);
}
