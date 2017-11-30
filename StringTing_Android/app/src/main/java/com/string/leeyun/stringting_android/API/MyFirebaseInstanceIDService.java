package com.string.leeyun.stringting_android.API;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.R.attr.data;

/**
 * Created by leeyun on 2017. 11. 19..
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG="MyFirebaseIIDService";

    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG,"Refreshed token:" + refreshedToken);


        sendRedgistrationToServer(refreshedToken);

    }
    public void  sendRedgistrationToServer(String token){


    }


}
