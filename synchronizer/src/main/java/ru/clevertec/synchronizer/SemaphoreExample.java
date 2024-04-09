package ru.clevertec.synchronizer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

@Slf4j
public class SemaphoreExample {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        Runnable task = () -> {
            try {
                var threadName = Thread.currentThread().getName();
                log.info("run");
                semaphore.acquire();
                log.info("-------lock: " + threadName);
                log.info("==================== availablePermits: " + semaphore.availablePermits());
                Thread.sleep(1000);
                log.info("++++++++unlock: " + threadName);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        IntStream.range(0, 20).forEach(i -> new Thread(task).start());
    }
}