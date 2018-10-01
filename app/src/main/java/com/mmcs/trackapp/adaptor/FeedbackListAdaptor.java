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
import com.mmcs.trackapp.model.FeedbackModel;
import com.mmcs.trackapp.model.PreRequestModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedbackListAdaptor extends BaseAdapter {
    public ArrayList<FeedbackModel> list;
    public Activity context;

    public FeedbackListAdaptor(Activity context, ArrayList<FeedbackModel> list) {
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
            view = inflater.inflate(R.layout.inf_feedbacklist_item, null);
            }

        TextView txtTitle = view.findViewById(R.id.txtTitle);
        txtTitle.setText(list.get(i).getCreated_on());

        TextView txtDate = view.findViewById(R.id.txtFeedback);
        txtDate.setText(list.get(i).getFeedback());
        TextView txt_Customer_Name=view.findViewById(R.id.txt_Customer_Name);
        txt_Customer_Name.setText(context.getString(R.string.Customer_Name)+list.get(i).getCustomer_id());

        ImageView img = view.findViewById(R.id.img);
        if(list.get(i).getImage().equalsIgnoreCase(""))
            Picasso.get().load( "http://intellisysglobal.com/web/").placeholder(R.drawable.feedback).resize(50,50).into(img);
            else
        Picasso.get().load(list.get(i).getImage()).placeholder(R.drawable.feedback).resize(50,50).into(img);
        return view;
    }
}
