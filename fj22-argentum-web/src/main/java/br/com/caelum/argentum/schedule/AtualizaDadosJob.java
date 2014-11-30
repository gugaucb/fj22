package br.com.caelum.argentum.schedule;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.argentum.interfaces.Indicador;
import br.com.caelum.argentum.mb.ApplicationResourcesBean;
import br.com.caelum.argentum.modelo.Candle;
import br.com.caelum.argentum.modelo.CandlestickFactory;
import br.com.caelum.argentum.modelo.IndicadorFechamento;
import br.com.caelum.argentum.modelo.MediaMovelSimples;
import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.negocio.AtualizarDados;
import br.com.caelum.argentum.negocio.NegociacaoBovespaFactory;
import br.com.caelum.argentum.negocio.Predict;
import br.com.caelum.argentum.negocio.SerieTemporal;

public class AtualizaDadosJob implements Job {
	Logger log;

	public AtualizaDadosJob() {
		super();
		log = LoggerFactory.getLogger(AtualizaDadosJob.class);
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.info("------- Initializing Job ----------------------");
		AtualizarDados atualizarDados = new AtualizarDados();
		atualizarDados.execute();
		log.debug("------- Finishing Job----------------------");

	}


}
