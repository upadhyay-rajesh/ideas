

package threadperformance;
//Problem: Creating Too Many Threads
//Bad Example — Manual Thread Creation
public class BadThreadExample {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}

/*Issue:
   Creates 1000 threads.
   Each consumes ~1 MB stack → ~1 GB memory usage.
   Context switching overhead is massive → CPU throttling.
*/

/*Solution: Use Thread Pools
 
ThreadPoolExecutor manages:
	A pool of worker threads.
	A queue for submitted tasks.
Automatic reuse of threads (reducing creation overhead).

Example: CPU Performance Using ExecutorService
*/
/*
import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU Cores: " + cores);
        
		//Fixed number of threads
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            executor.submit(new HeavyComputation(i));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - start) + " ms");
    }

    static class HeavyComputation implements Runnable {
        private final int taskId;
        HeavyComputation(int id) { this.taskId = id; }

        @Override
        public void run() {
            long sum = 0;
            for (int i = 0; i < 100_000_000; i++) {
                sum += Math.sqrt(i);
            }
            System.out.println("Task " + taskId + " done by " + Thread.currentThread().getName());
        }
    }
}
*/
/*
Explanation:
  Executors.newFixedThreadPool(cores) → uses exactly one thread per core.
  CPU utilization remains near 100% but with minimal context switching.
  Each thread continuously executes queued tasks.
*/
/*
import java.util.concurrent.*;

public class IOBoundExample {
    public static void main(String[] args) {
    //Cached pool dynamically creates threads as needed, and reuses idle ones. Grows dynamically
        ExecutorService executor = Executors.newCachedThreadPool(); // for IO bound tasks

        for (int i = 0; i < 50; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep(500); // simulate network/disk I/O
                    System.out.println(Thread.currentThread().getName() + " completed I/O task");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}

*/