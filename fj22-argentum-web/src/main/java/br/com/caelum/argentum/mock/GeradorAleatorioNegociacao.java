package br.com.caelum.argentum.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.util.logging.resources.logging;

import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.schedule.AtualizaDadosJob;

public class GeradorAleatorioNegociacao {
	private static GeradorAleatorioNegociacao gerador;
	private static double precoBase=15.0;
	private List<Negociacao> negociacoes;
	private Calendar data;
	private static Logger log = LoggerFactory.getLogger(GeradorAleatorioNegociacao.class);
	
	private void GeradorAleatorioNegociacao(){
		
	}
	
	public static GeradorAleatorioNegociacao getInstance(){
		if(gerador==null){
			gerador = new GeradorAleatorioNegociacao();
			gerador.setNegociacoes(new ArrayList<Negociacao>());
			gerador.setData(Calendar.getInstance());
		}
		
		return gerador;
	}
	
	public static List<Negociacao> gerarNegociacao(){
		double porcentagem = ((new Random().nextDouble())/10.0);
		log.debug("Random Double "+(new Random().nextDouble()));
		log.debug("porcentagem "+porcentagem);
		double diferenca = precoBase*porcentagem;
		log.debug("diferenca "+diferenca);
		if(new Random().nextInt(1)==0){
			diferenca*=-1;
		}
		
		double preco = precoBase+diferenca;
		log.debug("preco "+preco);
		
		int quantidade = new Random().nextInt(1000);
		/*if(gerador.getNegociacoes().size()%3==0){
			gerador.getData().add(Calendar.DATE, 1);
		}else{
			gerador.getData().add(Calendar.HOUR_OF_DAY, 1);
		}*/
		
		if(gerador.getNegociacoes().size()%3==0){
			gerador.getData().add(Calendar.HOUR_OF_DAY, 1);
		}else{
			gerador.getData().add(Calendar.MINUTE, 1);
		}
		Negociacao negociacao = new Negociacao(preco, quantidade, gerador.getData());
		log.debug(""+negociacao);
		gerador.getNegociacoes().add(negociacao);
		return gerador.getNegociacoes();
	}

	public List<Negociacao> getNegociacoes() {
		return negociacoes;
	}

	public void setNegociacoes(List<Negociacao> negociacoes) {
		this.negociacoes = negociacoes;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}
	
	
	
	
}
