package com.example.fx_sms;

public class courseData {

    private String course;
    private String description;
    private int teacher_id;

    public courseData(String course, String description, int teacher_id){
        this.course = course;
        this.description = description;
        this.teacher_id = teacher_id;
    }
    public String getCourse(){
        return course;
    }
    public String getDescription(){
        return description;
    }

    public int getTeacher_id() {
        return teacher_id;
    }
}
