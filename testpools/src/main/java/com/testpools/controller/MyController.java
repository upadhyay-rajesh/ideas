package com.testpools.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testpools.entity.Student;
import com.testpools.service.MyServiceInterface;

@RestController
@RequestMapping("api/v1/")
public class MyController {
	
	@Autowired
	private MyServiceInterface mService;
	
	@GetMapping
	public List<Student> getAllStudent(){
		return mService.getAllStudentRecord(); 
	}
}
