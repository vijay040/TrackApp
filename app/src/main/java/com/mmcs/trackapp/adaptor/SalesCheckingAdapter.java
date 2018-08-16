package com.mmcs.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.SalesCheckingModel;

import java.util.ArrayList;

/**
 * Created by Lenovo on 16-08-2018.
 */

public class SalesCheckingAdapter extends BaseAdapter {
    public ArrayList<SalesCheckingModel> list;
    public Activity context;

    public SalesCheckingAdapter(Activity context, ArrayList<SalesCheckingModel> list) {
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
            view = inflater.inflate(R.layout.activity_sales_check_item, null);
        }
        TextView txt_vendor_name = view.findViewById(R.id.txt_vendor_name);
        txt_vendor_name.setText("Vendor Name:"+list.get(i).getVendor_name());
        TextView txtDate = view.findViewById(R.id.txtDate);
        txtDate.setText("Date:"+list.get(i).getDate());
        TextView txt_valid_date = view.findViewById(R.id.txt_valid_date);
        txt_valid_date.setText("Valid Till:"+list.get(i).getValid_till());
        TextView  txt_cost = view.findViewById(R.id.txt_cost);
        txt_cost.setText("Cost:"+list.get(i).getCost());
        TextView  txt_margin = view.findViewById(R.id.txt_margin);
        txt_margin.setText("Margin:"+list.get(i).getMargin());
        return view;
    }
}
