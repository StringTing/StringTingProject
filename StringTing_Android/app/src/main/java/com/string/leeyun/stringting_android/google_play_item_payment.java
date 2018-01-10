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
import android.widget.TextView;
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

        Intent intent=new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);


        String base64EncodiedPushedkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnlzT6nogEoRuXA5X2+x9547JuEK277xBTsLbtH6D7Jp4BWSfvX2HmyRVYJvpJkF/IfrAYIBVCFrlXNZ7/x83UVdHvrmZkIJbMPXFQ3K5jZ8Q6MYgQvco/9nkAHhZC8AHzkLTKkQmZEcYrkUZj+5GBH/bgtCi2RPPDufpaaSvIepzXVFI1m4CvecNDCo1GOQ1tPl2Au7aL3WMKEuCIhHJxRp062cXE5TbSJKvmgPF08zq5nVnn2gMlQ8OY6BEVJmJ631F8rcTgxjIPNtqrfE7htoDTe640QFffszh6wb7kLBuw1ifg4kj69uzu8y8/wouUSLf1QzUJBGwicZBv+t6lQIDAQAB"
                ;
        // developer console  -> 서비스및 API -> 어플리케이션용 라이센스 키를 복사해서 넣으시면 됩니다.

        InAppInit_U(base64EncodiedPushedkey, true);




        TextView coin1 = (TextView) findViewById(R.id.coin_item_100);

        coin1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                InAppBuyItem_U("coin_item_100"); // 제품id를 써줍니다. 앱배포시에 인앱상품등록시  등록할 id입니다.

            }

        });

        TextView coin2 = (TextView) findViewById(R.id.coin_item_220);

        coin2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                InAppBuyItem_U("coin_item_220"); // 제품id를 써줍니다. 앱배포시에 인앱상품등록시  등록할 id입니다.

            }

        });

        TextView coin3 = (TextView) findViewById(R.id.coin_item_360);

        coin3.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                InAppBuyItem_U("coin_item_360"); // 제품id를 써줍니다. 앱배포시에 인앱상품등록시  등록할 id입니다.

            }

        });
        TextView coin4 = (TextView) findViewById(R.id.coin_item_500);

        coin4.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                InAppBuyItem_U("coin_item_500"); // 제품id를 써줍니다. 앱배포시에 인앱상품등록시  등록할 id입니다.

            }

        });

        TextView coin5 = (TextView) findViewById(R.id.coin_item_1000);

        coin5.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                InAppBuyItem_U("coin_item_1000"); // 제품id를 써줍니다. 앱배포시에 인앱상품등록시  등록할 id입니다.

            }

        });
        TextView coin6 = (TextView) findViewById(R.id.coin_item_3000);

        coin6.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                InAppBuyItem_U("coin_item_3000"); // 제품id를 써줍니다. 앱배포시에 인앱상품등록시  등록할 id입니다.

            }

        });







    }


    public void InAppInit_U(String strPublicKey, boolean bDebug) {

        Log.d("myLog", "Creating IAB helper." + bDebug);

        iaphelper = new IabHelper(this, strPublicKey);



        if (bDebug == true) {

            iaphelper.enableDebugLogging(true, "IAB");

        }



        iaphelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {



            @Override

            public void onIabSetupFinished(IabResult result) {

                // TODO Auto-generated method stub

                boolean bInit = result.isSuccess();

                Log.d("myLog", "IAB Init " + bInit + result.getMessage());



                if (bInit == true) {

                    Log.d("myLog", "Querying inventory.");



                    iaphelper.queryInventoryAsync(mGotInventoryListener);

                }



            }




        });

    }


    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {
            if (result.isFailure()) {
                Log.d("myLog", "Failed to query inventory: " + result);
                SendConsumeResult(null, result);
                return;
            }

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            List<String> inappList = inventory.getAllOwnedSkus(IabHelper.ITEM_TYPE_INAPP);
            // inappList.add("item01");

            for (String inappSku : inappList) {
                Purchase purchase = inventory.getPurchase(inappSku);
                Log.d("myLog", "Consumeing ... " + inappSku);
                iaphelper.consumeAsync(purchase, mConsumeFinishedListener);
            }

            Log.d("myLog", "Query inventory was successful.");
        }
    };







    public void InAppBuyItem_U(final String strItemId) {

        runOnUiThread(new Runnable() {



            @Override

            public void run() {

                // TODO Auto-generated method stub



                /*

                 * TODO: for security, generate your payload here for

                 * verification. See the comments on verifyDeveloperPayload()

                 * for more info. Since this is a SAMPLE, we just use an empty

                 * string, but on a production app you should carefully generate

                 * this.

                 */

                try{

                    String payload = "user_id";

                    iaphelper.launchPurchaseFlow(google_play_item_payment.this, strItemId, 1001, mPurchaseFinishedListener, payload);



                    Log.d("myLog", "InAppBuyItem_U " + strItemId);

                }catch(Exception e){

                    e.printStackTrace();

                }



            }

        });

    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d("myLog", "Purchase finished: " + result + ", purchase: "
                    + purchase);  //결제 완료 되었을때 각종 결제 정보들을 볼 수 있습니다. 이정보들을 서버에 저장해 놓아야 결제 취소시 변경된 정보를 관리 할 수 있을것 같습니다~

            if (purchase != null) {
                if (!verifyDeveloperPayload(purchase)) {
                    Log.d("myLog",
                            "Error purchasing. Authenticity verification failed.");
                }

                iaphelper.consumeAsync(purchase, mConsumeFinishedListener);
            } else {
                Toast.makeText(google_play_item_payment.this,
                        String.valueOf(result.getResponse()),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct.
         * It will be the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase
         * and verifying it here might seem like a good approach, but this will
         * fail in the case where the user purchases an item on one device and
         * then uses your app on a different device, because on the other device
         * you will not have access to the random string you originally
         * generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different
         * between them, so that one user's purchase can't be replayed to
         * another user.
         *
         * 2. The payload must be such that you can verify it even when the app
         * wasn't the one who initiated the purchase flow (so that items
         * purchased by the user on one device work on other devices owned by
         * the user).
         *
         * Using your own server to store and verify developer payloads across
         * app installations is recommended.
         */

        return true;
    }

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d("myLog", "Consumption finished. Purchase: " + purchase
                    + ", result: " + result);
            SendConsumeResult(purchase, result);
        }
    };



    protected void SendConsumeResult(Purchase purchase, IabResult result) {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("Result", result.getResponse());
            if (purchase != null) {
                jsonObj.put("OrderId", purchase.getOrderId());
                jsonObj.put("Sku", purchase.getSku());
                jsonObj.put("purchaseData", purchase.getOriginalJson());
                jsonObj.put("signature", purchase.getSignature());

                Log.d("myLog", "OrderId"  + purchase.getOrderId());
                Log.d("myLog", "Sku"  + purchase.getSku());
                Log.d("myLog", "purchaseData"  + purchase.getOriginalJson());
                Log.d("myLog", "signature"  + purchase.getSignature());
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("myLog", "onActivityResult(" + requestCode + "," + resultCode
                + "," + data);
        if (requestCode == 1001) {
            // Pass on the activity result to the helper for handling
            if (!iaphelper.handleActivityResult(requestCode, resultCode, data)) {
                // not handled, so handle it ourselves (here's where you'd
                // perform any handling of activity results not related to
                // in-app
                // billing...
                super.onActivityResult(requestCode, resultCode, data);
            } else {
                Log.d("myLog", "onActivityResult handled by IABUtil.");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceConn != null) {
            unbindService(mServiceConn);
        }
    }

    ServiceConnection mServiceConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;

        }

    };


    public void onBackPressed() {
        google_play_item_payment.this.finish();
    }




}
