package com.example.leeyun.stringting_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.support.v7.app.AlertDialog;

import com.example.leeyun.stringting_android.API.userinfo;

import java.io.File;


import static com.example.leeyun.stringting_android.R.id.Spinner_age;
import static com.example.leeyun.stringting_android.R.id.Spinner_blood;
import static com.example.leeyun.stringting_android.R.id.Spinner_city;
import static com.example.leeyun.stringting_android.R.id.Spinner_drink;
import static com.example.leeyun.stringting_android.R.id.Spinner_education;
import static com.example.leeyun.stringting_android.R.id.Spinner_religion;
import static com.example.leeyun.stringting_android.R.layout.spinner_item;
import static java.lang.Integer.parseInt;

/**
 * Created by leeyun on 2017. 9. 16..
 */

public class Basicinfo_Edit extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto1, iv_UserPhoto2, iv_UserPhoto3, iv_UserPhoto4, iv_UserPhoto5, iv_UserPhoto6;
    userinfo UserInfo =new userinfo();



    public void onClick_ChatView(View v) {
        Intent intent = new Intent(getApplicationContext(), ChatCustom.class);
        startActivity(intent);
    }

    public void onClick_Basicinfo_upload(View v){               //basicinfo에서 불러온 정보들을 변수에 저장
        RadioChecked_SpinnerCheck();
        Intent intent = new Intent(getApplicationContext(), ChatView.class);
        intent.putExtra("UserInfo",UserInfo);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicinfo_edit);
        userinfo UserInfo =new userinfo();

        userinfo_save();

        iv_UserPhoto1 = (ImageView) this.findViewById(R.id.photo1);
        iv_UserPhoto2 = (ImageView) this.findViewById(R.id.photo2);
        iv_UserPhoto3 = (ImageView) this.findViewById(R.id.photo3);
        iv_UserPhoto4 = (ImageView) this.findViewById(R.id.photo4);
        iv_UserPhoto5 = (ImageView) this.findViewById(R.id.photo5);
        iv_UserPhoto6 = (ImageView) this.findViewById(R.id.photo6);


        Spinner age = (Spinner) findViewById(Spinner_age);          //Spinner Setting
        Spinner city = (Spinner) findViewById(Spinner_city);
        Spinner blood = (Spinner) findViewById(Spinner_blood);
        Spinner drink = (Spinner) findViewById(Spinner_drink);
        Spinner religion = (Spinner) findViewById(Spinner_religion);
        Spinner education = (Spinner) findViewById(Spinner_education);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.age, spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.city, spinner_item);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.blood, spinner_item);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.drink, spinner_item);
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this, R.array.religion, spinner_item);
        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this, R.array.education, spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(adapter);
        city.setAdapter(adapter2);
        blood.setAdapter(adapter3);
        drink.setAdapter(adapter4);
        religion.setAdapter(adapter5);
        education.setAdapter(adapter6);

        RadioChecked_SpinnerCheck();   //RadioCehcked&&SpinnerCheck 값 받아오는 class



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

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");//CROP된 BITMAP


                    if (null == iv_UserPhoto1.getDrawable()) {
                        iv_UserPhoto1.setImageBitmap(photo);
                    } else if (null == iv_UserPhoto2.getDrawable()) {
                        iv_UserPhoto2.setImageBitmap(photo);
                    } else if (null == iv_UserPhoto3.getDrawable()) {
                        iv_UserPhoto3.setImageBitmap(photo);
                    } else if (null == iv_UserPhoto4.getDrawable()) {
                        iv_UserPhoto4.setImageBitmap(photo);

                    } else {
                        iv_UserPhoto5.setImageBitmap(photo);

                    }
                    break;
                }


                //임시파일삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }

            }

        }
    }
    public void userinfo_save(){

        final Intent i =getIntent();                      // facebook 또는 kakao의 아이디, 메신저타입을 받아와 변수에 저장
        String id=i.getExtras().getString("ID");
        char Setting_id=i.getExtras().getChar("setid");
        Log.e("Test", id);
        Log.e("Test1", String.valueOf(Setting_id));
        UserInfo.Id=id;
        UserInfo.login_format=Setting_id;
        Log.e("Test3",UserInfo.Id);                     //LOG.e는 테스트코드
        UserInfo.email=id;

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

        final Spinner spinnerAge = (Spinner)findViewById(R.id.Spinner_age);
        final Spinner spinnerCity = (Spinner)findViewById(R.id.Spinner_city);
        final Spinner spinnerBlood = (Spinner)findViewById(R.id.Spinner_blood);
        final Spinner spinnerDrink=(Spinner)findViewById(R.id.Spinner_drink);
        final Spinner spinnerReligion=(Spinner)findViewById(R.id.Spinner_religion);
        final Spinner spinnerEducation=(Spinner)findViewById(R.id.Spinner_education);


        RadioMan_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioMan_checked.isChecked()) {
                    Log.e("성별.", "남자");

                }

            }
        });
        RadioWomen_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioMan_checked.isChecked()) {
                    Log.e("성별.", "여자");
                }

            }
        });

        RadioArmy_Complete_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioArmy_Complete_checked.isChecked()) {
                    Log.e("병역.", "병역필");
                    UserInfo.military_service_status="Army_complete";
                }

            }
        });

        RadioArmy_InComplete_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioArmy_InComplete_checked.isChecked()) {

                    Log.e("병역.", "미필");
                    UserInfo.military_service_status="Army_Incomplete";

                }

            }
        });
        RadioArmy_Notduty_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioArmy_Notduty_checked.isChecked()) {
                    Log.e("병역.", "해당없음");
                    UserInfo.military_service_status="Army_Notduty";

                }

            }
        });
        Radio_smoking.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (Radio_smoking.isChecked()) {
                    Log.e("흡연.", "흡연");
                    UserInfo.smoke=true;

                }

            }
        });
        Radio_Notsmoking.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (Radio_Notsmoking.isChecked()) {
                    Log.e("흡연.", "비흡연");
                    UserInfo.smoke=false;
                }

            }
        });

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("age", (String) spinnerAge.getItemAtPosition(position));
                UserInfo.age= Integer.parseInt(spinnerAge.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("City", (String) spinnerCity.getItemAtPosition(position));
                UserInfo.location=spinnerCity.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Blood", (String) spinnerBlood.getItemAtPosition(position));
                UserInfo.blood_type=spinnerBlood.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerDrink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Drink", (String) spinnerDrink.getItemAtPosition(position));
                UserInfo.drink=spinnerDrink.getItemAtPosition(position).toString();
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

                if("기독교".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='P';
                }
                else if("불교".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='B';
                }
                else if("가톨릭".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='C';
                }
                else if("이슬람".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='I';

                }
                else if("없음".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='N';
                }
                else{
                    InputUserinfoReligion='O';
                }
                UserInfo.religion=InputUserinfoReligion;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("education", (String) spinnerEducation.getItemAtPosition(position));
                UserInfo.education=spinnerEducation.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText InputCareea=(EditText)findViewById(R.id.InputCarrea);
        String Check_InputCareea=InputCareea.getText().toString();
        UserInfo.department=Check_InputCareea;

        EditText InputTall=(EditText)findViewById(R.id.InputTall);
        String Check_InputTall=InputTall.getText().toString();
        try{
            UserInfo.hegiht= Integer.parseInt(Check_InputTall.toString());

        }catch(NumberFormatException nfe){
            Log.e("error","error");
        }


    }

}