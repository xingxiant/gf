package com.learn.factory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {
    public static ExecutorService executorService = new ThreadPoolExecutor(
            10,
            50,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(200)
    );
}