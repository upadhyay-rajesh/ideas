package com.example.performance;

public class GcExample3 {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            byte[] b = new byte[10_000_000]; // allocate 10MB repeatedly
        }
        System.out.println("Done allocations");
    }
}
//run as java -Xmx200m -verbose:gc GcExample