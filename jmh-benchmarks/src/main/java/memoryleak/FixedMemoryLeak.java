package memoryleak;

import java.lang.ref.WeakReference;
import java.util.*;

public class FixedMemoryLeak {
    private static List<WeakReference<String>> cache = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            String data = "WeakString_" + i;
            cache.add(new WeakReference<>(data));

            if (i % 1000 == 0) { 
                System.gc(); // encourage GC
                printMemoryUsage(i);
            }
        }
    }

    private static void printMemoryUsage(int iteration) {
        Runtime runtime = Runtime.getRuntime();
        long used = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        System.out.println("Iteration " + iteration + " | Used Memory: " + used + " MB");
    }
}
/*
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapFix {
    public static void main(String[] args) throws InterruptedException {
        Map<Object, String> weakMap = new WeakHashMap<>();

        for (int i = 0; i < 10000; i++) {
            Object key = new Object();
            weakMap.put(key, "Data_" + i);
            key = null; // make key eligible for GC

            if (i % 1000 == 0) {
                System.gc();
                System.out.println("Map size: " + weakMap.size());
            }
        }
    }
}
*/
//Here, once the key reference is lost, the entry is automatically removed by GC.
//No leak!