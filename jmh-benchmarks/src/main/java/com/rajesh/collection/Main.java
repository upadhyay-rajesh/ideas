package com.rajesh.collection;
import java.util.concurrent.ConcurrentLinkedDeque;
public class Main {
	public static void main(String[] args) throws Exception {
		ConcurrentLinkedDeque<String> list=new ConcurrentLinkedDeque<>();
		Thread threads[]=new Thread[100];
		for (int i=0; i<threads.length; i++){
			AddTask task=new AddTask(list);
			threads[i]=new Thread(task);
			threads[i].start();
		}
		System.out.printf("Main: %d AddTask threads have been launched\n",threads.length);
		for (int i=0; i<threads.length; i++) {
				threads[i].join();
		}
		System.out.printf("Main: Size of the List: %d\n",list.size());
		for (int i=0; i<threads.length; i++){
			PollTask task=new PollTask(list);
			threads[i]=new Thread(task);
			threads[i].start();
		}
		System.out.printf("Main: %d PollTask threads have been launched\n",threads.length);
		for (int i=0; i<threads.length; i++) {
			threads[i].join();
		}
		System.out.printf("Main: Size of the List: %d\n",list.size());
	}
}
