package com.example.leeyun.stringting_android;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by leeyun on 2017. 11. 11..
 */

public class RealmDB extends RealmObject{

    @PrimaryKey
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
