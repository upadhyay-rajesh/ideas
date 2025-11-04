package com.example.benchmarks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SerializationDemo {

	public static void main(String[] args)throws Exception {
		
		Employee e=new Employee();
		e.setName("Mahesh");
		e.setEmail("abc@yahoo.com");
		e.setAddress("Bangalore");
		
		FileOutputStream fo=new FileOutputStream(new File("one.txt"));
		ObjectOutputStream oo =new ObjectOutputStream(fo);
		oo.writeObject(e);
		System.out.println("object written");
		oo.close();
		fo.close();

	}

}
