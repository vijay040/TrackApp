package com.mmcs.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.ReportModel;

import java.util.ArrayList;

/**
 * Created by Lenovo on 01-06-2018.
 */

public class ReportAdapter extends BaseAdapter {
    public ArrayList<ReportModel> list;
    public Activity context;
    public ReportAdapter(Activity context, ArrayList<ReportModel> list) {
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
    public void filter(ArrayList<ReportModel>newList)
    {
        list=new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_expensereport_item, null);
        }
        TextView txtDescreption=view.findViewById(R.id.txtTitle);
        TextView txtTotalAmount=view.findViewById(R.id.txtTotalAmount);
        TextView txtDate=view.findViewById(R.id.txtDate);
        TextView txtStatus=view.findViewById(R.id.txtStatus);

        txtDescreption.setText(list.get(i).getDescription());
        txtTotalAmount.setText(list.get(i).getTotal_amount());
        txtDate.setText("Date:"+list.get(i).getCreated_on());
        txtStatus.setText("Status:"+list.get(i).getStatus());

        return view;
    }
}
