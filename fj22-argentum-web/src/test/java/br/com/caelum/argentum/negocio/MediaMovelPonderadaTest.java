package br.com.caelum.argentum.negocio;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.argentum.modelo.IndicadorFechamento;
import br.com.caelum.argentum.modelo.MediaMovelPonderada;
import br.com.caelum.argentum.util.GeradorDeSerie;

public class MediaMovelPonderadaTest {

	@Test
	public void sequenciaSimplesCandles() {
		SerieTemporal serie = GeradorDeSerie.criaSerie(1,2,3,4,5,6);
		MediaMovelPonderada mmp = new MediaMovelPonderada(new IndicadorFechamento());
		Assert.assertEquals(14.0/6, mmp.calcula(2, serie),0.00001);
	}

}
