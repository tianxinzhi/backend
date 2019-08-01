package com.pccw.backend;

import com.pccw.backend.entity.Student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Test
	public void StudentRepository(){

		// @Autowired
		// public final com.pccw.backend.repository.StudentRepository repo;

		// Student student = new Student("cjl");
		// repo.save(student);
	}

}
