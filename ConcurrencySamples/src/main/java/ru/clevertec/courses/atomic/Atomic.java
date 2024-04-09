package ru.clevertec.courses.atomic;

import ru.clevertec.courses.ConcurrentUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * @author Benjamin Winterberg
 */
public class Atomic {

    private static final int NUM_INCREMENTS = 1000;
    private static final int NUM_THREADS = 6;

    private static final AtomicInteger atomicInt = new AtomicInteger(0);
    private static final AtomicReference<Double> atomic = new AtomicReference<>();

    public static void main(String[] args) {
        testIncrement();
        testAccumulate();
        testUpdate();
    }

    private static void testIncrement() {
        atomicInt.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        Runnable increment = () -> atomicInt.incrementAndGet();

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(increment));

        ConcurrentUtils.stop(executor);

        System.out.format("Increment: Expected=%d; Is=%d\n", NUM_INCREMENTS, atomicInt.get());
    }

    private static void testUpdate() {
        atomicInt.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> {
                    Runnable task = () ->
                            atomicInt.updateAndGet(n -> n + 2);
                    executor.submit(task);
                });

        ConcurrentUtils.stop(executor);

        System.out.format("Update: Expected=2000; Is= %d\n", atomicInt.get());
    }

    private static void testAccumulate() {
        atomicInt.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> {
                    Runnable task = () ->
                            atomicInt.accumulateAndGet(i, Integer::sum);
                    executor.submit(task);
                });

        ConcurrentUtils.stop(executor);

        System.out.format("Accumulate: Expected=499500; Is= %d\n", atomicInt.get());
    }

}