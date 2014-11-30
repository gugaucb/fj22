package br.com.caelum.argentum.schedule;

import static org.quartz.DateBuilder.evenSecondDateAfterNow;
import static org.quartz.DateBuilder.evenMinuteDateAfterNow;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.argentum.mb.ApplicationResourcesBean;

public class Cron {
	public void iniciar() throws SchedulerException{
		 Logger log = LoggerFactory.getLogger(Cron.class);

	        log.info("------- Initializing ----------------------");

	        // First we must get a reference to a scheduler
	        SchedulerFactory sf = new StdSchedulerFactory();
	        Scheduler sched = sf.getScheduler();

	        log.info("------- Initialization Complete -----------");

	        // computer a time that is on the next round minute
	        Date runTime = evenSecondDateAfterNow();

	        log.info("------- Scheduling Job  -------------------");
	        // define the job and tie it to our HelloJob class
	        JobDetail job = newJob(AtualizaDadosJob.class)
	            .withIdentity("job1", "group1")
	            .build();
	        // Trigger the job to run on the next round minute
	        Trigger trigger = newTrigger()
	            .withIdentity("trigger1", "group1")
	            .startAt(runTime)
	            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
	            .build();
	        
	        // Tell quartz to schedule the job using our trigger
	        sched.scheduleJob(job, trigger);
	        log.info(job.getKey() + " will run at: " + runTime);  

	        // Start up the scheduler (nothing can actually run until the 
	        // scheduler has been started)
	        log.info("------- Started Scheduler -----------------");
	        
	        sched.start();

	        


	       
	}
}
