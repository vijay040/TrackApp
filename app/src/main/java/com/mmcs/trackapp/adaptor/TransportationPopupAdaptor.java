package com.mmcs.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.TransportationModel;

import java.util.ArrayList;

public class TransportationPopupAdaptor extends BaseAdapter {
    public ArrayList<TransportationModel> list;
    public Activity context;

    public TransportationPopupAdaptor(Activity context, ArrayList<TransportationModel> list) {
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
             view = inflater.inflate(R.layout.inf_meetingpopup_list, null);

        }

        TextView txtTitle = view.findViewById(R.id.txtTitle);

        txtTitle.setText(list.get(i).getTitle());

        return view;
    }
}
