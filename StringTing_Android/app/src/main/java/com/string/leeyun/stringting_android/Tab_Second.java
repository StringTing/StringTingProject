package com.string.leeyun.stringting_android;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.string.leeyun.stringting_android.API.Get_evalaccount;
import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_eval_accountList;
import com.string.leeyun.stringting_android.API.images;
import com.string.leeyun.stringting_android.API.post_register_score;
import com.string.leeyun.stringting_android.API.userinfo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.mipmap.t;


public class Tab_Second extends Fragment implements View.OnClickListener {

    private ImageView candy1,candy2,candy3,candy4,candy5;

    private ImageView o_candy1,o_candy2,o_candy3,o_candy4,o_candy5;

    int account_id;
    String token;
    String sex;

    ResponseApi responapi =new ResponseApi();
    Rest_ApiService apiService;
    Retrofit retrofit;
    userinfo Userinfo= new userinfo();
    ArrayList<Get_evalaccount> get_eval_accountLists;
    String get_eval_account;
    String generalInfoJson = "{'name': 'Future Studio Dev Team', 'website': 'https://futurestud.io', 'account': [{'name': 'Christian', 'flowerCount': 1 }, {'name': 'Marcus','flowerCount': 3 }, {'name': 'Norman','flowerCount': 2 }]}";

    ArrayList<String> image_url;
    ArrayList<String>image_url_replace;
    ArrayList<String>image_url_full;
    post_register_score postRegisterScore=new post_register_score();
    String from_id,score;
    ArrayList<String>to_id=new ArrayList<>();
    Gson gson= new Gson();
    private ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        View result=inflater.inflate(R.layout.activity_tab_second, container, false);

        pager= (ViewPager)result.findViewById(R.id.viewPager);




        get_eval_accountLists=new ArrayList<>();
        image_url=new ArrayList<>();
        image_url_replace=new ArrayList<>();
        image_url_full=new ArrayList<>();
        Call<get_eval_accountList> call = apiService.Get_evalaccount(sex,account_id);
        call.enqueue(new Callback<get_eval_accountList>() {
            @Override
            public void onResponse(Call<get_eval_accountList> call, Response<get_eval_accountList> response) {


                get_eval_accountLists=response.body().getAccounts();
                for (int i = 0; i  < get_eval_accountLists.size(); i++) {
                    try{
                        if(get_eval_accountLists.get(i)!=null){
                            image_url.add(get_eval_accountLists.get(i).getImages().getApproved().get(0).getName());
                            Log.e("image_log",get_eval_accountLists.get(i).getImages().getApproved().get(0).getName());
                            to_id.add(String.valueOf(get_eval_accountLists.get(i).getId()));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }

                Log.e("get_matched_accountList", String.valueOf(response.body()));
                Log.e("onresponse", String.valueOf(response.code()));
                String replace = "{}";
                String medium = "medium";
                String small = "small";
                try {
                    if (image_url.get(0) != null) {
                        for (int i = 0; i < image_url.size(); i++) {
                            image_url_replace.add(image_url.get(i).replace(replace, "large"));
                            image_url_full.add(API_IMAGE_URL + image_url_replace.get(i));
                            Log.e("i", String.valueOf(i));
                            Log.e("image_url_full_test", image_url_full.get(i));


                        }
                        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getLayoutInflater(), getContext(), image_url_full);
                        pager.setAdapter(adapter);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    Log.e("Get_evalaccount", String.valueOf(get_eval_accountLists.get(0)));

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<get_eval_accountList> call, Throwable t) {
                Log.v("onresponseImage2",t.toString());
            }
        });



        //캔디 버튼
        candy1 = (ImageView) result.findViewById(R.id.c1);
        candy2 = (ImageView) result.findViewById(R.id.c2);
        candy3 = (ImageView) result.findViewById(R.id.c3);
        candy4 = (ImageView) result.findViewById(R.id.c4);
        candy5 = (ImageView) result.findViewById(R.id.c5);

        o_candy1 = (ImageView) result.findViewById(R.id.cc1);
        o_candy2 = (ImageView) result.findViewById(R.id.cc2);
        o_candy3 = (ImageView) result.findViewById(R.id.cc3);
        o_candy4 = (ImageView) result.findViewById(R.id.cc4);
        o_candy5 = (ImageView) result.findViewById(R.id.cc5);

        candy1.setOnClickListener(this);
        candy2.setOnClickListener(this);
        candy3.setOnClickListener(this);
        candy4.setOnClickListener(this);
        candy5.setOnClickListener(this);





        //ViewPager에 설정할 Adapter 객체 생성

        //ListView에서 사용하는 Adapter와 같은 역할.

        //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름

        //PagerAdapter를 상속받은 CustomAdapter 객체 생성

        //CustomAdapter에게 LayoutInflater 객체 전달




        //ViewPager에 Adapter 설정


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                o_candy1.setVisibility(View.INVISIBLE);
                o_candy2.setVisibility(View.INVISIBLE);
                o_candy3.setVisibility(View.INVISIBLE);
                o_candy4.setVisibility(View.INVISIBLE);
                o_candy5.setVisibility(View.INVISIBLE);

            }
        });


        return result;
    }



    @Override
    public void onClick(View view) {

        int position;

        switch (view.getId()){
            case R.id.c1:

                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴
                o_candy1.setVisibility(View.VISIBLE);

                o_candy2.setVisibility(View.INVISIBLE);
                o_candy3.setVisibility(View.INVISIBLE);
                o_candy4.setVisibility(View.INVISIBLE);
                o_candy5.setVisibility(View.INVISIBLE);

                //현재 위치(position)에서 +1 을 해서 다음 position으로 변경

                //다음 Item으로 현재의 아이템 변경 설정(가장 마지막이면 더이상 이동하지 않음)

                //첫번째 파라미터: 설정할 현재 위치

                //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜
                post_register_score(String.valueOf(account_id),to_id.get(position),"1");
                pager.setCurrentItem(position+1,true);




                break;

            case R.id.c2:
                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴
                o_candy1.setVisibility(View.VISIBLE);
                o_candy2.setVisibility(View.VISIBLE);

                o_candy3.setVisibility(View.INVISIBLE);
                o_candy4.setVisibility(View.INVISIBLE);
                o_candy5.setVisibility(View.INVISIBLE);
                post_register_score(String.valueOf(account_id),to_id.get(position),"2");

                pager.setCurrentItem(position+1,true);
                break;

            case R.id.c3:
                position=pager.getCurrentItem();
                o_candy1.setVisibility(View.VISIBLE);
                o_candy2.setVisibility(View.VISIBLE);
                o_candy3.setVisibility(View.VISIBLE);

                o_candy4.setVisibility(View.INVISIBLE);
                o_candy5.setVisibility(View.INVISIBLE);
                post_register_score(String.valueOf(account_id),to_id.get(position),"3");

                pager.setCurrentItem(position+1,true);
                break;

            case R.id.c4:
                position=pager.getCurrentItem();
                o_candy1.setVisibility(View.VISIBLE);
                o_candy2.setVisibility(View.VISIBLE);
                o_candy3.setVisibility(View.VISIBLE);
                o_candy4.setVisibility(View.VISIBLE);

                o_candy5.setVisibility(View.INVISIBLE);
                post_register_score(String.valueOf(account_id),to_id.get(position),"4");

                pager.setCurrentItem(position+1,true);
                break;

            case R.id.c5:
                position=pager.getCurrentItem();
                o_candy1.setVisibility(View.VISIBLE);
                o_candy2.setVisibility(View.VISIBLE);
                o_candy3.setVisibility(View.VISIBLE);
                o_candy4.setVisibility(View.VISIBLE);
                o_candy5.setVisibility(View.VISIBLE);
                post_register_score(String.valueOf(account_id),to_id.get(position),"5");

                pager.setCurrentItem(position+1,true);
                break;
        }

    }




// Inflate the layout for this fragment


    public void get_local_data(){
        SharedPreferences pref = this.getActivity().getSharedPreferences("Local_DB", MODE_PRIVATE);
        account_id = pref.getInt("account_id",0);
        Log.e("local_account", Integer.toString(account_id));
        token=pref.getString("token","?");
        sex=pref.getString("sex","0");
        Log.e("local_token",String.valueOf(token));
    }

    //서버에 form_id로부터 to_id에게 매긴 score를 post함
    public void post_register_score(String form_id,String to_id,String score){
        postRegisterScore.setFrom_id(form_id);
        postRegisterScore.setScore(score);
        postRegisterScore.setTo_id(to_id);
        Call<post_register_score> call = apiService.post_register_score(postRegisterScore);
        call.enqueue(new Callback<post_register_score>() {
            @Override
            public void onResponse(Call<post_register_score> call, Response<post_register_score> response) {



                postRegisterScore=response.body();

                Log.e("postRegisterScore",String.valueOf(response.code()));
                Log.e("postscore결과값",postRegisterScore.getResult());


            }

            @Override
            public void onFailure(Call<post_register_score> call, Throwable t) {
                Log.v("onresponseImage2",t.toString());
            }
        });
    }

}