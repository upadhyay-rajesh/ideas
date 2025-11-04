package com.example.performance;

public class HeapMemoryExample {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        System.out.println("Total memory: " + runtime.totalMemory() / (1024 * 1024) + " MB");
        System.out.println("Free memory before allocation: " + runtime.freeMemory() / (1024 * 1024) + " MB");

        // Allocate large memory
        int[] largeArray = new int[10_000_000];  // 10 million integers

        System.out.println("Free memory after allocation: " + runtime.freeMemory() / (1024 * 1024) + " MB");

        // Help GC reclaim
        largeArray = null; 
        System.gc();

        System.out.println("Free memory after GC: " + runtime.freeMemory() / (1024 * 1024) + " MB");
    }
}
/*
Runtime.getRuntime() gives heap memory statistics.

You can monitor heap usage before/after allocations.

Useful for understanding memory pressure and GC behavior.
*/