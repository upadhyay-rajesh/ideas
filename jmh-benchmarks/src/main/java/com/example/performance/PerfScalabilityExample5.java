package com.example.performance;

import java.util.concurrent.*;
//Performance vs. Scalability vs. Throughput
//Example: Single-thread vs Multi-thread
/*
 *  Performance: total execution time
	Scalability: how well time improves with more threads
    Throughput: tasks per second processed
 */
public class PerfScalabilityExample5 {
    public static void main(String[] args) throws Exception {
        int taskCount = 10;

        // Single-thread execution
        long start = System.nanoTime();
        for (int i = 0; i < taskCount; i++) {
            compute();
        }
        long end = System.nanoTime();
        System.out.println("Single-thread time: " + (end-start)/1_000_000 + " ms");

        // Multi-thread execution
        ExecutorService executor = Executors.newFixedThreadPool(44);
        start = System.nanoTime();
        for (int i = 0; i < taskCount; i++) {
            executor.submit(() -> compute());
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        end = System.nanoTime();
        System.out.println("Multi-thread time: " + (end-start)/1_000_000 + " ms");
    }

    static void compute() {
        for (int i = 0; i < 10_000_000; i++); // dummy work
    }
}
