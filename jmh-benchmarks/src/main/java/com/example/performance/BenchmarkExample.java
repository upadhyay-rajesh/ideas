package com.example.performance;
//Profiling vs. Benchmarking

//Benchmarking Example (simple time measurement)

public class BenchmarkExample {
    public static void main(String[] args) {
        long start = System.nanoTime();
        compute();
        long end = System.nanoTime();
        System.out.println("Execution time: " + (end-start)/1_000_000 + " ms");
    }

    static void compute() {
        for (int i = 0; i < 100_000_000; i++);
    }
}

/*
 * Profiling Example
Run same program with JVisualVM or JConsole (comes with JDK).

Open jvisualvm

Attach to running process

See CPU usage, memory, threads, GC

This shows real-time profiling instead of just timing.
*/
