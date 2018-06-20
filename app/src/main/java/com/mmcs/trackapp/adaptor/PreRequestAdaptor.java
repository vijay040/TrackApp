package com.mmcs.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.PreRequestModel;
import com.mmcs.trackapp.model.PurposeModel;

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

        ImageView img = view.findViewById(R.id.img);

        int status = 0;

        if (list.get(i).getStatus() != null && !list.get(i).getStatus().equals("")) {
            status = (Integer.parseInt(list.get(i).getStatus()));

            switch (status) {
                case 1:
//Pending
                    img.setBackground(ContextCompat.getDrawable(context, R.drawable.pending));
                    break;

                case 2:
//Approved
                    img.setBackground(ContextCompat.getDrawable(context, R.drawable.accept));
                    break;

                case 3:
//Rejected
                    img.setBackground(ContextCompat.getDrawable(context, R.drawable.rejected));
                    break;
            }
        }


        return view;
    }
}
