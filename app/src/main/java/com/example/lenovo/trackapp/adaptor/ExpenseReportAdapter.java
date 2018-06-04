package com.example.lenovo.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.ExpenseReportModel;
import com.example.lenovo.trackapp.model.MeetingModel;

import java.util.ArrayList;

/**
 * Created by Lenovo on 04-06-2018.
 */

public class ExpenseReportAdapter extends BaseAdapter {
    public ArrayList<ExpenseReportModel> list;
    public Activity context;

    public ExpenseReportAdapter(Activity context, ArrayList<ExpenseReportModel> list) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_expensereport_item, null);
        }
            TextView txtTitle=view.findViewById(R.id.txtTitle);
            TextView txtTotalAmount=view.findViewById(R.id.txtTotalAmount);
            TextView date=view.findViewById(R.id.txtDate);
            TextView status=view.findViewById(R.id.txtStatus);
           return view;
    }
}
