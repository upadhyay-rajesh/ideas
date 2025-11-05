package threadperformance;
/*
What Is a Deadlock?
  A deadlock happens when two or more threads hold locks and each waits for the other to release theirs — none can proceed.
*/
/*
public class DeadlockExample {
    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public void task1() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + " locked A");
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + " locked B");
            }
        }
    }

    public void task2() {
        synchronized (lockB) {
            System.out.println(Thread.currentThread().getName() + " locked B");
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + " locked A");
            }
        }
    }

    public static void main(String[] args) {
        DeadlockExample obj = new DeadlockExample();

        Thread t1 = new Thread(obj::task1, "Thread-1");
        Thread t2 = new Thread(obj::task2, "Thread-2");

        t1.start();
        t2.start();
    }
}
*/
/* Output:
Thread-1 locked A
Thread-2 locked B
Thread-1 waiting for B...
Thread-2 waiting for A...
 Deadlock!
 */


// Both threads are stuck forever — classic deadlock pattern.

/*Detecting Deadlocks
Option 1: Using Command Prompt (jstack)

1️ Run your Java program:
  java DeadlockExample
2️ Find the process ID (PID):
  jps
3️ Capture thread dump:
  jstack <pid>
4️ Look for lines like:

Found one Java-level deadlock:
=============================
"Thread-1":
  waiting to lock monitor 0x000000001e7b9b08 (object B),
  which is held by "Thread-2"
"Thread-2":
  waiting to lock monitor 0x000000001e7b9b38 (object A),
  which is held by "Thread-1"

Confirms deadlock with object and thread names.

Option 2: Detect in VisualVM / JConsole

1️ Run program
2️ Open VisualVM (jvisualvm)
3️ Attach to the running process
4️ Go to Threads tab → you’ll see threads marked “Deadlocked” in red
5️ Click “Detect Deadlock” button

Visual and real-time detection.
*/

//Example using tryLock()

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

 class DeadlockAvoidance {
    private final ReentrantLock lockA = new ReentrantLock();
    private final ReentrantLock lockB = new ReentrantLock();

    public void safeTask() {
        try {
            if (lockA.tryLock(100, TimeUnit.MILLISECONDS)) {
                System.out.println(Thread.currentThread().getName() + " got Lock A");
                try {
                    if (lockB.tryLock(100, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread().getName() + " got Lock B");
                    } else {
                        System.out.println(Thread.currentThread().getName() + " couldn’t get Lock B");
                    }
                } finally {
                    lockB.unlock();
                }
            }
        } catch (InterruptedException ignored) {}
        finally {
            if (lockA.isHeldByCurrentThread()) lockA.unlock();
        }
    }

    public static void main(String[] args) {
        DeadlockAvoidance obj = new DeadlockAvoidance();
        Thread t1 = new Thread(obj::safeTask, "T1");
        Thread t2 = new Thread(obj::safeTask, "T2");

        t1.start(); t2.start();
    }
}


// No deadlock — tryLock() times out gracefully.

/*Best Practices

 Minimize synchronization — synchronize only critical sections.
 Use non-blocking algorithms (Atomic classes, concurrent collections).
 Always maintain consistent lock ordering.
 Prefer ExecutorService over manual thread management.
 Monitor production systems with VisualVM or JFR for lock contention.
*/