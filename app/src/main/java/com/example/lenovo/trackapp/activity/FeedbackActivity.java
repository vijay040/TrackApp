package com.example.lenovo.trackapp.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;

public class FeedbackActivity extends AppCompatActivity {
    EditText customername,comment;
    TextView textView;
    ImageView imageView;
    Button submit;
    private static final int SELECT_PHOTO =200 ;
    private static final int CAMERA_REQUEST = 1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        customername=(EditText)findViewById(R.id.edt_name);
        comment=(EditText)findViewById(R.id.edt_comment);
        submit=(Button) findViewById(R.id.btnSubmit);
        textView=(TextView)findViewById(R.id.btn_load);
        imageView=(ImageView)findViewById(R.id.imz_loadreceipt);
        getSupportActionBar().setTitle("FeedbackActivity");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
             String customer_name=customername.getText().toString();
             String comm=comment.getText().toString();
             if(customer_name.equals("")){
                 Toast.makeText(FeedbackActivity.this,"Enter Customer Name",Toast.LENGTH_SHORT).show();
             }
             else if(comm.equals("")) {
                 Toast.makeText(FeedbackActivity.this, "Enter Your Comment", Toast.LENGTH_SHORT).show();
             }
             else{
                 Toast.makeText(FeedbackActivity.this,"Saved",Toast.LENGTH_SHORT).show();
             }
            }
        });
    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    openGallery();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void openGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        else if (requestCode==SELECT_PHOTO){
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                if(selectedImage !=null){
                    imageView.setImageURI(selectedImage);
                }
            }
        }
    }
}


