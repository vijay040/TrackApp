package com.example.lenovo.trackapp.adaptor;

/**
 * Created by Ravi on 29/07/15.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.trackapp.model.CustomerDetails;
import com.example.lenovo.trackapp.R;

import java.util.ArrayList;
import java.util.List;


public class MessageDetailListAdapter extends RecyclerView.Adapter<MessageDetailListAdapter.MyViewHolder> {
    List<CustomerDetails> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Activity context;
    private ProgressDialog progressDialog;



    public MessageDetailListAdapter(Activity context, List<CustomerDetails> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait . . .");
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notification_detail_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    public void filter(ArrayList<CustomerDetails>newList)
    {
        data=new ArrayList<>();
        data.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CustomerDetails current = data.get(position);
        holder.name.setText("Customer Name: "+current.getName());
        holder.address.setText("Address: "+current.getAddress());
        holder.email.setText("Email: "+current.getEmail());
        holder.mobile.setText("Mobile: "+current.getPhone());
        holder.taxDetail.setText("Tax Details: "+current.getTaxDetail());
        holder.companyId.setText("Company Id: "+current.getCustomerId());

               /* if (isMessage)
                    if(current.getTotal_message()!=null)
                    holder.email.setText(current.getTotal_message() + "/2000");
                    else
                        holder.email.setText( "0/2000");
                else {
                        if(current.getAccount_bal()!=null)
                    holder.email.setText(current.getAccount_bal());
                    else
                            holder.email.setText("0");
                }
                holder.title.setText(current.getCreated_at() + " to " + current.getExpire_at());*/
    }
   

    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,email,mobile,taxDetail,companyId;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            email = (TextView) itemView.findViewById(R.id.email);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            taxDetail = (TextView) itemView.findViewById(R.id.tax_detail);
            companyId = (TextView) itemView.findViewById(R.id.company_id);

        }
    }
}




