package br.com.caelum.argentum.negocio;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.argentum.modelo.IndicadorFechamento;
import br.com.caelum.argentum.modelo.MediaMovelSimples;
import br.com.caelum.argentum.util.GeradorDeSerie;

public class MediaMovelSimplesTest {

	@Test
	public void sequenciaSimplesDeCandles() {
		SerieTemporal serie = GeradorDeSerie.criaSerie(1,2,3,4,3,4,5,4,3);
		MediaMovelSimples mms = new MediaMovelSimples(new IndicadorFechamento());
		Assert.assertEquals(2.0, mms.calcula(2, serie),0.00001);
	}

}
