package com.string.leeyun.stringting_android;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import static com.facebook.FacebookSdk.getApplicationContext;


public class Tab_Third extends Fragment {

    TableRow tr;

    public Tab_Third() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.activity_tab_third, container, false);


        //추가할 부모 뷰
        //final TableLayout inflatedLayout = (TableLayout) v.findViewById(R.id.addpic);
        final TableLayout tableLayout = (TableLayout) v.findViewById(R.id.addpic);

        //더보기 버튼
        final LinearLayout addbtn = (LinearLayout) v.findViewById(R.id.add_btn);

        //이미지 들어오는거
        final int[] p = {1};

        if (p.length >= 1) {
            TextView nothing = (TextView) v.findViewById(R.id.nothing);
            nothing.setVisibility(View.GONE);

                tr = new TableRow(getApplicationContext());
                tr.setPadding(0, 35, 0, 0);
                for (int k = 0; k < p.length; k++) {
                    // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
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

            if (p.length>3){
                addbtn.setVisibility(View.VISIBLE);
            }

        }

            final int remainder = p.length -3;
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addbtn.setVisibility(View.GONE);
                    // activity_main.xml에서 정의한 LinearLayout 객체 할당

                    for (int i = 0; i <remainder / 3; ++i) {
                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int j = 0; j < 3; j++) {
                            // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
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
                        if (p.length % 3 != 0) {
                            tr = new TableRow(getApplicationContext());
                            tr.setPadding(0, 35, 0, 0);
                            for (i = 0; i <remainder % 3; ++i) {
                                // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
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
                }
            });

            return v;
        }
}
