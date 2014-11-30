package br.com.caelum.argentum.listeners;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.quartz.SchedulerException;

import br.com.caelum.argentum.mb.ApplicationResourcesBean;
import br.com.caelum.argentum.schedule.Cron;

public class StartupApplication implements SystemEventListener {
	@ManagedProperty(value="#{applicationResourcesBean}")
	ApplicationResourcesBean applicationResourcesBean;
	
	ApplicationResourcesBean applicationResourcesBean2;
	
	@Override
	public boolean isListenerForSource(Object source) {
		// TODO Auto-generated method stub
		return (source instanceof Application);
	}
	
	@PostConstruct
	public void init(){
		//applicationResourcesBean2 = applicationResourcesBean;
	}
	
	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		System.out.println("StartupApplication.processEvent()");
		
		if(event instanceof PostConstructApplicationEvent){
			Cron cron = new Cron();
			try {
				cron.iniciar();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}

	}

	public void setApplicationResourcesBean(
			ApplicationResourcesBean applicationResourcesBean) {
		//this.applicationResourcesBean = applicationResourcesBean;
	}
	
	
	

}
