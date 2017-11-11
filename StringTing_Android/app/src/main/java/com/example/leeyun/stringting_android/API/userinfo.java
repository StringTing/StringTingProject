package com.example.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leeyun on 2017. 11. 1..
 */

public class userinfo implements Serializable{

    @SerializedName("Id")
    private String Id;
    private int age;
    private char login_format;
    private int birthday;
    private String military_service_status;
    private String education;
    private String department;
    private String location;
    private int hegiht;
    private char body_form;
    private boolean smoke;
    private String drink;            //논의 필요
    private char religion;
    private String blood_type;
    private char authenticated;
    private String id_image;



    private String email;



    public userinfo() {

    }


    public String getPassword() {
        return password;
    }

    public char getLogin_format() {
        return login_format;
    }

    public userinfo(String id) {
        Id = id;
    }

    public int getBirthday() {
        return birthday;
    }

    public String getMilitary_service_status() {
        return military_service_status;
    }

    public String getEducation() {
        return education;
    }

    public String getEmail() {
        return email;
    }

    private String password;

    public void setId_image(String id_image) {
        this.id_image = id_image;
    }

    public void setAuthenticated(char authenticated) {

        this.authenticated = authenticated;
    }

    public void setBlood_type(String blood_type) {

        this.blood_type = blood_type;
    }

    public void setReligion(char religion) {

        this.religion = religion;
    }

    public void setDrink(String drink) {

        this.drink = drink;
    }

    public void setSmoke(boolean smoke) {

        this.smoke = smoke;
    }

    public void setBody_form(char body_form) {

        this.body_form = body_form;
    }

    public void setHegiht(int hegiht) {

        this.hegiht = hegiht;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public void setDepartment(String department) {

        this.department = department;
    }

    public void setEducation(String education) {

        this.education = education;
    }

    public void setMilitary_service_status(String military_service_status) {

        this.military_service_status = military_service_status;
    }

    public void setBirthday(int birthday) {

        this.birthday = birthday;
    }

    public void setLogin_format(char login_format) {

        this.login_format = login_format;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public void setId(String id) {

        Id = id;
    }

    public String getId_image() {

        return id_image;
    }

    public char getAuthenticated() {

        return authenticated;
    }

    public String getBlood_type() {

        return blood_type;
    }

    public char getReligion() {

        return religion;
    }

    public String getDrink() {

        return drink;
    }

    public boolean isSmoke() {

        return smoke;
    }

    public char getBody_form() {

        return body_form;
    }

    public int getHegiht() {

        return hegiht;
    }

    public String getLocation() {

        return location;
    }

    public String getDepartment() {

        return department;
    }

    public String getId() {
        return Id;
    }

    public int getAge() {
        return age;
    }


    public void setEmail(String email) {
        this.email = email;
    }
}
