package br.com.caelum.argentum.modelo;

import java.io.IOException;
import java.util.List;

import org.primefaces.model.chart.ChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.argentum.graficos.GeradorModelGrafico;
import br.com.caelum.argentum.mb.Fila;
import br.com.caelum.argentum.mb.IFila;
import br.com.caelum.argentum.mock.GeradorAleatorioNegociacao;
import br.com.caelum.argentum.negocio.NegociacaoBovespaFactory;
import br.com.caelum.argentum.negocio.SerieTemporal;
import br.com.caelum.argentum.ws.ClienteWebService;

public class Filas {
	private static Filas filas;
	private IFila<Double> previsao;
	private IFila<Double> mediaSimples;
	private IFila<Candle> candleStick;
	private IFila<Negociacao> negociacoes;
	private ChartModel modeloGrafico;
	private Logger log;
	
	private Filas() {
		// TODO Auto-generated constructor stub
	}
	
	public static Filas getInstance(){
		if(filas==null){
			filas = new Filas();
			filas.iniciarCron();
		}
		return filas;
	}
	
	public void iniciarCron(){
		log = LoggerFactory.getLogger(Filas.class);
		previsao = new FilaPredict<Double>();
		mediaSimples = new FilaPredict<Double>();
		candleStick = new Fila<Candle>();
		negociacoes = new Fila<Negociacao>();
		
		//try {
			ClienteWebService cliente = new ClienteWebService();
			//List<Negociacao> negociacoesList = cliente.getNegociacoes();
			//List<Negociacao> negociacoesList = new NegociacaoBovespaFactory().constroiNegociacoesNovo();
			List<Negociacao> negociacoesList = GeradorAleatorioNegociacao.getInstance().gerarNegociacao();
			for (Negociacao negociacao : negociacoesList) {
				negociacoes.inserir(negociacao);
			}
			log.debug("Quant Negociacoes "+ negociacoesList.size());
			log.debug("Quant Negociacoes Fila "+ negociacoes.getItensFila().size());
			List<Candle> candles = new CandlestickFactory().constroiCandles(negociacoesList);
			for (Candle candle : candles) {
				candleStick.inserir(candle);
			}
			
			log.debug("Quant Candles "+ candles.size());
			log.debug("Quant Candles Fila "+ candleStick.getItensFila().size());
			//log.debug("Atualizando Gráfico após a construção do objeto");
			//atualizarGrafico();
	/*	} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
	}

	public IFila<Double> getPrevisao() {
		return previsao;
	}

	public void setPrevisao(IFila<Double> previsao) {
		this.previsao = previsao;
	}

	public IFila<Double> getMediaSimples() {
		return mediaSimples;
	}

	public void setMediaSimples(IFila<Double> mediaSimples) {
		this.mediaSimples = mediaSimples;
	}

	public IFila<Candle> getCandleStick() {
		return candleStick;
	}

	public void setCandleStick(IFila<Candle> candleStick) {
		this.candleStick = candleStick;
	}

	public IFila<Negociacao> getNegociacoes() {
		return negociacoes;
	}

	public void setNegociacoes(IFila<Negociacao> negociacoes) {
		this.negociacoes = negociacoes;
	}

	public ChartModel getModeloGrafico() {
		return modeloGrafico;
	}

	public void setModeloGrafico(ChartModel modeloGrafico) {
		this.modeloGrafico = modeloGrafico;
	}
	
	public ChartModel atualizarGrafico(){
		int ultimaPredictPlotada = 0;
		int ultimaNegociacaoPlotada = 0;
		log.debug("Iniciando atualização do Grafico");
		SerieTemporal serie = new SerieTemporal(new CandlestickFactory().constroiCandles(negociacoes.getItensFila()));
		GeradorModelGrafico gerador = new GeradorModelGrafico(serie, 2, serie.getTotal()-1);
		gerador.plotaIndicador(new MediaMovelSimples(new IndicadorFechamento()));
		gerador.plotaIndicador(new MediaMovelPonderada(new IndicadorFechamento()));
		gerador.plotaIndicadorPredict(getPrevisao().getItensFila(),ultimaPredictPlotada, getNegociacoes().getItensFila().size());
		gerador.plotaIndicadorNegociacao(getNegociacoes().getItensFila(),ultimaNegociacaoPlotada);
		ultimaPredictPlotada = getPrevisao().getItensFila().size()-1;
		ultimaNegociacaoPlotada = getNegociacoes().getItensFila().size()-1;
		
		log.debug("ModeloGrafico eh nulo "+(modeloGrafico==null));
		log.debug("Atualização do Grafico Finalizado");
		
		return gerador.getModeloGrafico();
		
	}

}
