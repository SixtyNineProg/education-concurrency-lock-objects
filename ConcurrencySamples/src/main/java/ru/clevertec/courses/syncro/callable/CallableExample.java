package ru.clevertec.courses.syncro.callable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class CallableExample {

    public CallableExample() {
        // Определяем пул из трех потоков
        ExecutorService executor = Executors.newFixedThreadPool(3);
//        Executors.newSingleThreadExecutor().submit(this::getInt);

        // Список ассоциированных с Callable задач Future
        List<Future<String>> futures =
                IntStream.rangeClosed(1, 3)
                        .mapToObj(i -> executor.submit(new CallableClass(i)))
                        .toList();

        var dateFormat = new SimpleDateFormat("HH:mm:ss.mmm  ");
        for (Future<String> future : futures) {
            try {

                String result = future.get();
                String text = dateFormat.format(new Date()) + result;
                System.out.println(text);

            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Exception!!!!: " + e);
            }
        }
        // Останавливаем пул потоков
        executor.shutdown();
    }

    public static void main(String[] args) {
        new CallableExample();
    }

    private Integer getInt() {
        return 1;
    }

}