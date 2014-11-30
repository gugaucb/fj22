package br.com.caelum.argentum.modelo;

import br.com.caelum.argentum.interfaces.Indicador;
import br.com.caelum.argentum.negocio.SerieTemporal;

public class MediaMovelSimples implements Indicador{
	private Indicador outroIndicador;
	
	public MediaMovelSimples(Indicador indicador){
		this.outroIndicador = indicador;
	}
	
	public double calcula(int posicao, SerieTemporal serie){
		double soma = 0.0;
			for (int i = posicao-2; i <= posicao; i++) {
				soma+=outroIndicador.calcula(i, serie);
			}
		
		
		return soma/3;
	}
	
	@Override
	public String toString() {
		return "MMS - Fechamento";
	}
}
