package latest;

/*Reducing Reflection Overhead
 What is Reflection?
     Reflection allows you to inspect or modify classes, methods, or fields at runtime:

Class<?> clazz = MyClass.class;
Method m = clazz.getMethod("doSomething");
m.invoke(obj);

The problem:
  Reflection bypasses JVM optimizations like inlining and JIT compilation.
  It’s 10x–100x slower than direct method calls.
  Each reflection call triggers access checks and uses internal APIs.
*/
//Example 1: Reflection Overhead Demonstration
import java.lang.reflect.Method;

public class ReflectionOverhead {
    public void sayHello() {}

    public static void main(String[] args) throws Exception {
        ReflectionOverhead obj = new ReflectionOverhead();
        Method method = ReflectionOverhead.class.getMethod("sayHello");

        long start = System.nanoTime();
        for (int i = 0; i < 10_000_000; i++) {
            method.invoke(obj); // reflective call
        }
        long end = System.nanoTime();
        System.out.println("Reflection time: " + (end - start) / 1_000_000 + " ms");
    }
}

//Output Example:  Reflection time: 850 ms
// Reflection is very slow due to access checks and call indirection.

//Example 2: Direct Invocation (Optimized)
/*
public class DirectCall {
    public void sayHello() {}

    public static void main(String[] args) {
        DirectCall obj = new DirectCall();
        long start = System.nanoTime();
        for (int i = 0; i < 10_000_000; i++) {
            obj.sayHello(); // direct call
        }
        long end = System.nanoTime();
        System.out.println("Direct call time: " + (end - start) / 1_000_000 + " ms");
    }
}
*/
//Output Example: Direct call time: 10 ms That’s roughly 85× faster!

//Example 3: Cached Reflection (Better)
/*
import java.lang.reflect.Method;

public class CachedReflection {
    public void sayHello() {}

    public static void main(String[] args) throws Exception {
        CachedReflection obj = new CachedReflection();
        Method method = CachedReflection.class.getMethod("sayHello");
        method.setAccessible(true); // removes security/access check overhead

        long start = System.nanoTime();
        for (int i = 0; i < 10_000_000; i++) {
            method.invoke(obj);
        }
        long end = System.nanoTime();

        System.out.println("Optimized reflection time: " + (end - start) / 1_000_000 + " ms");
    }
}

Output: Optimized reflection time: 120 ms Nearly 7× faster than unoptimized reflection.
*/
//3️ Example: Using MethodHandle (Faster Reflection Alternative)

/*import java.lang.invoke.*;

public class MethodHandleExample {
    public void greet() {}

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findVirtual(MethodHandleExample.class, "greet", MethodType.methodType(void.class));

        MethodHandleExample obj = new MethodHandleExample();

        long start = System.nanoTime();
        for (int i = 0; i < 10_000_000; i++) {
            mh.invokeExact(obj); // much faster than reflection
        }
        long end = System.nanoTime();
        System.out.println("MethodHandle time: " + (end - start) / 1_000_000 + " ms");
    }
}


MethodHandle is JIT-friendly and close to direct call performance.
*/
//4️ Avoiding Auto-Boxing & Unnecessary Wrappers
/*
Example:
Integer x = 10; // auto-boxed
int y = x;      // auto-unboxed

Problem:
   Creates unnecessary Integer objects.
   Adds GC overhead.
   Slows down loops and collections.

Example 1: Auto-Boxing Overhead
public class AutoBoxingExample {
    public static void main(String[] args) {
        long start = System.nanoTime();
        Long sum = 0L; // Wrapper type

        for (long i = 0; i < 10_000_000; i++) {
            sum += i; // Auto-unboxing and re-boxing every iteration!
        }

        long end = System.nanoTime();
        System.out.println("Sum = " + sum + " | Time: " + (end - start) / 1_000_000 + " ms");
    }
}
*/
//Output Example: Time: 900 ms   Extremely slow — millions of Long objects are created and discarded.

//Example 2: Avoid Auto-Boxing (Use Primitive)
/*public class PrimitiveExample {
    public static void main(String[] args) {
        long start = System.nanoTime();
        long sum = 0L; // primitive type

        for (long i = 0; i < 10_000_000; i++) {
            sum += i;
        }

        long end = System.nanoTime();
        System.out.println("Sum = " + sum + " | Time: " + (end - start) / 1_000_000 + " ms");
    }
}


Output:Time: 30 ms

*/