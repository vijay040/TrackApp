package com.mmcs.trackapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mmcs.trackapp.R;
public class ExpenseReportActivity extends AppCompatActivity {
    ListView listExpenseReport;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report);
        //getSupportActionBar().setTitle("Expense Reports");
        listExpenseReport=findViewById(R.id.listExpenseReport);
        progressBar=findViewById(R.id.progress);

        }

        }
