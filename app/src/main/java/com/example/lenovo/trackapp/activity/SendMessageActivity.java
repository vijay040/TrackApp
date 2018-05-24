package com.example.lenovo.trackapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.adaptor.ChatMessageAdapter;
import com.example.lenovo.trackapp.model.ChatMessageModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SendMessageActivity extends AppCompatActivity {
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatMessageAdapter adapter;
    private ArrayList<ChatMessageModel> chatHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message2);
        getSupportActionBar().setTitle("Messages");
        initControls();
    }
    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("Monika Singh");// Hard Coded
        loadDummyHistory();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                ChatMessageModel chatMessage = new ChatMessageModel();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);
                messageET.setText("");
                displayMessage(chatMessage);
            }
        });
    }

    public void displayMessage(ChatMessageModel message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }
    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }
    private void loadDummyHistory(){
        chatHistory = new ArrayList<ChatMessageModel>();
        ChatMessageModel msg = new ChatMessageModel();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessageModel msg1 = new ChatMessageModel();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u ???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);
        adapter = new ChatMessageAdapter(SendMessageActivity.this, new ArrayList<ChatMessageModel>());
        messagesContainer.setAdapter(adapter);
        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessageModel message = chatHistory.get(i);
            displayMessage(message);
        }
    }
}