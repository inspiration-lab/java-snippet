package com.lab.func;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.UUID;

@Aspect
@Component
public class WatchAspect {

    private static StopWatch stopWatch = new StopWatch(UUID.randomUUID().toString());

    @Pointcut(value = "@annotation(com.lab.func.WatchEnable)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before() {
        stopWatch.start();
    }

    @After("pointCut()")
    public void after() {
        stopWatch.stop();
        System.out.println(String.format("计时器{ %s }停止，耗时={ %sms }", stopWatch.getId(), stopWatch.getTotalTimeMillis()));
    }
}
