package com.mmcs.trackapp.activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    TextView edt_txt_password_professional;
    ArrayList<CurrencyModel> currencyList = new ArrayList<>();
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
        getCurrencyList();
        back();
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
               }
