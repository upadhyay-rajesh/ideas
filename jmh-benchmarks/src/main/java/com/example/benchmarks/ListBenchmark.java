package com.example.benchmarks;

import org.openjdk.jmh.annotations.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 1)
@Measurement(iterations = 2)
public class ListBenchmark {

    @Param({"1000"})
    int size;

    List<Integer> arrayList;
    List<Integer> linkedList;

    @Setup(Level.Iteration)
    public void setup() {
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
    }

    @Benchmark
    public void testArrayListAdd() {
        arrayList.add(size / 2, 42);
    }

    @Benchmark
    public void testLinkedListAdd() {
        linkedList.add(size / 2, 42);
    }

    @Benchmark
    public int testArrayListIterate() {
        int sum = 0;
        for (int i : arrayList) sum += i;
        return sum;
    }

    @Benchmark
    public int testLinkedListIterate() {
        int sum = 0;
        for (int i : linkedList) sum += i;
        return sum;
    }
}
