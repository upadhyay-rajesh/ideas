package com.example.benchmarks;

public class BenchmarkExample {
    public static void main(String[] args) {
        long start, end;

        // Test 1: Using String concatenation
        start = System.nanoTime();
        String s = "";
        for (int i = 0; i < 10000; i++) {
            s += i;
        }
        end = System.nanoTime();
        System.out.println("String (+) time: " + (end - start) / 1_000_000 + " ms");

        // Test 2: Using StringBuilder
        start = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append(i);
        }
        end = System.nanoTime();
        System.out.println("StringBuilder time: " + (end - start) / 1_000_000 + " ms");
    }
}
