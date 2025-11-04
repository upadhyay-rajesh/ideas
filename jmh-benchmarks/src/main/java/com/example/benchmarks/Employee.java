package com.example.benchmarks;

import java.io.Serializable;

public class Employee implements Serializable{
	private String name;
	private String email;
	private String address;
	public String getName() {
		return name;
	}
	public void setName(String name1) {
		name = name1;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email1) {
		email = email1;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address1) {
		address = address1;
	}
	
	
}
