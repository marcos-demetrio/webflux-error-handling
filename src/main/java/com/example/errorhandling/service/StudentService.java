package com.example.errorhandling.service;

import com.example.errorhandling.dto.Student;
import com.example.errorhandling.exception.BusinessException;
import com.example.errorhandling.exception.StudentNotFoundException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {
  private static final Map<Integer, Student> DB =
      Map.of(
          1,
          new Student(1, "FN1", "LN1"),
          2,
          new Student(2, "FN2", "LN2"),
          3,
          new Student(3, "FN3", "LN3"),
          4,
          new Student(4, "FN4", "LN4"),
          5,
          new Student(5, "FN5", "LN5"));

  public Flux<Student> findAll() {
    return Flux.fromIterable(DB.values()).doFirst(this::throwRandomError);
  }

  public Mono<Student> findById(final int studentId) {
    return Mono.just(studentId)
        .filter(DB::containsKey)
        .map(DB::get)
        .flatMap(
            student ->
                student.getId() % 2 == 0
                    ? Mono.error(() -> new BusinessException("some business error"))
                    : Mono.just(student))
        .switchIfEmpty(Mono.error(() -> new StudentNotFoundException(studentId)));
  }

  private void throwRandomError() {
    var random = ThreadLocalRandom.current().nextInt(0, 10);
    if (random > 5) throw new RuntimeException("some random error");
  }
}
