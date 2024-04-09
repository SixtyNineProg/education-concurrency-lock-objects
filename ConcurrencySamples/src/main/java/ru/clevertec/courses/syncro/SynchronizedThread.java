package ru.clevertec.courses.syncro;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SynchronizedThread {

    static class CommonObject {
        int counter = 0;
    }

    static class CounterThread implements Runnable {
        private final CommonObject resource;

        CounterThread(CommonObject res) {
            this.resource = res;
        }

        @Override
        public void run() {
            synchronized (resource) {
//            resource.counter = 1;
                for (int i = 1; i < 5; i++) {
                    System.out.printf("'%s' - %d\n", Thread.currentThread().getName(), resource.counter);
                    resource.counter++;
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        System.err.println("Exception!!!");
                    }
                }
            }
        }

        public synchronized void doSomething() {

        }

    }


    public static void main(String[] args) {
        CommonObject commonObject = new CommonObject();
        IntStream.rangeClosed(1, 3)
                .mapToObj(threadIndex -> new Thread(new CounterThread(commonObject), "Счетчик" + threadIndex))
                .forEach(Thread::start);
    }

}


