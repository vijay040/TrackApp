package com.mmcs.trackapp.activity;

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
import com.mmcs.trackapp.adaptor.RequestTypesStrAdaptor;
import com.mmcs.trackapp.model.ExpenseApprovalListModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseAppDetailActivity extends AppCompatActivity {
    ExpenseApprovalListModel expenseApprovalListModel;
    TextView txtdescreption,txtdate,txtadvance,txtstatus,txtaddress,txtcustomername,txt_expensetype;
    ListView list_requesttype;
    Button reject,approve;
    Shprefrences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh=new Shprefrences(this);
        setContentView(R.layout.activity_expense_app_detail);
        expenseApprovalListModel= (ExpenseApprovalListModel) getIntent().getSerializableExtra(getString(R.string.expense_appro_model));
        txtdescreption=findViewById(R.id.txtdescreption);
        txtdate=findViewById(R.id.txtdate);
        txtadvance=findViewById(R.id.txtadvance);
        txtaddress=findViewById(R.id.txtaddress);
        txtcustomername=findViewById(R.id.txtcustomername);
        txt_expensetype=findViewById(R.id.txt_expensetype);
        reject=findViewById(R.id.reject);
        approve=findViewById(R.id.approve);
        setTitle();
        back();
        //getSupportActionBar().setTitle("Request");
        list_requesttype=findViewById(R.id.list_requesttype);
       // RequestTypesStrAdaptor adaptor =new RequestTypesStrAdaptor(ExpenseAppDetailActivity.this,expenseApprovalListModel.getRequest_type());
      //  list_requesttype.setAdapter(adaptor);
        txtdescreption.setText(getString(R.string.description)+expenseApprovalListModel.getComment());
        txtdate.setText(getString(R.string.date)+expenseApprovalListModel.getCreated_on());
        txtadvance.setText(getString(R.string.advance)+expenseApprovalListModel.getAmount());
        txtaddress.setText(getString(R.string.address)+expenseApprovalListModel.getAddress());
        txtcustomername.setText(getString(R.string.customer_name)+expenseApprovalListModel.getCustomer_name());
        txt_expensetype.setText(getString(R.string.expense_type)+expenseApprovalListModel.getExpense_type_id());
        SpannableStringBuilder sb = new SpannableStringBuilder(txtdescreption.getText());
        // Span to set text color to some RGB value
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        // Span to make text bold
        //    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdescreption.setText(sb);

        sb = new SpannableStringBuilder(txtadvance.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtadvance.setText(sb);

        sb = new SpannableStringBuilder(txtdate.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdate.setText(sb);

        sb = new SpannableStringBuilder(txtaddress.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtaddress.setText(sb);

        sb = new SpannableStringBuilder(txtcustomername.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtcustomername.setText(sb);

        sb = new SpannableStringBuilder(txt_expensetype.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_expensetype.setText(sb);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postStatus(getString(R.string.accept));
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postStatus(getString(R.string.reject));
                finish();
            }
        });

    }

    private void postStatus(String status)
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().postAcceptRejectExpense(model.getId(),expenseApprovalListModel
                .getId(),status).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {

            }

            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t) {

            }
        });
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.exp_app_details));
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
