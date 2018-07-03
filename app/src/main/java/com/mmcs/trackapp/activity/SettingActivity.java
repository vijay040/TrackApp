package com.mmcs.trackapp.activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.CurrencyAdaptor;
import com.mmcs.trackapp.model.CurrencyModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.ResMetaCurrency;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SettingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
EditText edt_txt_first_name,edt_txt_last_name,edt_txt_email_id,edt_txt_role,edt_txt_manager,edt_txt_numberformate,edt_txt_position,edt_txt_joiningdate ,edt_txt_department,edt_txt_home_address,edt_txt_conf_personal_number,edt_txt_currency,edt_txt_dateformate,edt_txt_language;
    Shprefrences sh;
    TextView edt_txt_password_professional,text_edit;
    ArrayList<CurrencyModel> currencyList = new ArrayList<>();
    LoginModel model;
    ImageView imgProfile;
    private static final int SELECT_PHOTO = 200;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //getSupportActionBar().setTitle("Settings");
        sh = new Shprefrences(this);
        edt_txt_first_name=findViewById(R.id.edt_txt_first_name);
        edt_txt_last_name=findViewById(R.id.edt_txt_last_name);
        edt_txt_email_id=findViewById(R.id.edt_txt_email_id);
        edt_txt_conf_personal_number=findViewById(R.id.edt_txt_conf_personal_number);
        edt_txt_password_professional=findViewById(R.id.edt_txt_password_professional);
        edt_txt_home_address=findViewById(R.id.edt_txt_home_address);
        edt_txt_position=findViewById(R.id.edt_txt_position);
        edt_txt_manager=findViewById(R.id.edt_txt_manager);
        edt_txt_role=findViewById(R.id.edt_txt_role) ;
        edt_txt_numberformate=findViewById(R.id.edt_txt_numberformate);
        edt_txt_department=findViewById(R.id.edt_txt_department);
        edt_txt_joiningdate=findViewById(R.id.edt_txt_joiningdate);
        edt_txt_currency=findViewById(R.id.edt_txt_currency);
        edt_txt_dateformate=findViewById(R.id.edt_txt_dateformate);
        edt_txt_language=findViewById(R.id.edt_txt_language);
        imgProfile=findViewById(R.id.imgProfile);
        text_edit=findViewById(R.id.text_edit);
        text_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

         model = sh.getLoginModel("LOGIN_MODEL");
        edt_txt_first_name.setText(model.getDisplay_name()+"");
        edt_txt_last_name.setText(model.getUser_name()+"");
        edt_txt_email_id.setText(model.getEmail()+"");
        edt_txt_home_address.setText("Noida, India");
        edt_txt_manager.setText(model.getReporting_person()+"");
        edt_txt_role.setText(model.getRole()+"");
        edt_txt_department.setText(model.getDepartment_name()+"");
        edt_txt_joiningdate.setText(model.getJoining_date()+"");
        edt_txt_conf_personal_number.setText(model.getMobile_number()+"");
        edt_txt_password_professional.setText(model.getPassword()+"");

        getCurrencyList();
        back();
        setTitle();
        edt_txt_dateformate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                showDateFormate();
                }
                });
        edt_txt_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguage();
                }
                });
        edt_txt_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrencyList();
               }
               });
               }
    private void getCurrencyList() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getCurrencyList(model.getUser_id()).enqueue(new Callback<ResMetaCurrency>() {
            @Override
            public void onResponse(Call<ResMetaCurrency> call, Response<ResMetaCurrency> response) {
                currencyList = response.body().getResponse();
            }
            @Override
            public void onFailure(Call<ResMetaCurrency> call, Throwable t) {
            }
            });
            }
    android.app.AlertDialog alertDialog;
    CurrencyAdaptor currencyAdaptor;
    private void showCurrencyList() {
        currencyAdaptor = new CurrencyAdaptor(SettingActivity.this, currencyList);
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        TextView title = dialogView.findViewById(R.id.title);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        title.setText("Select Currency");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(currencyAdaptor);
        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CurrencyModel obj = (CurrencyModel) listPurpose.getAdapter().getItem(position);
                edt_txt_currency.setText(obj.getCurrency_name());
                alertDialog.dismiss();
                }
                });
                }
    private void showLanguage(){
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activity_language, null);
        final ListView listlanguage = dialogView.findViewById(R.id.listview);
        String[] Language = new String[] { "Indonesian","English","Thai","Vietenam"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Language);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listlanguage.setAdapter(adapter);
        listlanguage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  itemValue= (String) listlanguage.getItemAtPosition(position);
                edt_txt_language.setText(itemValue);
                alertDialog.dismiss();
                }
                });
                }
    private void showDateFormate(){
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activity_language, null);
        final ListView listDate = dialogView.findViewById(R.id.listview);
        String[] Date = new String[] { "DD/MM/YY","MM/DD/YY","YY/MM/DD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Date);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listDate.setAdapter(adapter);
        listDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String  itemValue    = (String) listDate.getItemAtPosition(position);
                edt_txt_dateformate.setText(itemValue);
                alertDialog.dismiss();
                }
                });
                }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
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
    public boolean onQueryTextChange(String s) {
        s=s.toLowerCase();
        ArrayList<CurrencyModel> newlist=new ArrayList<>();
        for(CurrencyModel list:currencyList)
               {
            String getCurrency = list.getCurrency_name().toLowerCase();
            if(getCurrency.contains(s)){
                newlist.add(list);
               }
               }
        currencyAdaptor.filter(newlist);
        return true;
               }
    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
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

  //  String imageImagePath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgProfile.setImageBitmap(photo);
           // imageImagePath = data.getData().getPath();
           // image_path.setText(imageImagePath);
        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    imgProfile.setImageURI(selectedImage);
                 //   imageImagePath = selectedImage.getPath();
                  //  image_path.setText(imageImagePath);
                }
            }
        }
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText("Settings");
    }

}
