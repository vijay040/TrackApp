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
      final   TextView quotation = view.findViewById(R.id.txt_quotation_no);
        quotation.setText("Quotation No."+list.get(i).getV_quot_no());

        final  TextView txt_liner=view.findViewById(R.id.txt_liner);
        txt_liner.setText("Liner:"+list.get(i).getLiner_id());

        final TextView txt_valid_till=view.findViewById(R.id.txt_valid_till);
        txt_valid_till.setText("Valid Till:"+list.get(i).getVaild_dt());

        final  TextView txt_container=view.findViewById(R.id.txt_container_size);
        txt_container.setText("Container Size:"+list.get(i).getContainer_size());

        final  TextView txt_charge_amt=view.findViewById(R.id.txt_charge_amt);
        txt_charge_amt.setText("Charge Amount:"+list.get(i).getCrg_amt());

        final   TextView txt_custmr_amt=view.findViewById(R.id.txt_custmr_amt);
        txt_custmr_amt.setText("Customer Amount:"+list.get(i).getCustomer_amt());

        final  ImageView hide=view.findViewById(R.id.imz_down);
        final   RelativeLayout relativeLayout=view.findViewById(R.id.relative);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(i).isVisible=! list.get(i).isVisible;
                if(list.get(i).isVisible) {
                    txt_charge_amt.setVisibility(view.VISIBLE);
                    txt_custmr_amt.setVisibility(view.VISIBLE);
                    hide.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_up));
                }
                else
                {
                    txt_charge_amt.setVisibility(view.GONE);
                    txt_custmr_amt.setVisibility(view.GONE);
                    hide.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_down));
                }

            }
        });


        return view;
    }
}
