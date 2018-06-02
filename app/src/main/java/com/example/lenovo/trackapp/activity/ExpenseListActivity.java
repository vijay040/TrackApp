package com.example.lenovo.trackapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.lenovo.trackapp.R;

public class ExpenseListActivity extends AppCompatActivity {
    ListView listExpenseView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        listExpenseView=findViewById(R.id.listExpenseView);
        progressBar=findViewById(R.id.progress);
    }
}
