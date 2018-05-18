package com.example.lenovo.trackapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

public class CurrencyListActivity extends AppCompatActivity {
   ListView listCurrency;
    Set<Currency> availableCurrenciesSet;
    List<Currency> availableCurrenciesList;
    ArrayAdapter<Currency> adapter;
    Button ok;
    EditText amount;
    TextView textView;
    static String totalamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);
        listCurrency = (ListView) findViewById(R.id.currencylist);
        amount=(EditText)findViewById(R.id.edt_amount);
        textView=(TextView)findViewById(R.id.textview);
        ok=(Button)findViewById(R.id.btn_ok);
        availableCurrenciesSet =
                Currency.getAvailableCurrencies();
        availableCurrenciesList = new ArrayList<Currency>(availableCurrenciesSet);
        adapter = new ArrayAdapter<Currency>(
                this,
                android.R.layout.simple_list_item_1,
                availableCurrenciesList);
        listCurrency.setAdapter(adapter);
        listCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Currency currency = (Currency) parent.getItemAtPosition(position);
                String currencyCode = currency.getCurrencyCode();
                String displayName = currency.getDisplayName();
                String symbol = currency.getSymbol();
                textView.setText(symbol);

                Toast.makeText(CurrencyListActivity.this,
                        displayName + "\n" +
                                currencyCode,
                        Toast.LENGTH_LONG).show();
            }});
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount1=amount.getText().toString();
                String symbol=textView.getText().toString();
                if(amount1.equals("")) {
                    amount.setError("EnterAmount");
                }
              else {
                    totalamount = amount1 + symbol;
                    Toast.makeText(CurrencyListActivity.this, totalamount, Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(CurrencyListActivity.this, NewExpenseActivity.class);
                    intent1.putExtra("amount",totalamount);
                    startActivity(intent1);
                }

            }
        });
    }
}
