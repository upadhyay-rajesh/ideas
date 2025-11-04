package memoryleak;


import java.lang.ref.*;

public class RefDemo {
    public static void main(String[] args) {
        Object strong = new Object();
        SoftReference<Object> soft = new SoftReference<>(new Object());
        WeakReference<Object> weak = new WeakReference<>(new Object());
        PhantomReference<Object> phantom =
            new PhantomReference<>(new Object(), new ReferenceQueue<>());

        System.out.println("Strong: " + strong);
        System.out.println("Soft: " + soft.get());
        System.out.println("Weak: " + weak.get());
        System.out.println("Phantom: " + phantom.get()); // always null
    }
}
