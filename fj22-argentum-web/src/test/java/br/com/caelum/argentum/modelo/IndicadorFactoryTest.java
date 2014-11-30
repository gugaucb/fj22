package br.com.caelum.argentum.modelo;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.argentum.interfaces.Indicador;

public class IndicadorFactoryTest {

	@Test
	public void testaMontaAbertura() {
		String nomeIndicador = "IndicadorAbertura";
		IndicadorFactory indicadorFactory = new IndicadorFactory(nomeIndicador, null);
		Indicador indicador = indicadorFactory.getIndicador();
		Assert.assertTrue(indicador instanceof IndicadorAbertura);
	}
	
	@Test
	public void testMontaAberturaComMediaMovelSimples() {
		String nomeIndicador = "IndicadorAbertura";
		String nomeMedia = "MediaMovelSimples";
		IndicadorFactory indicadorFactory = new IndicadorFactory(nomeIndicador, nomeMedia);
		Indicador indicador = indicadorFactory.getIndicador();
		Assert.assertTrue(indicador instanceof MediaMovelSimples);
		
	}

}
