package com.pccw.backend.ctrl;

import java.util.ArrayList;
import java.util.Arrays;

import com.pccw.backend.bean.JsonResult;
import com.pccw.backend.entity.Student;
import com.pccw.backend.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentCtrl {
	@Autowired
	private StudentRepository repo;
	
    @RequestMapping(method = {RequestMethod.GET})
	public JsonResult<String> search(){
		return new JsonResult<String>("", "3", new ArrayList<String>(Arrays.asList("ok!","333")));
    }
    
	@RequestMapping(method = {RequestMethod.POST})
	public JsonResult<Student> create(){
		Student student = new Student("cjl");
		repo.save(student);
		return new JsonResult<Student>("", "", new ArrayList<Student>(Arrays.asList(student)));
    }

    @RequestMapping(method = {RequestMethod.PUT})
    public JsonResult<String> update(){
        return new JsonResult<String>("", "", new ArrayList<String>(Arrays.asList("")));
    }

    @RequestMapping(method = {RequestMethod.DELETE})
    public JsonResult<String> delete(){
        return new JsonResult<String>("", "", new ArrayList<String>(Arrays.asList("")));
    }
}
