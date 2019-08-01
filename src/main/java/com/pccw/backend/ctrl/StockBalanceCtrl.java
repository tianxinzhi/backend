package com.pccw.backend.ctrl;

import java.util.ArrayList;
import java.util.Arrays;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.entity.Student;
import com.pccw.backend.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock/balance")
public class StockBalanceCtrl {
	@Autowired
	private StudentRepository repo;
	
	@RequestMapping("/search")
	public JsonResult<String> search(){
		return new JsonResult<String>("", "3", new ArrayList<String>(Arrays.asList("ok!","333")));
	}
	@RequestMapping("/create")
	public JsonResult<Student> create(){
		Student student = new Student("cjl");
		repo.save(student);
		return new JsonResult<Student>("", "", new ArrayList<Student>(Arrays.asList(student)));
	}
}
