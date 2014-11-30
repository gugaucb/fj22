package br.com.caelum.argentum.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.reader.LeitorXML;
import br.com.caelum.argentum.schedule.AtualizaDadosJob;
import br.com.caelum.argentum.writter.GeradorAleatorioXML;

public class NegociacaoBovespaFactory {
	Logger log;
	
	
	
	public NegociacaoBovespaFactory() {
		log = LoggerFactory.getLogger(NegociacaoBovespaFactory.class);
	}

	public List<Negociacao> carregarNegociacaoExistentes() throws FileNotFoundException{
		LeitorXML leitor = new LeitorXML();
		if(new File(GeradorAleatorioXML.FILE).exists()){
			return leitor.carrega(new FileInputStream(GeradorAleatorioXML.FILE));
		}else{
			return new ArrayList<Negociacao>();
		}
	}
	
	public List<Negociacao> constroiNegociacoesNovo() throws IOException{
		List<Negociacao> negociacoes = new ArrayList<Negociacao>();
		String resultadoConsulta;
		try {
			
			//negociacoes = carregarNegociacaoExistentes();
			log.debug("Iniciando Download");
			log.debug("Funcionado Localmente - Habilitar download");
			resultadoConsulta = new RecuperaValores().recupera();
			//resultadoConsulta = "D=2013/Jul/18&V=10:01:00@16@-0.37|10:02:23@15.99@-0.43|10:03:00@16.04@-0.12|10:04:02@16.04@-0.12|10:05:01@16.03@-0.18|10:06:46@16.02@-0.24|10:07:20@16.02@-0.24|10:08:19@16@-0.37|10:09:01@16@-0.37|10:10:08@16@-0.37|10:11:00@15.98@-0.49|10:12:00@16.01@-0.31|10:13:12@16@-0.37|10:14:02@16@-0.37|10:15:02@16@-0.37|10:16:04@16@-0.37|10:17:04@15.99@-0.43|10:18:05@15.98@-0.49|10:19:01@16@-0.37|10:20:00@16.01@-0.31|10:21:00@15.99@-0.43|10:22:03@16@-0.37|10:23:03@15.99@-0.43|10:24:09@16@-0.37|10:25:00@16@-0.37|10:26:00@16@-0.37|10:27:02@16@-0.37|10:28:00@16.01@-0.31|10:29:09@16.02@-0.24|10:30:02@16.05@-0.06|10:31:01@16.11@0.31|10:32:02@16.12@0.37|10:33:01@16.13@0.43|10:34:00@16.1@0.24|10:35:07@16.13@0.43|10:36:01@16.13@0.43|10:37:04@16.12@0.37|10:38:00@16.13@0.43|10:39:03@16.15@0.56|10:40:00@16.14@0.49|10:41:03@16.15@0.56|10:42:00@16.14@0.49|10:43:17@16.11@0.31|10:44:04@16.12@0.37|10:45:00@16.12@0.37|10:46:23@16.13@0.43|10:47:10@16.15@0.56|10:48:11@16.16@0.62|10:49:25@16.15@0.56|10:50:17@16.17@0.68|10:51:04@16.15@0.56|10:52:03@16.12@0.37|10:53:01@16.13@0.43|10:54:02@16.11@0.31|10:55:00@16.12@0.37|10:56:05@16.13@0.43|10:57:00@16.13@0.43|10:58:04@16.14@0.49|10:59:13@16.14@0.49|11:00:00@16.14@0.49|11:01:04@16.15@0.56|11:02:01@16.14@0.49|11:03:00@16.17@0.68|11:04:13@16.17@0.68|11:05:00@16.15@0.56|11:06:02@16.14@0.49|11:07:23@16.13@0.43|11:08:10@16.13@0.43|11:09:02@16.12@0.37|11:10:01@16.15@0.56|11:11:07@16.13@0.43|11:12:03@16.13@0.43|11:13:11@16.16@0.62|11:14:02@16.17@0.68|11:15:02@16.17@0.68|11:16:04@16.17@0.68|11:17:05@16.15@0.56|11:18:00@16.15@0.56|11:19:03@16.15@0.56|11:20:01@16.17@0.68|11:21:04@16.17@0.68|11:22:00@16.19@0.8|11:23:00@16.2@0.87|11:24:00@16.19@0.8|11:25:01@16.17@0.68|11:26:03@16.18@0.74|11:27:23@16.19@0.8|11:28:23@16.18@0.74|11:29:01@16.18@0.74|11:30:00@16.18@0.74|11:31:00@16.19@0.8|11:32:01@16.17@0.68|11:33:03@16.17@0.68|11:34:00@16.17@0.68|11:35:00@16.16@0.62|11:36:09@16.15@0.56|11:37:11@16.16@0.62|11:38:03@16.16@0.62|11:39:02@16.16@0.62|11:40:00@16.15@0.56|11:41:00@16.15@0.56|11:42:00@16.15@0.56|11:43:09@16.15@0.56|11:44:02@16.15@0.56|11:45:02@16.15@0.56|11:46:04@16.17@0.68|11:47:00@16.19@0.8|11:48:00@16.19@0.8|11:49:17@16.19@0.8|11:50:00@16.18@0.74|11:51:02@16.18@0.74|11:52:00@16.23@1.05|11:53:02@16.26@1.24|11:54:00@16.25@1.18|11:55:00@16.25@1.18|11:56:10@16.29@1.43|11:57:00@16.28@1.36|11:58:00@16.26@1.24|11:59:01@16.25@1.18|12:00:00@16.26@1.24|12:01:00@16.29@1.43|12:02:02@16.24@1.12|12:03:02@16.26@1.24|12:04:00@16.25@1.18|12:05:00@16.22@0.99|12:06:00@16.21@0.93|12:07:01@16.2@0.87|12:08:01@16.19@0.8|12:09:01@16.19@0.8|12:10:00@16.21@0.93|12:11:08@16.2@0.87|12:12:05@16.21@0.93|12:13:05@16.22@0.99|12:14:00@16.23@1.05|12:15:00@16.23@1.05|12:16:02@16.23@1.05|12:17:03@16.24@1.12|12:18:08@16.23@1.05|12:19:05@16.24@1.12|12:20:01@16.24@1.12|12:21:07@16.22@0.99|12:22:00@16.23@1.05|12:23:03@16.21@0.93|12:24:20@16.22@0.99|12:25:00@16.22@0.99|12:26:00@16.22@0.99|12:27:01@16.21@0.93|12:28:01@16.21@0.93|12:29:01@16.2@0.87|12:30:00@16.21@0.93|12:31:03@16.21@0.93|12:32:01@16.22@0.99|12:33:08@16.22@0.99|12:34:00@16.24@1.12|12:35:00@16.24@1.12|12:36:01@16.26@1.24|12:37:00@16.27@1.3|12:38:04@16.28@1.36|12:39:01@16.25@1.18|12:40:00@16.25@1.18|12:41:15@16.24@1.12|12:42:02@16.25@1.18|12:43:00@16.24@1.12|12:44:19@16.24@1.12|12:45:01@16.23@1.05|12:46:07@16.24@1.12|12:47:02@16.24@1.12|12:48:02@16.24@1.12|12:49:05@16.26@1.24|12:50:00@16.24@1.12|12:51:00@16.25@1.18|12:52:01@16.26@1.24|12:53:02@16.27@1.3|12:54:01@16.26@1.24|12:55:01@16.26@1.24|12:56:01@16.26@1.24|12:57:00@16.25@1.18|12:58:01@16.24@1.12|12:59:00@16.24@1.12|13:00:00@16.22@0.99|13:01:03@16.23@1.05|13:02:00@16.22@0.99|13:03:01@16.22@0.99|13:04:00@16.21@0.93|13:05:00@16.19@0.8|13:06:13@16.17@0.68|13:07:01@16.19@0.8|13:08:06@16.21@0.93|13:09:09@16.21@0.93|13:10:01@16.21@0.93|13:11:00@16.2@0.87|13:12:19@16.2@0.87|13:13:00@16.2@0.87|13:14:00@16.21@0.93|13:15:33@16.22@0.99|13:16:08@16.21@0.93|13:17:07@16.21@0.93|13:18:00@16.21@0.93|13:19:02@16.22@0.99|13:20:07@16.22@0.99|13:21:03@16.21@0.93|13:22:00@16.2@0.87|13:23:00@16.19@0.8|13:24:00@16.18@0.74|13:25:01@16.18@0.74|13:26:02@16.18@0.74|13:27:00@16.2@0.87|13:28:01@16.2@0.87|13:29:03@16.19@0.8|13:30:00@16.19@0.8|13:31:03@16.18@0.74|13:32:06@16.17@0.68|13:33:05@16.19@0.8|13:34:03@16.18@0.74|13:35:03@16.18@0.74|13:36:04@16.18@0.74|13:37:02@16.18@0.74|13:38:00@16.18@0.74|13:39:00@16.17@0.68|13:40:00@16.19@0.8|13:41:00@16.18@0.74|13:42:04@16.19@0.8|13:43:02@16.19@0.8|13:44:00@16.19@0.8|13:45:00@16.17@0.68|13:46:08@16.18@0.74|13:47:02@16.18@0.74|13:48:16@16.19@0.8|13:49:05@16.19@0.8|13:50:00@16.19@0.8|13:51:01@16.19@0.8|13:52:08@16.19@0.8|13:53:02@16.2@0.87|13:54:00@16.19@0.8|13:55:00@16.19@0.8|13:56:00@16.2@0.87|13:57:06@16.21@0.93|13:58:06@16.22@0.99|13:59:05@16.21@0.93|14:00:00@16.2@0.87|14:01:03@16.19@0.8|14:02:00@16.19@0.8|14:03:01@16.19@0.8|14:04:09@16.19@0.8|14:05:07@16.21@0.93|14:06:04@16.19@0.8|14:07:00@16.19@0.8|14:08:00@16.19@0.8|14:09:08@16.2@0.87|14:10:04@16.2@0.87|14:11:01@16.2@0.87|14:12:01@16.21@0.93|14:13:03@16.21@0.93|14:14:01@16.21@0.93|14:15:00@16.2@0.87|14:16:17@16.19@0.8|14:17:04@16.19@0.8|14:18:05@16.19@0.8|14:19:04@16.19@0.8|14:20:00@16.19@0.8|14:21:10@16.18@0.74|14:22:03@16.16@0.62|14:23:01@16.17@0.68|14:24:00@16.16@0.62|14:25:00@16.15@0.56|14:26:02@16.15@0.56|14:27:10@16.15@0.56|14:28:09@16.14@0.49|14:29:02@16.13@0.43|14:30:06@16.14@0.49|14:31:03@16.14@0.49|14:32:02@16.14@0.49|14:33:01@16.16@0.62|14:34:00@16.16@0.62|14:35:00@16.15@0.56|&CE=0&ME=";
			log.debug("Download finalizado");
			log.debug(("Resultado da Consulta "+resultadoConsulta));
		} catch (IOException e) {
			resultadoConsulta = "";
			throw new IOException(e);
		}//"D=2013/jul/15&V=10:19@45685@0.33|10:20@45767@0.51|10:21@45815@0.62|10:22@45856@0.71|10:23@45816@0.62|10:24@45832@0.66|10:25@45798@0.58|10:26@45844@0.68|10:27@45862@0.72|10:28@45873@0.75|10:29@45900@0.81|10:30@45886@0.78|10:31@45944@0.90|10:32@45958@0.93|10:33@45987@1.00&A=45533&MN=45533&MX=45987&MD=45824&v=&d=15 de Julho de 2013&s=Pregão em Andamento&CE=0&ME=";
		
		StringTokenizer stringTokenizer = new StringTokenizer(resultadoConsulta, "&");
		Calendar data = Calendar.getInstance();
		String[] elementos = new String[stringTokenizer.countTokens()];
		int i = 0;
		while(stringTokenizer.hasMoreTokens()){
			elementos[i++] = stringTokenizer.nextToken();
			
			while(stringTokenizer.hasMoreElements()){
				elementos[i++] = stringTokenizer.nextElement().toString();
			}
		}
		//System.out.println("Inicio");
		data = processarData(elementos[0]);
		if(!negociacoes.isEmpty()){
			List<Negociacao> temp = processarVariacao(elementos[1], data);
			negociacoes.addAll(temp.subList(negociacoes.size()-1, temp.size()-1));
		}else{
			negociacoes = processarVariacao(elementos[1], data);
		}
		new GeradorAleatorioXML().gerarXMLNegociacoes(negociacoes);
		//System.out.println("Fim");
		return negociacoes;
	}
	
	
	public List<Negociacao> constroiNegociacoesSemGravar() throws IOException{
		List<Negociacao> negociacoes = new ArrayList<Negociacao>();
		String resultadoConsulta;
		try {
			negociacoes = carregarNegociacaoExistentes();
			resultadoConsulta = new RecuperaValores().recupera();
			//System.out.println(resultadoConsulta);
		} catch (IOException e) {
			resultadoConsulta = "";
			throw new IOException(e);
		}//"D=2013/jul/15&V=10:19@45685@0.33|10:20@45767@0.51|10:21@45815@0.62|10:22@45856@0.71|10:23@45816@0.62|10:24@45832@0.66|10:25@45798@0.58|10:26@45844@0.68|10:27@45862@0.72|10:28@45873@0.75|10:29@45900@0.81|10:30@45886@0.78|10:31@45944@0.90|10:32@45958@0.93|10:33@45987@1.00&A=45533&MN=45533&MX=45987&MD=45824&v=&d=15 de Julho de 2013&s=Pregão em Andamento&CE=0&ME=";
		
		StringTokenizer stringTokenizer = new StringTokenizer(resultadoConsulta, "&");
		Calendar data = Calendar.getInstance();
		String[] elementos = new String[stringTokenizer.countTokens()];
		int i = 0;
		while(stringTokenizer.hasMoreTokens()){
			elementos[i++] = stringTokenizer.nextToken();
			
			while(stringTokenizer.hasMoreElements()){
				elementos[i++] = stringTokenizer.nextElement().toString();
			}
		}
		//System.out.println("Inicio");
		data = processarData(elementos[0]);
		negociacoes = processarVariacao(elementos[1], data);
		new GeradorAleatorioXML().gerarXMLNegociacoes(negociacoes);
		//System.out.println("Fim");
		return negociacoes;
	}
	
	private Calendar processarData(String elemento){
		Calendar data = Calendar.getInstance();
		StringTokenizer primeiroCampo = new StringTokenizer(elemento,"=");
		if(primeiroCampo.nextElement().toString().equals("D")){
		//System.out.println("data: "+);
			StringTokenizer dateStringTokenizer = new StringTokenizer(primeiroCampo.nextElement().toString(),"/");
			int ano = Integer.parseInt(dateStringTokenizer.nextElement().toString());
			//System.out.println("Ano: "+ano);
			int mes = traduzMes(dateStringTokenizer.nextElement().toString());
			//System.out.println("Mes: "+mes);
			int dia = Integer.parseInt(dateStringTokenizer.nextElement().toString());
			//System.out.println("Dia: "+dia);
			
			data.set(Calendar.YEAR, ano);
			data.set(Calendar.MONTH, mes);
			data.set(Calendar.DAY_OF_MONTH, dia);
		}
		return data;
	}
	
	private List<Negociacao> processarVariacao(String variacoes, Calendar data){
		
		
		List<Negociacao> negociacoes = new ArrayList<Negociacao>();
		StringTokenizer elemento1 = new StringTokenizer(variacoes,"=");
		if(elemento1.nextElement().equals("V")){
			StringTokenizer negocios = new StringTokenizer(elemento1.nextToken(),"|");
			//System.out.println(negocios);
			while(negocios.hasMoreElements()){
				Calendar dataInterna = (Calendar)data.clone();
				StringTokenizer camposNegocio = new StringTokenizer(negocios.nextElement().toString(),"@");
				StringTokenizer horaStringTokenizer = new StringTokenizer(camposNegocio.nextElement().toString(),":");
				String hora = horaStringTokenizer.nextElement().toString();
				//System.out.println(hora);
				String minuto = horaStringTokenizer.nextElement().toString();
				//System.out.println(minuto);
				dataInterna.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
				dataInterna.set(Calendar.MINUTE, Integer.parseInt(minuto));
				dataInterna.set(Calendar.SECOND, 0);
				Negociacao negociacao = new Negociacao(Double.parseDouble(camposNegocio.nextElement().toString()), new Random().nextInt(1000), dataInterna);
				negociacoes.add(negociacao);
				
				
				//	System.out.print(camposNegocio.nextElement()+" $ ");
					
				//System.out.println("");
				
			}
			
		}
		log.debug(("Quant Negociacoes Recuperadas "+negociacoes.size()));
		return negociacoes;
	}
	
	private int traduzMes(String mes){
		switch (mes) {
		case "Jan" :
			return 0;
		case "Fev" :
			return 1;
		case "Mar" :
			return 2;
		case "Abr" :
			return 3;
		case "Mai" :
			return 4;
		case "Jun" :
			return 5;
		case "Jul" :
			return 6;	
		case "Ago" :
			return 7;
		case "Set" :
			return 8;
		case "Out" :
			return 9;
		case "Nov" :
			return 10;
		case "Dec" :
			return 11;

		default:
			return 0;
		}
	}
	
}
