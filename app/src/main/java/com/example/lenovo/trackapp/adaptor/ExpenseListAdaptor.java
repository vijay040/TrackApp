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


        TextView txtTitle = view.findViewById(R.id.txtTitle);
       // txtTitle.setText(list.get(i).getDescription());

        TextView txtCreatedOn = view.findViewById(R.id.txtCreatedOn);
        txtCreatedOn.setText(list.get(i).getDate());

        //TextView txtCustomer = view.findViewById(R.id.txtCustomer);
       // txtCustomer.setText(list.get(i).getCustomer_name());

        TextView txtAddress = view.findViewById(R.id.txtAddress);
       // txtAddress.setText(list.get(i).getAddress());


        TextView txtAdvanceMoney = view.findViewById(R.id.txtAdvanceMoney);
        txtAdvanceMoney.setText(list.get(i).getAmount());

        ImageView img = view.findViewById(R.id.img);



        return view;
    }
}
