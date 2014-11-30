package br.com.caelum.argentum.mb;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.ChartModel;
import org.slf4j.Logger;

import br.com.caelum.argentum.modelo.Candle;
import br.com.caelum.argentum.modelo.Filas;
import br.com.caelum.argentum.modelo.Negociacao;

@ManagedBean(name="applicationResourcesBean", eager=true)
@ApplicationScoped
public class ApplicationResourcesBean {
	
	private ApplicationResourcesBean applicationResourcesBean = this;
	private Logger log;
	
	
	public IFila<Double> getPrevisao() {
		return Filas.getInstance().getPrevisao();
	}
	public IFila<Double> getMediaSimples() {
		return Filas.getInstance().getMediaSimples();
	}
	public IFila<Candle> getCandleStick() {
		return Filas.getInstance().getCandleStick();
	}
	
	
	public IFila<Negociacao> getNegociacoes() {
		return Filas.getInstance().getNegociacoes();
	}
	
	
	
	public ChartModel getModeloGrafico() {
		//atualizarGrafico();
		return Filas.getInstance().atualizarGrafico();
	}
	
	
}
