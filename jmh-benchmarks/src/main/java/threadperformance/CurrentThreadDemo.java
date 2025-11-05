package threadperformance;

public class CurrentThreadDemo {

	public static void main(String[] args) {
		Thread t = Thread.currentThread();
		System.out.println(t);
		
		System.out.println(t.getName());
		
		t.setName("mythread");
		System.out.println(t);
		System.out.println(t.getName());
	}

}
