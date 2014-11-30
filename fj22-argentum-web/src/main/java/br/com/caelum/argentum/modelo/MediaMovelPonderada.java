package br.com.caelum.argentum.modelo;

import br.com.caelum.argentum.interfaces.Indicador;
import br.com.caelum.argentum.negocio.SerieTemporal;

public class MediaMovelPonderada implements Indicador{
	private Indicador outroIndicador;
	public MediaMovelPonderada(Indicador indicador){
		this.outroIndicador = indicador;
	}
	
	public double calcula(int posicao, SerieTemporal serie){
		double soma = 0.0;
		int peso = 1;
		
		for (int i = posicao-2; i <= posicao; i++) {
			soma+=outroIndicador.calcula(i, serie)*peso;
			peso++;
		}
		return soma/6;
	}
	
	public String toString() {
		return "MMP - Fechamento";
	};
}
