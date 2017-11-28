package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by leeyun on 2017. 11. 26..
 */

public class Get_today_introduction {

    @SerializedName("login_format")
    private String login_format;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("military_service_status")
    private String military_service_status;
    @SerializedName("education")
    private String education;
    @SerializedName("department")
    private String department;
    @SerializedName("location")
    private String location;
    @SerializedName("height")
    private int height;
    @SerializedName("smoke")
    private boolean smoke;
    @SerializedName("drink")
    private String drink;
    @SerializedName("religion")
    private String religion;
    @SerializedName("blood_type")
    private String blood_type;
    @SerializedName("authenticated")
    private boolean authenticated;
    @SerializedName("id_image")
    private String id_image;
    @SerializedName("password")
    private String password;
    @SerializedName("body_form")
    private String body_form;
    @SerializedName("images")
    private List<String>images;

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public List<String> getImages() {

        return images;
    }

    public boolean isOpened() {
        return opened;
    }

    @SerializedName("opened")

    private boolean opened;


    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {

        return result;
    }

    private String result;


    private String email;




    public String getPassword() {
        return password;
    }

    public String getLogin_format() {
        return login_format;
    }


    public String getBirthday() {
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



    public void setId_image(String id_image) {
        this.id_image = id_image;
    }

    public void setAuthenticated(boolean authenticated) {

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

    public void setBody_form(String body_form) {

        this.body_form = body_form;
    }

    public void setheight(int height) {

        this.height = height;
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

    public void setBirthday(String birthday) {

        this.birthday = birthday;
    }

    public void setLogin_format(String login_format) {

        this.login_format = login_format;
    }

    public void setPassword(String password) {

        this.password = password;
    }




    public String getId_image() {

        return id_image;
    }

    public boolean getAuthenticated() {

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

    public String getBody_form() {

        return body_form;
    }

    public int getheight() {

        return height;
    }

    public String getLocation() {

        return location;
    }

    public String getDepartment() {

        return department;
    }



    public void setEmail(String email) {
        this.email = email;
    }


}
