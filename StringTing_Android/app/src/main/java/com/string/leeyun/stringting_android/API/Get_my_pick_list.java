package com.string.leeyun.stringting_android.API;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leeyun on 2017. 11. 20..
 */

public class Get_my_pick_list {

 /*   receive_like_list = ‘좋아요 받은 리스트’
    receive_high_score_list = ‘내 호감있는 리스트’
    send_like_list = ‘내가 좋아요 보낸 리스트’
    give_high_score_list = ‘나에게 호감보낸 리스트’

    나머지 response json은 받아서 알아서 파싱*/
/*
    ArrayList<JsonObject> give_high_score_list=new ArrayList<>();
    ArrayList<JsonObject>receive_high_score_list=new ArrayList<>();
    ArrayList<JsonObject>send_like_list=new ArrayList<>();
    ArrayList<JsonObject>receive_like_list=new ArrayList<>();

    public ArrayList<JsonObject> getAccount() {
        return give_high_score_list;
    }

    public void setAccount(ArrayList<JsonObject> account) {
        this.give_high_score_list = account;
    }*/
    private List<receive_like_list>receive_like_list;
    private List<receive_high_score_list>receive_high_score_list;
    private List<send_like_list>send_like_list;
    private List<give_high_score_list>give_high_score_list;

    public class receive_like_list{
        int id;
        int age,height;
        float score;
        boolean smoke,authenticated;
        String education,department,location,body_form,drink,religion,blood_type,id_image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public boolean isSmoke() {
            return smoke;
        }

        public void setSmoke(boolean smoke) {
            this.smoke = smoke;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBody_form() {
            return body_form;
        }

        public void setBody_form(String body_form) {
            this.body_form = body_form;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getBlood_type() {
            return blood_type;
        }

        public void setBlood_type(String blood_type) {
            this.blood_type = blood_type;
        }

        public String getId_image() {
            return id_image;
        }

        public void setId_image(String id_image) {
            this.id_image = id_image;
        }



    }

    public class receive_high_score_list{
        int id;
        int age,height;
        float score;
        boolean smoke,authenticated;
        String education,department,location,body_form,drink,religion,blood_type,id_image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public boolean isSmoke() {
            return smoke;
        }

        public void setSmoke(boolean smoke) {
            this.smoke = smoke;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBody_form() {
            return body_form;
        }

        public void setBody_form(String body_form) {
            this.body_form = body_form;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getBlood_type() {
            return blood_type;
        }

        public void setBlood_type(String blood_type) {
            this.blood_type = blood_type;
        }

        public String getId_image() {
            return id_image;
        }

        public void setId_image(String id_image) {
            this.id_image = id_image;
        }
    }

    public class send_like_list{
        int id;
        int age,height;
        float score;
        boolean smoke,authenticated;
        String education,department,location,body_form,drink,religion,blood_type,id_image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public boolean isSmoke() {
            return smoke;
        }

        public void setSmoke(boolean smoke) {
            this.smoke = smoke;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBody_form() {
            return body_form;
        }

        public void setBody_form(String body_form) {
            this.body_form = body_form;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getBlood_type() {
            return blood_type;
        }

        public void setBlood_type(String blood_type) {
            this.blood_type = blood_type;
        }

        public String getId_image() {
            return id_image;
        }

        public void setId_image(String id_image) {
            this.id_image = id_image;
        }
    }

    public class give_high_score_list{
        int id;
        int age,height;
        float score;
        boolean smoke,authenticated;
        String education,department,location,body_form,drink,religion,blood_type,id_image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public boolean isSmoke() {
            return smoke;
        }

        public void setSmoke(boolean smoke) {
            this.smoke = smoke;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBody_form() {
            return body_form;
        }

        public void setBody_form(String body_form) {
            this.body_form = body_form;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getBlood_type() {
            return blood_type;
        }

        public void setBlood_type(String blood_type) {
            this.blood_type = blood_type;
        }

        public String getId_image() {
            return id_image;
        }

        public void setId_image(String id_image) {
            this.id_image = id_image;
        }



    }

}
