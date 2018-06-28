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
            view = inflater.inflate(R.layout.activity_meetingdetails_item, null);
        }
        TextView txtDescreption=view.findViewById(R.id.txtdescreption);
        TextView txtPurpose=view.findViewById(R.id.txtpurpose);
        TextView txtCustomer=view.findViewById(R.id.txtcustomer);
        TextView txtAgenda=view.findViewById(R.id.txtagenda);
        TextView txtDate=view.findViewById(R.id.txtdate);
        TextView txtTime=view.findViewById(R.id.txttime);
        TextView txtContactPerson=view.findViewById(R.id.txtcontactperson);
        TextView txtAddress=view.findViewById(R.id.txtaddress);
        txtDescreption.setText(list.get(i).getAdvance());
        txtPurpose.setText("Purpose:"+list.get(i).getBalance_rct());
        txtCustomer.setText("Client:"+list.get(i).getTotal_amount());
        txtAgenda.setText("Agenda:"+list.get(i).getMeeting_id());
       /* txtDate.setText("Date:"+list.get(i).getDate());
        txtTime.setText("Time:"+list.get(i).getTime());
        txtContactPerson.setText("Con. Person:"+list.get(i).getContact_person());
        txtAddress.setText("Address: "+list.get(i).getAddress());*/
        return view;
    }
}
