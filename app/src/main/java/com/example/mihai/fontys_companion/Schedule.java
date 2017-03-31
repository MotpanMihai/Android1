package com.example.mihai.fontys_companion;

public class Schedule {

    private String course;
    private String room;
    private String teacher;
    private String start;
    private String end;

    public Schedule(String course, String room, String teacher, String start, String end) {
        this.course = course;
        this.room = room;
        this.teacher = teacher;
        this.start = start;
        this.end = end;
    }

    public String getCourse() { return course; }
    public void setCourse(String course) {
        this.course = course;
    }

    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String room) {
        this.teacher = teacher;
    }

    public String getStart() {
        return start;
    }
    public void setStart(String room) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }
    public void setEnd(String room) {this.end = end;
    }
}
