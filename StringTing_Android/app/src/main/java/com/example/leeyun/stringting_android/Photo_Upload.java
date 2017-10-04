package com.example.leeyun.stringting_android;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class Photo_Upload extends AppCompatActivity implements View.OnClickListener  {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto1, iv_UserPhoto2, iv_UserPhoto3, iv_UserPhoto4, iv_UserPhoto5, iv_UserPhoto6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_upload);

        iv_UserPhoto1 = (ImageView)this.findViewById(R.id.photo1);
        iv_UserPhoto2 = (ImageView)this.findViewById(R.id.photo2);
        iv_UserPhoto3 = (ImageView)this.findViewById(R.id.photo3);
        iv_UserPhoto4 = (ImageView)this.findViewById(R.id.photo4);
        iv_UserPhoto5 = (ImageView)this.findViewById(R.id.photo5);
        iv_UserPhoto6 = (ImageView)this.findViewById(R.id.photo6);
    }


    public void onClick(View v){

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
                .setNeutralButton("앨범선택",albumListener)
                .setNegativeButton("취소",canceListener)
                .show();
    }

    private void doTakeAlbumAction() { //앨범에서 이미지 가져오기
        //앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK)
            return;

        switch (requestCode){
            case PICK_FROM_ALBUM:
            {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel",mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA:
            {
                //이미지를 가져온 이후 리자이즈할 이미지 크기
                //이후에 이미지 크롭 어플리케이션 호출
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/*");

                //CROP할 이미지 75*75(키울예정)
                intent.putExtra("outputX", 75); //x축 크기
                intent.putExtra("outputY", 75); //y축 크기
                intent.putExtra("aspectX", 1); //x축 비율
                intent.putExtra("aspectY", 1); //y축 비율
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent,CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE:
            {
                //크롭 이후 이미지 받아서 이미지 뷰에 이미지 보여줌
                //임시파일 삭제
                if(resultCode != RESULT_OK){
                    return;
                }
                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SmartWheel" + System.currentTimeMillis()+".jpg";

                if(extras != null) {
                    Bitmap photo = extras.getParcelable("data");//CROP된 BITMAP
                    iv_UserPhoto1.setImageBitmap(photo);
                    break;
                }


                //임시파일삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists()){
                    f.delete();
                }

            }

        }
    }



}
