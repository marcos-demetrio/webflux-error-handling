package com.example.errorhandling.controller;

import com.example.errorhandling.dto.Student;
import com.example.errorhandling.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("student")
public class StudentController {

  @Autowired private StudentService service;

  @GetMapping("all")
  public Flux<Student> findAll() throws Exception {
    return service.findAll();
  }

  @GetMapping("{id}")
  public Mono<Student> findById(@PathVariable("id") final int studentId) {
    return service.findById(studentId);
  }
}
