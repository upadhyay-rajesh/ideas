package latest;

/*choosing the right algorithm and data structure to reduce:
  Time complexity (fewer operations)
  Space complexity (less memory)
  GC pressure (less object creation)
  Lock contention (thread efficiency)
*/

//Inefficient Search
import java.util.*;

public class LinearSearch {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) list.add(i);

        long start = System.nanoTime();
        System.out.println(list.contains(999_999));  // O(n)
        long end = System.nanoTime();

        System.out.println("Time: " + (end - start) / 1_000_000.0 + " ms");
    }
}

//Optimized Search Using HashSet
/*
import java.util.*;

public class HashSearch {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 1_000_000; i++) set.add(i);

        long start = System.nanoTime();
        System.out.println(set.contains(999_999));  // O(1)
        long end = System.nanoTime();

        System.out.println("Time: " + (end - start) / 1_000_000.0 + " ms");
    }
}
*/
//Huge performance improvement: from ~30 ms → <1 ms
//That’s 30× faster just by picking the right structure.

// Example: Using PriorityQueue for Efficient Sorting
/*
import java.util.*;

public class PriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.addAll(List.of(5, 1, 3, 8, 2));

        while (!pq.isEmpty()) {
            System.out.println(pq.poll()); // retrieves smallest element
        }
    }
}
*/

//Output: 1, 2, 3, 5, 8
//This uses a min-heap, giving O(log n) insertion and O(log n) removal — much faster than repeated sorting.


//Example: Efficient Frequency Counter
// Using HashMap.merge()
/*
import java.util.*;

public class FrequencyCounter {
    public static void main(String[] args) {
        String[] words = {"apple", "banana", "apple", "orange", "banana", "apple"};
        Map<String, Integer> freq = new HashMap<>();

        for (String word : words) {
            freq.merge(word, 1, Integer::sum);
        }

        System.out.println(freq);
    }
}
*/
/*Output:

{orange=1, banana=2, apple=3}


Efficient, thread-safe, and no explicit checks or if-statements.
*/

/*Summary Table
Problem Type	Best Structure/Algorithm	Complexity
Lookup by ID	HashMap	                    O(1)
Maintain order	LinkedHashMap	             O(1)
Sorted elements	TreeSet / TreeMap	        O(log n)
Frequent read, rare write	CopyOnWriteArrayList	O(1) read
Multi-thread queue	ConcurrentLinkedQueue	O(1)
Top N elements	PriorityQueue	            O(n log N)
Frequency count	 HashMap.merge()	            O(n)

Best Practices for Efficiency

 Prefer ArrayList over LinkedList — better cache locality.
 Use HashMap/Set for lookups instead of loops.
 Stream.parallel() only when CPU-bound, not I/O-bound.
 Use primitive collections (Trove, FastUtil) for large numeric data.
 Minimize object creation inside loops.
 Profile regularly with VisualVM / JFR for memory usage.
*/