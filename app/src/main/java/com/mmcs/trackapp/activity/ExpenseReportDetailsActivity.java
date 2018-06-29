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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.ReportAdapter;
import com.mmcs.trackapp.model.PreRequestModel;
import com.mmcs.trackapp.model.ReportModel;

public class ExpenseReportDetailsActivity extends AppCompatActivity {
    ReportModel reportmodel;
    TextView txtdescreption,txtdate,txtTotalAmount,txtAdvance,txt_Toatal_Amount,txt_balance;
    ListView listExpense;
    Button btn_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report_details);
        reportmodel= (ReportModel) getIntent().getSerializableExtra("REPORTMODEL");
        txtdescreption=findViewById(R.id.txtdescreption);
        txtdate=findViewById(R.id.txtdate);
        txtTotalAmount=findViewById(R.id.txtTotalAmount);
        txtAdvance=findViewById(R.id.txtAdvance);
        txt_Toatal_Amount=findViewById(R.id.txt_Toatal_Amount);
        txt_balance=findViewById(R.id.txt_balance);
        listExpense=findViewById(R.id.listExpense);
        btn_close=findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtdescreption.setText("Descreption:"+reportmodel.getDescription());
        txtdate.setText("Date:"+reportmodel.getCreated_on());
        txtTotalAmount.setText("Total:"+reportmodel.getTotal_amount());
        txtAdvance.setText("Advance:"+reportmodel.getAdvance());
        txt_balance.setText("Balance To Rct:"+reportmodel.getBalance_rct());
        txt_Toatal_Amount.setText("Total Amount:"+reportmodel.getTotal_amount());
        SpannableStringBuilder sb = new SpannableStringBuilder(txtdescreption.getText());
        // Span to set text color to some RGB value
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        // Span to make text bold
        //    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdescreption.setText(sb);

        sb = new SpannableStringBuilder(txtdate.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdate.setText(sb);

        sb = new SpannableStringBuilder(txtTotalAmount.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtTotalAmount.setText(sb);

        sb = new SpannableStringBuilder(txtAdvance.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtAdvance.setText(sb);

        sb = new SpannableStringBuilder(txt_balance.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_balance.setText(sb);

        sb = new SpannableStringBuilder(txt_Toatal_Amount.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_Toatal_Amount.setText(sb);
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