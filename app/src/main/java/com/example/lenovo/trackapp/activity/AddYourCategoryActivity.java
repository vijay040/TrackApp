package com.example.lenovo.trackapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.trackapp.db.Category;
import com.example.lenovo.trackapp.R;

public class AddYourCategoryActivity extends AppCompatActivity {
    EditText addcate;
    Button save;
    Category db=new Category(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_category);  getSupportActionBar().setTitle("Add Category");
        save=(Button)findViewById(R.id.btn_save);
        addcate=(EditText)findViewById(R.id.edt_addcate);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String C=addcate.getText().toString();
                if(C.equals("")){
                    addcate.setError("Enter Category");
                    addcate.requestFocus();
                }



                else{
                    db.saveData(C);
                    Intent intent=new Intent(AddYourCategoryActivity.this,AddCategoryActivity.class);
                    Toast.makeText(AddYourCategoryActivity.this,"Your Category is successfully Added", Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }
            }
        });

    }



}
