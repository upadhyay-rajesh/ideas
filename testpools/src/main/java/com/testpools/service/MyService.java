package com.testpools.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testpools.dao.MyDAOJDBCInterface;
import com.testpools.dao.MyDaoInterface;
import com.testpools.entity.Student;

@Service
public class MyService implements MyServiceInterface{
	
	@Autowired
	private MyDaoInterface mDao;
	
	@Autowired
	private MyDAOJDBCInterface mJdbc;

	@Override
	public List<Student> getAllStudentRecord() {
		// TODO Auto-generated method stub
		return mDao.findAll();
		//return mJdbc.findAllStudentData();
	}

}
