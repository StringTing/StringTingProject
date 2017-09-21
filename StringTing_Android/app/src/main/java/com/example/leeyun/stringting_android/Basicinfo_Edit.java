package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.w3c.dom.Text;

import static com.example.leeyun.stringting_android.R.id.Spinner_age;

/**
 * Created by leeyun on 2017. 9. 16..
 */

public class Basicinfo_Edit extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicinfo_edit);


        Spinner age = (Spinner)findViewById(Spinner_age);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.age, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(adapter);




    }

}
