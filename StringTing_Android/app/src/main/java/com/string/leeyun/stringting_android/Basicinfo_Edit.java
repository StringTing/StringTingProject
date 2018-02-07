package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.join;
import com.string.leeyun.stringting_android.API.message;
import com.string.leeyun.stringting_android.API.userinfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HEAD;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.id.Spinner_Tall;
import static com.string.leeyun.stringting_android.R.id.Spinner_birthday1;
import static com.string.leeyun.stringting_android.R.id.Spinner_birthday2;
import static com.string.leeyun.stringting_android.R.id.Spinner_birthday3;
import static com.string.leeyun.stringting_android.R.id.Spinner_blood;
import static com.string.leeyun.stringting_android.R.id.Spinner_body_form_female;
import static com.string.leeyun.stringting_android.R.id.Spinner_body_form_male;
import static com.string.leeyun.stringting_android.R.id.Spinner_city;
import static com.string.leeyun.stringting_android.R.id.Spinner_drink;
import static com.string.leeyun.stringting_android.R.id.Spinner_education;
import static com.string.leeyun.stringting_android.R.id.Spinner_religion;
import static com.string.leeyun.stringting_android.R.id.sex;
import static com.string.leeyun.stringting_android.R.layout.spinner_item;


/**
 * Created by leeyun on 2017. 9. 16..
 */

public class Basicinfo_Edit extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto1, iv_UserPhoto2, iv_UserPhoto3, iv_UserPhoto4, iv_UserPhoto5, iv_UserPhoto6;
    public int imageupload_count=0;
    public ArrayList<String> Imageupload_countList=new ArrayList<>();
    userinfo UserInfo = new userinfo();
    ResponseApi responapi =new ResponseApi();
    Rest_ApiService apiService;
    Retrofit retrofit;
    String birthdayYear;
    String token;
    String birthdayMonth;
    String birthdayDay;
    String real_album_path;
    int account_id;
    String sex;
    String token_localdb;
    int account_id_localdb;
    String Setting_id;
    message Message = new message();

    File Postfile;



     @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }




    public void onClick_ChatView(View v) {
        Intent intent = new Intent(getApplicationContext(), ChatCustom.class);
        startActivity(intent);
    }

    public void onClick_Basicinfo_upload(View v){               //basicinfo에서 불러온 정보들을 변수에 저장
        RadioChecked_SpinnerCheck();

        Intent intent = new Intent(getApplicationContext(), ChatView.class);
        intent.putExtra("UserInfo",UserInfo);
        intent.putExtra("ProfileFilepath",Imageupload_countList);
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        sex = pref.getString("sex","sex");
        UserInfo.setSex(sex);

        try {
            Call<join> getPostUserinfo = apiService.getPostUserinfo(UserInfo);
            getPostUserinfo.enqueue(new Callback<join>() {
                @Override
                public void onResponse(Call<join> call, Response<join> response) {

                    join gsonresponse=response.body();
                    Log.e("onresponse_join", gsonresponse.getResult());
                    Log.e("onresponse", String.valueOf(response.code()));
                    Log.e("onresponse", "success");
                    account_id=gsonresponse.getAccount_id();
                    Log.e("account_id", String.valueOf(account_id));
                    Log.e("fcm_token",String.valueOf(UserInfo.getFcm_token()));

                    if (gsonresponse.getToken()!=null){
                        save_token(gsonresponse.getToken(),gsonresponse.getAccount_id(),sex);
                    }
                    SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                    account_id_localdb = pref.getInt("account_id",0);
                    Log.e("local_account", Integer.toString(account_id_localdb));
                    token_localdb=pref.getString("token","?");
                    Log.e("local_token",String.valueOf(token_localdb));

                }

                @Override
                public void onFailure(Call<join> call, Throwable t) {
                    Log.d("sam", t.toString());
                }

            });

        } catch (Exception e)

        {

        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicinfo_edit);

        userinfo_save();

        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request builder = chain.request();
                Request newRequest;
                if (Setting_id.equals("EMAIL")){
                    newRequest = builder.newBuilder()
                            .addHeader("access-token"," ")
                            .build();
                }
                else {
                    newRequest = builder.newBuilder()
                            .addHeader("access-token", token_localdb)
                            .build();
                }


                return chain.proceed(newRequest);

            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .build();
        apiService= retrofit.create(Rest_ApiService.class);




        userinfo_save();

        iv_UserPhoto1 = (ImageView) this.findViewById(R.id.photo1);
        iv_UserPhoto2 = (ImageView) this.findViewById(R.id.photo2);
        iv_UserPhoto3 = (ImageView) this.findViewById(R.id.photo3);
        iv_UserPhoto4 = (ImageView) this.findViewById(R.id.photo4);
        iv_UserPhoto5 = (ImageView) this.findViewById(R.id.photo5);
        iv_UserPhoto6 = (ImageView) this.findViewById(R.id.photo6);


        Spinner birthday1 = (Spinner) findViewById(Spinner_birthday1);//Spinner Setting
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

        ArrayAdapter adapter= ArrayAdapter.createFromResource(this, R.array.birthday1, R.layout.spinner_item);

        ArrayAdapter adapter_bir2 = ArrayAdapter.createFromResource(this, R.array.birthday2, spinner_item);
        ArrayAdapter adapter_bir3 = ArrayAdapter.createFromResource(this, R.array.birthday3, spinner_item);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.city, spinner_item);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.blood, spinner_item);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.drink, spinner_item);
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this, R.array.religion, spinner_item);
        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this, R.array.education, spinner_item);
        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(this, R.array.Tall, spinner_item);
        ArrayAdapter adapter8 = ArrayAdapter.createFromResource(this, R.array.body_form_male,spinner_item);
        ArrayAdapter adapter9 = ArrayAdapter.createFromResource(this, R.array.body_form_female,spinner_item);


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
        Intent pop = new Intent(this,Basicinfo_pop.class);
        startActivity(pop);
    }

    @Override
    public void onBackPressed() {
        Intent pop = new Intent(this,Basicinfo_pop.class);
        startActivity(pop);
       // super.onBackPressed();
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
                Log.e("SmartWheel", mImageCaptureUri.getPath().toString());
                real_album_path= getPath(mImageCaptureUri);
                Log.e("real_album_path",real_album_path);
            }
            case PICK_FROM_CAMERA: {
                //이미지를 가져온 이후 리자이즈할 이미지 크기
                //이후에 이미지 크롭 어플리케이션 호출
//                Intent editIntent = new Intent(Intent.ACTION_EDIT);
//                editIntent.setDataAndType(mImageCaptureUri, "image/*");
//                editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                //CROP할 이미지 75*75(키울예정)
//
//                editIntent.putExtra("scale", true);
//                editIntent.putExtra("return-data", true);
//                startActivityForResult(editIntent, CROP_FROM_IMAGE);
//                break;
            }
            case CROP_FROM_IMAGE: {
                //크롭 이후 이미지 받아서 이미지 뷰에 이미지 보여줌
                //임시파일 삭제
                if (resultCode != RESULT_OK) {
                    return;
                }
                final Bundle extras = data.getExtras();

                imageupload_count++;
                Log.e("imageipload_count", String.valueOf(imageupload_count));


                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel" + System.currentTimeMillis() + ".jpg";
                Log.e("filepath",filePath);
                Imageupload_countList.add(real_album_path);

                for (int i=0;i<Imageupload_countList.size();i++){
                    Log.e("imageupload_countList", String.valueOf(Imageupload_countList.get(i)));

                }



//                if (extras != null) {
//                    Bitmap photo = extras.getParcelable("data");//CROP된 BITMAP

                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderWidthDp(0)
                            .cornerRadiusDp(8)
                            .oval(false)
                            .build();

                    switch (imageupload_count){
                        case 1:{
                            Picasso.with(Basicinfo_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto1);
                            storeCropImage(filePath);
                            break;
                        }
                        case 2:{
                            Picasso.with(Basicinfo_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto2);
//                            storeCropImage(photo,filePath);
                            break;
                        }
                        case 3:{
                            Picasso.with(Basicinfo_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto3);
//                            storeCropImage(photo,filePath);
                            break;
                        }
                        case 4:{
                            Picasso.with(Basicinfo_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto4);
//                            storeCropImage(photo,filePath);
                            break;
                        }
                        case 5:{
                            Picasso.with(Basicinfo_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto5);
//                            storeCropImage(photo,filePath);
                            break;
                        }
                    }


//                }

                //임시파일삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }

            }

        }
    }
    private void storeCropImage(String filePath){
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
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

            //sendBroadcst를 통해 Crop된 사진을 앨범에 보이도록 갱신한다
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(copyFile)));
            out.flush();
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }


    public void userinfo_save(){


        try {
            Intent i = getIntent();                      // facebook 또는 kakao의 아이디, 메신저타입을 받아와 변수에 저장
            String id = i.getExtras().getString("ID");
            String PW = i.getExtras().getString("PW");
<<<<<<< HEAD
            Setting_id = i.getExtras().getString("setformat");
=======
             Setting_id = i.getExtras().getString("setformat");
>>>>>>> d7acdc39510327b15ef34a2930fe5c6675b944ac
            token=i.getExtras().getString("token");
            String fcm_token = i.getExtras().getString("fcm_token");
            Log.e("Test", id);
            Log.e("Test1", String.valueOf(Setting_id));

        UserInfo.setEmail(id);
        UserInfo.setPassword(PW);
        UserInfo.setLogin_format(Setting_id);
        UserInfo.setEmail(id);

        SharedPreferences fcm = getSharedPreferences("Local_DB", MODE_PRIVATE);

        try {
            fcm_token = fcm.getString("fcm_token", "success");
            Log.v("fcm_token",fcm_token);

                Log.e("localid is null","fail");
                UserInfo.setFcm_token(fcm_token);



        }catch (Exception e){
            e.printStackTrace();
            Log.e("can not get localid","fail");
        }
        }catch (Exception e){
            e.printStackTrace();
        }


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

        final Spinner spinnerbir1 = (Spinner)findViewById(Spinner_birthday1);
        final Spinner spinnerbir2 = (Spinner)findViewById(Spinner_birthday2);
        final Spinner spinnerbir3 = (Spinner)findViewById(Spinner_birthday3);
        final Spinner spinnerbodyform_male=(Spinner)findViewById(Spinner_body_form_male);
        final Spinner spinnerbodyform_female=(Spinner)findViewById(Spinner_body_form_female);
        final Spinner spinnerCity = (Spinner)findViewById(Spinner_city);
        final Spinner spinnerBlood = (Spinner)findViewById(Spinner_blood);
        final Spinner spinnerDrink=(Spinner)findViewById(Spinner_drink);
        final Spinner spinnerReligion=(Spinner)findViewById(Spinner_religion);
        final Spinner spinnerEducation=(Spinner)findViewById(Spinner_education);
        final Spinner spinnerTall=(Spinner)findViewById(Spinner_Tall);



        RadioMan_checked.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (RadioMan_checked.isChecked()) {
                    Log.e("성별.", "남자");
                    Button b1 = (Button)findViewById(R.id.r_btn1);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                    RelativeLayout army = (RelativeLayout)findViewById(R.id.army);
                    RelativeLayout army_txt = (RelativeLayout) findViewById(R.id.army_txt);
                    army_txt.setVisibility(View.VISIBLE);
                    army.setVisibility(View.VISIBLE);
                    Spinner body_male=(Spinner)findViewById(Spinner_body_form_male);
                    Spinner body_female=(Spinner)findViewById(Spinner_body_form_female);


                    save_sex("male");
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
                    RelativeLayout army_txt = (RelativeLayout) findViewById(R.id.army_txt);
                    army_txt.setVisibility(View.GONE);
                    army.setVisibility(View.GONE);
                    Spinner body_male=(Spinner)findViewById(Spinner_body_form_male);
                    Spinner body_female=(Spinner)findViewById(Spinner_body_form_female);
                    save_sex("female");
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
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                } else {
                    ((TextView) view).setTextColor(BLACK);
                }
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
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                } else {
                    ((TextView) view).setTextColor(BLACK);
                }
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
                TextView t = (TextView)findViewById(R.id.spitem_set);
                try{
                    if (position == 0) {
                        t.setTextColor(Color.rgb(221,220,220));
                    } else {
                        t.setTextColor(BLACK);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(UserInfo.getBirthday().contains("년도")&&UserInfo.getBirthday().contains("월")&&UserInfo.getBirthday().contains("일") == false) {
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
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                    b1.setBackgroundResource(R.drawable.round_btn);
                } else {
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
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
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                    b1.setBackgroundResource(R.drawable.round_btn);
                } else {
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
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
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                    b1.setBackgroundResource(R.drawable.round_btn);
                } else {
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
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

                if (position == 0) {
                    InputUserinfoReligion='P';
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                    b1.setBackgroundResource(R.drawable.round_btn);
                }

                else if("기독교".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='P';
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
                else if("불교".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='B';
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
                else if("가톨릭".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='C';
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
                else if("이슬람".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='I';
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);

                }
                else if("없음".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='N';
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
                else if("기타".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='N';
                    ((TextView) view).setTextColor(BLACK);
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
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                    b1.setBackgroundResource(R.drawable.round_btn);
                } else {
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerbodyform_male.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("BODY_form", (String) spinnerbodyform_male.getItemAtPosition(position));
                UserInfo.setBody_form(spinnerbodyform_male.getItemAtPosition(position).toString());
                Button b1 = (Button) findViewById(R.id.r_btn12);
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                    b1.setBackgroundResource(R.drawable.round_btn);
                } else {
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerbodyform_female.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("BODY_form", (String) spinnerbodyform_female.getItemAtPosition(position));
                UserInfo.setBody_form(spinnerbodyform_female.getItemAtPosition(position).toString());
                Button b1 = (Button) findViewById(R.id.r_btn3);
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                    b1.setBackgroundResource(R.drawable.round_btn);
                } else {
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
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
                UserInfo.setheight(spinnerTall.getItemAtPosition(position).toString());


                Button b1 = (Button) findViewById(R.id.r_btn7);
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.rgb(221,220,220));
                    b1.setBackgroundResource(R.drawable.round_btn);
                } else {
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final EditText InputCareea=(EditText)findViewById(R.id.InputCarrea);
        String Check_InputCareea=InputCareea.getText().toString();
        UserInfo.setDepartment(Check_InputCareea);


        InputCareea.addTextChangedListener(new TextWatcher() {
            // 입력되는 텍스트에 변화가 있을 때
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Button b1 = (Button) findViewById(R.id.r_btn4);
                if (s.length() >= 1) {
                    b1.setBackgroundResource(R.drawable.press_round_btn);
                    InputCareea.setTextColor(BLACK);
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

    public void save_sex(String data){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sex",data);
        editor.clear();
        editor.commit();
    }
    public void save_token(String token,int account_id,String sex){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token",token);
        editor.putInt("account_id",account_id);
        editor.putString("sex",sex);
        editor.clear();
        editor.commit();
    }



}
