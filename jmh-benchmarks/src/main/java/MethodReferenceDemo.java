@FunctionalInterface
interface Reference{
	void add();
}


public class MethodReferenceDemo {
	
	void mult() {
		System.out.println("mult method");
	}
	
	static void sub() {
		System.out.println("sub method");
	}
 
	public static void main(String[] args) {
		Reference r=MethodReferenceDemo::sub;
		r.add();
		
		Reference r1=new MethodReferenceDemo()::mult;
		r1.add();

	}

}
