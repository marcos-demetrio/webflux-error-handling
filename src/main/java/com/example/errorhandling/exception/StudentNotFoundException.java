package com.example.errorhandling.exception;

public class StudentNotFoundException extends NotFoundException {
  private static final String MESSAGE = "Student with id %d is not found";

  public StudentNotFoundException(int studentId) {
    super(String.format(MESSAGE, studentId));
  }
}
