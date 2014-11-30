package br.com.caelum.argentum.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.negocio.NegociacaoBovespaFactory;

public class GeradorNegociacaoPorMinuto {
	private static GeradorNegociacaoPorMinuto gerador;
	private List<Negociacao> negociacoes;
	private List<Negociacao> todasNegociacoes;
	private Calendar data;
	int minuto=0;
	int hora=0;
	int ultimaPosicao=0;
	private static Logger log = LoggerFactory.getLogger(GeradorNegociacaoPorMinuto.class);
	
	private void GeradorAleatorioNegociacao(){
		
	}
	
	public static GeradorNegociacaoPorMinuto getInstance(){
		if(gerador==null){
			gerador = new GeradorNegociacaoPorMinuto();
			try {
				gerador.setTodasNegociacoes(new NegociacaoBovespaFactory().constroiNegociacoesNovo());
				log.info("Total de negociacoes Internet"+gerador.getTodasNegociacoes().size());
				gerador.setNegociacoes(new ArrayList<Negociacao>());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//2013/Jul/18
			gerador.setData(Calendar.getInstance());
			/*gerador.getData().set(Calendar.DATE, 18);
			gerador.getData().set(Calendar.MONTH, 7);
			gerador.getData().set(Calendar.YEAR, 2013);
			gerador.getData().set(Calendar.HOUR_OF_DAY, 10);
			gerador.getData().set(Calendar.MINUTE, 00);*/
			
		}
		
		return gerador;
	}
	
	public static List<Negociacao> gerarNegociacao(){
		log.info("Minuto "+gerador.getMinuto() +" Calendar Minuto "+gerador.getData().get(Calendar.MINUTE) );
		log.info("SECOND "+gerador.getHora() +" Calendar SECOND "+gerador.getData().get(Calendar.SECOND) );
		gerador.setData(Calendar.getInstance());
		if((gerador.getTodasNegociacoes().size()>gerador.getUltimaPosicao())&&
				(gerador.getData().get(Calendar.MINUTE)>=gerador.getMinuto()&&
				gerador.getData().get(Calendar.SECOND)>gerador.getHora())||gerador.getHora()==59){
			
			gerador.setMinuto(gerador.getData().get(Calendar.MINUTE));
			gerador.setHora(gerador.getData().get(Calendar.SECOND));
			Negociacao negociacao = gerador.getTodasNegociacoes().get(gerador.getUltimaPosicao());
			gerador.getNegociacoes().add(negociacao);
			gerador.setUltimaPosicao(gerador.getUltimaPosicao()+1);
			log.info("Nova Negociação entregue");
			log.info(""+negociacao);
			
		}else{
			log.info("Nenhuma Negociação entregue");
		}
		
		return gerador.getNegociacoes();
	}

	private List<Negociacao> getNegociacoes() {
		log.debug("Minuto "+gerador.getMinuto() +" Calendar Minuto"+gerador.getData().get(Calendar.MINUTE) );
		log.debug("Hora "+gerador.getHora() +" Calendar Hora"+gerador.getData().get(Calendar.HOUR_OF_DAY) );
		if(gerador.getData().get(Calendar.MINUTE)>gerador.getMinuto()&&gerador.getData().get(Calendar.HOUR_OF_DAY)>=gerador.getHora()){
			log.debug("Nova Negociação entregue");
			gerador.setMinuto(gerador.getData().get(Calendar.MINUTE));
			gerador.setHora(gerador.getData().get(Calendar.HOUR_OF_DAY));
			gerador.setUltimaPosicao(gerador.getUltimaPosicao()+1);
			negociacoes.add(getTodasNegociacoes().get(gerador.getUltimaPosicao()));
			gerador.setData(Calendar.getInstance());
		}else{
			log.debug("Nenhuma Negociação entregue");
		}
		return negociacoes;
	}

	private void setNegociacoes(List<Negociacao> negociacoes) {
		this.negociacoes = negociacoes;
	}

	private Calendar getData() {
		return data;
	}

	private void setData(Calendar data) {
		this.data = data;
	}

	private List<Negociacao> getTodasNegociacoes() {
		return todasNegociacoes;
	}

	private void setTodasNegociacoes(List<Negociacao> todasNegociacoes) {
		this.todasNegociacoes = todasNegociacoes;
	}

	private int getMinuto() {
		return minuto;
	}

	private void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	private int getHora() {
		return hora;
	}

	private void setHora(int hora) {
		this.hora = hora;
	}

	private int getUltimaPosicao() {
		return ultimaPosicao;
	}

	private void setUltimaPosicao(int ultimaPosicao) {
		this.ultimaPosicao = ultimaPosicao;
	}
	
	
	
	
}
