package com.testpools.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testpools.entity.Student;

@Repository
public interface MyDaoInterface extends JpaRepository<Student, String>{

}
