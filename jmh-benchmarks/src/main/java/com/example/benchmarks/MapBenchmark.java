package com.example.benchmarks;

import org.openjdk.jmh.annotations.*;
import java.util.*;
import java.util.concurrent.*;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MapBenchmark {

    Map<Integer, Integer> hashMap;
    Map<Integer, Integer> concurrentHashMap;

    @Setup(Level.Iteration)
    public void setup() {
        hashMap = new HashMap<>();
        concurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 1000; i++) {
            hashMap.put(i, i);
            concurrentHashMap.put(i, i);
        }
    }

    @Benchmark
    public void testHashMapPut() {
        hashMap.put(new Random().nextInt(1000), 42);
    }

    @Benchmark
    public void testConcurrentHashMapPut() {
        concurrentHashMap.put(new Random().nextInt(1000), 42);
    }

    @Benchmark
    public int testHashMapGet() {
        return hashMap.get(new Random().nextInt(1000));
    }

    @Benchmark
    public int testConcurrentHashMapGet() {
        return concurrentHashMap.get(new Random().nextInt(1000));
    }
}
