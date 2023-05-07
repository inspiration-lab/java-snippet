package com.lab.snippet.多线程和异步.ExecutorService;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public record TaskCallable(String taskID, Double value, Double rate) implements Callable<String> {
    @Override
    public String call() throws Exception {
        TimeUnit.MILLISECONDS.sleep(value.longValue());
        return String.format("taskID={ %s } result={ %s*%s -> %s }", taskID, value, rate, value * rate);
    }
}
