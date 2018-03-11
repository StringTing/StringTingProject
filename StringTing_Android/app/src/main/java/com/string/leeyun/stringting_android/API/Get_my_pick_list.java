package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sg970 on 2018-03-10.
 */

public class Get_my_pick_list {

    /*   receive_like_list = ‘좋아요 받은 리스트’
    receive_high_score_list = ‘내 호감있는 리스트’
    send_like_list = ‘내가 좋아요 보낸 리스트’
    give_high_score_list = ‘나에게 호감보낸 리스트’*/


    @SerializedName("accounts")
    private ArrayList<Get_my_pick_list> accounts;



    private List<receive_like_list> receive_like_list;
    private List<receive_high_score_list>receive_high_score_list;
    private List<send_like_list>send_like_list;
    private List<give_high_score_list>give_high_score_list;

    public List<Get_my_pick_list.receive_like_list> getReceive_like_list() {
        return receive_like_list;
    }

    public void setReceive_like_list(List<Get_my_pick_list.receive_like_list> receive_like_list) {
        this.receive_like_list = receive_like_list;
    }

    public List<Get_my_pick_list.receive_high_score_list> getReceive_high_score_list() {
        return receive_high_score_list;
    }

    public void setReceive_high_score_list(List<Get_my_pick_list.receive_high_score_list> receive_high_score_list) {
        this.receive_high_score_list = receive_high_score_list;
    }

    public List<Get_my_pick_list.send_like_list> getSend_like_list() {
        return send_like_list;
    }

    public void setSend_like_list(List<Get_my_pick_list.send_like_list> send_like_list) {
        this.send_like_list = send_like_list;
    }

    public List<Get_my_pick_list.give_high_score_list> getGive_high_score_list() {
        return give_high_score_list;
    }

    public void setGive_high_score_list(List<Get_my_pick_list.give_high_score_list> give_high_score_list) {
        this.give_high_score_list = give_high_score_list;
    }

    public ArrayList<Get_my_pick_list> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Get_my_pick_list> accounts) {
        this.accounts = accounts;
    }

    public class receive_like_list{
        int id;
        int age,height;
        float score;
        boolean smoke,authenticated;
        String education,department,location,body_form,drink,religion,blood_type,id_image;
        private images images;

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

        public com.string.leeyun.stringting_android.API.images getImages() {
            return images;
        }

        public void setImages(com.string.leeyun.stringting_android.API.images images) {
            this.images = images;
        }


    }

    public class receive_high_score_list{
        int id;
        int age,height;
        float score;
        boolean smoke,authenticated;
        String education,department,location,body_form,drink,religion,blood_type,id_image;
        private images images;

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
        public com.string.leeyun.stringting_android.API.images getImages() {
            return images;
        }

        public void setImages(com.string.leeyun.stringting_android.API.images images) {
            this.images = images;
        }
    }

    public class send_like_list{
        int id;
        int age,height;
        float score;
        boolean smoke,authenticated;
        String education,department,location,body_form,drink,religion,blood_type,id_image;
        private images images;

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

        public com.string.leeyun.stringting_android.API.images getImages() {
            return images;
        }

        public void setImages(com.string.leeyun.stringting_android.API.images images) {
            this.images = images;
        }
    }

    public class give_high_score_list{
        int id;
        int age,height;
        float score;
        boolean smoke,authenticated;
        String education,department,location,body_form,drink,religion,blood_type,id_image;
        private images images;

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

        public com.string.leeyun.stringting_android.API.images getImages() {
            return images;
        }

        public void setImages(com.string.leeyun.stringting_android.API.images images) {
            this.images = images;
        }



    }





}
