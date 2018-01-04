package com.string.leeyun.stringting_android;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.message;
import com.string.leeyun.stringting_android.API.userinfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Retrofit;

public class PersonalProfile_Edit extends AppCompatActivity {

    //사진 및 정보
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto1, iv_UserPhoto2, iv_UserPhoto3, iv_UserPhoto4, iv_UserPhoto5, iv_UserPhoto6;
    public int imageupload_count=0;
    public ArrayList<String> Imageupload_countList=new ArrayList<>();
    userinfo UserInfo = new userinfo();
    ResponseApi responapi =new ResponseApi();
    Rest_ApiService apiService;
    Retrofit retrofit;
    String birthdayYear;
    String birthdayMonth;
    String birthdayDay;
    String real_album_path;
    int account_id;
    String sex;
    String token_localdb;
    int account_id_localdb;
    message Message = new message();

    File Postfile;




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }




    //대화창
    ListView m_ListView;
    PersonalChatCustom m_Adapter;

    ArrayList<String> question_array;
    ArrayList<String>answer_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile__edit);




        // 커스텀 어댑터 생성
        m_Adapter = new PersonalChatCustom();


        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);



        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);


        m_Adapter.add(question_array.get(0), 0);

        m_Adapter.add(answer_array.get(0), 1);

        m_Adapter.add(question_array.get(1), 0);

        m_Adapter.add(answer_array.get(1), 1);

        m_Adapter.add(question_array.get(2), 0);

        m_Adapter.add(answer_array.get(2), 1);
        setListViewHeightBasedOnItems(m_ListView);

    }

    public void setListViewHeightBasedOnItems(ListView listView) {

        // Get list adpter of listview;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)  return;

        int numberOfItems = listAdapter.getCount();

        // Get total height of all items.
        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = listAdapter.getView(itemPos, null, listView);
            item.measure(0, 0);
            totalItemsHeight += item.getMeasuredHeight();
        }

        // Get total height of all item dividers.
        int totalDividersHeight = listView.getDividerHeight() *  (numberOfItems - 1);

        // Set list height.
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
