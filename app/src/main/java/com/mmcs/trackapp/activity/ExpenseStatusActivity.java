package com.mmcs.trackapp.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseStatusActivity extends AppCompatActivity {
    Spinner spnStatusType;
    TextView txt_data;
    ImageView image_uploaded;
    ExpenseModel expensemodel;
    Button btn_submit;
    EditText edt_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_status);
        txt_data=findViewById(R.id.txt_data);
        spnStatusType=findViewById(R.id.spnStatusType);
        image_uploaded=findViewById(R.id.image_uploaded);
        edt_message=findViewById(R.id.edt_message);
        btn_submit=findViewById(R.id.btn_submit);
        String type[] = {"Got It","Not Required Yet"};
        spnStatusType.setAdapter( new ArrayAdapter(this, R.layout.spn_textview_item, R.id.spn_txt_item,type ));
        expensemodel = (ExpenseModel) getIntent().getSerializableExtra(getString(R.string.expense_model));
        back();
        setTitle();
        txt_data.setText( expensemodel.getUpdate_comment());
        Glide.with(this).load(expensemodel.getUpdate_image()).into(image_uploaded);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spnStatusType.getSelectedItem().toString();
                String msg=edt_message.getText().toString();
                if (text.equals("")){
                    Toast.makeText(ExpenseStatusActivity.this,"Select Your Choice",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(msg.equals("")){
                    Toast.makeText(ExpenseStatusActivity.this,"Please Enter Your Message",Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
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
        title.setText(getString(R.string.expense_status));
    }
}
