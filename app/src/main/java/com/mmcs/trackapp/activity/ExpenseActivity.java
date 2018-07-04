package com.mmcs.trackapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseActivity extends AppCompatActivity {

    Button receipt, expenses;
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;

    Button btn_receipt, btn_exp;
TextView txtPreRequest,txtExpanse,txtExpenseReport,txtApprovals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        btn_receipt = findViewById(R.id.btn_receipt);
        btn_exp = findViewById(R.id.btn_exp);
        txtPreRequest=findViewById(R.id.txtPreRequest);
        txtExpanse=findViewById(R.id.txtExpense);
        txtExpenseReport=findViewById(R.id.txtExpenseReport);
        txtApprovals=findViewById(R.id.txtApprovals);
        back();
        setTitle();
        //getSupportActionBar().setTitle("Expenses");
        txtApprovals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ExpenseActivity.this,ApprovalActivity.class);
                startActivity(intent);
            }
        });

        txtExpenseReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseActivity.this,ExpenseReportActivity.class));
            }
        });
        txtExpanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ExpenseActivity.this,ExpenseListActivity.class);
                startActivity(intent);
            }
        });

        btn_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        btn_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseActivity.this, NewExpenseActivity.class);
                startActivity(intent);
            }
        });

        txtPreRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseActivity.this, PreRequestActivity.class));
            }
        });



        /*preexpense=(ImageView) findViewById(R.id.imz_prerequest);
        expense=(ImageView)findViewById(R.id.imz_expense);
        expensereport=(ImageView) findViewById(R.id.imz_expensereport);
        approval=(ImageView)findViewById(R.id.imz_expenseappro) ;
        receipt=(Button)findViewById(R.id.btn_receipt) ;
        expenses=(Button)findViewById(R.id.btn_exp) ;
        getSupportActionBar().setTitle("");*/



        /* getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setLogo(R.drawable.logo1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));*/
/*
        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
      expenses.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       Intent intent=new Intent(ExpenseActivity.this,NewExpenseActivity.class);
       startActivity(intent);
    }
});
preexpense.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(ExpenseActivity.this,"this is pre request",Toast.LENGTH_SHORT).show();
    }
});
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExpenseActivity.this,"this is  expense",Toast.LENGTH_SHORT).show();
            }
        });
        expensereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExpenseActivity.this,"this is expense report",Toast.LENGTH_SHORT).show();
            }
        });
        approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExpenseActivity.this,"this is expense approval",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void selectImage() {
        final CharSequence[] options = { "Receipt Store", "New ExpenseActivity","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseActivity.this);

        builder.setTitle( Html.fromHtml("<font color='#E8ADAA'>Choose Your Destination!</font>"));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public Uri file;
            @Override

            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Receipt Store"))

                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file = Uri.fromFile(getOutputMediaFile());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                    startActivity(intent);

                }
                else if (options[item].equals("New ExpenseActivity"))

                {
                    Intent intent = new   Intent(ExpenseActivity.this,NewExpenseActivity.class);
                    startActivity(intent);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                    Toast.makeText(ExpenseActivity.this,"Cancelled",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.expenseslist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Receipt Store");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".png");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int	itemid=item.getItemId();
        if(itemid==R.id.nav_prerequest){

            Toast.makeText(ExpenseActivity.this,"this is pre request",Toast.LENGTH_SHORT).show();
        }
        if(itemid==R.id.nav_expensereport){
            Toast.makeText(ExpenseActivity.this,"this is expense report",Toast.LENGTH_SHORT).show();

        }

        else if(itemid==R.id.nav_expense){
            Toast.makeText(ExpenseActivity.this, "this is expense ",Toast.LENGTH_LONG).show();


        }
        else if(itemid==R.id.nav_approval){
            Toast.makeText(ExpenseActivity.this, "this is expense",Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);

    }*/


    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    Uri photoURI;
    File file;

    private void dispatchTakePictureIntent() {
       /* if (loadingDialog != null && loadingDialog.isShowing() == false)
            loadingDialog.show();*/
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/BTracker");

        myDir.mkdirs();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String fileName = dateFormat.format(new Date()) + ".png";
        file = new File(myDir, fileName);

        photoURI = Uri.fromFile(file);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void resize(File file, String benchMark) {
        try {
            bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            bmOptions.inDither = true;
            bitmap = BitmapFactory.decodeFile(imagepath, bmOptions);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Log.e("width & Height", "width " + bitmap.getWidth());
            if (bitmap.getWidth() > 1200) {
                w = bitmap.getWidth() * 20 / 100;
                h = bitmap.getHeight() * 20 / 100;
            }

            Log.e("width & Height", "width " + w + " height " + h);
            bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);

            Canvas c = new Canvas(bitmap);
            Paint p = new Paint();
            p.setColor(Color.BLUE);
            p.setStyle(Paint.Style.FILL);
            p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            // paint.setColor(Color.BLACK);
            p.setTextSize(30);
            c.drawText(benchMark, 20, 40, p);

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

    String imagepath;
    BitmapFactory.Options bmOptions;
    Bitmap bitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (photoURI == null) {
                Toast.makeText(this, "Image is not Captured", Toast.LENGTH_SHORT).show();
                return;
            }
            imagepath = photoURI.getPath();
            if (imagepath == null || imagepath == "") {
                Toast.makeText(this, "Image is not Captured!", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.e("imagepath", "imagepath**********" + imagepath);

            DateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy HH:mm:ss");//"EEE, MMM d, yyyy HH:mm:ss"
            Date date = new Date();

            Log.e("getMonth(", "Month name is ********" + dateFormat.format(date));
            resize(file, dateFormat.format(date));
            NewExpenseActivity.imgUrl = "file:///"+imagepath;

            //imgView.setImageBitmap(bitmap);
        }
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
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getText(R.string.Expenses));
    }


}

