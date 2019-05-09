package com.learn.factory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {
    public static ExecutorService executorService = new ThreadPoolExecutor(
            30,
            50,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(500)
    );
    public static ExecutorService appExecutor = new ThreadPoolExecutor(
            30,
            50,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(500)
    );
    public static ExecutorService httpExecutor = new ThreadPoolExecutor(
            30,
            50,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(500)
    );
    public static ExecutorService executorPool = new ThreadPoolExecutor(
            20,
            50,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(200)
    );
}
