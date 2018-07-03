package com.mmcs.trackapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
    private static final int SELECT_PHOTO = 200;
    private static final int CAMERA_REQUEST = 1888;
    Button btn_close;
    TextView txtdescreption,txtCreatedOn,txtAddress,txtCustomerName,txtMeetingDate,txtExpenseType,txtAdvance,txtEdit;
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
        txtEdit=findViewById(R.id.txtEdit);
        btn_close=findViewById(R.id.btn_close);
        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
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
    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseDetailActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else if (options[item].equals("Choose from Gallery")) {
                    openGallery();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

   // String imageImagePath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image_uploaded.setImageBitmap(photo);
           // imageImagePath = data.getData().getPath();
           // image_path.setText(imageImagePath);
        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    image_uploaded.setImageURI(selectedImage);
                   // imageImagePath = selectedImage.getPath();
                   // image_path.setText(imageImagePath);
                }
            }
        }
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText("Expense Details");
    }

}
