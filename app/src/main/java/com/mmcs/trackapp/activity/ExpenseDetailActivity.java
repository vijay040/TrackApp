package com.mmcs.trackapp.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.model.UploadImageModel;
import com.mmcs.trackapp.model.UploadImageResMeta;
import com.mmcs.trackapp.util.CircleTransform;
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


public class ExpenseDetailActivity extends AppCompatActivity {
    ExpenseModel expensemodel;
    private static final int SELECT_PHOTO = 200;
    private static final int CAMERA_REQUEST = 1888;
    Button btn_close;
    TextView action,re_submit,txtMeeting,txtPurpose ,txt_rejection_message,txt_rejection_title,txt_status,
            txt_manager_status,txtCreatedOn, txtAddress, txtCustomerName, txtMeetingDate, txtExpenseType, txtAdvance, txtEdit,txt_cancel;
    ImageView image_uploaded;
    Shprefrences sh;
    Animation animBlink;
    LoginModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh=new Shprefrences(this);
        setContentView(R.layout.activity_expense_detail);
        expensemodel = (ExpenseModel) getIntent().getSerializableExtra(getString(R.string.expense_model));
        animBlink = AnimationUtils.loadAnimation(this, R.anim.blink);
        txtMeeting = findViewById(R.id.txtMeeting);
        txtCreatedOn = findViewById(R.id.txtCreatedOn);
        txtAddress = findViewById(R.id.txtAddress);
        txt_rejection_message=findViewById(R.id.txt_rejection_message);
        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtMeetingDate = findViewById(R.id.txtMeetingDate);
        txtExpenseType = findViewById(R.id.txtExpenseType);
        txtAdvance = findViewById(R.id.txtAdvance);
        txt_rejection_title=findViewById(R.id.txt_rejection_title);
        image_uploaded = findViewById(R.id.image_uploaded);
        txt_manager_status=findViewById(R.id.txt_manager_status);
        txtPurpose=findViewById(R.id.txtPurpose);
        txtEdit = findViewById(R.id.txtEdit);
        re_submit=findViewById(R.id.re_submit);
        txt_status=findViewById(R.id.txt_status);
        btn_close = findViewById(R.id.btn_close);
        txt_cancel= findViewById(R.id.txt_cancel);
        action=findViewById(R.id.action);

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

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postReSubmitStatus("Not required yet!","");
                Toast.makeText(ExpenseDetailActivity.this,"Successfully Re-Submitted",Toast.LENGTH_SHORT).show();

            }
        });
        txtMeeting.setText(getString(R.string.meeting) + expensemodel.getDescreption());
        txtCreatedOn.setText(getString(R.string.expense_created_on) + expensemodel.getCreated_on());
        txtCustomerName.setText(getString(R.string.customer_name) + expensemodel.getCustomer_name());
        txtAddress.setText(getString(R.string.address) + expensemodel.getAddress());
        txtAdvance.setText(getString(R.string.amount) + expensemodel.getAmount());
        txtExpenseType.setText(getString(R.string.expense_type) + expensemodel.getExpense_type());
        txtMeetingDate.setText(getString(R.string.meeting_date) + expensemodel.getDate() + ", " + expensemodel.getTime());
        txtPurpose.setText(getString(R.string.purpose) + expensemodel.getComment());
        txt_status.setText(getString(R.string.status) + expensemodel.getFinal_status());
        model = sh.getLoginModel(getString(R.string.login_model));

        image_uploaded.setOnTouchListener(new ImageMatrixTouchHandler(ExpenseDetailActivity.this));
        if(expensemodel.getStatus().equals("REJECT")||expensemodel.getStatus().equals("RESUBMIT"))
        {
            re_submit.setVisibility(View.VISIBLE);
            txt_cancel.setVisibility(View.VISIBLE);
            action.setVisibility(View.GONE);
        }
        else if (expensemodel.getStatus().equals("PENDING")){
            action.setVisibility(View.GONE);
        }
        if (expensemodel.getFinal_status().equals("PAID")||expensemodel.getFinal_status().equals("CANCEL")){
            re_submit.setVisibility(View.GONE);
            txt_cancel.setVisibility(View.GONE);
            action.setVisibility(View.GONE);
        }
        if(model.getExpense_request_approval().equals("NO")||model.getPre_request_approval().equals("NO")){
            txt_manager_status.setVisibility(View.GONE);
        }
        if(expensemodel.getRejection_message()==null){
            txt_rejection_title.setVisibility(View.GONE);
            txt_rejection_message.setVisibility(View.GONE);

        }
        else if(expensemodel.getRejection_message().equals("")){
            txt_rejection_message.setText("No Message");
        }
        else{
            txt_rejection_message.setText(expensemodel.getRejection_message());
        }

        if(expensemodel.getFinal_status().equals("CANCELED"))
        {
            re_submit.setVisibility(View.GONE);
            txt_cancel.setVisibility(View.GONE);
            action.setVisibility(View.GONE);
            String status= "<font color=#5fb0c9>" +getString(R.string.status) + "</font>"+"<font color=#D50000>" +expensemodel.getFinal_status() + "</font>";
            txt_status.setText( Html.fromHtml(status) );
            txt_status.startAnimation(animBlink);
        }

        if(expensemodel.getStatus() != null && !expensemodel.getStatus().equals("")) {

            switch (expensemodel.getStatus()) {
                case "PENDING":
//Pending
                    String status= "<font color=#5fb0c9>" +getString(R.string.manager_approval) + "</font>"+"<font color=#FDD835>" +expensemodel.getStatus() + "</font>";
                    txt_manager_status.setText( Html.fromHtml(status) );
                    txt_manager_status.startAnimation(animBlink);
                    break;

                case "ACCEPT":
//Approved


                    String status1= "<font color=#5fb0c9>" +getString(R.string.manager_approval) + "</font>"+"<font color=#00C853>" +expensemodel.getStatus() + "</font>";

                    txt_manager_status.setText( Html.fromHtml(status1) );
                    txt_manager_status.startAnimation(animBlink);
                    break;



                case "REJECT":
//Rejected

                    String status2= "<font color=#5fb0c9>" +getString(R.string.manager_approval) + "</font>"+"<font color=#D50000>" +expensemodel.getStatus() + "</font>";
                    txt_manager_status.setText( Html.fromHtml(status2) );
                    txt_manager_status.startAnimation(animBlink);
                    break;

                case "RESUBMIT":
//Rejected

                    String status3= "<font color=#5fb0c9>" +getString(R.string.manager_approval) + "</font>"+"<font color=#EF6C00>" +expensemodel.getStatus() + "</font>";
                    txt_manager_status.setText( Html.fromHtml(status3) );
                    txt_manager_status.startAnimation(animBlink);
                    break;


            }
        }
        if(expensemodel.getFinal_status() != null && !expensemodel.getFinal_status().equals("")) {

            switch (expensemodel.getFinal_status()) {
                case "PENDING":
//Pending
                    String status= "<font color=#5fb0c9>" +getString(R.string.status) + "</font>"+"<font color=#FDD835>" +expensemodel.getFinal_status() + "</font>";
                    txt_status.setText( Html.fromHtml(status) );
                    txt_status.startAnimation(animBlink);
                    break;

                case "ACCEPT":
//Approved
                    String status1= "<font color=#5fb0c9>" +getString(R.string.status) + "</font>"+"<font color=#00C853>" +expensemodel.getFinal_status() + "</font>";
                    txt_status.setText( Html.fromHtml(status1) );
                    txt_status.startAnimation(animBlink);
                    break;



                case "REJECT":
//Rejected
                    String status2= "<font color=#5fb0c9>" +getString(R.string.status) + "</font>"+"<font color=#D50000>" +expensemodel.getFinal_status() + "</font>";
                    txt_status.setText( Html.fromHtml(status2) );
                    txt_status.startAnimation(animBlink);
                    break;


                case "PROCESSED":
//Rejected
                    String status3= "<font color=#5fb0c9>" +getString(R.string.status) + "</font>"+"<font color=#FDD835>" +expensemodel.getFinal_status() + "</font>";
                    txt_status.setText( Html.fromHtml(status3) );
                    txt_status.startAnimation(animBlink);
                    break;

                case "PAID":
//Rejected
                    String status4= "<font color=#5fb0c9>" +getString(R.string.status) + "</font>"+"<font color=#0277BD>" +expensemodel.getFinal_status() + "</font>";
                    txt_status.setText( Html.fromHtml(status4) );
                    txt_status.startAnimation(animBlink);
                    break;


            }
        }

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ExpenseDetailActivity.this, ExpenseStatusActivity.class);
                intent.putExtra(getString(R.string.expense_model), expensemodel);
                startActivity(intent);
            }
        });
        re_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(ExpenseDetailActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.activity_rejection_submit_popup, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(ExpenseDetailActivity.this);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.edt_message);
                final EditText edt_rejection_message = (EditText) mView.findViewById(R.id.edt_rejection_message);
                final EditText edt_amount = (EditText) mView.findViewById(R.id.edt_amount);
                edt_rejection_message.setText(expensemodel.getRejection_message());
                edt_amount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        edt_amount.removeTextChangedListener(this);

                        try {
                            String givenstring = editable.toString();
                            Long longval;
                            if (givenstring.contains(",")) {
                                givenstring = givenstring.replaceAll(",", "");
                            }
                            longval = Long.parseLong(givenstring);
                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String formattedString = formatter.format(longval);
                            edt_amount.setText(formattedString);
                            edt_amount.setSelection(edt_amount.getText().length());
                            // to place the cursor at the end of text
                        } catch (NumberFormatException nfe) {
                            nfe.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        edt_amount.addTextChangedListener(this);

                    }


                });
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Re-Submit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                String message=userInputDialogEditText.getText().toString();
                                String amount=expensemodel.getAmount().split(" ")[0];
                                if (message.equals("")){
                                    Toast.makeText(ExpenseDetailActivity.this,"Enter Something..", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    if((edt_amount.getText()+"").length()>0)
                                        amount=edt_amount.getText()+"";
                                    postReSubmitStatus(message,amount);
                                    Toast.makeText(ExpenseDetailActivity.this,"Successfully Re-Submitted",Toast.LENGTH_SHORT).show();
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
                buttonPositive.setTextColor(ContextCompat.getColor(ExpenseDetailActivity.this, R.color.them_color));
                Button buttonNegative = alertDialogAndroid.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(ExpenseDetailActivity.this, R.color.them_color));
            }
        });
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

        sb = new SpannableStringBuilder( txtPurpose.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtPurpose.setText(sb);

       /* sb = new SpannableStringBuilder( txt_manager_status.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txt_manager_status.setText(sb);*/

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

    Uri fileUri;

    private void selectImage() {
        final CharSequence[] options = {getString(R.string.take_photo), getString(R.string.choose_from_gallery), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseDetailActivity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getString(R.string.take_photo))) {
                    String fileName = System.currentTimeMillis() + ".jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (options[item].equals(getString(R.string.choose_from_gallery))) {
                    openGallery();
                } else if (options[item].equals(getString(R.string.cancel))) {
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

    String imageImagePath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                imageImagePath = getPath(fileUri);
                File file=new File(imageImagePath);
                resize(file,"");
                Picasso.get().load(file).placeholder(R.drawable.ic_userlogin).resize(100,100).into(image_uploaded);
                updateExpenseReceipt(imageImagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    imageImagePath = getPath(selectedImage);
                    File file=new File(imageImagePath);
                    resize(file,"");
                    Picasso.get().load(file).placeholder(R.drawable.ic_userlogin).resize(100,100).into(image_uploaded);
                    updateExpenseReceipt(imageImagePath);
                }
            }
        }
    }

    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.expensedetails));
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 72;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;

        int scale = 1;

        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;

            height_tmp /= 2;

            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();

        o2.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                .openInputStream(selectedImage), null, o2);

        return bitmap;
    }

    @SuppressWarnings("deprecation")
    private String getPath(Uri selectedImaeUri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = managedQuery(selectedImaeUri, projection, null, null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            return cursor.getString(columnIndex);
        }

        return selectedImaeUri.getPath();
    }

    public void updateExpenseReceipt(String fileUrl) {
        RequestBody imgFile = null;
        File imagPh = new File(fileUrl);
        Log.e("***********", "*************" + imagPh.getAbsolutePath());
        if (imagPh != null)
            imgFile = RequestBody.create(MediaType.parse("image/*"), imagPh);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), expensemodel.getId());
        Singleton.getInstance().getApi().updateExpanseReceipt(requestId, imgFile).enqueue(new Callback<UploadImageResMeta>() {
            @Override
            public void onResponse(Call<UploadImageResMeta> call, Response<UploadImageResMeta> response) {

                UploadImageModel model= response.body().getData();
                if(model!=null && model.getImage()!=null)
                    Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_bill).resize(100,100).into(image_uploaded);
                else
                    Toast.makeText(ExpenseDetailActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UploadImageResMeta> call, Throwable t) {

            }
        });
    }


    BitmapFactory.Options bmOptions;
    Bitmap bitmap;
    public void resize(File file, String benchMark) {
        try {
            bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            bmOptions.inDither = true;
            bitmap = BitmapFactory.decodeFile(imageImagePath, bmOptions);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Log.e("width & Height", "width " + bitmap.getWidth());
            if (bitmap.getWidth() > 1200) {
                w = bitmap.getWidth() * 30 / 100;
                h = bitmap.getHeight() * 30 / 100;
            }

            Log.e("width & Height", "width " + w + " height " + h);
            bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);
            try {
                Log.e("Compressing", "Compressing");
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (Exception e) {
                Log.e("Exception", "Image Resizing" + e.getMessage());
            }
        } catch (
                Exception e
                ) {
            Log.e("Exception", "Exception in resizing image");
        }
    }
    private void postReSubmitStatus(String msg,String amount)
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().postReSubmit(model.getId(),expensemodel
                .getId(),msg,amount,expensemodel.getFinal_status()).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {

            }

            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t) {

            }
        });
    }

}