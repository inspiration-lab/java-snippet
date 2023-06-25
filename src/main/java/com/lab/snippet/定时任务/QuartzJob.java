package com.lab.snippet.定时任务;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class QuartzJob extends QuartzJobBean {
    
    @Override
    protected void executeInternal(JobExecutionContext context) {
        // System.out.println(Thread.currentThread().getName() + "-" + LocalDateTime.now());
    }
    
}
