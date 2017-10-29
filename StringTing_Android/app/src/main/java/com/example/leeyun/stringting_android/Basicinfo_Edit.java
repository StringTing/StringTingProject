package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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


import org.w3c.dom.Text;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.example.leeyun.stringting_android.R.id.Email;
import static com.example.leeyun.stringting_android.R.id.Spinner_age;
import static com.example.leeyun.stringting_android.R.id.Spinner_blood;
import static com.example.leeyun.stringting_android.R.id.Spinner_city;
import static com.example.leeyun.stringting_android.R.id.Spinner_drink;
import static com.example.leeyun.stringting_android.R.id.Spinner_religion;
import static com.example.leeyun.stringting_android.R.layout.spinner_item;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by leeyun on 2017. 9. 16..
 */

public class Basicinfo_Edit extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto1, iv_UserPhoto2, iv_UserPhoto3, iv_UserPhoto4, iv_UserPhoto5, iv_UserPhoto6;

    public void onClick_ChatView(View v) {
        Intent intent = new Intent(getApplicationContext(), ChatCustom.class);
        startActivity(intent);
    }

    public void onClick_Basicinfo_upload(View v){               //basicinfo에서 불러온 정보들을 변수에 저장
        EditText Check_email= (EditText)findViewById(Email);
        Intent intent = new Intent(getApplicationContext(), ChatView.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicinfo_edit);


        iv_UserPhoto1 = (ImageView) this.findViewById(R.id.photo1);
        iv_UserPhoto2 = (ImageView) this.findViewById(R.id.photo2);
        iv_UserPhoto3 = (ImageView) this.findViewById(R.id.photo3);
        iv_UserPhoto4 = (ImageView) this.findViewById(R.id.photo4);
        iv_UserPhoto5 = (ImageView) this.findViewById(R.id.photo5);
        iv_UserPhoto6 = (ImageView) this.findViewById(R.id.photo6);


        Spinner age = (Spinner) findViewById(Spinner_age);
        Spinner city = (Spinner) findViewById(Spinner_city);
        Spinner blood = (Spinner) findViewById(Spinner_blood);
        Spinner drink = (Spinner) findViewById(Spinner_drink);
        Spinner religion = (Spinner) findViewById(Spinner_religion);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.age, spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.city, spinner_item);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.blood, spinner_item);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.drink, spinner_item);
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this, R.array.religion, spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(adapter);
        city.setAdapter(adapter2);
        blood.setAdapter(adapter3);
        drink.setAdapter(adapter4);
        religion.setAdapter(adapter5);


    }

    public void onClick_photo_upload(View v) {
        Intent intent = new Intent(getApplicationContext(), ChatView.class);

        startActivity(intent);

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

    public void onClick_imageUpload2(View v) {
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

    public void onClick_imageUpload3(View v) {
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

    public void onClick_imageUpload4(View v) {
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

    public void onClick_imageUpload5(View v) {
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

    public void onClick_imageUpload6(View v) {
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

    public void onClick_back(View v) {
        super.onBackPressed(); // or super.finish();

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
            }
            case PICK_FROM_CAMERA: {
                //이미지를 가져온 이후 리자이즈할 이미지 크기
                //이후에 이미지 크롭 어플리케이션 호출
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                //CROP할 이미지 75*75(키울예정)
                intent.putExtra("outputX", 75); //x축 크기
                intent.putExtra("outputY", 75); //y축 크기
                intent.putExtra("aspectX", 1); //x축 비율
                intent.putExtra("aspectY", 1); //y축 비율
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


                    if (null == iv_UserPhoto1.getDrawable()) {
                        iv_UserPhoto1.setImageBitmap(photo);
                    } else if (null == iv_UserPhoto2.getDrawable()) {
                        iv_UserPhoto2.setImageBitmap(photo);
                    } else if (null == iv_UserPhoto3.getDrawable()) {
                        iv_UserPhoto3.setImageBitmap(photo);
                    } else if (null == iv_UserPhoto4.getDrawable()) {
                        iv_UserPhoto4.setImageBitmap(photo);

                    } else {
                        iv_UserPhoto5.setImageBitmap(photo);

                    }
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
}