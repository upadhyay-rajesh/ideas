package com.example.performance;

public class PerformanceTuningExample7 {
    public static void main(String[] args) {
        long start = System.nanoTime();

        // Bad: String concatenation in loop
        String str = "";
        for (int i = 0; i < 10000; i++) {
            str += i;
        }
        long end = System.nanoTime();
        System.out.println("Naive String concat took: " + (end - start)/1_000_000 + " ms");

        start = System.nanoTime();

        // Good: StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append(i);
        }
        end = System.nanoTime();
        System.out.println("StringBuilder concat took: " + (end - start)/1_000_000 + " ms");
    }
}
