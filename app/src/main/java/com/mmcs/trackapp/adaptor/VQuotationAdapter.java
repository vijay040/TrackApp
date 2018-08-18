package com.mmcs.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.VQuotationModel;

import java.util.ArrayList;

/**
 * Created by Lenovo on 18-08-2018.
 */

public class VQuotationAdapter extends BaseAdapter {
    public ArrayList<VQuotationModel> list;
    public Activity context;

    public VQuotationAdapter(Activity context, ArrayList<VQuotationModel> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_vendor_quo_item, null);
        }
        TextView quotation = view.findViewById(R.id.txt_quotation_no);
        quotation.setText("Quotation No."+list.get(i).getV_quot_no());

        TextView exchange_rate=view.findViewById(R.id.txt_exchange_rate);
        exchange_rate.setText("Exchange Rate:"+list.get(i).getEx_rate());

        TextView margin_txt=view.findViewById(R.id.txt_margin);
        margin_txt.setText("Margin:"+list.get(i).getMargin());

        TextView txt_container=view.findViewById(R.id.txt_container_size);
        txt_container.setText("Container Size:"+list.get(i).getContainer_size());
        return view;
    }
}
