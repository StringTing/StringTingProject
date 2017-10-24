package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.os.Bundle;

import com.example.leeyun.stringting_android.R;
import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;
/**
 * Created by leeyun on 2017. 10. 25..
 */

public class Chat_View extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        final ChatView chatView = (ChatView) findViewById(R.id.chat_view);
        chatView.addMessage(new ChatMessage("QA1", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {

                return true;
            }

        });




        chatView.setTypingListener(new ChatView.TypingListener() {
            @Override
            public void userStartedTyping() {

            }

            @Override
            public void userStoppedTyping() {

            }
        });

    }



}
