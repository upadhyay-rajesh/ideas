package latest;

/*Reducing Object Creation
Goal:
  Reduce GC load, heap fragmentation, and CPU cost by reusing existing objects — instead of allocating new ones repeatedly.

Why Object Creation Hurts
   Each new call allocates heap memory.
   The Garbage Collector must later reclaim it.
   Frequent short-lived allocations → frequent GC → performance drop.
*/

//Example 1: Expensive Object Creation (BAD)
public class WithoutPooling {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            StringBuilder sb = new StringBuilder(); // new object each time
            sb.append("Hello").append(i);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + " ms");
    }
}

// Problem:  Creates 1 million StringBuilder objects → GC-heavy.

//Example 2: Object Pooling (BETTER)
 /*
public class WithPooling {
    private static final StringBuilder builder = new StringBuilder();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            builder.setLength(0); // reset instead of creating new
            builder.append("Hello").append(i);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + " ms");
    }
}


 Much faster (lower GC pressure) — same object reused.
*/
// Example 3: Using Object Pool (Custom)
/*
import java.util.concurrent.*;

public class ObjectPoolExample {
    private final BlockingQueue<StringBuilder> pool = new ArrayBlockingQueue<>(5);

    public ObjectPoolExample() {
        for (int i = 0; i < 5; i++) pool.add(new StringBuilder());
    }

    public String buildMessage(String input) throws InterruptedException {
        StringBuilder sb = pool.take(); // borrow from pool
        sb.setLength(0);
        sb.append("Hello ").append(input);
        String result = sb.toString();
        pool.offer(sb); // return to pool
        return result;
    }

    public static void main(String[] args) throws Exception {
        ObjectPoolExample ex = new ObjectPoolExample();
        System.out.println(ex.buildMessage("Dhruv"));
    }
}


*/ //Reduces memory churn when objects are reused efficiently.

//Example 4: Caching Results to Avoid Re-creation
/*
import java.util.concurrent.*;

public class FibonacciCache {
    private static final ConcurrentHashMap<Integer, Long> cache = new ConcurrentHashMap<>();

    public static long fib(int n) {
        if (n <= 1) return n;
        return cache.computeIfAbsent(n, key -> fib(key - 1) + fib(key - 2));
    }

    public static void main(String[] args) {
        System.out.println("Fib(45): " + fib(45)); // runs fast after caching
    }
}


 Caching avoids redundant object creation and recursive computation.
*/
//2️ Using Lazy Initialization
/*Concept: Delay the creation of heavy or rarely used objects until actually needed.
Benefits:Saves memory on startup,Reduces unnecessary allocation,Improves startup performance
*/
//Example 1: Eager Initialization (BAD)
/*
public class EagerConfig {
    private static final ExpensiveResource resource = new ExpensiveResource();

    public static void main(String[] args) {
        System.out.println("App started..."); // resource created unnecessarily
    }

    static class ExpensiveResource {
        ExpensiveResource() {
            System.out.println("ExpensiveResource initialized!");
        }
    }
}

Problem: Object is created even if never used.
*/

//Example 2: Lazy Initialization (GOOD)
/*
public class LazyConfig {
    private static ExpensiveResource resource;

    public static ExpensiveResource getResource() {
        if (resource == null) {
            resource = new ExpensiveResource();
        }
        return resource;
    }

    public static void main(String[] args) {
        System.out.println("App started...");
        getResource(); // only created now
    }

    static class ExpensiveResource {
        ExpensiveResource() {
            System.out.println("ExpensiveResource initialized lazily!");
        }
    }
}


Created only when first needed.
*/

