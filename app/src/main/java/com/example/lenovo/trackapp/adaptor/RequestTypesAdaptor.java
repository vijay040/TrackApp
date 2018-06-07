package com.example.lenovo.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.PurposeModel;
import com.example.lenovo.trackapp.model.RequestTypeModel;

import java.util.ArrayList;

public class RequestTypesAdaptor extends BaseAdapter {
    public ArrayList<RequestTypeModel> list;
    public Activity context;

    public RequestTypesAdaptor(Activity context, ArrayList<RequestTypeModel> list) {
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

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.inf_request_types_list, null);
        }


        TextView txtTitle = view.findViewById(R.id.txtTitle);
        final ImageView chkSelect = view.findViewById(R.id.imgSelect);
        RelativeLayout lay=view.findViewById(R.id.lay);
        txtTitle.setText(list.get(i).getRequest_type());

        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list.get(i).isSelected())
                {
                    list.get(i).setSelected(false);
                    chkSelect.setVisibility(View.GONE);
                }
                else
                {
                    list.get(i).setSelected(true);
                    chkSelect.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }
}
