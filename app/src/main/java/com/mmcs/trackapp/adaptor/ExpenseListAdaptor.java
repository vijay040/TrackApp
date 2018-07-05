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
import com.mmcs.trackapp.model.ExpenseModel;
import java.util.ArrayList;

public class ExpenseListAdaptor extends BaseAdapter {
    public ArrayList<ExpenseModel> list;
    public Activity context;

    public ExpenseListAdaptor(Activity context, ArrayList<ExpenseModel> list) {
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
            view = inflater.inflate(R.layout.activity_expanselist_item, null);
        }
        TextView txtDescreption = view.findViewById(R.id.txtDescreption);
        ImageView imz_down=view.findViewById(R.id.imz_down);
        txtDescreption.setText(list.get(i).getDescreption ());

        TextView txtCreatedOn = view.findViewById(R.id.txtCreatedOn);
        txtCreatedOn.setText(context.getString(R.string.expense_created_on)+list.get(i).getCreated_on ());

        TextView txtCustomer = view.findViewById(R.id.txtCustomerName);
        txtCustomer.setText(context.getString(R.string.customer_name)+list.get(i).getCustomer_name());

        TextView txtAddress = view.findViewById(R.id.txtAddress);
       txtAddress.setText(context.getString(R.string.address)+list.get(i).getAddress ());

        TextView txtAdvanceMoney = view.findViewById(R.id.txtAdvanceMoney);
        txtAdvanceMoney.setText(list.get(i).getAmount());

        final TextView txtRequestType = view.findViewById(R.id.txtRequestType);
        txtRequestType.setText(context.getString(R.string.expense_type)+list.get(i).getExpense_type ());

        final TextView txtDate = view.findViewById(R.id.txtDate);
        txtDate.setText(context.getString(R.string.meeting_date)+list.get(i).getDate ()+", "+list.get(i).getTime ());

        final ImageView   hide=view.findViewById(R.id.imz_down);
        RelativeLayout    relativeLayout=view.findViewById(R.id.relativelayout);
        final RelativeLayout  lay=view.findViewById(R.id.lay);

        if(list.get(i).isVisible()) {
            lay.setVisibility(View.VISIBLE);
            hide.setImageResource( R.drawable.ic_up);
        }
        else {
            lay.setVisibility(View.GONE);
            hide.setImageResource( R.drawable.ic_down);
        }

        imz_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(i).isVisible= !list.get(i).isVisible;
                if(list.get(i).isVisible()) {
                    lay.setVisibility(View.VISIBLE);
                    hide.setImageResource(R.drawable.ic_up);
                }
                else {
                    lay.setVisibility(View.GONE);
                    hide.setImageResource( R.drawable.ic_down);
                }
            }
        });



        ImageView img = view.findViewById(R.id.img);
        return view;
    }
}
