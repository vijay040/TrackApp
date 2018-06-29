package com.mmcs.trackapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.MessageModel;

import java.util.ArrayList;

/**
 * Created by Lenovo on 29-06-2018.
 */

public class MessageAdapter extends BaseAdapter {
    public ArrayList<MessageModel> list;
    public Activity context;

    public MessageAdapter(Activity context, ArrayList<MessageModel> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_messagelist_item, null);
        }
        TextView txtTitle = view.findViewById(R.id.txtTitle);
       TextView txtDate=view.findViewById(R.id.txtDate);
       TextView txtMessage=view.findViewById(R.id.txtMessage);

        txtTitle.setText(list.get(i).getUser_name());
        txtDate.setText(list.get(i).getCreated_on());
        txtMessage.setText(list.get(i).getText_msg());

        return view;
    }
}
