package br.com.caelum.argentum.modelo;

import java.lang.reflect.Constructor;

import br.com.caelum.argentum.interfaces.Indicador;

public class IndicadorFactory {
	private static final String BR_COM_CAELUM_ARGENTUM_MODELO = "br.com.caelum.argentum.modelo.";
	private String nomeIndicador;
	private String nomeMedia;
	public IndicadorFactory(String nomeIndicador, String nomeMedia) {
		this.nomeIndicador = nomeIndicador;
		this.nomeMedia = nomeMedia;
	}
	
	public Indicador getIndicador(){
		try{
			Indicador indicador = (Indicador) Class.forName(BR_COM_CAELUM_ARGENTUM_MODELO+this.nomeIndicador).newInstance();
			if(this.nomeMedia!=null && !this.nomeMedia.isEmpty()){
				Constructor<?> constructor = Class.forName(BR_COM_CAELUM_ARGENTUM_MODELO+ this.nomeMedia).getConstructor(Indicador.class);
				indicador = (Indicador) constructor.newInstance(indicador);
			}
			return indicador;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
