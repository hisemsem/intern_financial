//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.core.schedule;

import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class ApiSchedulingConfig implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(ApiSchedulingConfig.class);
    @Autowired
    private SchedulerService schedulerService;

    public ApiSchedulingConfig() {
    }

    @Bean(
        name = {"lonExternalMasterApiSync"}
    )
    public MethodInvokingJobDetailFactoryBean lonExternalMasterApiSync() {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setTargetObject(this.schedulerService);
        jobDetail.setTargetMethod("executeMasterScheduledTask");
        jobDetail.setConcurrent(false);
        return jobDetail;
    }

    @Bean(
        name = {"coreBankApiMasterDetail"}
    )
    public CronTriggerFactoryBean coreBankApiMasterDetail(@Qualifier("lonExternalMasterApiSync") MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();
        ctfb.setJobDetail(jobDetail.getObject());
        ctfb.setCronExpression("0 0 0 1 * ?");
        return ctfb;
    }

    @Bean
    public SchedulerFactoryBean coreBankApiMasterScheduler(@Qualifier("coreBankApiMasterDetail") CronTriggerFactoryBean triggerBean) throws Exception {
        SchedulerFactoryBean sfb = new SchedulerFactoryBean();
        sfb.setTriggers(new Trigger[]{triggerBean.getObject()});
        sfb.setAutoStartup(true);
        sfb.setSchedulerName("coreBankApiMasterScheduler");
        return sfb;
    }

    @Bean(
        name = {"lonExternalItemApiSync"}
    )
    public MethodInvokingJobDetailFactoryBean lonExternalItemApiSync() {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setTargetObject(this.schedulerService);
        jobDetail.setTargetMethod("executeItemScheduledTask");
        jobDetail.setConcurrent(false);
        return jobDetail;
    }

    @Bean(
        name = {"coreBankApiItemDetail"}
    )
    public CronTriggerFactoryBean coreBankApiItemDetail(@Qualifier("lonExternalItemApiSync") MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();
        ctfb.setJobDetail(jobDetail.getObject());
        ctfb.setCronExpression("0 0 0 * * ?");
        return ctfb;
    }

    @Bean
    public SchedulerFactoryBean coreBankApiItemScheduler(@Qualifier("coreBankApiItemDetail") CronTriggerFactoryBean triggerBean) throws Exception {
        SchedulerFactoryBean sfb = new SchedulerFactoryBean();
        sfb.setTriggers(new Trigger[]{triggerBean.getObject()});
        sfb.setAutoStartup(true);
        sfb.setSchedulerName("coreBankApiItemScheduler");
        return sfb;
    }

    @Bean(
        name = {"lonExternalDataApiCycleYSync"}
    )
    public MethodInvokingJobDetailFactoryBean lonExternalDataApiCycleYSync() {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setTargetObject(this.schedulerService);
        jobDetail.setTargetMethod("executeDataCycleYScheduledTask");
        jobDetail.setConcurrent(false);
        return jobDetail;
    }

    @Bean(
        name = {"coreBankApiDataCycleYDetail"}
    )
    public CronTriggerFactoryBean coreBankApiDataCycleYDetail(@Qualifier("lonExternalDataApiCycleYSync") MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();
        ctfb.setJobDetail(jobDetail.getObject());
        ctfb.setCronExpression("0 0 0 1 * ?");
        return ctfb;
    }

    @Bean
    public SchedulerFactoryBean coreBankApiDataCycleYScheduler(@Qualifier("coreBankApiDataCycleYDetail") CronTriggerFactoryBean triggerBean) throws Exception {
        SchedulerFactoryBean sfb = new SchedulerFactoryBean();
        sfb.setTriggers(new Trigger[]{triggerBean.getObject()});
        sfb.setAutoStartup(true);
        sfb.setSchedulerName("coreBankApiDataCycleYScheduler");
        return sfb;
    }

    @Bean(
        name = {"lonExternalDataApiCycleMSync"}
    )
    public MethodInvokingJobDetailFactoryBean lonExternalDataApiCycleMSync() {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setTargetObject(this.schedulerService);
        jobDetail.setTargetMethod("executeDataCycleMScheduledTask");
        jobDetail.setConcurrent(false);
        return jobDetail;
    }

    @Bean(
        name = {"coreBankApiDataCycleMDetail"}
    )
    public CronTriggerFactoryBean coreBankApiDataCycleMDetail(@Qualifier("lonExternalDataApiCycleMSync") MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();
        ctfb.setJobDetail(jobDetail.getObject());
        ctfb.setCronExpression("0 0 0 ? * FRI");
        return ctfb;
    }

    @Bean
    public SchedulerFactoryBean coreBankApiDataCycleMScheduler(@Qualifier("coreBankApiDataCycleMDetail") CronTriggerFactoryBean triggerBean) throws Exception {
        SchedulerFactoryBean sfb = new SchedulerFactoryBean();
        sfb.setTriggers(new Trigger[]{triggerBean.getObject()});
        sfb.setAutoStartup(true);
        sfb.setSchedulerName("coreBankApiDataCycleMScheduler");
        return sfb;
    }

    @Bean(
        name = {"lonExternalDataCycleDApiSync"}
    )
    public MethodInvokingJobDetailFactoryBean lonExternalDataCycleDApiSync() {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setTargetObject(this.schedulerService);
        jobDetail.setTargetMethod("executeDataCycleDScheduledTask");
        jobDetail.setConcurrent(false);
        return jobDetail;
    }

    @Bean(
        name = {"coreBankApiDataCycleDDetail"}
    )
    public CronTriggerFactoryBean coreBankApiDataCycleDDetail(@Qualifier("lonExternalDataCycleDApiSync") MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();
        ctfb.setJobDetail(jobDetail.getObject());
        ctfb.setCronExpression("0 0 3 * * ?");
        return ctfb;
    }

    @Bean
    public SchedulerFactoryBean coreBankApiDataCycleDScheduler(@Qualifier("coreBankApiDataCycleDDetail") CronTriggerFactoryBean triggerBean) throws Exception {
        SchedulerFactoryBean sfb = new SchedulerFactoryBean();
        sfb.setTriggers(new Trigger[]{triggerBean.getObject()});
        sfb.setAutoStartup(true);
        sfb.setSchedulerName("coreBankApiDataCycleDScheduler");
        return sfb;
    }

    @Bean(
        name = {"lonExternalDataCycleQApiSync"}
    )
    public MethodInvokingJobDetailFactoryBean lonExternalDataCycleQApiSync() {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setTargetObject(this.schedulerService);
        jobDetail.setTargetMethod("executeDataCycleQScheduledTask");
        jobDetail.setConcurrent(false);
        return jobDetail;
    }

    @Bean(
        name = {"coreBankApiDataCycleQDetail"}
    )
    public CronTriggerFactoryBean coreBankApiDataCycleQDetail(@Qualifier("lonExternalDataCycleQApiSync") MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();
        ctfb.setJobDetail(jobDetail.getObject());
        ctfb.setCronExpression("0 0 2 1 * ?");
        return ctfb;
    }

    @Bean
    public SchedulerFactoryBean coreBankApiDataCycleQScheduler(@Qualifier("coreBankApiDataCycleQDetail") CronTriggerFactoryBean triggerBean) throws Exception {
        SchedulerFactoryBean sfb = new SchedulerFactoryBean();
        sfb.setTriggers(new Trigger[]{triggerBean.getObject()});
        sfb.setAutoStartup(true);
        sfb.setSchedulerName("coreBankApiDataCycleQScheduler");
        return sfb;
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }

    public void afterPropertiesSet() throws Exception {
        log.debug("--------------------------------------");
        log.debug("ContextSchedulerConfig..Init..");
        log.debug("--------------------------------------");
    }
}
