package com.example.performance;

public class CpuExample1 {
    public static void main(String[] args) {
        long sum = 0;
        for (long i = 0; i < 1_000_000_000L; i++) {
            sum += i;
        }
        System.out.println("Sum = " + sum);
    }
}

//Run this and check CPU usage in Task Manager / top command.
  