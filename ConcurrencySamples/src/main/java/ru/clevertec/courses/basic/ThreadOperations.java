package ru.clevertec.courses.basic;


import java.util.concurrent.TimeUnit;

public class ThreadOperations {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main: Hello world!");

        Thread first = new Thread(new HelloRunnable(1));
        Thread second = new Thread(new HelloRunnable(2), "second thread name");
        Runnable greeting = () -> System.out.println("This is greeting thread...  HELLO!!!");
        Thread another = new Thread(greeting);
        Thread yetAnother = new Thread() {
            public void run() {
                super.run();
                System.out.println("yetAnother");
            }
        };
        Thread deamon = new Thread(() -> {
            System.out.println("Hello from deamon thread!!!");
            try {
                TimeUnit.SECONDS.sleep(10);   // устанавливаем время жизни больше чем время жизни всех потоков.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Deamon thread is finished");
        });

        deamon.setDaemon(true);

        deamon.start();
        first.start();
        second.start();
        another.start();
        yetAnother.start();
        second.join();
        // deamon.join(); // Main поток будет ждать завершения даже демона, если он присоединен (join)

        System.out.println("Main: Goodbye world.....");
    }
}
