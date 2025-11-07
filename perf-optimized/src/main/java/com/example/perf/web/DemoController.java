package com.example.perf.web;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class DemoController {

    private final MeterRegistry registry;
    private final Timer workTimer;

    public DemoController(MeterRegistry registry) {
        this.registry = registry;
        this.workTimer = Timer.builder("demo.work.timer")
                .description("Time spent doing demo work")
                .publishPercentiles(0.5, 0.9, 0.95, 0.99)
                .maximumExpectedValue(Duration.ofSeconds(5))
                .register(registry);
    }

    @GetMapping("/hello")
    public Map<String, Object> hello(@RequestParam(defaultValue = "World") String name) {
        registry.counter("demo.hello.count").increment();
        return Map.of("message", "Hello " + name + "!", "ts", System.currentTimeMillis());
    }

    @GetMapping("/work")
    public ResponseEntity<Map<String, Object>> work(@RequestParam(defaultValue = "100") long millis) {
        return ResponseEntity.ok(workInternal(millis));
    }

    private Map<String, Object> workInternal(long millis) {
        return workTimer.record(() -> {
            try {
                // Simulate variable workload (observe in metrics)
                long jitter = ThreadLocalRandom.current().nextLong(Math.max(1, millis / 2));
                Thread.sleep(Math.min(millis + jitter, 2000));
            } catch (InterruptedException ignored) {}
            registry.counter("demo.work.completed").increment();
            return Map.of("ok", true, "sleptMs", millis);
        });
    }
}
