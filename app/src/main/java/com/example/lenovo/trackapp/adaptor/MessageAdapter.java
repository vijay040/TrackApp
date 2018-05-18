package com.example.lenovo.trackapp.adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.Message;
import com.example.lenovo.trackapp.activity.SendMessageActivity;

import java.util.List;

/**
 * Created by Lenovo on 25-04-2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Message> messageList;

    //getting the context and product list with constructor
    public MessageAdapter(Context mCtx, List<Message> messageList) {
        this.mCtx = mCtx;
        this.messageList = messageList;
    }
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_message, null);
        return new MessageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        //getting the product of the specified position
    final Message product = messageList.get(position);
        //binding the data with the viewholder views
        holder.textViewName.setText(product.getName());
        holder.textViewmsg.setText(product.getMsg());
holder.newmessage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(mCtx,SendMessageActivity.class);
        mCtx.startActivity(intent);

    }
});
        holder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCtx);
        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.delete);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                String itemLabel = String.valueOf(messageList.get(position));
                // Remove the item on remove/button click
                messageList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,messageList.size());
                // Show the removed item label`enter code here`
                Toast.makeText(mCtx,"Removed : "+product.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(mCtx, "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
});
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName,textViewmsg;
        ImageView imageView;
        Button delete,newmessage;
        public MessageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview1);
            textViewmsg = itemView.findViewById(R.id.textview2);
            imageView = itemView.findViewById(R.id.imageView);
            delete=itemView.findViewById(R.id.btn_delete);
            newmessage=itemView.findViewById(R.id.btn_newmsg);
        }
    }
}