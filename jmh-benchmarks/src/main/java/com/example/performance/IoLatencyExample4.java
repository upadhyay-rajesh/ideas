package com.example.performance;

import java.io.*;

public class IoLatencyExample4 {
    public static void main(String[] args) throws Exception {
        long start = System.nanoTime();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (int i = 0; i < 1_000_000; i++) {
                writer.write("Line " + i + "\n");
            }
        }

        long end = System.nanoTime();
        System.out.println("File write latency: " + (end - start)/1_000_000 + " ms");
    }
}
