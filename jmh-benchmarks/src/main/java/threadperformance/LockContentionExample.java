package threadperformance;
/*What Is Lock Contention?
Lock contention happens when multiple threads try to acquire the same lock simultaneously, and some must wait.

Impact:
Threads are blocked, waiting for the monitor lock.
Increased context switching and CPU idle time.
Causes performance degradation in high-concurrency systems.

Example Scenario:
     Two threads share a synchronized block. Only one can enter; the other waits:
     */
public class LockContentionExample {
    private final Object lock = new Object();

    public void criticalSection() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " acquired lock");
            try {
                Thread.sleep(2000); // simulate long work inside lock
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " released lock");
        }
    }

    public static void main(String[] args) {
        LockContentionExample obj = new LockContentionExample();

        Runnable task = obj::criticalSection;

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");

        long start = System.currentTimeMillis();
        t1.start(); t2.start(); t3.start();
        try { t1.join(); t2.join(); t3.join(); } catch (InterruptedException ignored) {}
        long end = System.currentTimeMillis();

        System.out.println("Total time: " + (end - start) + " ms");
    }
}
//Correct but  performance bottleneck — only one thread runs at a time due to lock contention.

/* Optimizing Lock Contention
   Reduce time inside synchronized block:
*/
/*
public void optimizedSection() {
    // do non-critical work first
    int localData = computeSomething();

    synchronized (lock) {
        sharedList.add(localData);  // only critical update is synchronized
    }
}
*/

// Use lock-free structures (ConcurrentHashMap, AtomicInteger).
// Use fine-grained locks (split one big lock into smaller independent locks).

