package com.example.lenovo.trackapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.trackapp.db.Category;
import com.example.lenovo.trackapp.R;

public class AddCategoryActivity extends AppCompatActivity {
    Button OK;
    ListView list;
    AutoCompleteTextView autocompleted;
    Category db = new Category(this);
    static String text;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        autocompleted = (AutoCompleteTextView) findViewById(R.id.autocompleted);
        OK = (Button) findViewById(R.id.btn_ok);

        getSupportActionBar().setTitle("Choose Category");


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstRun", true)) {
            db.saveData("Entertainment");
            db.saveData("Food");
            db.saveData("Phone");
            db.saveData("Transpotation");
            prefs.edit().putBoolean("firstRun", false).commit();
        }
        Cursor cursor = db.fetchData();
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            Toast.makeText(AddCategoryActivity.this, "there is no category show", Toast.LENGTH_LONG).show();
        } else {
            ArrayAdapter<String>adptr = new ArrayAdapter<String>(AddCategoryActivity.this, android.R.layout.simple_list_item_1);
            do {
                String CT = cursor.getString(cursor.getColumnIndex("category"));
                adptr.add(CT);
            } while (cursor.moveToNext());
            list = (ListView) findViewById(R.id.feedback_list);
            list.setAdapter(adptr);
            autocompleted.setAdapter(adptr);
            autocompleted.setThreshold(1);
            autocompleted.setTextColor(Color.BLUE);
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                String text = (String) list.getItemAtPosition(arg2);
                autocompleted.setText(text);
            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 text = autocompleted.getText().toString();
                // TODO Auto-generated method stub
              //  EditText etLocation = (EditText) findViewById(R.id.et_location);
                Intent intent = new Intent(AddCategoryActivity.this,NewExpenseActivity.class);
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("categoryitem",text);
                editor.apply();
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addcarte, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int itemid = item.getItemId();

        if (itemid == R.id.add_category) {
            Intent intent = new Intent(AddCategoryActivity.this, AddYourCategoryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

