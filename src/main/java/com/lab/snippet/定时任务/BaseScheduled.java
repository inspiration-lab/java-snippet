package com.lab.snippet.定时任务;

import com.lab.snippet.多线程和异步.ExecutorService.TaskCallable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.function.BiFunction;

@Component
public class BaseScheduled {

    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    private static CompletionService<String> completionService;

    static{
        completionService = new ExecutorCompletionService<>(executorService);
    }

    @Scheduled(fixedDelayString = "3", timeUnit = TimeUnit.SECONDS)
    public void job() throws InterruptedException, ExecutionException {
        System.out.println("定时任务开始=" + Thread.currentThread().getName() + "-" + LocalDateTime.now());

        BiFunction<Integer, Integer, Callable<String>> function = (id, second) -> {
            return new Callable<String>() {
                @Override
                public String call() throws Exception {
                    TimeUnit.SECONDS.sleep(second);
                    return String.format("当前任务{ %s }已完成", id);
                }
            };
        };

        completionService.submit(function.apply(1,2));
        completionService.submit(function.apply(2,5));
        completionService.submit(function.apply(3,7));

        for (int index = 0; index < 3; index++) {
            System.out.println(completionService.take().get());
        }
    }
}
