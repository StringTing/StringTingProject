package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.w3c.dom.Text;

import static com.example.leeyun.stringting_android.R.id.Spinner_age;
import static com.example.leeyun.stringting_android.R.id.Spinner_blood;
import static com.example.leeyun.stringting_android.R.id.Spinner_city;
import static com.example.leeyun.stringting_android.R.id.Spinner_drink;
import static com.example.leeyun.stringting_android.R.id.Spinner_religion;
import static com.example.leeyun.stringting_android.R.layout.spinner_item;

/**
 * Created by leeyun on 2017. 9. 16..
 */

public class Basicinfo_Edit extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicinfo_edit);


        Spinner age = (Spinner)findViewById(Spinner_age);
        Spinner city = (Spinner)findViewById(Spinner_city);
        Spinner blood= (Spinner)findViewById(Spinner_blood);
        Spinner drink= (Spinner)findViewById(Spinner_drink);
        Spinner religion=(Spinner)findViewById(Spinner_religion);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.age, spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.city, spinner_item);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.blood, spinner_item);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.drink, spinner_item);
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this, R.array.religion, spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(adapter);
        city.setAdapter(adapter2);
        blood.setAdapter(adapter3);
        drink.setAdapter(adapter4);
        religion.setAdapter(adapter5);



    }
    public void onClick_photo_upload(View v){
        Intent intent = new Intent(getApplicationContext(),Photo_Upload.class);

        startActivity(intent);

    }
    public void onClick_back(View v){
        super.onBackPressed(); // or super.finish();

    }

}
