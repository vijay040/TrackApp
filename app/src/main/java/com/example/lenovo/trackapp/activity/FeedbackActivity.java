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
    Button cancel;
    TextView save;
    private static final int SELECT_PHOTO =200 ;
    private static final int CAMERA_REQUEST = 1888;
    TextInputLayout inputLayoutname,inputLayoutcomment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        customername=(EditText)findViewById(R.id.edt_name);
        comment=(EditText)findViewById(R.id.edt_comment);
        save=(TextView) findViewById(R.id.btn_save);
        textView=(TextView)findViewById(R.id.btn_load);
        cancel=(Button)findViewById(R.id.btn_cancel);
        imageView=(ImageView)findViewById(R.id.imz_loadreceipt);
        inputLayoutname = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutcomment=(TextInputLayout) findViewById(R.id.input_layout_comment);
        customername.addTextChangedListener(new MyTextWatcher(customername));
        comment.addTextChangedListener(new MyTextWatcher(comment));
        getSupportActionBar().setTitle("FeedbackActivity");
       /* customername.setTextColor(Color.parseColor("#2196F3"));
        comment.setTextColor(Color.parseColor("#2196F3"));*/
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FeedbackActivity.this,LandingActivity.class);
                startActivity(intent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                submitForm();
            }
        });
    }
    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (!validateComment()) {
            return;
        }
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }
    private boolean validateName() {
        if (customername.getText().toString().trim().isEmpty()) {
            inputLayoutname.setError(getString(R.string.err_msg_name));
            requestFocus(customername);
            return false;
        } else {
            inputLayoutname.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateComment() {
        if (comment.getText().toString().trim().isEmpty()) {
            inputLayoutcomment.setError(getString(R.string.err_msg_comment));
            requestFocus(comment);
            return false;
        } else {
            inputLayoutcomment.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {
        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edt_name:
                    validateName();
                    break;
                case R.id.edt_comment:
                    validateComment();
                    break;

            }
        }
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
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        else if (requestCode==SELECT_PHOTO) {
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                if(selectedImage !=null){
                    imageView.setImageURI(selectedImage);
                }
            }
        }
    }
}


