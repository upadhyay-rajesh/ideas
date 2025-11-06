package com.testpools.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.testpools.entity.Student;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
@Repository
public class StudentDAO implements MyDAOJDBCInterface{

	
	public List<Student> findAllStudentData() {
		List<Student> st=null;
		HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb");
        config.setUsername("root");
        config.setPassword("rajesh");
        config.setMaximumPoolSize(10);

        HikariDataSource ds = new HikariDataSource(config);

        try (Connection con = ds.getConnection();
             Statement st1 = con.createStatement();
             ResultSet rs = st1.executeQuery("SELECT * FROM student")) {
        	st=new ArrayList<Student>();
            while (rs.next()) {
            	Student s1=new Student();
            	s1.setName(rs.getString(1));
            	s1.setEmail(rs.getString(2));
                s1.setAddress(rs.getString(3));
            	st.add(s1);
            }
        }

       catch(Exception e) {
    	   e.printStackTrace();
	}
		return st;
	}

}
