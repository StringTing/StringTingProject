package com.example.leeyun.stringting_android.API;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by leeyun on 2017. 11. 19..
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG="MyFirebaseIIDService";

    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v(TAG,"Refreshed token:" + refreshedToken);

        sendRedgistrationToServer(refreshedToken);
    }
    private void  sendRedgistrationToServer(String token){

    }


}
