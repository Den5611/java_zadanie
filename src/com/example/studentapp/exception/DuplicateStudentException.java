package com.example.studentapp.exception;

public class DuplicateStudentException extends RuntimeException {
    public DuplicateStudentException(int id) {
        super("Студент с ID=" + id + " уже существует в списке!");
    }
}
