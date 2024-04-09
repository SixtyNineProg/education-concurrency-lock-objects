package ru.clevertec.courses.syncro.callable;

import java.util.concurrent.Callable;

class CallableClass implements Callable<String> {
    private final int threadNum;

    public CallableClass(int threadNum) {
        this.threadNum = threadNum;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(2000L * threadNum);
        return Thread.currentThread().getName();
    }
}
