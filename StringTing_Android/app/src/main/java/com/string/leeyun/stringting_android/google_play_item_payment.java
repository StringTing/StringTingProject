package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.facebook.common.Common;
import com.string.leeyun.stringting_android.util.IabHelper;
import com.string.leeyun.stringting_android.util.IabResult;
import com.string.leeyun.stringting_android.util.Inventory;
import com.string.leeyun.stringting_android.util.Purchase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by leeyun on 2018. 1. 6..
 */

public class google_play_item_payment extends Activity{
    IInAppBillingService mService;
    IabHelper iaphelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        bindService(new Intent(
                        "com.android.vending.billing.InAppBillingService.BIND"),
                mServiceConn, Context.BIND_AUTO_CREATE);

        Intent intent=new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);


        String base64EncodiedPushedkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnlzT6nogEoRuXA5X2+x9547JuEK277xBTsLbtH6D7Jp4BWSfvX2HmyRVYJvpJkF/IfrAYIBVCFrlXNZ7/x83UVdHvrmZkIJbMPXFQ3K5jZ8Q6MYgQvco/9nkAHhZC8AHzkLTKkQmZEcYrkUZj+5GBH/bgtCi2RPPDufpaaSvIepzXVFI1m4CvecNDCo1GOQ1tPl2Au7aL3WMKEuCIhHJxRp062cXE5TbSJKvmgPF08zq5nVnn2gMlQ8OY6BEVJmJ631F8rcTgxjIPNtqrfE7htoDTe640QFffszh6wb7kLBuw1ifg4kj69uzu8y8/wouUSLf1QzUJBGwicZBv+t6lQIDAQAB"
                ;
        // developer console  -> 서비스및 API -> 어플리케이션용 라이센스 키를 복사해서 넣으시면 됩니다.







    }




    ServiceConnection mServiceConn = new ServiceConnection()
    {
        @Override public void onServiceDisconnected(ComponentName name)
        {
            mService = null;
        }
        @Override
         public void onServiceConnected(ComponentName name, IBinder service)
        {
            mService = IInAppBillingService.Stub.asInterface(service);
        }

    };







}
