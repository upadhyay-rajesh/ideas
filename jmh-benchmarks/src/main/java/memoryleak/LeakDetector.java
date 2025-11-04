package memoryleak;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class LeakDetector {
    public static void main(String[] args) throws InterruptedException {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        for (int i = 0; i < 1000; i++) {
            MemoryLeakDemo.simulateLeak(i); // simulate allocation
            if (i % 100 == 0) {
                MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
                long used = heapUsage.getUsed() / (1024 * 1024);
                long max = heapUsage.getMax() / (1024 * 1024);
                System.out.println("Iteration: " + i + " | Used: " + used + "MB | Max: " + max + "MB");
            }
            Thread.sleep(100);
        }
    }
}
