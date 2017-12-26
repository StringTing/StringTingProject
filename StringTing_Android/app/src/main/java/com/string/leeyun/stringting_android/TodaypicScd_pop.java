package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.open_id;
import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;

public class TodaypicScd_pop extends Activity {
    Rest_ApiService apiService;
    Retrofit retrofit;
    int matching_account;
    String matching_sex;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_todaypic_scd_pop);
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiService= retrofit.create(Rest_ApiService.class);

        Intent intent=getIntent();
        matching_account= (int) intent.getSerializableExtra("matching_account");
        matching_sex= (String) intent.getSerializableExtra("matching_sex");

        open_id OpenId=new open_id();
        OpenId.setOpen_id(matching_account);
        //추가팝업개발
        try {
            Call<open_id> post_open_id = apiService.post_open_id(OpenId);
            post_open_id.enqueue(new Callback<open_id>() {

                @Override
                public void onResponse(Call<open_id> call5day, retrofit2.Response<open_id> response) {
                    open_id response_open=new open_id();
                    response_open=response.body();
                    Log.e("response_open_result",response_open.getResult());

                }

                @Override
                public void onFailure(Call<open_id> call, Throwable t) {
                    Log.e("open_id 실패", t.toString());
                }
            });

        }catch (Exception e){
            e.printStackTrace();
            Log.e("last_introduction","null");
        }
    }

    public void cancle(View v){

        super.onBackPressed();

    }


    public void confirm(View v){
        //코인 사용이 충분할때
        Intent detail = new Intent(this,Personal_profile.class);

        detail.putExtra("matching_account",matching_account);
        detail.putExtra("matching_sex",matching_sex);

        View header = getLayoutInflater().inflate(R.layout.activity_tab_first, null, true);

        ImageView today_rock= (ImageView)header.findViewById(R.id.today_rock_img2);
        today_rock.setVisibility(View.GONE);

        startActivity(detail);
        finish();
    }
}
