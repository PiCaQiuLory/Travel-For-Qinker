package com.ss.quartz;

import com.ss.mapper.SpiderDao;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;




@Component
public class UpdateAppWechatStatusJob implements Job{
	
	SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private static boolean flag = true;
	@Resource
	private SpiderDao spiderDao;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		spiderDao.updateAppWechatStatus();
		System.out.println("----------------------------------------update database");
		if(flag) {
			modifyJobTime("hw_job1", "hw_group1", "hw_trigger1", "hw_trigger_group1", "*/10 * * * * ?");
		}
		flag = false;
	}
	
	public void modifyJobTime(String jobName, 
            String jobGroupName, String triggerName, String triggerGroupName, String cron) {  
        try {  
        	ApplicationContext context = new ClassPathXmlApplicationContext("spring-quartz_l.xml");
        	Scheduler sched = (Scheduler) context.getBean("scheduler");
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
            if (trigger == null) {  
                return;  
            }  

            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(cron)) { 
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器  
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组  
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定  
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                sched.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
                //Class<? extends Job> jobClass = jobDetail.getJobClass();  
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  

}
