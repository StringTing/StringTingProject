package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Whitemember extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;


    private Uri mImageCaptureUri;
    private ImageView iv_UserIDcard;
    public int imageupload_count=0;
    public ArrayList<String> Imageupload_countList=new ArrayList<>();

    String real_album_path;

    ImageView certify;
    String token;
    int account_id;
    String sex;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whitemember);

        //account_id,sex,token 불러옴
        get_local_data();

        certify = (ImageView)findViewById(R.id.certify_btn);
        certify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(),White_webview.class);
                startActivity(i);
            }
        });


    }


    public void onClick(View v) {

        //앨범가져오기
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };
        //취소
        DialogInterface.OnClickListener canceListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드이미지 선택")
                //.setPositiveButton("사진촬영",cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", canceListener)
                .show();
    }

    private void doTakeAlbumAction() { //앨범에서 이미지 가져오기
        //앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
                real_album_path = getPath(mImageCaptureUri);
                Log.e("real_album_path", real_album_path);
            }
            case PICK_FROM_CAMERA: {
                //이미지를 가져온 이후 리자이즈할 이미지 크기
                //이후에 이미지 크롭 어플리케이션 호출
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                //CROP할 이미지 75*75(키울예정)

                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE: {
                //크롭 이후 이미지 받아서 이미지 뷰에 이미지 보여줌
                //임시파일 삭제
                if (resultCode != RESULT_OK) {
                    return;
                }
                final Bundle extras = data.getExtras();


                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel" + System.currentTimeMillis() + ".jpg";


                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");//CROP된 BITMAP

                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderWidthDp(0)
                            .cornerRadiusDp(8)
                            .oval(false)
                            .build();

                    Picasso.with(Whitemember.this)
                            .load(mImageCaptureUri)
                            .fit()
                            .transform(transformation)
                            .into(iv_UserIDcard);
                    break;
                }

                //임시파일삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }



    public void back(View v){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void get_local_data(){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        account_id = pref.getInt("account_id",0);
        Log.e("local_account", Integer.toString(account_id));
        token=pref.getString("token","?");
        sex=pref.getString("sex","0");
        Log.e("local_token",String.valueOf(token));
    }
}
