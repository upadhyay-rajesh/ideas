package com.example.performance;

public class MonitoringDemo8 {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            // Allocate memory
            byte[] arr = new byte[10_000_000];
            Thread.sleep(1000); // pause
        }
    }
}
