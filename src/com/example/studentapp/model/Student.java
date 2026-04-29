package com.example.studentapp.model;


public class Student {

    private int id;
    private String name;
    private String surname;
    private double score;

    // ===================== КОНСТРУКТОРЫ =====================

    public Student(int id, String name, String surname, double score) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.score = score;
    }

    // ===================== ГЕТТЕРЫ / СЕТТЕРЫ =====================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    // ===================== toString =====================

    @Override
    public String toString() {
        return String.format("Student{ id=%-2d | name=%-10s | surname=%-10s | score=%.2f }",
                id, name, surname, score);
    }
}
