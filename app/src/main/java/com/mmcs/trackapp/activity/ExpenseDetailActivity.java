package com.mmcs.trackapp.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.ExpenseModel;

public class ExpenseDetailActivity extends AppCompatActivity {
    ExpenseModel  expensemodel;
    Button btn_close;
    TextView txtdescreption,txtCreatedOn,txtAddress,txtCustomerName,txtMeetingDate,txtExpenseType,txtAdvance;
    ImageView image_uploaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);
        expensemodel= (ExpenseModel) getIntent().getSerializableExtra("EXPENSEMODEL");
        txtdescreption=findViewById(R.id.txtdescreption);
        txtCreatedOn=findViewById(R.id.txtCreatedOn);
        txtAddress=findViewById(R.id.txtAddress);
        txtCustomerName=findViewById(R.id.txtCustomerName);
        txtMeetingDate=findViewById(R.id.txtMeetingDate);
        txtExpenseType=findViewById(R.id.txtExpenseType);
        txtAdvance=findViewById(R.id.txtAdvance);
        image_uploaded=findViewById(R.id.image_uploaded);
        btn_close=findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtdescreption.setText("Descreption:"+expensemodel.getDescreption ());
        txtCreatedOn.setText("Expense Created On:"+expensemodel.getCreated_on ());
        txtCustomerName.setText("Customer Name:"+expensemodel.getCustomer_name());
        txtAddress.setText("Address:"+expensemodel.getAddress ());
        txtAdvance.setText("Advance:"+expensemodel.getAmount());
        txtExpenseType.setText("ExpenseType:"+expensemodel.getExpense_type ());
        txtMeetingDate.setText("Meeting Date:"+expensemodel.getDate ()+", "+expensemodel.getTime ());
        SpannableStringBuilder sb = new SpannableStringBuilder(txtdescreption.getText());
        // Span to set text color to some RGB value
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        // Span to make text bold
        //    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdescreption.setText(sb);

        sb = new SpannableStringBuilder(txtCreatedOn.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 19, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtCreatedOn.setText(sb);

        sb = new SpannableStringBuilder(txtCustomerName.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtCustomerName.setText(sb);

        sb = new SpannableStringBuilder(txtAddress.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtAddress.setText(sb);

        sb = new SpannableStringBuilder(txtAdvance.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtAdvance.setText(sb);


        sb = new SpannableStringBuilder(txtExpenseType.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtExpenseType.setText(sb);

        sb = new SpannableStringBuilder(txtMeetingDate.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtMeetingDate.setText(sb);

     back();






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
