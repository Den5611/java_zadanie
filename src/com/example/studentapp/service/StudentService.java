package com.example.studentapp.service;

import com.example.studentapp.exception.DuplicateStudentException;
import com.example.studentapp.exception.StudentNotFoundException;
import com.example.studentapp.filter.StudentFilter;
import com.example.studentapp.model.Student;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {


    public void addStudent(List<Student> students, Student newStudent) {
        boolean exists = students.stream()
                .anyMatch(s -> s.getId() == newStudent.getId());
        if (exists) {
            throw new DuplicateStudentException(newStudent.getId());
        }
        students.add(newStudent);
    }

    public void removeStudent(List<Student> students, int id) {
        Student toRemove = students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException(id));
        students.remove(toRemove);
    }

    public List<Student> filterStudents(List<Student> students, StudentFilter filter) {
        return students.stream()
                .filter(filter::test)
                .collect(Collectors.toList());
    }

    public List<Student> filterBySurname(List<Student> students, String surname) {
        return filterStudents(students,
                student -> student.getSurname().toLowerCase().contains(surname.toLowerCase()));
    }

    public List<Student> filterByScoreAndKeyword(List<Student> students, double minScore, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return students.stream()
                .filter(s -> s.getScore() >= minScore)
                .filter(s -> s.getName().toLowerCase().contains(lowerKeyword)
                          || s.getSurname().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }


    public List<Student> sortByScoreDesc(List<Student> students) {
        return students.stream()
                .sorted(new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        return Double.compare(s2.getScore(), s1.getScore());
                    }
                })
                .collect(Collectors.toList());
    }


    public List<Student> sortByNameAsc(List<Student> students) {
        return students.stream()
                .sorted((s1, s2) -> s1.getName().trim().compareToIgnoreCase(s2.getName().trim()))
                .collect(Collectors.toList());
    }


    public List<Student> searchByKeyword(List<Student> students, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(lowerKeyword)
                          || s.getSurname().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public List<Student> normalizeData(List<Student> students) {
        return students.stream()
                .map(s -> new Student(
                        s.getId(),
                        s.getName().trim().toLowerCase(),
                        s.getSurname().trim().toLowerCase(),
                        s.getScore()
                ))
                .collect(Collectors.toList());
    }
}
