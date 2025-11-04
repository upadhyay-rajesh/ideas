package com.example.performance;

public class PerformanceMeasurement {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        long startTime = System.nanoTime();
        long startUsedMem = runtime.totalMemory() - runtime.freeMemory();

        // Task: Create 5 million objects
        for (int i = 0; i < 5_000_000; i++) {
            String s = new String("Object " + i);
        }

        long endUsedMem = runtime.totalMemory() - runtime.freeMemory();
        long endTime = System.nanoTime();

        System.out.println("Execution time: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("Memory used: " + (endUsedMem - startUsedMem) / (1024 * 1024) + " MB");
    }
}

