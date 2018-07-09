package com.mmcs.trackapp.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.CustomerPopupAdaptor;
import com.mmcs.trackapp.model.CustomerModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.model.ResMetaCustomer;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    EditText edtCustomer,comment;
    Button submit;
    Shprefrences sh;
    ProgressBar progressBar;
    Button attachement;
    ImageView imageView;
    TextView image_path;
    public static String imgUrl;
    private static final int SELECT_PHOTO = 200;
    private static final int CAMERA_REQUEST = 1888;
    final int MY_PERMISSIONS_REQUEST_WRITE=103;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh=new Shprefrences(this);
        setContentView(R.layout.activity_feedback);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE);
        }
        edtCustomer = findViewById(R.id.edtCustomer);
        comment=(EditText)findViewById(R.id.edt_comment);
        submit=(Button) findViewById(R.id.btnSubmit);
        progressBar=findViewById(R.id.progressbar);
        attachement = (Button) findViewById(R.id.btAttchment);
        imageView = (ImageView) findViewById(R.id.imageView);
        image_path = findViewById(R.id.image_path);
        if (imgUrl != null && !imgUrl.equalsIgnoreCase(""))
            Picasso.get().load(imgUrl).into(imageView);
        //getSupportActionBar().setTitle("Feedback");
        progressBar.setVisibility(View.VISIBLE);
        getCustomerList();
        back();
        setTitle();
        attachement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        edtCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showCustomerPopup();
            }
        });
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
             String customer_name=edtCustomer.getText().toString();
             String comm=comment.getText().toString();
             if(customer_name.equals("")){
                 Toast.makeText(FeedbackActivity.this,getString(R.string.select_customer),Toast.LENGTH_SHORT).show();
             }
             else if(comm.equals("")) {
                 Toast.makeText(FeedbackActivity.this, getString(R.string.enter_your_comment), Toast.LENGTH_SHORT).show();
             }
             else{
                 DateFormat df = new SimpleDateFormat(getString(R.string.date_formate));
                 final String createddate = df.format(Calendar.getInstance().getTime());
                 postFeedback(customerid,comm,createddate);
             }
            }
        });
    }
    ArrayList<CustomerModel> listCustomer;
    CustomerPopupAdaptor customerPopupAdaptor;
    android.app.AlertDialog alertDialog;
    String customerid;
    private void showCustomerPopup() {
        customerPopupAdaptor = new CustomerPopupAdaptor(FeedbackActivity.this, listCustomer);
         android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);

        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        title.setText(getString(R.string.select_customer));
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(customerPopupAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                CustomerModel obj = (CustomerModel) listPurpose.getAdapter().getItem(position);
                edtCustomer.setText(obj.getCustomer_name());
                customerid=obj.getId();
                alertDialog.dismiss();
            }
        });
    }
    public void getCustomerList() {
        Singleton.getInstance().getApi().getCustomerList("").enqueue(new Callback<ResMetaCustomer>() {
            @Override
            public void onResponse(Call<ResMetaCustomer> call, Response<ResMetaCustomer> response) {
                listCustomer = response.body().getResponse();
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResMetaCustomer> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
              }
        });
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
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();
        ArrayList<CustomerModel> newlist1 = new ArrayList<>();
                for (CustomerModel list : listCustomer) {
                    String getCustomer = list.getCustomer_name().toLowerCase();
                    if (getCustomer.contains(s)) {
                        newlist1.add(list);
                    }
                }
                customerPopupAdaptor.filter(newlist1);
        return false;
    }

    public void postFeedback(String customerId,String feedback, String posted_on)
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        progressBar.setVisibility(View.VISIBLE);
        Singleton.getInstance().getApi().postFeedback(model.getId(),customerId,feedback,posted_on).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {
                Toast.makeText(FeedbackActivity.this,getString(R.string.successfully_posted),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                finish();
                 }
            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t){
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.feedback));
    }
    Uri fileUri;
    private void selectImage() {
        final CharSequence[] options = {getString(R.string.take_photo), getString(R.string.choose_from_gallery),getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getString(R.string.take_photo))) {
                   /* Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);*/

                    String fileName = System.currentTimeMillis()+".jpg";
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

           /* Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            Log.e("************","Path "+data+ " "+data.getData());

            if(data.getData()!=null) {
                imageImagePath = data.getData().getPath();
                image_path.setText(imageImagePath);
            }*/

            try
            {
              String  photoPath = getPath(fileUri);

                System.out.println("Image Path : " + photoPath);
                image_path.setText(photoPath);
                Bitmap b = decodeUri(fileUri);
                imageView.setImageBitmap(b);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    imageView.setImageURI(selectedImage);
                    imageImagePath = selectedImage.getPath();
                    image_path.setText(imageImagePath);
                }
            }
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException
    {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 72;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;

        int scale = 1;

        while (true)
        {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
            {
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
    private String getPath(Uri selectedImaeUri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = managedQuery(selectedImaeUri, projection, null, null,
                null);

        if (cursor != null)
        {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            return cursor.getString(columnIndex);
        }

        return selectedImaeUri.getPath();
    }


}


