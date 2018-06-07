package com.example.lenovo.trackapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.lenovo.trackapp.R;
public class ExpenseReportActivity extends AppCompatActivity {
    ListView listExpenseReport;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report);
        getSupportActionBar().setTitle("Expense Reports");
        listExpenseReport=findViewById(R.id.listExpenseReport);
        progressBar=findViewById(R.id.progress);

        }

        }
