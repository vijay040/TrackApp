package com.mmcs.trackapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.activity.AddCustomerActivity;
import com.mmcs.trackapp.activity.AttandanceActivity;
import com.mmcs.trackapp.activity.CreateMeetingActivity;
import com.mmcs.trackapp.activity.ExpenseActivity;
import com.mmcs.trackapp.activity.FeedbackListActivity;
import com.mmcs.trackapp.activity.MessageActivity;
import com.mmcs.trackapp.activity.MyScheduleActivity;
import com.mmcs.trackapp.activity.NewCustomerActivity;
import com.mmcs.trackapp.activity.PendingActivity;
import com.mmcs.trackapp.activity.SalesCheckingActivity;
import com.mmcs.trackapp.activity.SettingActivity;
import com.mmcs.trackapp.model.HomeItemModel;

import java.util.ArrayList;


public class HomeRecyclerAdaptor  extends RecyclerView.Adapter<HomeRecyclerAdaptor.ViewHolder> {

    ArrayList<HomeItemModel> list;
    Context context;
    private LayoutInflater mInflater;
    ImageView ic;
    TextView txtTitle;
    RelativeLayout layUser;
    public HomeRecyclerAdaptor(Context context, ArrayList<HomeItemModel> list)
    {
        this.list=list;
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home_inf, parent, false);
        ic=view.findViewById(R.id.ic);
        txtTitle=view.findViewById(R.id.txt_title);
        layUser=view.findViewById(R.id.layUser);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        txtTitle.setText(list.get(position).getTitle()+"");
        Log.e("list.get(i).getTitle()", "*****************" + list.get(position).getTitle());
        setIcons(list.get(position).getTitle(),ic);
        //imgUserProfile.setBackground(context.getResources().getDrawable(list.get(position).getImage()));
        layUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (list.get(position).getTitle()) {
                    case "Create Meeting":
                        Intent intent = new Intent(context, CreateMeetingActivity.class);
                        context.startActivity(intent);
                        break;

                    case "Messages":
                        Intent intent1 = new Intent(context, MessageActivity.class);
                        context.startActivity(intent1);
                        break;

                    case "My Schedule":
                        Intent intent2 = new Intent(context, MyScheduleActivity.class);
                        context.startActivity(intent2);
                        break;

                    case "Feedback":
                        Intent intent3 = new Intent(context, FeedbackListActivity.class);
                        context.startActivity(intent3);
                        break;

                    case "Attendance":
                        Intent intent4 = new Intent(context, AttandanceActivity.class);
                        context.startActivity(intent4);
                        break;

                    case "Clients":
                        Intent intent5 = new Intent(context, AddCustomerActivity.class);
                        context.startActivity(intent5);
                        break;

                    case "Settings":
                        Intent intent6 = new Intent(context, SettingActivity.class);
                        context.startActivity(intent6);
                        break;
                    case "Expense":
                        Intent intent7 = new Intent(context, ExpenseActivity.class);
                        context.startActivity(intent7);
                        break;
                    case "Pendings":

                        Intent intent8 = new Intent(context, PendingActivity.class);
                        context.startActivity(intent8);
                        break;

                    case "Sales Checking":
                        Intent intent9 = new Intent(context, SalesCheckingActivity.class);
                        context.startActivity(intent9);
                        break;
                    case "Logout":

                        break;

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);


        }
    }

    public void setIcons(String page, ImageView img) {
        switch (page) {
            case "Create Meeting":
                img.setBackground(context.getResources().getDrawable(R.drawable.ic_meeting));
                break;

            case "Messages":
                img.setBackground(context.getResources().getDrawable(R.drawable.msgicon));
                break;

            case "My Schedule":
                img.setBackground(context.getResources().getDrawable(R.drawable.schedule));
                break;

            case "Feedback":
                img.setBackground(context.getResources().getDrawable(R.drawable.ic_feedback));
                break;

            case "Attendance":
                img.setBackground(context.getResources().getDrawable(R.drawable.attaindance));
                break;

            case "Clients":
                img.setBackground(context.getResources().getDrawable(R.drawable.ic_customer));
                break;

            case "Settings":
                img.setBackground(context.getResources().getDrawable(R.drawable.ic_settings));
                break;
            case "Expense":
                img.setBackground(context.getResources().getDrawable(R.drawable.ic_expense));
                break;
            case "Pendings":

                img.setBackground(context.getResources().getDrawable(R.drawable.ic_notification));
                break;

            case "Sales Checking":
                img.setBackground(context.getResources().getDrawable(R.drawable.ic_sale));
                break;
            case "Logout":
                layUser.setVisibility(View.GONE);
                img.setBackground(context.getResources().getDrawable(R.drawable.ic_logout));
                break;

        }
    }
}
