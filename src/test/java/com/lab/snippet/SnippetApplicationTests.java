package com.lab.snippet;

import com.lab.snippet.多线程和异步.ExecutorService.AsyncRun;
import com.lab.snippet.多线程和异步.ExecutorService.CompletionServiceRun;
import com.lab.snippet.多线程和异步.ExecutorService.ExecutorServiceRun;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
class SnippetApplicationTests {

    @Autowired
    ExecutorServiceRun executorServiceRun;

    @Autowired
    CompletionServiceRun completionServiceRun;

    @Autowired
    AsyncRun asyncRun;

    @Test
    void executorServiceRun() throws ExecutionException, InterruptedException {
        executorServiceRun.run();
    }

    @Test
    void completionServiceRun() throws ExecutionException, InterruptedException {
        completionServiceRun.run();
    }

    @Test
    void asyncRun() throws ExecutionException, InterruptedException {
        asyncRun.run();
    }
}
