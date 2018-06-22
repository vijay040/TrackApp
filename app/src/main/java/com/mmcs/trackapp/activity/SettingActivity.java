package com.mmcs.trackapp.activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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
EditText edt_txt_currency;
    Shprefrences sh;
    ArrayList<CurrencyModel> currencyList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle("Settings");
        sh = new Shprefrences(this);
        edt_txt_currency=findViewById(R.id.edt_txt_currency);
        getCurrencyList();
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
    CurrencyAdaptor currencyAdaptor;
    android.app.AlertDialog alertDialog;
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
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CurrencyModel obj = (CurrencyModel) listPurpose.getAdapter().getItem(position);
                edt_txt_currency.setText(obj.getCurrency_name());
                alertDialog.dismiss();
            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
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
