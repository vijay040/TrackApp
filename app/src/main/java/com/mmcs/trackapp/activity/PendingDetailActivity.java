package com.mmcs.trackapp.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

;import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingDetailActivity extends AppCompatActivity {
PreRequestModel prerequestmodel;
TextView txtdescreption,txtdate,txtadvance,txtstatus,txtaddress,txtcustomername;
ListView list_requesttype;
Button reject,approve;
Shprefrences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh=new Shprefrences(this);
        setContentView(R.layout.activity_pendingdetail);
        prerequestmodel= (PreRequestModel) getIntent().getSerializableExtra(getString(R.string.prerequest_model));


        txtdescreption=findViewById(R.id.txtdescreption);
        txtdate=findViewById(R.id.txtdate);
        txtadvance=findViewById(R.id.txtadvance);
        txtaddress=findViewById(R.id.txtaddress);
        txtcustomername=findViewById(R.id.txtcustomername);


        reject=findViewById(R.id.reject);
        approve=findViewById(R.id.approve);
      setTitle();
      back();
        //getSupportActionBar().setTitle("Request");
        list_requesttype=findViewById(R.id.list_requesttype);
        RequestTypesStrAdaptor adaptor =new RequestTypesStrAdaptor(PendingDetailActivity.this,prerequestmodel.getRequest_type());
        list_requesttype.setAdapter(adaptor);
        txtdescreption.setText(getString(R.string.description)+prerequestmodel.getComment());
        txtdate.setText(getString(R.string.date)+prerequestmodel.getDate());
        txtadvance.setText(getString(R.string.advance)+prerequestmodel.getAdvance());
        txtaddress.setText(getString(R.string.address)+prerequestmodel.getAddress());
        txtcustomername.setText(getString(R.string.customer_name)+prerequestmodel.getCustomer_name());

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

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PendingDetailActivity.this);
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
                buttonPositive.setTextColor(ContextCompat.getColor(PendingDetailActivity.this, R.color.them_color));
                Button buttonNegative = alertDialogAndroid.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(PendingDetailActivity.this, R.color.them_color));
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(PendingDetailActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.activity_rejection_reason, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(PendingDetailActivity.this);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.edt_message);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                String message=userInputDialogEditText.getText().toString();
                                if (message.equals("")){
                                    Toast.makeText(PendingDetailActivity.this,"Enter Something..", Toast.LENGTH_SHORT).show();
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
                buttonPositive.setTextColor(ContextCompat.getColor(PendingDetailActivity.this, R.color.them_color));
                Button buttonNegative = alertDialogAndroid.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(PendingDetailActivity.this, R.color.them_color));
            }
        });
    }

    private void postStatus(String status,String msg)
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().postAcceptRejectPendings(model.getId(),prerequestmodel.getId(),status,msg).enqueue(new Callback<PreRequestResMeta>() {
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
        title.setText(getString(R.string.pending_details));
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
