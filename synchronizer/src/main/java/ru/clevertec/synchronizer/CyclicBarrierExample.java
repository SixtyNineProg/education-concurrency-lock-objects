package ru.clevertec.synchronizer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
public class CyclicBarrierExample {

    static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {
        Runnable action = () -> log.info("На старт!");
        CyclicBarrier barrier = new CyclicBarrier(3, action);
        Runnable task = () -> {
            try {
                if (i.incrementAndGet() == 5) {
                    System.out.println("i=" + i);
                    barrier.reset();
                }
                log.info("run");
                barrier.await();
                log.info("Finished");
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        };
        log.info("Limit: " + barrier.getParties());

        IntStream.range(0, 6).forEach(i -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(task).start();
        });
    }
}