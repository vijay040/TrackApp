package com.example.lenovo.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.activity.CteateMeetingActivity;
import com.example.lenovo.trackapp.model.MeetingsModel;
import com.example.lenovo.trackapp.model.PurposeModel;

import java.util.ArrayList;

public class PurposePopupAdaptor extends BaseAdapter {
    public ArrayList<PurposeModel> list;
    public Activity context;

    public PurposePopupAdaptor(Activity context, ArrayList<PurposeModel> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.inf_purpose_popup_list, null);


        }

        TextView txtTitle = view.findViewById(R.id.txtTitle);

        txtTitle.setText(list.get(i).getPurpose());

        return view;
    }
}
