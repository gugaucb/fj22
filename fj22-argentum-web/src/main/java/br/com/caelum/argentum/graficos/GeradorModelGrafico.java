package br.com.caelum.argentum.graficos;

import java.util.List;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.argentum.interfaces.Indicador;
import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.negocio.SerieTemporal;

public class GeradorModelGrafico {
	private SerieTemporal serie;
	private int comeco;
	private int fim;
	private CartesianChartModel modeloGrafico;
	private Logger log = LoggerFactory.getLogger(GeradorModelGrafico.class);

     

	public GeradorModelGrafico(SerieTemporal serie, int comeco, int fim) {
		this.serie = serie;
		this.comeco = comeco;
		this.fim = fim;
		this.modeloGrafico = new CartesianChartModel();
		log.info("------- Criou GeradorModelGrafico ----------------------");
	}

	public void plotaIndicador(Indicador indicador) {
		
		LineChartSeries chartSeries = new LineChartSeries(indicador.toString());
		log.info("Começo "+comeco+" ");
		log.info("Fim "+fim);
		for (int i = comeco; i <fim ; i++) {
			double valor = indicador.calcula(i, serie);
			chartSeries.set(i*2, valor);
			//log.info("------- "+valor+" ----------------------");
		}
		if(chartSeries.getData().size()>0){
			this.modeloGrafico.addSeries(chartSeries);
		}
	}

	public void plotaIndicadorPredict(List<Double> predicts, int ultimaPredictPlotada, int quantNegociacoes ) {
		LineChartSeries chartSeries = new LineChartSeries("Predicts");
		log.info("Começo "+ultimaPredictPlotada);
		log.info("Fim "+predicts.size());
		log.info("predicts.size "+predicts.size());
		int x = quantNegociacoes - (predicts.size() - ultimaPredictPlotada);
		for (int j = ultimaPredictPlotada; j < predicts.size()-1; j++) {
			j++;
			chartSeries.set(j*2, predicts.get(j));
			//log.info("------- "+predict+" ----------------------");
		}	
			
			
		this.modeloGrafico.addSeries(chartSeries);
	}
	
	public void plotaIndicadorNegociacao(List<Negociacao> negociacoes, int ultimaNegociacaoPlotada ) {
		LineChartSeries chartSeries = new LineChartSeries("Negociacao");
		log.info("Começo "+ultimaNegociacaoPlotada);
		log.info("Fim "+negociacoes.size());
		log.info("predicts.size "+negociacoes.size());
		for (int j = ultimaNegociacaoPlotada; j < negociacoes.size()-1; j++) {
			chartSeries.set(j++, negociacoes.get(j).getPreco());
			//log.info("------- "+predict+" ----------------------");
		}	
			
			
		this.modeloGrafico.addSeries(chartSeries);
	}

	public ChartModel getModeloGrafico() {
		return this.modeloGrafico;
	}

}
