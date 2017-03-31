package com.example.mihai.fontys_companion;

public class Grades {

    private String course;
    private Number grade;
    private String date;

    public Grades(String date, String course, Number grade) {
        this.course = course;
        this.grade = grade;
        this.date = date;
    }

    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }

    public Number getGrade() {
        return grade;
    }
    public void setGrade(Number grade) {
        this.grade = grade;
    }

    public String getDate() { return date; }
    public void setDate(String date) {
        this.date = date;
    }
}
