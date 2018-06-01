package com.example.lenovo.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.MeetingModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Lenovo on 01-06-2018.
 */

public class MeetingDetailsAdapter extends BaseAdapter {
    public ArrayList<MeetingModel> list;
    public Activity context;

    public MeetingDetailsAdapter(Activity context, ArrayList<MeetingModel> list) {
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
        txtDescreption.setText(list.get(i).getDescreption());
        txtPurpose.setText(list.get(i).getPurpose());
        txtCustomer.setText(list.get(i).getCustomer_name());
        txtAgenda.setText(list.get(i).getAgenda());
        txtDate.setText(list.get(i).getDate());
        txtTime.setText(list.get(i).getTime());
        txtContactPerson.setText(list.get(i).getContact_person());
        txtAddress.setText(list.get(i).getAddress());
        return view;
    }
}
