package com.example.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

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
    private String religion;
    private String blood_type;
    private char authenticated;
    private String id_image;



    private String email;



    public userinfo() {

    }

    public HashMap<String, String> haspMapSetting(){
        HashMap<String, String> location_Map = new HashMap();
        location_Map.put("서울","SU");
        location_Map.put("부산","BS");
        location_Map.put("인천","IC");
        location_Map.put("광주","KJ");
        location_Map.put("울산","OS");
        location_Map.put("대전","DJ");
        location_Map.put("경기","GG");
        location_Map.put("강원","KW");
        location_Map.put("경남","KN");
        location_Map.put("경북","KB");
        location_Map.put("충북","CB");
        location_Map.put("충남","CN");
        location_Map.put("전북","JB");
        location_Map.put("전남","JN");
        location_Map.put("세종","SJ");

        return location_Map;
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

    public void setReligion(String religion) {

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

    public String getReligion() {

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
