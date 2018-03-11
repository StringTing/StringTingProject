package com.string.leeyun.stringting_android;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.string.leeyun.stringting_android.API.Get_my_pick_list;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.model.api_call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;


public class Tab_Third extends Fragment {

    TableRow tr;

    int account_id;
    String token;
    String sex;
    Rest_ApiService apiService;
    Retrofit retrofit;


    ArrayList<String> get_like_image_list=new ArrayList<>();
    ArrayList<String> get_like_image_replace=new ArrayList<>();
    ArrayList<String> get_like_image_full_url=new ArrayList<String>();

    ArrayList<String> get_high_image_list=new ArrayList<>();
    ArrayList<String> get_high_image_replace=new ArrayList<>();
    ArrayList<String> get_high_image_full_url=new ArrayList<String>();

    ArrayList<String> send_like_image_list=new ArrayList<>();
    ArrayList<String> send_like_image_replace=new ArrayList<>();
    ArrayList<String> send_like_image_full_url=new ArrayList<String>();

    ArrayList<String> give_high_image_list= new ArrayList<>();
    ArrayList<String> give_high_image_replace=new ArrayList<>();
    ArrayList<String> give_high_image_full_url=new ArrayList<String>();


    Get_my_pick_list myPick = new Get_my_pick_list();


    public Tab_Third() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        get_local_data();

        Thread t = new Thread() {
            public void run() {
                try {
                    OkHttpClient.Builder client1 = new OkHttpClient.Builder();
                    client1.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {

                            Request builder = chain.request();
                            Request newRequest;


                            newRequest = builder.newBuilder()
                                    .addHeader("access-token",token)
                                    .addHeader("account-id", String.valueOf(account_id))
                                    .addHeader("account-sex",sex)
                                    .build();


                            return chain.proceed(newRequest);

                        }
                    });


                    retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client1.build())
                            .build();
                } catch (Exception e) {
                    // 무시..
                }
            }
        };
        // 스레드 시작
        t.start();

        try {
            t.join();
            apiService= retrofit.create(Rest_ApiService.class);
            Call<Get_my_pick_list> call = apiService.get_my_pick_list(sex,account_id);
            call.enqueue(new Callback<Get_my_pick_list>() {
                @Override
                public void onResponse(Call<Get_my_pick_list> call, Response<Get_my_pick_list> response) {
                    Log.e("나의 픽",String.valueOf(response.raw()));
                    Log.e("나의 픽", String.valueOf(response.body()));
                    Log.e("나의 픽", String.valueOf(response.code()));

                    Get_my_pick_list get_my_pick = response.body();

                    Log.e("좋아요보낸리스트",String.valueOf(get_my_pick.getSend_like_list().size()));
                    Log.e("호감보낸리스트",String.valueOf(get_my_pick.getGive_high_score_list().size()));
                    Log.e("호감보낸리스트",String.valueOf(get_my_pick.getGive_high_score_list()));

                    try {
                        String replace = "{}";
                        String medium = "medium";
                        String small = "small";
                        if(get_my_pick.getReceive_like_list().get(0).getImages().getApproved().get(0).getName() != null) {
                            for (int i = 0; i < get_my_pick.getReceive_like_list().size(); i++) {
                                get_like_image_list.add(String.valueOf(get_my_pick.getReceive_like_list().get(i).getImages().getApproved().get(0).getName()));
                                get_like_image_replace.add(get_like_image_list.get(i).replace(replace,small));
                                get_like_image_full_url.add(API_IMAGE_URL+get_like_image_replace.get(i));
                                Log.e("지난픽들 log", String.valueOf(get_like_image_full_url));
                            }
                            get_like_view(inflater,get_like_image_full_url);
                        }

                        if(get_my_pick.getReceive_high_score_list().get(0).getImages().getApproved().get(0).getName() != null) {
                            for (int i = 0; i < get_my_pick.getReceive_high_score_list().size(); i++) {
                                get_high_image_list.add(String.valueOf(get_my_pick.getReceive_high_score_list().get(i).getImages().getApproved().get(0).getName()));
                                get_high_image_replace.add(get_high_image_list.get(i).replace(replace,small));
                                get_high_image_full_url.add(API_IMAGE_URL+get_high_image_replace.get(i));
                                Log.e("지난픽들 log", String.valueOf(get_high_image_full_url));
                            }
                            get_high_view(inflater,get_high_image_full_url);
                        }

                        if(get_my_pick.getSend_like_list().get(0).getImages().getApproved().get(0).getName() != null) {
                            for (int i = 0; i < get_my_pick.getSend_like_list().size(); i++) {
                                send_like_image_list.add(String.valueOf(get_my_pick.getSend_like_list().get(i).getImages().getApproved().get(0).getName()));
                                send_like_image_replace.add(send_like_image_list.get(i).replace(replace,small));
                                send_like_image_full_url.add(API_IMAGE_URL+send_like_image_replace.get(i));
                                Log.e("지난픽들 log", String.valueOf(send_like_image_full_url));
                            }
                            send_like_view(inflater,send_like_image_full_url);
                        }

                        if(get_my_pick.getGive_high_score_list().get(0).getImages().getApproved().get(0).getName() != null) {
                            for (int i = 0; i < get_my_pick.getGive_high_score_list().size(); i++) {
                                give_high_image_list.add(String.valueOf(get_my_pick.getGive_high_score_list().get(6).getImages().getApproved().get(0).getName()));
                                give_high_image_replace.add(give_high_image_list.get(i).replace(replace,small));
                                give_high_image_full_url.add(API_IMAGE_URL+give_high_image_replace.get(i));
                                Log.e("지난픽들 log", String.valueOf(give_high_image_full_url));
                            }
                            give_high_view(inflater,give_high_image_full_url);
                        }

                        //     Log.e("get_eval_list_image", String.valueOf(im_get_today.get(0).getImages().getApproved().get(0).getName()));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("imageNULL_send_like", "false");
                    }
                }

                @Override
                public void onFailure(Call<Get_my_pick_list> call, Throwable t) {

                }

            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        final View v = inflater.inflate(R.layout.activity_tab_third, container, false);


        return v;
    }


    public void get_like_view(final LayoutInflater inflater, final ArrayList<String> pos){

        //추가할 부모 뷰
        //final TableLayout inflatedLayout = (TableLayout) v.findViewById(R.id.addpic);
        final TableLayout tableLayout = (TableLayout) getView().findViewById(R.id.addpic);

        //더보기 버튼
        final LinearLayout addbtn = (LinearLayout) getView().findViewById(R.id.add_btn);

        //이미지 들어오는거
        int last_img_count;

        //픽이 3개 이하일 때
        if (pos.size() < 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing);
            nothing.setVisibility(View.GONE);

            if (pos.size() % 3 != 0) {
                addbtn.setVisibility(View.GONE);
                tr = new TableRow(getApplicationContext());
                tr.setPadding(0, 35, 0, 0);
                for (last_img_count = 0; last_img_count <pos.size() % 3; ++last_img_count) {
                    View img = inflater.inflate(R.layout.addimg, null);
                    ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderColor(Color.BLACK)
                            .borderWidthDp(0)
                            .cornerRadiusDp(8)
                            .oval(false)
                            .build();


                    Picasso.with(getContext())
                            .load(pos.get(last_img_count))
                            .fit()
                            .transform(transformation)
                            .into(img_pic);

                    tr.addView(img);
                }
                tableLayout.addView(tr);
            }

        }

        //3개 이상 픽
        else if (pos.size()>= 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing);
            nothing.setVisibility(View.GONE);


            tr = new TableRow(getApplicationContext());
            tr.setPadding(0, 35, 0, 0);
            for (int i = 0; i < 3 ; ++i) {
                View img = inflater.inflate(R.layout.addimg, null);
                ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(0)
                        .cornerRadiusDp(8)
                        .oval(false)
                        .build();


                Picasso.with(getContext())
                        .load(pos.get(i))
                        .fit()
                        .transform(transformation)
                        .into(img_pic);

                tr.addView(img);
            }
            tableLayout.addView(tr);

            final int remainder =pos.size()-3;



            addbtn.setVisibility(View.VISIBLE);
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addbtn.setVisibility(View.GONE);

                    for (int i = 1; i <= remainder/3; ++i) {
                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int j = 0; j < 3; ++j) {
                            int img_cnt = j+(3*i);
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(pos.get(img_cnt))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);


                            Log.e("카운트",String.valueOf(img_cnt));

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                    if (remainder % 3 != 0) {

                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int i = 0; i < remainder % 3; ++i) {
                            int remainder_cnt = pos.size() - (remainder%3) +i;
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(pos.get(remainder_cnt))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                }
            });

        }

    }

    public void get_high_view(final LayoutInflater inflater, final ArrayList<String> pos){

        //추가할 부모 뷰
        //final TableLayout inflatedLayout = (TableLayout) v.findViewById(R.id.addpic);
        final TableLayout tableLayout = (TableLayout) getView().findViewById(R.id.addpic2);

        //더보기 버튼
        final LinearLayout addbtn = (LinearLayout) getView().findViewById(R.id.add_btn2);

        //이미지 들어오는거
        int last_img_count;

        //픽이 3개 이하일 때
        if (pos.size() < 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing2);
            nothing.setVisibility(View.GONE);

            if (pos.size() % 3 != 0) {
                addbtn.setVisibility(View.GONE);
                tr = new TableRow(getApplicationContext());
                tr.setPadding(0, 35, 0, 0);
                for (last_img_count = 0; last_img_count <pos.size() % 3; ++last_img_count) {
                    View img = inflater.inflate(R.layout.addimg, null);
                    ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderColor(Color.BLACK)
                            .borderWidthDp(0)
                            .cornerRadiusDp(8)
                            .oval(false)
                            .build();


                    Picasso.with(getContext())
                            .load(pos.get(last_img_count))
                            .fit()
                            .transform(transformation)
                            .into(img_pic);

                    tr.addView(img);
                }
                tableLayout.addView(tr);
            }

        }

        //3개 이상 픽
        else if (pos.size()>= 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing2);
            nothing.setVisibility(View.GONE);


            tr = new TableRow(getApplicationContext());
            tr.setPadding(0, 35, 0, 0);
            for (int i = 0; i < 3 ; ++i) {
                View img = inflater.inflate(R.layout.addimg, null);
                ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(0)
                        .cornerRadiusDp(8)
                        .oval(false)
                        .build();


                Picasso.with(getContext())
                        .load(pos.get(i))
                        .fit()
                        .transform(transformation)
                        .into(img_pic);

                tr.addView(img);
            }
            tableLayout.addView(tr);

            final int remainder =pos.size()-3;



            addbtn.setVisibility(View.VISIBLE);
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addbtn.setVisibility(View.GONE);

                    for (int i = 1; i <= remainder/3; ++i) {
                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int j = 0; j < 3; ++j) {
                            int img_cnt = j+(3*i);
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(pos.get(img_cnt))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);


                            Log.e("카운트",String.valueOf(img_cnt));

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                    if (remainder % 3 != 0) {

                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int i = 0; i < remainder % 3; ++i) {
                            int remainder_cnt = pos.size() - (remainder%3) +i;
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(pos.get(remainder_cnt))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                }
            });

        }

    }

    public void send_like_view(final LayoutInflater inflater,final ArrayList<String> pos){

        //추가할 부모 뷰
        //final TableLayout inflatedLayout = (TableLayout) v.findViewById(R.id.addpic);
        final TableLayout tableLayout = (TableLayout) getView().findViewById(R.id.addpic3);

        //더보기 버튼
        final LinearLayout addbtn = (LinearLayout) getView().findViewById(R.id.add_btn3);

        //이미지 들어오는거
        int last_img_count;

        //픽이 3개 이하일 때
        if (pos.size() < 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing3);
            nothing.setVisibility(View.GONE);

            if (pos.size() % 3 != 0) {
                addbtn.setVisibility(View.GONE);
                tr = new TableRow(getApplicationContext());
                tr.setPadding(0, 35, 0, 0);
                for (last_img_count = 0; last_img_count <pos.size() % 3; ++last_img_count) {
                    View img = inflater.inflate(R.layout.addimg, null);
                    ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderColor(Color.BLACK)
                            .borderWidthDp(0)
                            .cornerRadiusDp(8)
                            .oval(false)
                            .build();


                    Picasso.with(getContext())
                            .load(pos.get(last_img_count))
                            .fit()
                            .transform(transformation)
                            .into(img_pic);

                    tr.addView(img);
                }
                tableLayout.addView(tr);
            }

        }

        //3개 이상 픽
        else if (pos.size()>= 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing3);
            nothing.setVisibility(View.GONE);


            tr = new TableRow(getApplicationContext());
            tr.setPadding(0, 35, 0, 0);
            for (int i = 0; i < 3 ; ++i) {
                View img = inflater.inflate(R.layout.addimg, null);
                ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(0)
                        .cornerRadiusDp(8)
                        .oval(false)
                        .build();


                Picasso.with(getContext())
                        .load(pos.get(i))
                        .fit()
                        .transform(transformation)
                        .into(img_pic);

                tr.addView(img);
            }
            tableLayout.addView(tr);

            final int remainder =pos.size()-3;



            addbtn.setVisibility(View.VISIBLE);
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addbtn.setVisibility(View.GONE);

                    for (int i = 1; i <= remainder/3; ++i) {
                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int j = 0; j < 3; ++j) {
                            int img_cnt = j+(3*i);
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(pos.get(img_cnt))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);


                            Log.e("카운트",String.valueOf(img_cnt));

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                    if (remainder % 3 != 0) {

                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int i = 0; i < remainder % 3; ++i) {
                            int remainder_cnt = pos.size() - (remainder%3) +i;
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(pos.get(remainder_cnt))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                }
            });

        }

    }

    public void give_high_view(final LayoutInflater inflater,final ArrayList<String> pos){

        //추가할 부모 뷰
        //final TableLayout inflatedLayout = (TableLayout) v.findViewById(R.id.addpic);
        final TableLayout tableLayout = (TableLayout) getView().findViewById(R.id.addpic4);

        //더보기 버튼
        final LinearLayout addbtn = (LinearLayout) getView().findViewById(R.id.add_btn4);

        //이미지 들어오는거
        int last_img_count;

        //픽이 3개 이하일 때
        if (pos.size() < 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing4);
            nothing.setVisibility(View.GONE);

            if (pos.size() % 3 != 0) {
                addbtn.setVisibility(View.GONE);
                tr = new TableRow(getApplicationContext());
                tr.setPadding(0, 35, 0, 0);
                for (last_img_count = 0; last_img_count <pos.size() % 3; ++last_img_count) {
                    View img = inflater.inflate(R.layout.addimg, null);
                    ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderColor(Color.BLACK)
                            .borderWidthDp(0)
                            .cornerRadiusDp(8)
                            .oval(false)
                            .build();


                    Picasso.with(getContext())
                            .load(pos.get(last_img_count))
                            .fit()
                            .transform(transformation)
                            .into(img_pic);

                    tr.addView(img);
                }
                tableLayout.addView(tr);
            }

        }

        //3개 이상 픽
        else if (pos.size()>= 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing4);
            nothing.setVisibility(View.GONE);


            tr = new TableRow(getApplicationContext());
            tr.setPadding(0, 35, 0, 0);
            for (int i = 0; i < 3 ; ++i) {
                View img = inflater.inflate(R.layout.addimg, null);
                ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(0)
                        .cornerRadiusDp(8)
                        .oval(false)
                        .build();


                Picasso.with(getContext())
                        .load(pos.get(i))
                        .fit()
                        .transform(transformation)
                        .into(img_pic);

                tr.addView(img);
            }
            tableLayout.addView(tr);

            final int remainder =pos.size()-3;



            addbtn.setVisibility(View.VISIBLE);
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addbtn.setVisibility(View.GONE);

                    for (int i = 1; i <= remainder/3; ++i) {
                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int j = 0; j < 3; ++j) {
                            int img_cnt = j+(3*i);
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(pos.get(img_cnt))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);


                            Log.e("카운트",String.valueOf(img_cnt));

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                    if (remainder % 3 != 0) {

                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int i = 0; i < remainder % 3; ++i) {
                            int remainder_cnt = pos.size() - (remainder%3) +i;
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(pos.get(remainder_cnt))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                }
            });

        }

    }

    public void get_local_data(){
        SharedPreferences pref = this.getActivity().getSharedPreferences("Local_DB", MODE_PRIVATE);
        account_id = pref.getInt("account_id",0);
        Log.e("local_account", Integer.toString(account_id));
        token=pref.getString("token","?");
        sex=pref.getString("sex","0");
        Log.e("local_token",String.valueOf(token));
    }
}
