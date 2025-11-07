package com.example.perf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class HeavyService {
    private static final Logger log = LoggerFactory.getLogger(HeavyService.class);

    public HeavyService() {
        log.info("HeavyService constructed (should be lazy-loaded on first use).");
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    public String compute() {
        return "heavy-result";
    }
}
