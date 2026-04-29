package com.example.studentapp.filter;

import com.example.studentapp.model.Student;


@FunctionalInterface
public interface StudentFilter {


    boolean test(Student student);
}
