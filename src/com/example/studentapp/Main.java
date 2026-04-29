package com.example.studentapp;

import com.example.studentapp.exception.DuplicateStudentException;
import com.example.studentapp.exception.StudentNotFoundException;
import com.example.studentapp.filter.StudentFilter;
import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        StudentService service = new StudentService();
        Scanner scanner = new Scanner(System.in);

        List<Student> students = new ArrayList<>();
        int choice;

        do {
            printMenu();
            System.out.print("Ваш выбор: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    printSection("Добавить студента");
                    System.out.print("Введите ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Введите имя: ");
                    String name = capitalize(scanner.nextLine().trim());
                    System.out.print("Введите фамилию: ");
                    String surname = capitalize(scanner.nextLine().trim());
                    System.out.print("Введите балл (0.0 - 5.0): ");
                    double score = scanner.nextDouble();
                    scanner.nextLine();
                    try {
                        Student newStudent = new Student(id, name, surname, score);
                        service.addStudent(students, newStudent);
                        System.out.println("Студент добавлен: " + newStudent);
                    } catch (DuplicateStudentException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case 2:
                    printSection("Удалить студента");
                    System.out.print("Введите ID студента для удаления: ");
                    int removeId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        service.removeStudent(students, removeId);
                        System.out.println("Студент с ID=" + removeId + " удалён.");
                    } catch (StudentNotFoundException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case 3:
                    printSection("Все студенты");
                    printResult(students);
                    break;

                case 4:
                    printSection("Фильтр: балл > 4.5  [Анонимный класс]");
                    List<Student> highScorers = service.filterStudents(students, new StudentFilter() {
                        @Override
                        public boolean test(Student student) {
                            return student.getScore() > 4.5;
                        }
                    });
                    printResult(highScorers);
                    break;

                case 5:
                    printSection("Фильтр: по имени  [Лямбда]");
                    System.out.print("Введите букву/подстроку для поиска по имени: ");
                    String namePart = scanner.nextLine().trim();
                    List<Student> byName = service.filterStudents(students,
                            s -> s.getName().toLowerCase().contains(namePart.toLowerCase()));
                    printResult(byName);
                    break;

                case 6:
                    printSection("Фильтр: по фамилии");
                    System.out.print("Введите фамилию (или часть): ");
                    String surnameInput = scanner.nextLine().trim();
                    List<Student> bySurname = service.filterBySurname(students, surnameInput);
                    printResult(bySurname);
                    break;

                case 7:
                    printSection("Комбинированный фильтр: балл >= X И ключевое слово");
                    System.out.print("Введите минимальный балл: ");
                    double minScore = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Введите ключевое слово: ");
                    String keyword = scanner.nextLine().trim();
                    List<Student> combined = service.filterByScoreAndKeyword(students, minScore, keyword);
                    printResult(combined);
                    break;

                case 8:
                    printSection("Сортировка по баллу (убывание)  [Анонимный Comparator]");
                    printResult(service.sortByScoreDesc(students));
                    break;

                case 9:
                    printSection("Сортировка по имени (алфавит)  [Лямбда Comparator]");
                    printResult(service.sortByNameAsc(students));
                    break;

                case 10:
                    printSection("Поиск по ключевому слову");
                    System.out.print("Введите ключевое слово: ");
                    String searchWord = scanner.nextLine().trim();
                    printResult(service.searchByKeyword(students, searchWord));
                    break;

                case 0:
                    System.out.println("\nВыход из программы. До свидания!");
                    break;

                default:
                    System.out.println("\n[!] Неверный выбор. Введите число от 0 до 10.");
            }

        } while (choice != 0);

        scanner.close();
    }

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    private static void printMenu() {
        System.out.println("       МЕНЮ УПРАВЛЕНИЯ СТУДЕНТАМИ");
        System.out.println("");
        System.out.println("  1.  Добавить студента");
        System.out.println("  2.  Удалить студента по ID");
        System.out.println("  3.  Показать всех студентов");
        System.out.println("  4.  Фильтр: балл > 4.5         [Анонимный класс]");
        System.out.println("  5.  Фильтр: по имени           [Лямбда]");
        System.out.println("  6.  Фильтр: по фамилии");
        System.out.println("  7.  Комбинированный фильтр     [балл + ключевое слово]");
        System.out.println("  8.  Сортировка по баллу        [убывание]");
        System.out.println("  9.  Сортировка по имени        [алфавит]");
        System.out.println("  10. Поиск по ключевому слову");
        System.out.println("  0.  Выход");
        System.out.println("=".repeat(60));
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
