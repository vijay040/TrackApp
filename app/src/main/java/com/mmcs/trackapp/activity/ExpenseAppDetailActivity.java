package com.mmcs.trackapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView txtdescreption,txtdate,txt_resubmit_msg,txt_requested_by,txt_meeting_purpose,txt_expense_des,txtadvance,title_re_submit,txtaddress,txtcustomername,txt_expensetype,txt_meeting_date,txt_meeting_time,txt_expense_date,txt_exchange_rate;
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
        txt_expense_des=findViewById(R.id.txt_expense_des);
        txt_meeting_date=findViewById(R.id.txt_meeting_date);
        txt_meeting_time=findViewById(R.id.txt_meeting_time);
        txt_expense_date=findViewById(R.id.txt_expense_date);
        txt_exchange_rate=findViewById(R.id.txt_exchange_rate);
        txt_requested_by=findViewById(R.id.txt_requested_by);
        txt_meeting_purpose=findViewById(R.id.txt_meeting_purpose);
        title_re_submit=findViewById(R.id.title_re_submit);
        txt_resubmit_msg=findViewById(R.id.txt_resubmit_msg);
        reject=findViewById(R.id.reject);
        approve=findViewById(R.id.approve);
        setTitle();
        back();
        //getSupportActionBar().setTitle("Request");
        list_requesttype=findViewById(R.id.list_requesttype);
       //RequestTypesStrAdaptor adaptor =new RequestTypesStrAdaptor(ExpenseAppDetailActivity.this,expenseApprovalListModel.getRequest_type());
      // list_requesttype.setAdapter(adaptor);
        txtdescreption.setText(getString(R.string.meeting)+expenseApprovalListModel.getDescription());
        txtdate.setText(getString(R.string.date)+expenseApprovalListModel.getCreated_on());
        txtadvance.setText(getString(R.string.amount)+expenseApprovalListModel.getAmount());
        txtaddress.setText(getString(R.string.address)+expenseApprovalListModel.getAddress());
        txtcustomername.setText(getString(R.string.customer_name)+expenseApprovalListModel.getCustomer_name());
        txt_expensetype.setText(getString(R.string.expense_type)+expenseApprovalListModel.getExpense_type_id());
        txt_meeting_date.setText(getString(R.string.meeting_date)+expenseApprovalListModel.getDate());
        txt_meeting_time.setText(getString(R.string.meeting_time)+expenseApprovalListModel.getTime());
        txt_expense_date.setText(getString(R.string.expense_date)+expenseApprovalListModel.getCreated_on());
        txt_exchange_rate.setText(getString(R.string.exch_rate)+expenseApprovalListModel.getExchange_rate());
        txt_requested_by.setText(getString(R.string.requested_by)+expenseApprovalListModel.getUser_name());
        txt_expense_des.setText(getString(R.string.expsense_des)+expenseApprovalListModel.getComment());
        txt_meeting_purpose.setText(getString(R.string.meeting_purpose)+expenseApprovalListModel.getDescription());
        SpannableStringBuilder sb = new SpannableStringBuilder(txtdescreption.getText());
        if(expenseApprovalListModel.getResubmit_msg().equals("")){
            title_re_submit.setVisibility(View.GONE);
            txt_resubmit_msg.setVisibility(View.GONE);

        }
        else{
            txt_resubmit_msg.setText(expenseApprovalListModel.getResubmit_msg());
        }


        // Span to set text color to some RGB value
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        // Span to make text bold
        //    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdescreption.setText(sb);

        sb = new SpannableStringBuilder(txtadvance.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
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

        sb = new SpannableStringBuilder(txt_meeting_date.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_meeting_date.setText(sb);

        sb = new SpannableStringBuilder(txt_meeting_time.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_meeting_time.setText(sb);

        sb = new SpannableStringBuilder(txt_expense_date.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_expense_date.setText(sb);

        sb = new SpannableStringBuilder(txt_exchange_rate.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_exchange_rate.setText(sb);

        sb = new SpannableStringBuilder(txt_requested_by.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_requested_by.setText(sb);

        sb = new SpannableStringBuilder(txt_expense_des.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 20, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_expense_des.setText(sb);

        sb = new SpannableStringBuilder(txt_meeting_purpose.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_meeting_purpose.setText(sb);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExpenseAppDetailActivity.this);
                alertDialog.setTitle("Confirm Approve...");
                alertDialog.setMessage("Are you sure you want to Approve it?");
                alertDialog.setIcon(R.drawable.ic_expense_app);
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        postStatus(getString(R.string.accept),"");
                        finish();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialogAndroid = alertDialog.create();
                alertDialogAndroid.show();
                Button buttonPositive = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setTextColor(ContextCompat.getColor(ExpenseAppDetailActivity.this, R.color.them_color));
                Button buttonNegative = alertDialogAndroid.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(ExpenseAppDetailActivity.this, R.color.them_color));
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(ExpenseAppDetailActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.activity_rejection_reason, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(ExpenseAppDetailActivity.this);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.edt_message);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                String message=userInputDialogEditText.getText().toString();
                                if (message.equals("")){
                                    Toast.makeText(ExpenseAppDetailActivity.this,"Enter Something..", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    postStatus(getString(R.string.reject),message);
                                    finish();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
                Button buttonPositive = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setTextColor(ContextCompat.getColor(ExpenseAppDetailActivity.this, R.color.them_color));
                Button buttonNegative = alertDialogAndroid.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(ExpenseAppDetailActivity.this, R.color.them_color));
            }
        });

    }

    private void postStatus(String status,String msg)
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().postAcceptRejectExpense(model.getId(),expenseApprovalListModel
                .getId(),status,msg).enqueue(new Callback<PreRequestResMeta>() {
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
