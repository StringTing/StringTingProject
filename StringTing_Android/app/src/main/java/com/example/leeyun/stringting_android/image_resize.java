package com.example.leeyun.stringting_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static android.R.attr.src;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.kakao.auth.StringSet.file;

/**
 * Created by leeyun on 2017. 11. 27..
 */

public class image_resize {
    ArrayList<String>imageList;
    Bitmap bitmap;

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<String> getImageList() {

        return imageList;
    }


    public static String bitmap_resized_small(String filepath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        File filePre = new File(filepath,".jpg");
        File fileNow = new File(filepath, "_small.jpg");
        Bitmap src = BitmapFactory.decodeFile(filepath, options);

        String dirPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel =new File(dirPath);

        if(!directory_SmartWheel.exists()){
            directory_SmartWheel.mkdir();
        }
        filepath=filepath+"_small.jpg";
        File copyFile= new File(filepath);
        BufferedOutputStream out=null;

        try{
            copyFile.createNewFile();
            out= new BufferedOutputStream(new FileOutputStream(copyFile));
            src.compress(Bitmap.CompressFormat.JPEG,70,out);

            //sendBroadcst를 통해 Crop된 사진을 앨범에 보이도록 갱신한다
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return filepath;
    }
    public static String bitmap_resized_middle(String filepath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        File filePre = new File(filepath,".jpg");
        File fileNow = new File(filepath, "_middle.jpg");
        Bitmap src = BitmapFactory.decodeFile(filepath, options);

        String dirPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel =new File(dirPath);

        if(!directory_SmartWheel.exists()){
            directory_SmartWheel.mkdir();
        }
        filepath=filepath+"_middle.jpg";
        File copyFile= new File(filepath);
        BufferedOutputStream out=null;

        try{
            copyFile.createNewFile();
            out= new BufferedOutputStream(new FileOutputStream(copyFile));
            src.compress(Bitmap.CompressFormat.JPEG,70,out);

            //sendBroadcst를 통해 Crop된 사진을 앨범에 보이도록 갱신한다
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return filepath;
    }
    public static String bitmap_resized_large(String filepath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        File filePre = new File(filepath,".jpg");
        File fileNow = new File(filepath, "_large.jpg");
        Bitmap src = BitmapFactory.decodeFile(filepath, options);

        String dirPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel =new File(dirPath);

        if(!directory_SmartWheel.exists()){
            directory_SmartWheel.mkdir();
        }
        filepath=filepath+"_large.jpg";
        File copyFile= new File(filepath);
        BufferedOutputStream out=null;

        try{
            copyFile.createNewFile();
            out= new BufferedOutputStream(new FileOutputStream(copyFile));
            src.compress(Bitmap.CompressFormat.JPEG,70,out);

            //sendBroadcst를 통해 Crop된 사진을 앨범에 보이도록 갱신한다
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return filepath;
    }

}
