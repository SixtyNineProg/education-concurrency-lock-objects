package ru.clevertec.courses.basic;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;


public class HelloRunnable implements Runnable {

    private final int threadNum;

    public HelloRunnable(int threadNum) {
        this.threadNum = threadNum;
    }

    @SneakyThrows
    public void run() {
        System.out.println("Hello from " + threadNum + " " + Thread.currentThread().getName() + " !");
        Thread.sleep(3000L * threadNum);
        System.out.println("Goodbye from " + threadNum + " " + Thread.currentThread().getName() + " !");
    }

}