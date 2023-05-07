package com.lab.snippet.多线程和异步.ExecutorService;

import com.lab.func.WatchEnable;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.concurrent.*;

@Component
public class CompletionServiceRun {

    @WatchEnable
    public void run() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

        completionService.submit(new TaskCallable("1aa8c994-281e-4fbb-a09b-cdf389eedf3b", 1000.0, 0.5));
        completionService.submit(new TaskCallable("321996c2-73c0-411a-8e66-fcfa04d94ae1", 5000.0, 1.0));
        completionService.submit(new TaskCallable("3df9d22e-17d9-4b48-a821-867974681d6e", 600.0, 1.2));

        for (int index = 0; index < 3; index++) {
            System.out.println(completionService.take().get());
        }

        executorService.shutdown();
    }
}
