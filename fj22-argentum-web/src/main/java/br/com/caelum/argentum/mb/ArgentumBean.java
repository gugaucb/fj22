package br.com.caelum.argentum.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.ChartModel;

import com.sun.org.apache.bcel.internal.generic.IDIV;

import br.com.caelum.argentum.graficos.GeradorModelGrafico;
import br.com.caelum.argentum.modelo.CandlestickFactory;
import br.com.caelum.argentum.modelo.IndicadorFactory;
import br.com.caelum.argentum.modelo.IndicadorFechamento;
import br.com.caelum.argentum.modelo.MediaMovelPonderada;
import br.com.caelum.argentum.modelo.MediaMovelSimples;
import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.negocio.SerieTemporal;
import br.com.caelum.argentum.ws.ClienteWebService;

@ManagedBean
@SessionScoped
public class ArgentumBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Negociacao> negociacoes;
	private ChartModel modeloGrafico;
	private String titulo;
	private String nomeIndicador;
	private String nomeMedia;
	
	//@PostConstruct
	public void preparaDados() {
		System.out.println("Indicador: "+ nomeIndicador+ ", "+ nomeMedia);
		
		ClienteWebService cliente = new ClienteWebService();
		this.negociacoes = cliente.getNegociacoes();
		/*try {
			this.negociacoes = new NegociacaoBovespaFactory().constroiNegociacoesNovo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
				
		SerieTemporal serie = new SerieTemporal(new CandlestickFactory().constroiCandles(negociacoes));
		GeradorModelGrafico gerador = new GeradorModelGrafico(serie, 2, serie.getTotal()-1);
		IndicadorFactory indicadorFactory = new IndicadorFactory(getNomeIndicador(), getNomeMedia());
		gerador.plotaIndicador(indicadorFactory.getIndicador());
		this.modeloGrafico = gerador.getModeloGrafico();
				
	}
	
	public List<Negociacao> getNegociacoes(){
		return this.negociacoes;
	}

	public ChartModel getModeloGrafico() {
		return modeloGrafico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNomeIndicador() {
		return nomeIndicador;
	}

	public void setNomeIndicador(String nomeIndicador) {
		this.nomeIndicador = nomeIndicador;
	}

	public String getNomeMedia() {
		return nomeMedia;
	}

	public void setNomeMedia(String nomeMedia) {
		this.nomeMedia = nomeMedia;
	}
	
	

}
