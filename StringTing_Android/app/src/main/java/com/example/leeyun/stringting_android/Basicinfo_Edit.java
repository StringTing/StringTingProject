package com.example.leeyun.stringting_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.example.leeyun.stringting_android.API.ResponseApi;
import com.example.leeyun.stringting_android.API.Rest_ApiService;
import com.example.leeyun.stringting_android.API.userinfo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.leeyun.stringting_android.R.id.Spinner_Tall;

import static com.example.leeyun.stringting_android.R.id.Spinner_birthday1;
import static com.example.leeyun.stringting_android.R.id.Spinner_birthday2;
import static com.example.leeyun.stringting_android.R.id.Spinner_birthday3;
import static com.example.leeyun.stringting_android.R.id.Spinner_blood;
import static com.example.leeyun.stringting_android.R.id.Spinner_body_form_female;
import static com.example.leeyun.stringting_android.R.id.Spinner_body_form_male;
import static com.example.leeyun.stringting_android.R.id.Spinner_city;
import static com.example.leeyun.stringting_android.R.id.Spinner_drink;
import static com.example.leeyun.stringting_android.R.id.Spinner_education;
import static com.example.leeyun.stringting_android.R.id.Spinner_religion;
import static com.example.leeyun.stringting_android.R.layout.spinner_item;
import static java.lang.Integer.parseInt;
import static okhttp3.Protocol.get;

/**
 * Created by leeyun on 2017. 9. 16..
 */

public class Basicinfo_Edit extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto1, iv_UserPhoto2, iv_UserPhoto3, iv_UserPhoto4, iv_UserPhoto5, iv_UserPhoto6;
    int imageupload_count=0;
    ArrayList<String> Imageupload_countList=new ArrayList<>();
    userinfo UserInfo = new userinfo();
    ResponseApi responapi =new ResponseApi();
    Rest_ApiService apiService;
    Retrofit retrofit;
    String birthdayYear;
    String birthdayMonth;
    String birthdayDay;

    File Postfile;




    public void onClick_ChatView(View v) {
        Intent intent = new Intent(getApplicationContext(), ChatCustom.class);
        startActivity(intent);
    }

    public void onClick_Basicinfo_upload(View v){               //basicinfo에서 불러온 정보들을 변수에 저장
        RadioChecked_SpinnerCheck();

        Intent intent = new Intent(getApplicationContext(), ChatView.class);
        intent.putExtra("UserInfo",UserInfo);
        intent.putExtra("ProfileFilepate",Imageupload_countList);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicinfo_edit);

        retrofit = new Retrofit.Builder().baseUrl(Rest_ApiService.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService= retrofit.create(Rest_ApiService.class);



        userinfo UserInfo =new userinfo();

        userinfo_save();

        iv_UserPhoto1 = (ImageView) this.findViewById(R.id.photo1);
        iv_UserPhoto2 = (ImageView) this.findViewById(R.id.photo2);
        iv_UserPhoto3 = (ImageView) this.findViewById(R.id.photo3);
        iv_UserPhoto4 = (ImageView) this.findViewById(R.id.photo4);
        iv_UserPhoto5 = (ImageView) this.findViewById(R.id.photo5);
        iv_UserPhoto6 = (ImageView) this.findViewById(R.id.photo6);


        Spinner birthday1 = (Spinner) findViewById(Spinner_birthday1);          //Spinner Setting
        Spinner birthday2 = (Spinner) findViewById(Spinner_birthday2);
        Spinner birthday3 = (Spinner) findViewById(Spinner_birthday3);
        Spinner city = (Spinner) findViewById(Spinner_city);
        Spinner blood = (Spinner) findViewById(Spinner_blood);
        Spinner drink = (Spinner) findViewById(Spinner_drink);
        Spinner religion = (Spinner) findViewById(Spinner_religion);
        Spinner education = (Spinner) findViewById(Spinner_education);
        Spinner Tall=(Spinner)findViewById(Spinner_Tall);
        Spinner body_form_male=(Spinner)findViewById(Spinner_body_form_male);
        Spinner body_form_female=(Spinner)findViewById(Spinner_body_form_female);


        ArrayAdapter adapter= ArrayAdapter.createFromResource(this, R.array.birthday1, spinner_item);
        ArrayAdapter adapter_bir2 = ArrayAdapter.createFromResource(this, R.array.birthday2, spinner_item);
        ArrayAdapter adapter_bir3 = ArrayAdapter.createFromResource(this, R.array.birthday3, spinner_item);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.city, spinner_item);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.blood, spinner_item);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.drink, spinner_item);
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this, R.array.religion, spinner_item);
        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this, R.array.education, spinner_item);
        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(this, R.array.Tall, spinner_item);
        ArrayAdapter adapter8 = ArrayAdapter.createFromResource(this,R.array.body_form_male,spinner_item);
        ArrayAdapter adapter9 = ArrayAdapter.createFromResource(this,R.array.body_form_female,spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthday1.setAdapter(adapter);
        birthday2.setAdapter(adapter_bir2);
        birthday3.setAdapter(adapter_bir3);
        city.setAdapter(adapter2);
        blood.setAdapter(adapter3);
        drink.setAdapter(adapter4);
        religion.setAdapter(adapter5);
        education.setAdapter(adapter6);
        Tall.setAdapter(adapter7);
        body_form_male.setAdapter(adapter8);
        body_form_female.setAdapter(adapter9);
        RadioChecked_SpinnerCheck();   //RadioCehcked&&SpinnerCheck 값 받아오는 class

        //body_form 임의로 넣어줌


    }

    public void onClick_photo_upload(View v) {



        Intent intent = new Intent(getApplicationContext(), ChatView.class);

        startActivity(intent);

    }


    public void onClick(View v) {

        //앨범가져오기
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };
        //취소
        DialogInterface.OnClickListener canceListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드이미지 선택")
                //.setPositiveButton("사진촬영",cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", canceListener)
                .show();
    }

    public void onClick_imageUpload2(View v) {
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };
        //취소
        DialogInterface.OnClickListener canceListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드이미지 선택")
                //.setPositiveButton("사진촬영",cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", canceListener)
                .show();
    }

    public void onClick_imageUpload3(View v) {
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };
        //취소
        DialogInterface.OnClickListener canceListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드이미지 선택")
                //.setPositiveButton("사진촬영",cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", canceListener)
                .show();
    }

    public void onClick_imageUpload4(View v) {
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };
        //취소
        DialogInterface.OnClickListener canceListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드이미지 선택")
                //.setPositiveButton("사진촬영",cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", canceListener)
                .show();
    }

    public void onClick_imageUpload5(View v) {
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };
        //취소
        DialogInterface.OnClickListener canceListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드이미지 선택")
                //.setPositiveButton("사진촬영",cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", canceListener)
                .show();
    }

    public void onClick_imageUpload6(View v) {
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };
        //취소
        DialogInterface.OnClickListener canceListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드이미지 선택")
                //.setPositiveButton("사진촬영",cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", canceListener)
                .show();
    }

    public void onClick_back(View v) {
        super.onBackPressed(); // or super.finish();

    }


    private void doTakeAlbumAction() { //앨범에서 이미지 가져오기
        //앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA: {
                //이미지를 가져온 이후 리자이즈할 이미지 크기
                //이후에 이미지 크롭 어플리케이션 호출
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                //CROP할 이미지 75*75(키울예정)
                intent.putExtra("outputX", 75); //x축 크기
                intent.putExtra("outputY", 75); //y축 크기
                intent.putExtra("aspectX", 1); //x축 비율
                intent.putExtra("aspectY", 1); //y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE: {
                //크롭 이후 이미지 받아서 이미지 뷰에 이미지 보여줌
                //임시파일 삭제
                if (resultCode != RESULT_OK) {
                    return;
                }
                final Bundle extras = data.getExtras();
                imageupload_count++;            //배열에 집어넣기위
                Log.v("imageipload_count", String.valueOf(imageupload_count));

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel" + System.currentTimeMillis() + ".jpg";
                Imageupload_countList.add(filePath);
                for (int i=0;i<Imageupload_countList.size();i++){
                    Log.v("imageupload_countList", String.valueOf(Imageupload_countList.get(i)));

                }
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");//CROP된 BITMAP

                    switch (imageupload_count){
                        case 1:{
                            iv_UserPhoto1.setImageBitmap(photo);
                            storeCropImage(photo,filePath);
                            break;
                        }
                        case 2:{
                            iv_UserPhoto2.setImageBitmap(photo);
                            storeCropImage(photo,filePath);
                            break;
                        }
                        case 3:{
                            iv_UserPhoto3.setImageBitmap(photo);
                            storeCropImage(photo,filePath);
                            break;
                        }
                        case 4:{
                            iv_UserPhoto4.setImageBitmap(photo);
                            storeCropImage(photo,filePath);
                            break;
                        }
                        case 5:{
                            iv_UserPhoto5.setImageBitmap(photo);
                            storeCropImage(photo,filePath);
                            break;
                        }
                    }


                }

                //임시파일삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }

            }

        }
    }
    private void storeCropImage(Bitmap bitmap,String filePath){
        //SmartWheel 폴더를 생성하여 이미지를 저장하는 방식
        String dirPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel =new File(dirPath);

        if(!directory_SmartWheel.exists()){
            directory_SmartWheel.mkdir();
        }
        File copyFile= new File(filePath);
        BufferedOutputStream out=null;

        try{
            copyFile.createNewFile();
            out= new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

            //sendBroadcst를 통해 Crop된 사진을 앨범에 보이도록 갱신한다
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(copyFile)));
            out.flush();
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void userinfo_save(){

        final Intent i =getIntent();                      // facebook 또는 kakao의 아이디, 메신저타입을 받아와 변수에 저장
        String id=i.getExtras().getString("ID");
        String PW=i.getExtras().getString("PW");
        String Setting_id=i.getExtras().getString("setformat");
        Log.e("Test", id);
        Log.e("Test1", String.valueOf(Setting_id));
        UserInfo.setEmail(id);
        UserInfo.setPassword(PW);
        UserInfo.setLogin_format(Setting_id);

        UserInfo.setEmail(id);

    }
        // RadioButton을  checked 하는 함수
    public void RadioChecked_SpinnerCheck(){
        final RadioButton RadioMan_checked = (RadioButton) findViewById(R.id.RadioMan);
        final RadioButton RadioWomen_checked= (RadioButton)findViewById(R.id.RadioWoman);
        final RadioButton RadioArmy_Complete_checked= (RadioButton)findViewById(R.id.RadioArmy_Complete);
        final RadioButton RadioArmy_InComplete_checked= (RadioButton)findViewById(R.id.RadioArmy_InComplete);
        final RadioButton RadioArmy_Notduty_checked= (RadioButton)findViewById(R.id.RadioArmy_Notduty);
        final RadioButton Radio_smoking= (RadioButton)findViewById(R.id.Radio_smokingO);
        final RadioButton Radio_Notsmoking= (RadioButton)findViewById(R.id.RadioNot_smoking);

        final Spinner spinnerbir1 = (Spinner)findViewById(R.id.Spinner_birthday1);
        final Spinner spinnerbir2 = (Spinner)findViewById(R.id.Spinner_birthday2);
        final Spinner spinnerbir3 = (Spinner)findViewById(R.id.Spinner_birthday3);
        final Spinner spinnerbodyform_male=(Spinner)findViewById(R.id.Spinner_body_form_male);
        final Spinner spinnerbodyform_female=(Spinner)findViewById(R.id.Spinner_body_form_female);
        final Spinner spinnerCity = (Spinner)findViewById(R.id.Spinner_city);
        final Spinner spinnerBlood = (Spinner)findViewById(R.id.Spinner_blood);
        final Spinner spinnerDrink=(Spinner)findViewById(R.id.Spinner_drink);
        final Spinner spinnerReligion=(Spinner)findViewById(R.id.Spinner_religion);
        final Spinner spinnerEducation=(Spinner)findViewById(R.id.Spinner_education);
        final Spinner spinnerTall=(Spinner)findViewById(R.id.Spinner_Tall);



        RadioMan_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioMan_checked.isChecked()) {
                    Log.e("성별.", "남자");
                    Button b1 = (Button)findViewById(R.id.r_btn1);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                    RelativeLayout army = (RelativeLayout) findViewById(R.id.army);
                    army.setVisibility(View.VISIBLE);
                    Spinner body_male=(Spinner)findViewById(R.id.Spinner_body_form_male);
                    Spinner body_female=(Spinner)findViewById(R.id.Spinner_body_form_female);

                    body_male.setVisibility(View.VISIBLE);
                    body_female.setVisibility(View.GONE);

                }

            }
        });
        RadioWomen_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioWomen_checked.isChecked()) {
                    Log.e("성별.", "여자");
                    Button b1 = (Button)findViewById(R.id.r_btn1);
                    b1.setBackgroundResource(R.drawable.press_round_btn);

                    RelativeLayout army = (RelativeLayout) findViewById(R.id.army);
                    army.setVisibility(View.GONE);
                    Spinner body_male=(Spinner)findViewById(R.id.Spinner_body_form_male);
                    Spinner body_female=(Spinner)findViewById(R.id.Spinner_body_form_female);

                    body_male.setVisibility(View.GONE);
                    body_female.setVisibility(View.VISIBLE);

                }

            }
        });

        RadioArmy_Complete_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioArmy_Complete_checked.isChecked()) {
                    Log.e("병역.", "병역필");
                    UserInfo.setMilitary_service_status("군필");
                    Button b1 = (Button)findViewById(R.id.r_btn2);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }

            }
        });

        RadioArmy_InComplete_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioArmy_InComplete_checked.isChecked()) {

                    Log.e("병역.", "미필");
                    UserInfo.setMilitary_service_status("미필");
                    Button b1 = (Button)findViewById(R.id.r_btn2);
                    b1.setBackgroundResource(R.drawable.press_round_btn);

                }

            }
        });
        RadioArmy_Notduty_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioArmy_Notduty_checked.isChecked()) {
                    Log.e("병역.", "해당없음");
                    UserInfo.setMilitary_service_status("해당없음");
                    Button b1 = (Button)findViewById(R.id.r_btn2);
                    b1.setBackgroundResource(R.drawable.press_round_btn);

                }

            }
        });
        Radio_smoking.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (Radio_smoking.isChecked()) {
                    Log.e("흡연.", "흡연");
                    UserInfo.setSmoke(true);
                    Button b1 = (Button)findViewById(R.id.r_btn9);
                    b1.setBackgroundResource(R.drawable.press_round_btn);

                }

            }
        });
        Radio_Notsmoking.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (Radio_Notsmoking.isChecked()) {
                    Log.e("흡연.", "비흡연");
                    UserInfo.setSmoke(false);
                    Button b1 = (Button)findViewById(R.id.r_btn9);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }

            }
        });

        spinnerbir1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("birthdayYear", (String) spinnerbir1.getItemAtPosition(position));
                birthdayYear= (String) spinnerbir1.getItemAtPosition(position);
                Button b1 = (Button) findViewById(R.id.r_btn5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerbir2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("birthdayMonth", (String) spinnerbir2.getItemAtPosition(position));
                birthdayMonth= (String) spinnerbir2.getItemAtPosition(position);
                Button b1 = (Button) findViewById(R.id.r_btn5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerbir3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("birthdayDay", (String) spinnerbir3.getItemAtPosition(position));
                birthdayDay= (String) spinnerbir3.getItemAtPosition(position);
                UserInfo.setBirthday(birthdayYear+"-"+birthdayMonth+"-"+birthdayDay);
                Log.v("SETBIRTHDAY",UserInfo.getBirthday());
                Button b1 = (Button) findViewById(R.id.r_btn5);
                if(UserInfo.getBirthday().contains("00") == false) {
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                } else {
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("City", (String) spinnerCity.getItemAtPosition(position));
                UserInfo.setLocation(spinnerCity.getItemAtPosition(position).toString());
                Button b1 = (Button) findViewById(R.id.r_btn6);
                if("--".equals(UserInfo.getLocation())!=true) {
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                } else  {
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Blood", (String) spinnerBlood.getItemAtPosition(position));
                UserInfo.setBlood_type(spinnerBlood.getItemAtPosition(position).toString());
                Button b1 = (Button) findViewById(R.id.r_btn8);
                if("--".equals(UserInfo.getBlood_type())!=true) {
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                } else {
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerDrink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Drink", (String) spinnerDrink.getItemAtPosition(position));
                UserInfo.setDrink(spinnerDrink.getItemAtPosition(position).toString());
                Button b1 = (Button)findViewById(R.id.r_btn10);
                if("--".equals(UserInfo.getDrink())!=true){
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                } else {
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Religion", (String) spinnerReligion.getItemAtPosition(position));
                String CheckSpinnerReligion=(String)spinnerReligion.getItemAtPosition(position);
                Character InputUserinfoReligion;
                UserInfo.setReligion(CheckSpinnerReligion);
                Button b1 = (Button)findViewById(R.id.r_btn11);

                if("--".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='P';
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
                else if("기독교".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='P';
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
                else if("불교".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='B';
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
                else if("가톨릭".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='C';
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
                else if("이슬람".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='I';
                    b1.setBackgroundResource(R.drawable.press_round_btn);

                }
                else if("없음".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='N';
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
                else if("기타".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='N';
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }


                else{
                    InputUserinfoReligion='O';
                    b1.setBackgroundResource(R.drawable.round_btn);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("education", (String) spinnerEducation.getItemAtPosition(position));
                UserInfo.setEducation(spinnerEducation.getItemAtPosition(position).toString());
                Button b1 = (Button) findViewById(R.id.r_btn3);
                if("--".equals(UserInfo.getEducation())!=true) {
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                } else {
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("BODY_form", (String) spinnerbodyform_male.getItemAtPosition(position));
                UserInfo.setBody_form(spinnerbodyform_male.getItemAtPosition(position).toString());
                Button b1 = (Button) findViewById(R.id.r_btn3);
                if("--".equals(UserInfo.getEducation())!=true) {
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                } else {
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("BODY_form", (String) spinnerbodyform_female.getItemAtPosition(position));
                UserInfo.setBody_form(spinnerbodyform_female.getItemAtPosition(position).toString());
                Button b1 = (Button) findViewById(R.id.r_btn3);
                if("--".equals(UserInfo.getBody_form())!=true) {
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                } else {
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Tall", (String) spinnerTall.getItemAtPosition(position));
                UserInfo.setheight(Integer.parseInt(spinnerTall.getItemAtPosition(position).toString()));

                //bodyform 임의로 넣어줌
                UserInfo.setBody_form("슬림");

                Button b1 = (Button) findViewById(R.id.r_btn7);
                if(UserInfo.getheight() != 00){
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                } else {
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        EditText InputCareea=(EditText)findViewById(R.id.InputCarrea);
        String Check_InputCareea=InputCareea.getText().toString();
        UserInfo.setDepartment(Check_InputCareea);

        InputCareea.addTextChangedListener(new TextWatcher() {
            // 입력되는 텍스트에 변화가 있을 때
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Button b1 = (Button) findViewById(R.id.r_btn4);
                if (s.length() >= 1) {
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                } else {
                    b1.setBackgroundResource(R.drawable.round_btn);
                }
            }

            // 입력이 끝났을 때
            @Override
            public void afterTextChanged(Editable editable) {

            }

            // 입력하기 전에
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });





    }


}