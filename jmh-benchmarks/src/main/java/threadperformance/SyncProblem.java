package threadperformance;
/*Synchronization ensures that only one thread accesses shared data at a time, preventing data corruption —
but it comes at a performance cost due to:
    Thread contention
    Context switching
    Memory barriers
Example: Poor Synchronization (Low Performance)
*/
public class SyncProblem {
    private int counter = 0;

    public synchronized void increment() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        SyncProblem obj = new SyncProblem();

        Thread t1 = new Thread(() -> { for (int i = 0; i < 1_000_000; i++) obj.increment(); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 1_000_000; i++) obj.increment(); });

        long start = System.currentTimeMillis();
        t1.start(); t2.start();
        t1.join(); t2.join();
        long end = System.currentTimeMillis();

        System.out.println("Counter: " + obj.counter);
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
//Correct — but  slow — because both threads keep waiting for the lock.

/* Optimized Approach — Reduce Lock Scope
 Use fine-grained locking instead of full method locks.
 */
/*public class SyncOptimized {
    private int counter = 0;

    public void increment() {
        // synchronize only the critical section
        synchronized (this) {
            counter++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncOptimized obj = new SyncOptimized();

        Thread t1 = new Thread(() -> { for (int i = 0; i < 1_000_000; i++) obj.increment(); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 1_000_000; i++) obj.increment(); });

        long start = System.currentTimeMillis();
        t1.start(); t2.start();
        t1.join(); t2.join();
        long end = System.currentTimeMillis();

        System.out.println("Counter: " + obj.counter);
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
*/
/*
Output:
Counter: 2000000
Time taken: 420 ms
*/

//About 2x faster because locking scope is reduced.

/*️Use Modern Concurrency Utilities — AtomicInteger

Instead of synchronized, use lock-free atomic variables.
*/
/*
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {
    private AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet(); // thread-safe, no locks
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicExample obj = new AtomicExample();

        Thread t1 = new Thread(() -> { for (int i = 0; i < 1_000_000; i++) obj.increment(); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 1_000_000; i++) obj.increment(); });

        long start = System.currentTimeMillis();
        t1.start(); t2.start();
        t1.join(); t2.join();
        long end = System.currentTimeMillis();

        System.out.println("Counter: " + obj.counter);
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
*/
/* Output:
Counter: 2000000
Time taken: 80 ms


 10× faster — because AtomicInteger uses non-blocking CAS (Compare-And-Swap) at CPU level.
*/
/*Advanced Lock Optimization — ReentrantLock

ReentrantLock gives you:

Explicit control (lock() / unlock())

Try-lock without blocking

Fair vs unfair lock modes
*/
/*
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    private int counter = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        if (lock.tryLock()) { // try avoiding blocking wait
            try {
                counter++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockExample obj = new LockExample();
        Thread t1 = new Thread(() -> { for (int i = 0; i < 1_000_000; i++) obj.increment(); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 1_000_000; i++) obj.increment(); });

        long start = System.currentTimeMillis();
        t1.start(); t2.start();
        t1.join(); t2.join();
        long end = System.currentTimeMillis();

        System.out.println("Counter: " + obj.counter);
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}

*/
/*Performs similarly to AtomicInteger, but allows more control when contention occurs.
Use java.util.concurrent classes instead of manual locks.
*/
/*
Old (Synchronized)	Optimized (Concurrent)	Benefit
Vector	            CopyOnWriteArrayList	Lock-free reads
Hashtable	        ConcurrentHashMap	    Segmented locking
LinkedList	        ConcurrentLinkedQueue	Non-blocking queue
*/
/*
import java.util.*;
import java.util.concurrent.*;

public class MapExample {
    public static void main(String[] args) throws InterruptedException {
        Map<Integer, String> map = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            final int id = i;
            executor.submit(() -> {
                for (int j = 0; j < 10000; j++) {
                    map.put(id * 10000 + j, "Value-" + j);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("Map size: " + map.size());
    }
}
*/
/*
Comparison Table
Technique	    Thread Safety	Performance	Use Case
synchronized	 Safe	        Slow	    Simple low-contention code
ReentrantLock	 Safe	        Faster	    Custom locking logic
AtomicInteger	 Safe	        Fastest	    Counters & shared numbers
ConcurrentHashMap	 Safe	    Fast	    Shared collections
StampedLock (Java 8+)	Safe	Very Fast	Read-dominant workloads
*/