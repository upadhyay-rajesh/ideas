package com.example.benchmarks;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)                  // only 1 fork instead of 5
@Warmup(iterations = 2)   // fewer warmup iterations
@Measurement(iterations = 3, time = 1) // fewer measurement iterations
public class StringBenchmark {

    String base = "Hello";

    @Benchmark
    public String testStringConcat() {
        return base + " World";
    }

    @Benchmark
    public String testStringBuilder() {
        return new StringBuilder(base).append(" World").toString();
    }
    
}
