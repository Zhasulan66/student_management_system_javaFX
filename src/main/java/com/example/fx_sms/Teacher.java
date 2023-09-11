package com.example.fx_sms;

import java.sql.Date;

public class Teacher {

    private Integer teacher_id;
    private String first_name;
    private String last_name;
    private String gender;
    private Date birth_date;
    private String phone_num;
    private String english_lvl;
    private String image;

    public Teacher(Integer teacher_id, String first_name, String last_name, String gender, Date birth_date, String phone_num, String english_lvl, String image) {
        this.teacher_id = teacher_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.phone_num = phone_num;
        this.english_lvl = english_lvl;
        this.image = image;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getEnglish_lvl() {
        return english_lvl;
    }

    public void setEnglish_lvl(String english_lvl) {
        this.english_lvl = english_lvl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
