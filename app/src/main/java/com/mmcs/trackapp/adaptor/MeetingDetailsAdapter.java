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
import com.mmcs.trackapp.model.MeetingModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 01-06-2018.
 */

public class MeetingDetailsAdapter extends BaseAdapter {
    public ArrayList<MeetingModel> list;
    public Activity context;
    public MeetingDetailsAdapter(Activity context,ArrayList<MeetingModel> list) {
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
        txtPurpose.setText(context.getString(R.string.purpose)+list.get(i).getPurpose());
        txtCustomer.setText(context.getString(R.string.client)+list.get(i).getCustomer_name());
        txtAgenda.setText(context.getString(R.string.agenda)+list.get(i).getAgenda());
        txtDate.setText(context.getString(R.string.date)+list.get(i).getDate());
        txtTime.setText(context.getString(R.string.time)+list.get(i).getTime());
        txtContactPerson.setText(context.getString(R.string.con_person)+list.get(i).getContact_person());
        txtAddress.setText(context.getString(R.string.address)+list.get(i).getAddress());
        return view;
    }
}
