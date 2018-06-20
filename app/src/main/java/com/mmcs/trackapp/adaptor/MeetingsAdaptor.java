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
import com.mmcs.trackapp.model.CustomerModel;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.PurposeModel;

import java.util.ArrayList;

public class MeetingsAdaptor extends BaseAdapter {
    public ArrayList<MeetingModel> list;
    public Activity context;

    public MeetingsAdaptor(Activity context, ArrayList<MeetingModel> list) {
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
    public void filter(ArrayList<MeetingModel>newList)
    {
        list=new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.inf_purpose_popup_list, null);
        }
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        Log.e("Description",""+list.get(i).getDescreption());
        txtTitle.setText(list.get(i).getDescreption());
        return view;
    }
}
