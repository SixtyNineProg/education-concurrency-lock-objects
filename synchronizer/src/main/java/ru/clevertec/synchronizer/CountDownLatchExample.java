package ru.clevertec.synchronizer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class CountDownLatchExample {
    private static final CountDownLatch LATCH = new CountDownLatch(4);

    public static void main(String[] args) {
        Runnable task = () -> {
            try {
                var threadName = Thread.currentThread().getName();
                log.info("start: " + threadName);

                TimeUnit.SECONDS.sleep((long) (Math.random() * 10L));

                LATCH.countDown();
                log.info("Countdown: " + LATCH.getCount());
                LATCH.await();

                TimeUnit.MILLISECONDS.sleep(100L);

                log.info("run after: " + threadName);
                TimeUnit.SECONDS.sleep((long) (Math.random() * 10L));
                log.info("Finished:" + threadName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        IntStream.range(0, 5).forEach(i -> new Thread(task).start());
//        IntStream.range(0, 2).forEach(i -> new Thread(task).start());
    }
}