package com.mmcs.trackapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.MessageModel;
import com.mmcs.trackapp.model.ReportModel;

public class MessageDetailsActivity extends AppCompatActivity {
    MessageModel messageModel;
    TextView txt_name,txt_Date,txt_message;
    Button btn_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        messageModel= (MessageModel) getIntent().getSerializableExtra(getString(R.string.message_model));
        txt_name=findViewById(R.id.txt_name);
        txt_Date=findViewById(R.id.txt_Date);
        txt_message=findViewById(R.id.txt_message);
        btn_ok=findViewById(R.id.btn_ok);
        back();
        setTitle();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txt_name.setText(messageModel.getUser_name());
        txt_Date.setText(messageModel.getCreated_on());
        txt_message.setText(messageModel.getText_msg());

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
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.message_details));
    }

}
