package com.mmcs.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.ExpenseModel;
import com.mmcs.trackapp.model.PreRequestModel;

import java.util.ArrayList;

public class PreRequestAdaptor extends BaseAdapter {
    public ArrayList<PreRequestModel> list;
    public Activity context;

    public PreRequestAdaptor(Activity context, ArrayList<PreRequestModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(list!=null)
        return list.size();
        else return 0;
    }
    public void filter(ArrayList<PreRequestModel> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
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
            view = inflater.inflate(R.layout.inf_prerequest_item, null);
            }

        TextView txtTitle = view.findViewById(R.id.txtTitle);
        txtTitle.setText(list.get(i).getComment());

        TextView txtDate = view.findViewById(R.id.txtDate);
        txtDate.setText(list.get(i).getDate());

        TextView txtCustomer = view.findViewById(R.id.txtCustomer);
        txtCustomer.setText(list.get(i).getCustomer_name());

        TextView txtAddress = view.findViewById(R.id.txtAddress);
        txtAddress.setText(list.get(i).getAddress());

        TextView txtAdvanceMoney = view.findViewById(R.id.txtAdvanceMoney);
        txtAdvanceMoney.setText(list.get(i).getAdvance());
        TextView txt_status=view.findViewById(R.id.txt_status);
        txt_status.setText(list.get(i).getStatus());

        ImageView img = view.findViewById(R.id.img);
        if(list.get(i).getStatus() != null && !list.get(i).getStatus().equals("")) {

            switch (list.get(i).getStatus()) {
                case "PENDING":
//Pending
                    img.setBackground(ContextCompat.getDrawable(context, R.drawable.pending));
                    break;

                case "APPROVED":
//Approved
                    img.setBackground(ContextCompat.getDrawable(context, R.drawable.approved));
                    break;

                case "REJECTED":
//Rejected
                    img.setBackground(ContextCompat.getDrawable(context, R.drawable.reject));
                    break;

                case "PROCESSED":
//Processed
                    img.setBackground(ContextCompat.getDrawable(context, R.drawable.star));
                    break;
            }
        }
        if(list.get(i).getStatus() != null && !list.get(i).getStatus().equals("")) {

            switch (list.get(i).getStatus()) {
                case "PENDING":
//Pending
                    txt_status.setTextColor(Color.parseColor("#FDD835"));
                    break;

                case "ACCEPT":
//Approved
                    txt_status.setTextColor(Color.parseColor("#00C853"));
                    break;

                case "REJECT":
//Rejected
                    txt_status.setTextColor(Color.parseColor("#D50000"));
                    break;

                case "PROCESSED":
//Processed
                    txt_status.setTextColor(Color.parseColor("#FDD835"));
                    break;
            }
        }


        return view;
    }
}
