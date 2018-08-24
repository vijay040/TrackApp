package com.mmcs.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.CustomerModel;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_vendor_quo_item, null);
        }
        final  TextView quotation = view.findViewById(R.id.txt_quotation_no);
        quotation.setText(context.getString(R.string.v_quot_no)+list.get(i).getV_quot_no());

        final  TextView txt_vendor_name=view.findViewById(R.id.txt_vendor_name);
        txt_vendor_name.setText(context.getString(R.string.vendor_name)+list.get(i).getVendor_name());

        final TextView txt_valid_till=view.findViewById(R.id.txt_valid_till);
        txt_valid_till.setText(context.getString(R.string.valid_till)+list.get(i).getVaild_dt());

        final  TextView txt_container=view.findViewById(R.id.txt_container_size);
        txt_container.setText(context.getString(R.string.container_size)+list.get(i).getContainer_size());

        final  TextView txt_charge_amt=view.findViewById(R.id.txt_charge_amt);
        txt_charge_amt.setText(context.getString(R.string.crg_amt)+list.get(i).getCrg_amt());

        final   TextView txt_total_amt=view.findViewById(R.id.txt_total_amt);
        if(list.get(i).getAmount_in_idr()=="")
            list.get(i).setAmount_in_idr("0");
        if(list.get(i).getMargin()==null)
            list.get(i).setMargin("0");
        txt_total_amt.setText("Final Amount=amount("+Long.parseLong(list.get(i).getAmount_in_idr())+")"+"+"+"margin("+Long.parseLong(list.get(i).getMargin())+")="+(Long.parseLong(list.get(i).getAmount_in_idr())+(Long.parseLong(list.get(i).getMargin()))));

        final   TextView txt_margin=view.findViewById(R.id.txt_margin);
        txt_margin.setText(context.getString(R.string.margin)+list.get(i).getMargin());

        final   TextView txt_amnt_idr=view.findViewById(R.id.txt_amnt_idr);
        txt_amnt_idr.setText(context.getString(R.string.amt_in_idr)+list.get(i).getAmount_in_idr());


        final  ImageView hide=view.findViewById(R.id.imz_down);
        final  RelativeLayout relativeLayout=view.findViewById(R.id.relative);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(i).isVisible=! list.get(i).isVisible;
                if(list.get(i).isVisible) {
                    txt_charge_amt.setVisibility(view.VISIBLE);
                    txt_valid_till.setVisibility(view.VISIBLE);
                    hide.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_up));
                }
                else
                {
                    txt_charge_amt.setVisibility(view.GONE);
                    txt_valid_till.setVisibility(view.GONE);
                    hide.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_down));
                }

            }
        });
        return view;
    }
}
