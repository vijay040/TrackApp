package com.example.lenovo.trackapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.example.lenovo.trackapp.R;

public class ExpenseListActivity extends AppCompatActivity {
    ListView listExpenseView;
    ProgressBar progressBar;
    RelativeLayout txtAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        listExpenseView=findViewById(R.id.listExpenseView);
        progressBar=findViewById(R.id.progress);
        txtAdd = findViewById(R.id.txtAdd);
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ExpenseListActivity.this, NewExpenseActivity.class);
          startActivity(intent);
            }
        });

    }
}
