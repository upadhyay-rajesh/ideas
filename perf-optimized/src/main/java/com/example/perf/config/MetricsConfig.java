package com.example.perf.config;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class MetricsConfig {

    @Bean
    public AtomicInteger inMemoryQueueSize(MeterRegistry registry) {
        AtomicInteger gauge = new AtomicInteger(0);
        Gauge.builder("demo.queue.size", gauge, AtomicInteger::get)
                .description("Size of a demo in-memory queue")
                .register(registry);
        return gauge;
    }
}
