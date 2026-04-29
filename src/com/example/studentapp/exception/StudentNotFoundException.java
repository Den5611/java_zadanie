package com.example.studentapp.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(int id) {
        super("Студент с ID=" + id + " не найден в списке!");
    }
}
