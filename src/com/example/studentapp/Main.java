package com.example.studentapp;

import com.example.studentapp.filter.StudentFilter;
import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс для запуска программы.
 * Демонстрирует все возможности приложения.
 */
public class Main {

    public static void main(String[] args) {

        StudentService service = new StudentService();

        // =========================================================
        // 1. ИНИЦИАЛИЗАЦИЯ ТЕСТОВЫХ ДАННЫХ (с намеренными пробелами)
        // =========================================================
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, " Alice",   "Smith  ",   4.8));
        students.add(new Student(2, "Bob  ",    "Johnson",   3.5));
        students.add(new Student(3, "Charlie",  " Brown",    4.2));
        students.add(new Student(4, "Anna",     "Davis",     4.9));
        students.add(new Student(5, "David",    "Miller",    3.8));
        students.add(new Student(6, "alexey",   "Sokolov",   4.0));
        students.add(new Student(7, "Marina",   "Smith",     2.7));

        printSection("1. Исходный список студентов");
        students.forEach(System.out::println);

        // =========================================================
        // 2. ФИЛЬТРАЦИЯ — АНОНИМНЫЙ КЛАСС (балл > 4.5)
        // =========================================================
        printSection("2. Фильтрация: балл > 4.5  [используется Анонимный класс]");
        List<Student> highScorers = service.filterStudents(students, new StudentFilter() {
            @Override
            public boolean test(Student student) {
                return student.getScore() > 4.5;
            }
        });
        printResult(highScorers);

        // =========================================================
        // 3. ФИЛЬТРАЦИЯ — ЛЯМБДА-ВЫРАЖЕНИЕ (имя содержит 'a' или 'A')
        // =========================================================
        printSection("3. Фильтрация: имя содержит букву 'a'  [используется Лямбда]");
        List<Student> nameFilter = service.filterStudents(students,
                s -> s.getName().trim().toLowerCase().contains("a"));
        printResult(nameFilter);

        // =========================================================
        // 4. ФИЛЬТРАЦИЯ — ПО ФАМИЛИИ
        // =========================================================
        printSection("4. Фильтрация по фамилии: 'Smith'");
        List<Student> bySurname = service.filterBySurname(students, "Smith");
        printResult(bySurname);

        // =========================================================
        // 5. КОМБИНИРОВАННАЯ ФИЛЬТРАЦИЯ (балл >= 4.0 И keyword 'a')
        // =========================================================
        printSection("5. Комбинированный фильтр: балл >= 4.0 И keyword='a'");
        List<Student> combined = service.filterByScoreAndKeyword(students, 4.0, "a");
        printResult(combined);

        // =========================================================
        // 6. СОРТИРОВКА ПО БАЛЛУ (убывание) — Анонимный Comparator
        // =========================================================
        printSection("6. Сортировка по баллу (убывание)  [Анонимный Comparator внутри сервиса]");
        service.sortByScoreDesc(students).forEach(System.out::println);

        // =========================================================
        // 7. СОРТИРОВКА ПО ИМЕНИ (алфавит) — Лямбда Comparator
        // =========================================================
        printSection("7. Сортировка по имени (алфавит)  [Лямбда Comparator внутри сервиса]");
        service.sortByNameAsc(students).forEach(System.out::println);

        // =========================================================
        // 8. ПОИСК ПО КЛЮЧЕВОМУ СЛОВУ
        // =========================================================
        String keyword = "son";
        printSection("8. Поиск по ключевому слову: '" + keyword + "'");
        service.searchByKeyword(students, keyword).forEach(System.out::println);

        // =========================================================
        // 9. НОРМАЛИЗАЦИЯ ДАННЫХ (нижний регистр + trim)
        // =========================================================
        printSection("9. Нормализация данных (trim + toLowerCase)");
        List<Student> normalized = service.normalizeData(students);
        normalized.forEach(System.out::println);
    }

    /**
     * Вспомогательный метод — выводит заголовок секции.
     */
    private static void printSection(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + title);
        System.out.println("=".repeat(60));
    }

    /**
     * Вспомогательный метод — выводит результат или сообщение о пустом списке.
     */
    private static void printResult(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("  [Нет результатов]");
        } else {
            list.forEach(System.out::println);
        }
    }
}
