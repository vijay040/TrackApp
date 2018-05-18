package com.example.lenovo.trackapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.adaptor.MessageAdapter;
import com.example.lenovo.trackapp.model.Message;

import java.util.ArrayList;
import java.util.List;

public class SendMessageActivity extends AppCompatActivity {
    Button send;
EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message2);
        send=(Button)findViewById(R.id.btn_send);
        editText=(EditText)findViewById(R.id.edt_msg);
        getSupportActionBar().setTitle("Messages");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=editText.getText().toString();
                if(message.equals("")){
                    editText.setError("Enter Your Message");
                    editText.requestFocus();
                }
                else{
                    Toast.makeText(SendMessageActivity.this,"Message Sent",Toast.LENGTH_SHORT).show();
                    startActivity(getIntent());
                }


            }
        });

    }

    public static class Messages extends AppCompatActivity {
        private List<Message> messageList;
        RecyclerView recyclerView;
        View ChildView ;
        int RecyclerViewItemPosition ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_messages);
            getSupportActionBar().setTitle("Messages");
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setImageResource(R.drawable.add);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            messageList = new ArrayList<>();
            messageList.add(new Message("anu", "hello this is anu", R.drawable.msgs));
            messageList.add(new Message("moni", "hello this is monika", R.drawable.msgs));
            messageList.add(new Message("vivek", "hello this is vivek", R.drawable.msgs));
            messageList.add(new Message("jagriti", "hello this is jagriti", R.drawable.msgs));
            messageList.add(new Message("sameer", "hello this is sammer", R.drawable.msgs));
            messageList.add(new Message("Ajeet", "hello this is Ajeet", R.drawable.msgs));
            messageList.add(new Message("Amit", "hello this is amit", R.drawable.msgs));
            final MessageAdapter adapter = new MessageAdapter(this, messageList);
            recyclerView.setAdapter(adapter);  fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Messages.this,SendMessageActivity.class);
                    startActivity(intent);
                }
            });




        }


    }
}
