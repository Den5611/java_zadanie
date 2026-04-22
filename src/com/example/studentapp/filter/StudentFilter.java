package com.example.studentapp.filter;

import com.example.studentapp.model.Student;

/**
 * Функциональный интерфейс для фильтрации студентов.
 *
 * Может быть использован:
 * - через анонимный класс (new StudentFilter() { ... })
 * - через лямбда-выражение (student -> student.getScore() > 4.0)
 */
@FunctionalInterface
public interface StudentFilter {

    /**
     * Проверяет, соответствует ли студент заданному критерию.
     *
     * @param student студент для проверки
     * @return true — если студент соответствует критерию, иначе false
     */
    boolean test(Student student);
}
