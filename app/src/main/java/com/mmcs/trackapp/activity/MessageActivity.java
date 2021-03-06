package com.mmcs.trackapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.MessageAdapter;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.MessageModel;
import com.mmcs.trackapp.model.MessageResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {
    ListView listMessageView;
    ProgressBar progressBar;
    RelativeLayout txtAdd;
    Shprefrences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //getSupportActionBar().setTitle("Messages");
        listMessageView=findViewById(R.id.listMessageView);
        progressBar=findViewById(R.id.progress);
        //getSupportActionBar().setTitle("Expenses");
        txtAdd=findViewById(R.id.txtAdd);
        sh=new Shprefrences(this);
        back();
        setTitle();
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this,SendMessageActivity.class));
            }
        });
        listMessageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MessageAdapter adaptor = (MessageAdapter) adapterView.getAdapter();
                MessageModel model = adaptor.list.get(i);
                Intent intent = new Intent(MessageActivity.this, MessageDetailsActivity.class);
                intent.putExtra(getString(R.string.message_model), model);
                startActivity(intent);
            }
        });

    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.messages));
    }

    private void getMessageList()
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getMessages(model.getId()).enqueue(new Callback<MessageResMeta>() {
            @Override
            public void onResponse(Call<MessageResMeta> call, Response<MessageResMeta> response) {
                if(response==null)
                    return;
                ArrayList<MessageModel> model=response.body().getResponse();
                MessageAdapter adaptor=new MessageAdapter(MessageActivity.this,model);
                listMessageView.setAdapter(adaptor);
                listMessageView.setEmptyView(findViewById(R.id.imz_nodata));
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<MessageResMeta> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        getMessageList();
    }


    private void back() {
        RelativeLayout drawerIcon = (RelativeLayout) findViewById(R.id.drawerIcon);
        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}