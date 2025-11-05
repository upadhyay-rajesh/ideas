package threadperformance;

/*completableFuture — The Modern Async API
 Non-blocking asynchronous programming
 Easy chaining of dependent tasks
 Built-in thread management using ForkJoinPool
 Support for combining multiple async tasks
*/

import java.util.concurrent.*;

public class CompletableFutureBasic {
    public static void main(String[] args) throws Exception {
        System.out.println("Main starts in: " + Thread.currentThread().getName());

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Task running in: " + Thread.currentThread().getName());
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("Task completed!");
        });

        future.join(); // wait for completion
        System.out.println("Main finished!");
    }
}

/* Output:
Main starts in: main
Task running in: ForkJoinPool.commonPool-worker-1
Task completed!
Main finished!

 Automatically uses ForkJoinPool.commonPool behind the scenes.
*/
// 4️ Returning and Combining Results
/*
import java.util.concurrent.*;

public class CompletableFutureReturn {
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task1 in " + Thread.currentThread().getName());
            return 10;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task2 in " + Thread.currentThread().getName());
            return 20;
        });

        CompletableFuture<Integer> combined = future1.thenCombine(future2, Integer::sum);

        System.out.println("Result: " + combined.join());
    }
}
*/
/*
 Output:
Task1 in ForkJoinPool.commonPool-worker-1
Task2 in ForkJoinPool.commonPool-worker-2
Result: 30
Both run in parallel, then combine results.
*/


//5️ Chaining Async Tasks (thenApplyAsync, thenComposeAsync)
/*
import java.util.concurrent.*;

public class CompletableFutureChain {
    public static void main(String[] args) {
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            System.out.println("Stage 1: " + Thread.currentThread().getName());
            return 5;
        }).thenApplyAsync(x -> {
            System.out.println("Stage 2: " + Thread.currentThread().getName());
            return x * 10;
        }).thenApplyAsync(y -> {
            System.out.println("Stage 3: " + Thread.currentThread().getName());
            return y + 3;
        });

        System.out.println("Final Result: " + result.join());
    }
}
*/

//Each stage can run in parallel or separate threads for better throughput.

/*6️ Using a Custom Executor (Custom ForkJoinPool or ThreadPool)

By default, all CompletableFuture tasks use ForkJoinPool.commonPool().
For more control, use a custom Executor.
*/
/*
import java.util.concurrent.*;

public class CompletableFutureCustomExecutor {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        CompletableFuture<Void> f = CompletableFuture.runAsync(() -> {
            System.out.println("Running task in: " + Thread.currentThread().getName());
        }, executor);

        f.join();
        executor.shutdown();
    }
}
*/

// Uses your own thread pool instead of the global ForkJoinPool.

//7️ Parallel Execution Example — Multiple Tasks
/*
import java.util.*;
import java.util.concurrent.*;

public class CompletableFutureParallel {
    public static void main(String[] args) {
        List<String> websites = List.of("A", "B", "C", "D");

        long start = System.currentTimeMillis();

        List<CompletableFuture<String>> futures = websites.stream()
                .map(site -> CompletableFuture.supplyAsync(() -> fetchData(site)))
                .toList();

        // Wait for all
        List<String> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        long end = System.currentTimeMillis();

        System.out.println("Results: " + results);
        System.out.println("Time taken: " + (end - start) + " ms");
    }

    private static String fetchData(String site) {
        System.out.println("Fetching from " + site + " in " + Thread.currentThread().getName());
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        return "Data-" + site;
    }
}
*/
/* Output:
Fetching from A in ForkJoinPool.commonPool-worker-1
Fetching from B in ForkJoinPool.commonPool-worker-2
Fetching from C in ForkJoinPool.commonPool-worker-3
Fetching from D in ForkJoinPool.commonPool-worker-4
Results: [Data-A, Data-B, Data-C, Data-D]
Time taken: ~1000 ms


 All tasks run in parallel using ForkJoinPool → 4× faster than sequential execution.
*/
/*8️ Introducing the ForkJoinPool
Concept:

Introduced in Java 7.

Based on work-stealing algorithm:

Each worker thread has its own queue.

If a thread finishes early, it “steals” work from others.

Ideal for divide and conquer or recursive parallel algorithms.
*/
/*
import java.util.concurrent.*;

public class ForkJoinSum extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start, end;
    private static final int THRESHOLD = 10000;

    public ForkJoinSum(long[] numbers, int start, int end) {
        this.numbers = numbers; this.start = start; this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) sum += numbers[i];
            return sum;
        }
        int mid = (start + end) / 2;
        ForkJoinSum left = new ForkJoinSum(numbers, start, mid);
        ForkJoinSum right = new ForkJoinSum(numbers, mid, end);

        left.fork(); // async compute left
        long rightResult = right.compute(); // compute right
        long leftResult = left.join(); // wait for left
        return leftResult + rightResult;
    }

    public static void main(String[] args) {
        long[] arr = new long[1_000_000];
        for (int i = 0; i < arr.length; i++) arr[i] = i + 1;

        ForkJoinPool pool = new ForkJoinPool();
        long start = System.currentTimeMillis();
        long result = pool.invoke(new ForkJoinSum(arr, 0, arr.length));
        long end = System.currentTimeMillis();

        System.out.println("Sum = " + result);
        System.out.println("Time: " + (end - start) + " ms");
    }
}
*/

// ForkJoinPool efficiently uses all CPU cores by splitting the work recursively.

/*
Use CompletableFuture for:
      Asynchronous task chains  API calls, I/O tasks, or dependent operations
Use ForkJoinPool for:
      CPU-heavy recursive or divide-and-conquer tasks
Combine both for:
      Asynchronous pipelines + CPU-optimized parallelism
      */
