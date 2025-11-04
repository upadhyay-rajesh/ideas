package memoryleak;

import java.util.ArrayList;
import java.util.List;

public class MemoryLeakDemo {
    private static List<String> cache = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            String data = "LeakyString_" + i;
            cache.add(data); // adding to static list causes memory leak
            Thread.sleep(10);

            if (i % 1000 == 0) {
                printMemoryUsage(i);
            }
        }
    }

    private static void printMemoryUsage(int iteration) {
        Runtime runtime = Runtime.getRuntime();
        long used = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        System.out.println("Iteration " + iteration + " | Used Memory: " + used + " MB");
    }
    
    //You’ll notice memory usage increases continuously — GC can’t reclaim.
    public static void simulateLeak(int i) {
        cache.add("Leak_" + i);
    }
}
