package com.example.performance;

import java.util.*;

public class MemoryLeakExample {
    static List<byte[]> memoryLeakList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            byte[] leak = new byte[1024 * 1024]; // 1MB per iteration
            memoryLeakList.add(leak); // Holding reference, won't be GC'd
            System.out.println("Iteration: " + i + " | Total objects: " + memoryLeakList.size());
            Thread.sleep(100);
        }
    }
}
/*
Every loop adds a new 1 MB array to memoryLeakList.

Old objects are never released → Heap grows → OutOfMemoryError.

This mimics leaks from static lists, caches, or unclosed resources.
*/
//java -Xmx512m MemoryLeakExample
