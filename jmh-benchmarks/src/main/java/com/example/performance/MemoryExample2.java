package com.example.performance;

public class MemoryExample2 {
    public static void main(String[] args) {
        int[] bigArray = new int[10_000_000]; // uses ~40MB
        System.out.println("Array created with size: " + bigArray.length);

        Runtime runtime = Runtime.getRuntime();
        System.out.println("Used memory: " + 
            (runtime.totalMemory() - runtime.freeMemory()) / (1024*1024) + " MB");
    }
}
