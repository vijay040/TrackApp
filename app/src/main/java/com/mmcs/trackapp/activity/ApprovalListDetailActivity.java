package com.mmcs.trackapp.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.ExpenseModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.model.UploadImageModel;
import com.mmcs.trackapp.model.UploadImageResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApprovalListDetailActivity extends AppCompatActivity {
    ExpenseModel expensemodel;
    Button btn_close;
    TextView  txtMeeting,txtApprovedBy, txtPurpose, txt_manager_status, txtCreatedOn,txt_meeting_des, txtAddress, txtCustomerName, txtMeetingDate, txtExpenseType, txtAdvance;
    ImageView image_uploaded;
    Shprefrences sh;
    Animation animBlink;
    LoginModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh = new Shprefrences(this);
        setContentView(R.layout.activity_approvallist_detail);
        expensemodel = (ExpenseModel) getIntent().getSerializableExtra(getString(R.string.appro_model));
        animBlink = AnimationUtils.loadAnimation(this, R.anim.blink);
        txtMeeting = findViewById(R.id.txtMeeting);
        txtCreatedOn = findViewById(R.id.txtCreatedOn);
        txtAddress = findViewById(R.id.txtAddress);
        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtMeetingDate = findViewById(R.id.txtMeetingDate);
        txtExpenseType = findViewById(R.id.txtExpenseType);

        txtAdvance = findViewById(R.id.txtAdvance);
        txt_meeting_des = findViewById(R.id.txt_meeting_des);
        image_uploaded = findViewById(R.id.image_uploaded);
        txt_manager_status = findViewById(R.id.txt_manager_status);
        txtPurpose = findViewById(R.id.txtPurpose);
        txtApprovedBy= findViewById(R.id.txtApprovedBy);
        txtApprovedBy.setText("Approved By:"+expensemodel.getManager());
        btn_close = findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        txtMeeting.setText(getString(R.string.meeting) + expensemodel.getDescreption());
        txtCreatedOn.setText(getString(R.string.expense_created_on) + expensemodel.getCreated_on());
        txtCustomerName.setText(getString(R.string.customer_name) + expensemodel.getCustomer_name());
        txtAddress.setText(getString(R.string.address) + expensemodel.getAddress());
        txtAdvance.setText(getString(R.string.amount) + expensemodel.getAmount());
        txtExpenseType.setText(getString(R.string.expense_type) + expensemodel.getExpense_type());
        txtMeetingDate.setText(getString(R.string.meeting_date) + expensemodel.getDate() + ", " + expensemodel.getTime());
        txtPurpose.setText(getString(R.string.expsense_des) + expensemodel.getComment());
        txt_meeting_des.setText(getString(R.string.meeting_purpose) + expensemodel.getPurpose());
        txt_manager_status.setText(getString(R.string.manager_approval) + expensemodel.getStatus());
        model = sh.getLoginModel(getString(R.string.login_model));

        image_uploaded.setOnTouchListener(new ImageMatrixTouchHandler(ApprovalListDetailActivity.this));


        if (expensemodel.getStatus() != null && !expensemodel.getStatus().equals("")) {

            switch (expensemodel.getStatus()) {


                case "ACCEPT":

                    String status1 = "<font color=#5fb0c9>" + getString(R.string.manager_approval) + "</font>" + "<font color=#00C853>" + expensemodel.getStatus() + "</font>";
                    txt_manager_status.setText(Html.fromHtml(status1));
                    txt_manager_status.startAnimation(animBlink);
                    break;

            }
        }

        SpannableStringBuilder sb = new SpannableStringBuilder(txtMeeting.getText());
        Glide.with(this).load(expensemodel.getImage()).placeholder(R.drawable.no_image).into(image_uploaded);

        // Picasso.get().load(expensemodel.getImage()).placeholder(R.drawable.ic_bill).resize(100,100).into(image_uploaded);
        // Span to set text color to some RGB value
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        // Span to make text bold
        //    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtMeeting.setText(sb);

        sb = new SpannableStringBuilder(txtCreatedOn.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 19, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtCreatedOn.setText(sb);

        sb = new SpannableStringBuilder(txtCustomerName.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtCustomerName.setText(sb);

        sb = new SpannableStringBuilder(txtAddress.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtAddress.setText(sb);

        sb = new SpannableStringBuilder(txtAdvance.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtAdvance.setText(sb);


        sb = new SpannableStringBuilder(txtExpenseType.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtExpenseType.setText(sb);

        sb = new SpannableStringBuilder(txtMeetingDate.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtMeetingDate.setText(sb);

        sb = new SpannableStringBuilder(txtPurpose.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 20, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtPurpose.setText(sb);

        sb = new SpannableStringBuilder(txt_meeting_des.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_meeting_des.setText(sb);

        sb = new SpannableStringBuilder( txt_manager_status.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_manager_status.setText(sb);

        back();
        setTitle();

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


    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.appro_details));
    }




}