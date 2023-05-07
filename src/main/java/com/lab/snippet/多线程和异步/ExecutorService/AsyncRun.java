package com.lab.snippet.多线程和异步.ExecutorService;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class AsyncRun {

    public void run() throws ExecutionException, InterruptedException {

        // 定义队列
        LinkedBlockingDeque<TaskCallable> linkedBlockingDeque = new LinkedBlockingDeque<>();
        // 模拟任务数量
        final int taskCount = 5;
        // 获取CPU核心数
        int coreNum = Runtime.getRuntime().availableProcessors();
        // 定义线程池
        ThreadPoolExecutor customPool = new ThreadPoolExecutor(coreNum, coreNum, coreNum, TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
                new ThreadFactory() {
                    private final AtomicInteger customPoolCurrent = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable runnable) {
                        Thread thread = new Thread(runnable, String.format("customPool-thread-%s", customPoolCurrent.getAndIncrement()));
                        thread.setPriority(Thread.MAX_PRIORITY);
                        return thread;
                    }
                }, new ThreadPoolExecutor.CallerRunsPolicy());

        CompletionService<String> completionService = new ExecutorCompletionService<>(customPool);

        var pushElement = CompletableFuture.runAsync(() -> {
            for (int index = 0; index < taskCount; index++) {
                var value = Math.random() * 1000;
                var taskCallable = new TaskCallable(UUID.randomUUID().toString(), value, Math.random());
                linkedBlockingDeque.push(taskCallable);
                System.out.println(String.format("线程 %s 完成 %s 入队", Thread.currentThread().getName(), taskCallable));
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        var submitTask = CompletableFuture.supplyAsync(() -> {
            while (true) {
                try {
                    boolean flagTask = pushElement.isDone();
                    boolean flagPeek = Optional.ofNullable(linkedBlockingDeque.peek()).isPresent();
                    // 当【入队任务完成】且【队列没有元素可取】时结束
                    if (flagTask == true && flagPeek == false) {
                        break;
                    }
                    if (flagPeek == false) {
                        // 仅【队列没有元素可取】时，跳过
                        continue;
                    }
                    // 【队列有元素可取】时，添加任务到线程池
                    TimeUnit.MILLISECONDS.sleep(500);
                    var taskCallable = linkedBlockingDeque.take();
                    completionService.submit(taskCallable);
                    System.out.println(String.format("线程 %s 将 %s 提交到线程池", Thread.currentThread().getName(), taskCallable));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        });

        boolean submit = submitTask.get();
        if (!submit) {
            for (int index = 0; index < taskCount; index++) {
                System.out.println(completionService.take().get());
            }
        }

        customPool.shutdown();
        customPool.awaitTermination(3, TimeUnit.SECONDS);
    }
}
