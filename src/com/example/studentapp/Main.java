package com.example.studentapp;

import com.example.studentapp.filter.StudentFilter;
import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        StudentService service = new StudentService();

        List<Student> students = new ArrayList<>();
        students.add(new Student(1, " Benjamin",   "Netanyahu  ",   4.8));
        students.add(new Student(2, "Bob  ",    "Job",   3.5));
        students.add(new Student(3, "Charlie",  " Kirk",    4.2));
        students.add(new Student(4, "Anna",     "Gooning",     4.9));
        students.add(new Student(5, "David",    "Nigger",    3.8));
        students.add(new Student(6, "Alexey",   "Sokolov",   4.0));
        students.add(new Student(7, "Marina",   "Smith",     2.7));

        printSection("1. Исходный список студентов");
        students.forEach(System.out::println);


        printSection("2. Фильтрация: балл > 4.5  [используется Анонимный класс]");
        List<Student> highScorers = service.filterStudents(students, new StudentFilter() {
            @Override
            public boolean test(Student student) {
                return student.getScore() > 4.5;
            }
        });
        printResult(highScorers);

        printSection("3. Фильтрация: имя содержит букву 'a'  [используется Лямбда]");
        List<Student> nameFilter = service.filterStudents(students,
                s -> s.getName().trim().toLowerCase().contains("a"));
        printResult(nameFilter);

        printSection("4. Фильтрация по фамилии: 'Smith'");
        List<Student> bySurname = service.filterBySurname(students, "Smith");
        printResult(bySurname);


        printSection("5. Комбинированный фильтр: балл >= 4.0 И keyword='a'");
        List<Student> combined = service.filterByScoreAndKeyword(students, 4.0, "a");
        printResult(combined);


        printSection("6. Сортировка по баллу (убывание)  [Анонимный Comparator внутри сервиса]");
        service.sortByScoreDesc(students).forEach(System.out::println);


        printSection("7. Сортировка по имени (алфавит)  [Лямбда Comparator внутри сервиса]");
        service.sortByNameAsc(students).forEach(System.out::println);


        String keyword = "son";
        printSection("8. Поиск по ключевому слову: '" + keyword + "'");
        service.searchByKeyword(students, keyword).forEach(System.out::println);


        printSection("9. Нормализация данных (trim + toLowerCase)");
        List<Student> normalized = service.normalizeData(students);
        normalized.forEach(System.out::println);
    }

    private static void printSection(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + title);
        System.out.println("=".repeat(60));
    }

    private static void printResult(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("  [Нет результатов]");
        } else {
            list.forEach(System.out::println);
        }
    }
}
