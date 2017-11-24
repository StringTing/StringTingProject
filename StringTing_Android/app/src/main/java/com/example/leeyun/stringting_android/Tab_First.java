package com.example.leeyun.stringting_android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.graphics.drawable.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;


import static com.facebook.FacebookSdk.getApplicationContext;


public class Tab_First extends Fragment {

    private RelativeLayout mLayout;

    private Context mContext;
    private Resources mResources;
    private ImageView mImageView , mImageView2 , l1,l2,l3;
    private Bitmap mBitmap , lb1,lb2,lb3;




    public Tab_First() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tab_first, container, false);


        // Get the application context
        mContext = getApplicationContext();


        // Get the Resources
        mResources = getResources();

        // Get the widgets reference from XML layout
        //mRelativeLayout = (RelativeLayout) v.findViewById(R.id.rl);
        mImageView = (ImageView) v.findViewById(R.id.ph1);
        mImageView2 = (ImageView) v.findViewById(R.id.ph2);
        //mBTN = (Button) v.findViewById(R.id.btn);



        // Get the bitmap from drawable resources
        mBitmap = BitmapFactory.decodeResource(mResources, R.drawable.gametitle_01);


        // Display the bitmap in ImageView
        mImageView.setImageBitmap(mBitmap);
        mImageView2.setImageBitmap(mBitmap);


        // Define the ImageView corners radius
        float cornerRadius = 25f;

        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources, mBitmap);


        roundedBitmapDrawable.setCornerRadius(cornerRadius);

        roundedBitmapDrawable.setAntiAlias(true);

        // Set the ImageView image as drawable object
        mImageView.setImageDrawable(roundedBitmapDrawable);
        mImageView2.setImageDrawable(roundedBitmapDrawable);



        //last pic
        l1 = (ImageView) v.findViewById(R.id.last1);
        l2 = (ImageView) v.findViewById(R.id.last2);
        l3 = (ImageView) v.findViewById(R.id.last3);

        lb1 =  BitmapFactory.decodeResource(mResources, R.drawable.kakao_default_profile_image);
        lb2 =  BitmapFactory.decodeResource(mResources, R.drawable.kakao_default_profile_image);
        lb3 =  BitmapFactory.decodeResource(mResources, R.drawable.kakao_default_profile_image);

        l1.setImageBitmap(lb1);
        l2.setImageBitmap(lb2);
        l3.setImageBitmap(lb3);

        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(mResources, lb1);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable2 = RoundedBitmapDrawableFactory.create(mResources, lb2);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable3 = RoundedBitmapDrawableFactory.create(mResources,lb3);


        roundedBitmapDrawable1.setCornerRadius(cornerRadius);
        roundedBitmapDrawable2.setCornerRadius(cornerRadius);
        roundedBitmapDrawable3.setCornerRadius(cornerRadius);

        roundedBitmapDrawable1.setAntiAlias(true);
        roundedBitmapDrawable2.setAntiAlias(true);
        roundedBitmapDrawable3.setAntiAlias(true);


        l1.setImageDrawable(roundedBitmapDrawable1);
        l2.setImageDrawable(roundedBitmapDrawable2);
        l3.setImageDrawable(roundedBitmapDrawable3);

       /* //블러 라이브러리
        ImageView bluri = (ImageView) v.findViewById(R.id.pic1_background);

       Glide.with(mContext).load(R.drawable.gametitle_01).into(blur);

 //.bitmapTransform(new BlurTransformation(mContext)*/



        // Inflate the layout for this fragment
        return  v;
    }



}
