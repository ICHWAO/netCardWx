package com.code.common;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.code.model.JobBean;
import com.code.utils.PropertiesUtil;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;

public class QuartzPlugin implements IPlugin {
	private List<JobBean> jobs = new ArrayList<JobBean>();
	private SchedulerFactory sf;
	private static Scheduler scheduler;
	private String jobConfig;
	private String confConfig;
	private Map<String, String> jobProp;
 
	public QuartzPlugin(String jobConfig, String confConfig) {
		this.jobConfig = jobConfig;
		this.confConfig = confConfig;
	}
 
	public QuartzPlugin(String jobConfig) {
		this.jobConfig = jobConfig;
		this.confConfig = "/quartz_config.properties";
	}
 
	public QuartzPlugin() {
		String path = "";
		try {
			 path = PathKit.class.getClassLoader().getResource("").toURI().getPath();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.jobConfig = path+"//quartz_job.properties";
		this.confConfig = path+"//quartz_config.properties";
	}
 
	@SuppressWarnings("unchecked")
	public static void addJob(JobBean job) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobDesc(), job.getJobGroup());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 不存在，创建一个
			if (null == trigger) {
				Class<Job> j2 = (Class<Job>) Class.forName(job.getJobClass());
				JobDetail jobDetail = JobBuilder.newJob(j2).withIdentity(job.getJobDesc(), job.getJobGroup()).build();
				jobDetail.getJobDataMap().put("scheduleJob", job);
 
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
 
				// 按新的cronExpression表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobDesc(), job.getJobGroup())
						.withSchedule(scheduleBuilder).build();
				try {
					scheduler.scheduleJob(jobDetail, trigger);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// Trigger已存在，那么更新相应的定时设置
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
 
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
 
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	@Override
	public boolean start() {
		loadJobsFromProperties();
		startJobs();
		return true;
	}
 
	private void startJobs() {
		try {
			if (StrKit.notBlank(confConfig)) {
				sf = new StdSchedulerFactory(confConfig);
			} else {
				sf = new StdSchedulerFactory();
			}
			scheduler = sf.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		for (JobBean entry : jobs) {
			addJob(entry);
		}
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
 
	private void loadJobsFromProperties() {
		if (StrKit.isBlank(jobConfig)) {
			return;
		}
		String jobArray = PropertiesUtil.getValue("jobArray");
		if (StrKit.isBlank(jobArray)) {
			return;
		}
		String[] jobArrayList = jobArray.split(",");
		for (String jobName : jobArrayList) {
			jobs.add(createJobBean(jobName));
		}
	}
 
	private JobBean createJobBean(String key) {
		JobBean job = new JobBean();
		job.setJobId(PropertiesUtil.getValue(key + ".id"));
		job.setJobClass(PropertiesUtil.getValue(key + ".job"));
		job.setCronExpression(PropertiesUtil.getValue(key + ".cron"));
		job.setJobGroup(PropertiesUtil.getValue(key));
		job.setJobDesc(PropertiesUtil.getValue(key + ".desc"));
		job.setJobGroup(PropertiesUtil.getValue(key + ".group"));
		return job;
	}
 
	@Override
	public boolean stop() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}
}
