package com.example.lenovo.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.ExpenseModel;
import com.example.lenovo.trackapp.model.PreRequestModel;

import java.util.ArrayList;

public class ExpenseListAdaptor extends BaseAdapter {
    public ArrayList<ExpenseModel> list;
    public Activity context;

    public ExpenseListAdaptor(Activity context, ArrayList<ExpenseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(list!=null)
        return list.size();
        else return 0;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_expanselist_item, null);
        }
        TextView txtDescreption = view.findViewById(R.id.txtDescreption);
        txtDescreption.setText(list.get(i).getDescreption ());

        TextView txtCreatedOn = view.findViewById(R.id.txtCreatedOn);
        txtCreatedOn.setText("Expense Created On:"+list.get(i).getCreated_on ());

        TextView txtCustomer = view.findViewById(R.id.txtCustomerName);
        txtCustomer.setText("Customer Name:"+list.get(i).getCustomer_name());

        TextView txtAddress = view.findViewById(R.id.txtAddress);
       txtAddress.setText("Address:"+list.get(i).getAddress ());

        TextView txtAdvanceMoney = view.findViewById(R.id.txtAdvanceMoney);
        txtAdvanceMoney.setText(list.get(i).getAmount());

        TextView txtRequestType = view.findViewById(R.id.txtRequestType);
        txtRequestType.setText(""+list.get(i).getExpense_type ());

        TextView txtDate = view.findViewById(R.id.txtDate);
        txtDate.setText("Meeting Date:"+list.get(i).getDate ()+", "+list.get(i).getTime ());
/*
        TextView txtTime = view.findViewById(R.id.txtTime);
        txtTime.setText(list.get(i).getTime ());*/

        ImageView img = view.findViewById(R.id.img);
        return view;
    }
}
