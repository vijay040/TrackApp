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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.VQuotationModel;

public class VQuotationDetailActivity extends AppCompatActivity {
VQuotationModel quotationModel;
    Button btn_close;
    TextView txtvender_masterid,txtContainer ,txtexchange_rate, txtMargin_currency, txtAudit_id, txtCommodity, txtSerial_No, txtCharge_Code, txtQuotation_no,txtCharge_amount,txtMargin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail);
        quotationModel = (VQuotationModel) getIntent().getSerializableExtra(getString(R.string.vender_model));
        btn_close = findViewById(R.id.btn_close);
        txtvender_masterid=findViewById(R.id.txtvender_masterid);
        txtexchange_rate=findViewById(R.id.txtexchange_rate);
        txtMargin_currency=findViewById(R.id.txtMargin_currency);
        txtAudit_id=findViewById(R.id.txtAudit_id);
        txtCommodity=findViewById(R.id.txtCommodity);
        txtSerial_No=findViewById(R.id.txtSerial_No);
        txtCharge_Code=findViewById(R.id.txtCharge_Code);
        txtQuotation_no=findViewById(R.id.txtQuotation_no);
        txtCharge_amount=findViewById(R.id.txtCharge_amount);
        txtMargin=findViewById(R.id.txtMargin);
        txtContainer=findViewById(R.id.txtContainer);
        back();
        setTitle();
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtvender_masterid.setText(getString(R.string.master_id) + quotationModel.getV_quot_mstr_id());
        txtexchange_rate.setText(getString(R.string.exchange_rate) + quotationModel.getEx_rate());
        txtMargin_currency.setText(getString(R.string.margin_currency) + quotationModel.getMargin_curr());
        txtAudit_id.setText(getString(R.string.audit_mster_id) + quotationModel.getAudit_mas_id());
        txtCommodity.setText(getString(R.string.commodity) + quotationModel.getCommodity());
        txtCharge_Code.setText(getString(R.string.charge_code) + quotationModel.getCharge_code());
        txtQuotation_no.setText(getString(R.string.v_quot_no) + quotationModel.getV_quot_no());
        txtCharge_amount.setText(getString(R.string.crg_amt) + quotationModel.getCrg_amt());
        txtMargin.setText(getString(R.string.margin) + quotationModel.getMargin());
        txtSerial_No.setText(getString(R.string.s_no) + quotationModel.getS_no());
        txtContainer.setText(getString(R.string.container_size) + quotationModel.getContainer_size());

        SpannableStringBuilder sb = new SpannableStringBuilder(txtvender_masterid.getText());
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtvender_masterid.setText(sb);

        sb = new SpannableStringBuilder(txtexchange_rate.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtexchange_rate.setText(sb);

        sb = new SpannableStringBuilder(txtMargin_currency.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtMargin_currency.setText(sb);

        sb = new SpannableStringBuilder(txtAudit_id.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtAudit_id.setText(sb);

        sb = new SpannableStringBuilder(txtCommodity.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtCommodity.setText(sb);

        sb = new SpannableStringBuilder(txtCharge_Code.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtCharge_Code.setText(sb);


        sb = new SpannableStringBuilder(txtQuotation_no.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtQuotation_no.setText(sb);

        sb = new SpannableStringBuilder(txtCharge_amount.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtCharge_amount.setText(sb);


        sb = new SpannableStringBuilder(txtMargin.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtMargin.setText(sb);


        sb = new SpannableStringBuilder(txtSerial_No.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtSerial_No.setText(sb);

        sb = new SpannableStringBuilder(txtContainer.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtContainer.setText(sb);

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
        title.setText(getString(R.string.v_quo_detail));
    }
}
