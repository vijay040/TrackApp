package com.example.lenovo.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.CurrencyModel;
import com.example.lenovo.trackapp.model.DepartmentModel;
import com.example.lenovo.trackapp.model.MeetingModel;

import java.util.ArrayList;

public class CurrencyAdaptor extends BaseAdapter {
    public ArrayList<CurrencyModel> list;
    public Activity context;

    public CurrencyAdaptor(Activity context, ArrayList<CurrencyModel> list) {
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
    public void filter(ArrayList<CurrencyModel>newList1)
    {
        list=new ArrayList<>();
        list.addAll(newList1);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.inf_purpose_popup_list, null);


        }

        TextView txtTitle = view.findViewById(R.id.txtTitle);

        txtTitle.setText(list.get(i).getCurrency_name());

        return view;
    }
}
