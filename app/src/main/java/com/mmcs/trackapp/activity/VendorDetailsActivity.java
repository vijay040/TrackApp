package com.mmcs.trackapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.VQuotationModel;

import java.util.Calendar;

public class VendorDetailsActivity extends AppCompatActivity {
    EditText edtQuotation, edtport_destination, edtVersion, edtsubject, edtStaffName, edtReceiveDate, edtValidDate, edtport_loading;
    Button btnOk;
    EditText editConatiner,edtCommodity,editIDR,editCharge_code,editUnitBase,editCurrency,editChargeAmnt,editExchangeRate,editMargin,editMarginCurr;
    VQuotationModel quotationModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_details);
        quotationModel = (VQuotationModel) getIntent().getSerializableExtra(getString(R.string.vender_model));
        edtQuotation = findViewById(R.id.edtQuotation);
        edtport_loading = findViewById(R.id.edtport_loading);
        edtport_destination = findViewById(R.id.edtport_destination);
        edtVersion = findViewById(R.id.edtVersion);
        edtsubject = findViewById(R.id.edtsubject);
        edtStaffName = findViewById(R.id.edtStaffName);
        edtReceiveDate = findViewById(R.id.edtReceiveDate);
        edtValidDate = findViewById(R.id.edtValidDate);
             editConatiner=findViewById(R.id.editConatiner);
        edtCommodity=findViewById(R.id.edtCommodity);
        editCharge_code=findViewById(R.id.editCharge_code);
        editUnitBase=findViewById(R.id.editUnitBase);
        editCurrency=findViewById(R.id.editCurrency);
        editChargeAmnt=findViewById(R.id.editChargeAmnt);
        editExchangeRate=findViewById(R.id.editExchangeRate);
        editMargin=findViewById(R.id.editMargin);
        editMarginCurr=findViewById(R.id.editMarginCurr);
        editIDR=findViewById(R.id.editIDR);
        btnOk = findViewById(R.id.btnOk);
        back();
        setTitle();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edtReceiveDate.setText("Rcv Date:"+quotationModel.getRcv_dt());
        edtQuotation.setText("Quotation No:"+quotationModel.getV_quot_no());
        edtport_loading.setText("POL:"+quotationModel.getPol());
        edtport_destination.setText("POD:"+quotationModel.getPod());
        edtValidDate.setText("Valid Till:"+quotationModel.getVaild_dt());
        edtVersion.setText("Version No:"+quotationModel.getVer_no());
        edtsubject.setText("Subject:"+quotationModel.getQuot_subject());
        edtStaffName.setText("Staff Name:"+quotationModel.getStaff_id());
        editConatiner.setText(quotationModel.getContainer_size());
        edtCommodity.setText(quotationModel.getCommodity());
        editCharge_code.setText(quotationModel.getCharge_code());
        editUnitBase.setText(quotationModel.getUnit_base());
        editCurrency.setText(quotationModel.getCurrency());
        editChargeAmnt.setText(quotationModel.getCrg_amt());
        editExchangeRate.setText(quotationModel.getEx_rate());
        editMargin.setText(quotationModel.getMargin());
        editMarginCurr.setText(quotationModel.getMargin_curr());
        editIDR.setText(quotationModel.getCurrency());
        setTitle();
        back();
    }
    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("VENDOR QUOTATION");
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


