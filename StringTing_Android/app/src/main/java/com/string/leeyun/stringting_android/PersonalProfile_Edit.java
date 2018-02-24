package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.string.leeyun.stringting_android.API.Getdetail;
import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.accounts;
import com.string.leeyun.stringting_android.API.get_introduction_qnalist;
import com.string.leeyun.stringting_android.API.message;
import com.string.leeyun.stringting_android.API.userinfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.BLACK;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.ChatView.position;
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
import static com.string.leeyun.stringting_android.R.layout.spinner_item;
import com.string.leeyun.stringting_android.model.api_call;

public class PersonalProfile_Edit extends AppCompatActivity {

    //사진 및 정보
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mImageCaptureUri;
    ImageView iv_UserPhoto1, iv_UserPhoto2, iv_UserPhoto3, iv_UserPhoto4, iv_UserPhoto5, iv_UserPhoto6;
    public int imageupload_count=0;
    public ArrayList<String> Imageupload_countList=new ArrayList<>();
    userinfo UserInfo = new userinfo();
    ResponseApi responapi =new ResponseApi();
    Rest_ApiService apiService;
    Retrofit retrofit;
    String token;
    int account_id;
    String sex;
    Getdetail getdetail;
    accounts account;
    ImageView personfile_image;
    ArrayList<String>profile_image_list;
    ArrayList<String>profile_image_replace;
    ArrayList<String>profile_image_full_url=new ArrayList<String>();
    ArrayList<String>profile_image_list_in_review;
    ArrayList<String>profile_image_replace_in_review;
    ArrayList<String>profile_image_full_url_in_review=new ArrayList<String>();
    ArrayList<Integer>profile_approved_index_list=new ArrayList<Integer>();
    ArrayList<Integer>Profile_in_review_index_list=new ArrayList<>();

    int macthing_account;
    String macthing_sex;
    ArrayList<Integer>detail_photo;
    get_introduction_qnalist QnaList;
    ArrayList<String> question_array;
    ArrayList<String>answer_array;
    String real_album_path;
    message Message = new message();

    String birthdayYear;

    File Postfile;




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }




    //대화창
    ListView m_ListView;
    ChatCustom m_Adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile__edit);

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


        city.setAdapter(adapter2);
        blood.setAdapter(adapter3);
        drink.setAdapter(adapter4);
        religion.setAdapter(adapter5);
        education.setAdapter(adapter6);
        Tall.setAdapter(adapter7);
        body_form_male.setAdapter(adapter8);
        body_form_female.setAdapter(adapter9);

        //RadioChecked_SpinnerCheck();


        get_local_data();


        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request builder = chain.request();
                Request newRequest;


                newRequest = builder.newBuilder()
                        .addHeader("access-token",token)
                        .addHeader("account-id", String.valueOf(account_id))
                        .addHeader("account-sex",sex)
                        .addHeader("Content-Type","application/json")
                        .build();


                return chain.proceed(newRequest);

            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .build();


        apiService= retrofit.create(Rest_ApiService.class);



        try {
            Call<Getdetail> call = apiService.get_detail(sex, account_id);

            call.enqueue(new Callback<Getdetail>() {

                @Override
                public void onResponse(Call<Getdetail> call, retrofit2.Response<Getdetail> response) {
                    Log.e("today_introduction", String.valueOf(response.raw()));
                    Log.e("today_introduction", String.valueOf(response.body()));
                    Log.e("today_introduction", String.valueOf(response.code()));

                    getdetail=new Getdetail();
                    getdetail=response.body();
                    profile_image_list=new ArrayList<String>();
                    profile_image_replace=new ArrayList<String>();
                    profile_image_list_in_review=new ArrayList<String>();
                    profile_image_replace_in_review=new ArrayList<String>();
                    try {
                        detail_photo=new ArrayList<Integer>();

                        if (getdetail.getImages().getApproved().get(0).getName() != null) {
                            for (int i=0;i<getdetail.getImages().getApproved().size();i++) {
                                //이미지 url 받아오는 로직
                                profile_image_list.add(String.valueOf(getdetail.getImages().getApproved().get(i).getName()));
                                profile_approved_index_list.add(getdetail.getImages().getApproved().get(i).getIndex());
                            }

                            Log.e("get_approved_list_image", String.valueOf(getdetail.getImages().getApproved().get(0).getName()));

                        }
                        if(getdetail.getImages().getIn_review().get(0).getName()!=null){
                            for (int i=0;i<getdetail.getImages().getIn_review().size();i++) {
                                //이미지 url 받아오는 로직
                                profile_image_list_in_review.add(getdetail.getImages().getIn_review().get(i).getName());
                                Profile_in_review_index_list.add(getdetail.getImages().getIn_review().get(i).getIndex());
                            }
                            Log.e("get_in_review_list_image", String.valueOf(getdetail.getImages().getApproved().get(0).getName()));

                        }

                        String replace = "{}";
                        String medium = "medium";
                        String large="large";
                        String small = "small";

                        if (getdetail.getImages().getApproved().get(0) != null) {
                            for (int i=0;i<getdetail.getImages().getApproved().size();i++){
                                //이미지 url 받아온 것 size 변경
                                profile_image_replace.add(profile_image_list.get(i).replace(replace,large));
                                //이미지 full url 이거 picasso에 바로 넣으면 됨
                                profile_image_full_url.add(API_IMAGE_URL+profile_image_replace.get(i));
                            }
                            get_image_setting(profile_image_full_url,profile_approved_index_list);
                            Log.e("image_url_first", profile_image_replace.get(0));
                        }

                        if (getdetail.getImages().getIn_review().get(0) !=null) {
                            for(int i=0; i<getdetail.getImages().getIn_review().size();i++){
                                profile_image_replace_in_review.add(profile_image_list_in_review.get(i).replace(replace,large));
                                profile_image_full_url_in_review.add(API_IMAGE_URL+profile_image_replace_in_review.get(i));
                            }
                            Log.e("image_url_in_review",profile_image_full_url_in_review.get(0));
                            get_image_setting(profile_image_full_url_in_review,Profile_in_review_index_list);
                        }
                        //이런식으로 음주,나이,키 등등 받아오면 됩니다.
                        getdetail.getDrink();
                        getdetail.getAge();

                        if(sex.equals("female")){

                            Spinner body_male=(Spinner)findViewById(Spinner_body_form_male);
                            Spinner body_female=(Spinner)findViewById(Spinner_body_form_female);
                            body_male.setVisibility(View.GONE);
                            body_female.setVisibility(View.VISIBLE);
                        }else {
                            Spinner body_male=(Spinner)findViewById(Spinner_body_form_male);
                            Spinner body_female=(Spinner)findViewById(Spinner_body_form_female);
                            body_male.setVisibility(View.VISIBLE);
                            body_female.setVisibility(View.GONE);
                        }

                        radioCheck();
                        //Log.e("담배", String.valueOf(getdetail.isSmoke()));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("imagenull_today", "false");
                    }

                }

                @Override
                public void onFailure(Call<Getdetail> call, Throwable t) {
                    Log.e("getdatail-fail", t.toString());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("get_detail_profile","null");
        }


        /*QnaList=new get_introduction_qnalist();

        Call<get_introduction_qnalist> PostQnaList = apiService.get_introduction_qnalist(sex,account_id);
        PostQnaList.enqueue(new Callback<get_introduction_qnalist>() {
            @Override
            public void onResponse(Call<get_introduction_qnalist> call, Response<get_introduction_qnalist> response) {

                QnaList=response.body();

                question_array=new ArrayList<String>();
                answer_array=new ArrayList<String>();


                Log.e("onresponse_get_question", String.valueOf(QnaList.getQna_list().getApproved().get(0).getAnswer()));



                for (int i=0;i<3;i++){
                    //질문,답변 차례로 arraylist에 add합니다.
                    question_array.add(QnaList.getQna_list().getApproved().get(i).getQuestion());
                    answer_array.add(QnaList.getQna_list().getApproved().get(i).getAnswer());
                }



                // 커스텀 어댑터 생성
                m_Adapter = new ChatCustom();


                // Xml에서 추가한 ListView 연결
                m_ListView = (ListView) findViewById(R.id.listView1);



                // ListView에 어댑터 연결
                m_ListView.setAdapter(m_Adapter);


                m_Adapter.add(question_array.get(0), 0);

                m_Adapter.add(answer_array.get(0), 1);

                m_Adapter.add(question_array.get(1), 0);

                m_Adapter.add(answer_array.get(1), 1);

                m_Adapter.add(question_array.get(2), 0);

                m_Adapter.add(answer_array.get(2), 1);
                setListViewHeightBasedOnItems(m_ListView);

            }

            @Override
            public void onFailure(Call<get_introduction_qnalist> call, Throwable t) {
                Log.d("sam", t.toString());
            }

        });


        //수정 버튼을 클릭하면 edittext를 받아서 listview에 세팅해줌
        findViewById(R.id.modify_sendbtn).setOnClickListener(new TextView.OnClickListener() {
            int i=1;
            @Override
            public void onClick(View v) {

                EditText editText =(EditText)findViewById(R.id.modify_Edit);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
                Toast.makeText(getApplicationContext(), "modify_send", Toast.LENGTH_LONG).show();
                String modifyString = editText.getText().toString();
                Log.v("modifyString",modifyString);

                m_Adapter.getM_List().set(position, new ChatCustom.ListContents(modifyString,position));
                m_Adapter.notifyDataSetChanged();

                LinearLayout l2 = (LinearLayout)findViewById(R.id.enter_chatting_visible);
                l2.setVisibility(View.GONE);
                Button confirm = (Button)findViewById(R.id.confirm_btn);
                confirm.setVisibility(Button.VISIBLE);


            }
        }
        );*/

    }

    public void radioCheck() {

        final RadioButton RadioArmy_Complete_checked = (RadioButton) findViewById(R.id.RadioArmy_Complete);
        final RadioButton RadioArmy_InComplete_checked = (RadioButton) findViewById(R.id.RadioArmy_InComplete);
        final RadioButton RadioArmy_Notduty_checked = (RadioButton) findViewById(R.id.RadioArmy_Notduty);
        final RadioButton Radio_smoking = (RadioButton) findViewById(R.id.Radio_smokingO);
        final RadioButton Radio_Notsmoking = (RadioButton) findViewById(R.id.RadioNot_smoking);

        final Spinner spinnerbir1 = (Spinner) findViewById(Spinner_birthday1);
        final Spinner spinnerbir2 = (Spinner) findViewById(Spinner_birthday2);
        final Spinner spinnerbir3 = (Spinner) findViewById(Spinner_birthday3);
        final Spinner spinnerbodyform_male = (Spinner) findViewById(Spinner_body_form_male);
        final Spinner spinnerbodyform_female = (Spinner) findViewById(Spinner_body_form_female);
        final Spinner spinnerCity = (Spinner) findViewById(Spinner_city);
        final Spinner spinnerBlood = (Spinner) findViewById(Spinner_blood);
        final Spinner spinnerDrink = (Spinner) findViewById(Spinner_drink);
        final Spinner spinnerReligion = (Spinner) findViewById(Spinner_religion);
        final Spinner spinnerEducation = (Spinner) findViewById(Spinner_education);
        final Spinner spinnerTall = (Spinner) findViewById(Spinner_Tall);


        //학력스피너
        //기존값 불러오기
        int education_length = getResources().getStringArray(R.array.education).length;
        int education_spinnerIndex =0;

        for(int p=0;p<education_length;p++){

            if(spinnerEducation.getItemAtPosition(p).toString().equals(getdetail.getEducation())){
                education_spinnerIndex = p;
            }
        }
        spinnerEducation.setSelection(education_spinnerIndex);

        //새로 정보 저장 후 보내기
        spinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("education", (String) spinnerEducation.getItemAtPosition(position));
                UserInfo.setEducation(spinnerEducation.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //커리어
        final EditText InputCareea=(EditText)findViewById(R.id.InputCarrea);
        InputCareea.setText(getdetail.getDepartment());
        String Check_InputCareea=InputCareea.getText().toString();
        UserInfo.setDepartment(Check_InputCareea);

        final Button confirm_btn = (Button)findViewById(R.id.confirm_btn) ;


        InputCareea.addTextChangedListener(new TextWatcher() {
            // 입력되는 텍스트에 변화가 있을 때
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputValue = s.toString();
                if(inputValue.replace(" ", "").equals("")||s.length()<1) {
                    //null값
                    confirm_btn.setBackgroundColor(Color.rgb(206,206,206));
                } else {
                    confirm_btn.setBackgroundColor(Color.rgb(255,203,61));
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


        //지역스피너
        int city_length = getResources().getStringArray(R.array.city).length;
        int city_spinnerIndex =0;

        for(int p=0;p<city_length;p++){

            if(spinnerCity.getItemAtPosition(p).toString().equals(getdetail.getLocation())){
                city_spinnerIndex = p;
            }
        }
        spinnerCity.setSelection(city_spinnerIndex);


        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("City", (String) spinnerCity.getItemAtPosition(position));
                UserInfo.setLocation(spinnerCity.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //키스피너
        int tall_length = getResources().getStringArray(R.array.Tall).length;
        int tall_spinnerIndex =0;

        for(int p=1;p<tall_length;p++){
            if(Integer.parseInt(spinnerTall.getItemAtPosition(p).toString())==getdetail.getheight()){
                tall_spinnerIndex = p;
            }
        }
        spinnerTall.setSelection(tall_spinnerIndex);


        spinnerTall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Tall", (String) spinnerTall.getItemAtPosition(position));
                UserInfo.setheight(spinnerTall.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //혈액형스피너
        int blood_length = getResources().getStringArray(R.array.blood).length;
        int blood_spinnerIndex = 0;

        for(int p=0;p<blood_length;p++){
            if(spinnerBlood.getItemAtPosition(p).toString().equals(getdetail.getBlood_type())){
                blood_spinnerIndex = p;
            }
        }
        spinnerBlood.setSelection(blood_spinnerIndex);

        spinnerBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Blood", (String) spinnerBlood.getItemAtPosition(position));
                UserInfo.setBlood_type(spinnerBlood.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //남자체형스피너
        int male_body_length = getResources().getStringArray(R.array.body_form_male).length;
        int male_body_spinnerIndex = 0;

        for(int p=0;p<male_body_length;p++){
            if(spinnerbodyform_male.getItemAtPosition(p).toString().equals(getdetail.getBody_form())){
                male_body_spinnerIndex = p;
            }
        }
        spinnerbodyform_male.setSelection(male_body_spinnerIndex);

        spinnerbodyform_male.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("BODY_form", (String) spinnerbodyform_male.getItemAtPosition(position));
                UserInfo.setBody_form(spinnerbodyform_male.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //여자체형스피너
        int female_body_length = getResources().getStringArray(R.array.body_form_female).length;
        int female_body_spinnerIndex = 0;

        for(int p=0;p<female_body_length;p++){
            if(spinnerbodyform_female.getItemAtPosition(p).toString().equals(getdetail.getBody_form())){
                female_body_spinnerIndex = p;
            }
        }
        spinnerbodyform_female.setSelection(female_body_spinnerIndex);

        spinnerbodyform_female.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("BODY_form", (String) spinnerbodyform_female.getItemAtPosition(position));
                UserInfo.setBody_form(spinnerbodyform_female.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //흡연스피너
        if(getdetail.isSmoke()){
            Radio_smoking.setChecked(true);
        }
        else {
            Radio_Notsmoking.setChecked(true);
        }

        Radio_smoking.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (Radio_smoking.isChecked()) {
                    Log.e("흡연.", "흡연");
                    UserInfo.setSmoke(true);

                }

            }
        });
        Radio_Notsmoking.setOnClickListener(new RadioButton.OnClickListener(){
            public void onClick(View v) {
                if (Radio_Notsmoking.isChecked()) {
                    Log.e("흡연.", "비흡연");
                    UserInfo.setSmoke(false);
                }

            }
        });



        //음주스피너
        int drink_length = getResources().getStringArray(R.array.drink).length;
        int drink_spinnerIndex = 0;

        for(int p=0;p<drink_length;p++){
            if(spinnerDrink.getItemAtPosition(p).toString().equals(getdetail.getDrink())){
                drink_spinnerIndex = p;
            }
        }
        spinnerDrink.setSelection(drink_spinnerIndex);


        spinnerDrink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Drink", (String) spinnerDrink.getItemAtPosition(position));
                UserInfo.setDrink(spinnerDrink.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //종교스피너
        int religion_length = getResources().getStringArray(R.array.drink).length;
        int religion_spinnerIndex = 0;

        for(int p=0;p<religion_length;p++){
            if(spinnerReligion.getItemAtPosition(p).toString().equals(getdetail.getReligion())){
                religion_spinnerIndex = p;
            }
        }
        spinnerReligion.setSelection(religion_spinnerIndex);

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
                }

                else if("기독교".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='P';
                }
                else if("불교".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='B';
                    ((TextView) view).setTextColor(BLACK);
                    b1.setBackgroundResource(R.drawable.press_round_btn);
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
                else if("기타".equals(CheckSpinnerReligion)){
                    InputUserinfoReligion='N';
                }


                else{
                    InputUserinfoReligion='O';

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onClick(View v) {

        //앨범가져오기
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imageupload_count=1;
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
                imageupload_count=2;
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
                imageupload_count=3;
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
                imageupload_count=4;
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
                imageupload_count=5;
                doTakeAlbumAction();
            }
        };
        //취소
        DialogInterface.OnClickListener canceListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imageupload_count=6;
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
                real_album_path= getPath(mImageCaptureUri);
                Log.e("real_album_path",real_album_path);
            }
            case PICK_FROM_CAMERA: {
                //이미지를 가져온 이후 리자이즈할 이미지 크기
//                //이후에 이미지 크롭 어플리케이션 호출
//                Intent intent = new Intent("com.android.camera.action.CROP");
//                intent.setDataAndType(mImageCaptureUri, "image/*");
//
//                //CROP할 이미지 75*75(키울예정)
//
//                intent.putExtra("scale", true);
//                intent.putExtra("return-data", true);
//                startActivityForResult(intent, CROP_FROM_IMAGE);
//                break;
            }
            case CROP_FROM_IMAGE: {
                //크롭 이후 이미지 받아서 이미지 뷰에 이미지 보여줌
                //임시파일 삭제
                if (resultCode != RESULT_OK) {
                    return;
                }
                final Bundle extras = data.getExtras();

                Log.e("imageipload_count", String.valueOf(imageupload_count));


                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel" + System.currentTimeMillis() + ".jpg";
                Imageupload_countList.add(real_album_path);

                for (int i=0;i<Imageupload_countList.size();i++){
                    Log.e("imageupload_countList", String.valueOf(Imageupload_countList.get(i)));

                }

                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderWidthDp(0)
                            .cornerRadiusDp(8)
                            .oval(false)
                            .build();

                    switch (imageupload_count){
                        case 1:{
                            Picasso.with(PersonalProfile_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto1);
                            break;
                        }
                        case 2:{
                            Picasso.with(PersonalProfile_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto2);
                            break;
                        }
                        case 3:{
                            Picasso.with(PersonalProfile_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto3);
                            break;
                        }
                        case 4:{
                            Picasso.with(PersonalProfile_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto4);
                            break;
                        }
                        case 5:{
                            Picasso.with(PersonalProfile_Edit.this)
                                    .load(mImageCaptureUri)
                                    .fit()
                                    .transform(transformation)
                                    .into(iv_UserPhoto5);
                            break;
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

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }


    //수정하기 버튼을 눌렀을때 position을 받아옴, 전송버튼을 수정버튼으로 바꿔줌
    public void modify(View view) {

        Button confirm = (Button)findViewById(R.id.confirm_btn);
        confirm.setVisibility(Button.GONE);
        LinearLayout ll = (LinearLayout)findViewById(R.id.enter_chatting);
        ll.setVisibility(View.GONE);
        LinearLayout l2 = (LinearLayout)findViewById(R.id.enter_chatting_visible);
        l2.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
        EditText editText =(EditText)findViewById(R.id.input_text);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);


        String modifyString = editText.getText().toString();
        Log.v("modifyString",modifyString);

        int count,checked;
        count=m_Adapter.getCount();
        Log.v("count", String.valueOf(count));
        if(count>0){

            position=m_ListView.getPositionForView(view);

            Log.v("checked", String.valueOf(position));
        }

    }


    public void setListViewHeightBasedOnItems(ListView listView) {

        // Get list adpter of listview;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)  return;

        int numberOfItems = listAdapter.getCount();

        // Get total height of all items.
        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = listAdapter.getView(itemPos, null, listView);
            item.measure(0, 0);
            totalItemsHeight += item.getMeasuredHeight();
        }

        // Get total height of all item dividers.
        int totalDividersHeight = listView.getDividerHeight() *  (numberOfItems - 1);

        // Set list height.
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void get_local_data(){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        account_id = pref.getInt("account_id",0);
        Log.e("local_account", Integer.toString(account_id));
        token=pref.getString("token","?");
        sex=pref.getString("sex","0");
        Log.e("local_token",String.valueOf(token));
    }

    public void send_question(View view) {

    }
    public void edit_profile_confirm(View view){

        ImageUploading imageUploading = new ImageUploading();
        if(Imageupload_countList.size()>=1) {
            imageUploading.image_uplodaing_method(Imageupload_countList, token, account_id, sex);
        }
        api_call editing_basicinfo = new api_call();
        editing_basicinfo.editing_basicinfo(UserInfo,token,account_id,sex);
        Log.e("수정하기베이직인포테스트","메소드호출완료");
    }

    public void get_image_setting(ArrayList<String> image_url,ArrayList<Integer> image_index){
        Transformation transformation = new RoundedTransformationBuilder()
                .borderWidthDp(0)
                .cornerRadiusDp(8)
                .oval(false)
                .build();
        Log.e("get_image_setting에서 imageurl파라미터 테스트",image_url.get(0));
        ArrayList<ImageView>get_image_setting_preview= new ArrayList<>();
            get_image_setting_preview.add(iv_UserPhoto1);
            get_image_setting_preview.add(iv_UserPhoto2);
            get_image_setting_preview.add(iv_UserPhoto3);
            get_image_setting_preview.add(iv_UserPhoto4);
            get_image_setting_preview.add(iv_UserPhoto5);
            get_image_setting_preview.add(iv_UserPhoto6);

        for (int i=0;i<image_url.size();i++){
            Picasso.with(PersonalProfile_Edit.this)
                    .load(image_url.get(i))
                    .fit()
                    .transform(transformation)
                    .into(get_image_setting_preview.get(image_index.get(i)));
        }
    }
}
