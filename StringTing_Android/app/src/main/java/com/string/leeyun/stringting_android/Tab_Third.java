package com.string.leeyun.stringting_android;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.string.leeyun.stringting_android.API.get_last_5days_matched_accountList;
import com.string.leeyun.stringting_android.model.api_call;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
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


    public Tab_Third() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        get_local_data();



        api_call my_pic = new api_call();
        my_pic.my_pic(token,account_id,sex);




        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.activity_tab_third, container, false);


        //추가할 부모 뷰
        //final TableLayout inflatedLayout = (TableLayout) v.findViewById(R.id.addpic);
        final TableLayout tableLayout = (TableLayout) v.findViewById(R.id.addpic);

        //더보기 버튼
        final LinearLayout addbtn = (LinearLayout) v.findViewById(R.id.add_btn);

        //이미지 들어오는거
        final int[] p = {1,2};

        //픽이 3개 이하일 때
        if (p.length < 3) {
            TextView nothing = (TextView) v.findViewById(R.id.nothing);
            nothing.setVisibility(View.GONE);

            if (p.length % 3 != 0) {
                addbtn.setVisibility(View.GONE);
                tr = new TableRow(getApplicationContext());
                tr.setPadding(0, 35, 0, 0);
                for (int i = 0; i < p.length % 3; ++i) {
                    View img = inflater.inflate(R.layout.addimg, null);
                    ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderColor(Color.BLACK)
                            .borderWidthDp(0)
                            .cornerRadiusDp(8)
                            .oval(false)
                            .build();


                    Picasso.with(getContext())
                            .load(R.drawable.gametitle_01)
                            .fit()
                            .transform(transformation)
                            .into(img_pic);

                    tr.addView(img);
                }
                tableLayout.addView(tr);
            }

        }

        //3개 이상 픽
        else if (p.length >= 3) {
            TextView nothing = (TextView) v.findViewById(R.id.nothing);
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
                            .load(R.drawable.gametitle_01)
                            .fit()
                            .transform(transformation)
                            .into(img_pic);

                    tr.addView(img);
                }
                tableLayout.addView(tr);

            final int remainder = p.length -3;

            addbtn.setVisibility(View.VISIBLE);
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addbtn.setVisibility(View.GONE);

                    for (int i = 0; i < remainder / 3; ++i) {
                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int j = 0; j < 3; ++j) {
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(R.drawable.gametitle_01)
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                    if (remainder % 3 != 0) {

                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int i = 0; i < remainder % 3; ++i) {
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(R.drawable.gametitle_01)
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


        return v;
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
