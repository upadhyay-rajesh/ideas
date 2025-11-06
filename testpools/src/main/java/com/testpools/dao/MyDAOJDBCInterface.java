package com.testpools.dao;

import java.util.List;

import com.testpools.entity.Student;

public interface MyDAOJDBCInterface {

	List<Student> findAllStudentData();

}
