package com.mmcs.trackapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.activity.AddCustomerActivity;
import com.mmcs.trackapp.activity.AttandanceActivity;
import com.mmcs.trackapp.activity.CreateMeetingActivity;
import com.mmcs.trackapp.activity.DrawerActivity;
import com.mmcs.trackapp.activity.ExpenseActivity;
import com.mmcs.trackapp.activity.ExpenseApprovalActivity;
import com.mmcs.trackapp.activity.FeedbackListActivity;
import com.mmcs.trackapp.activity.LoginActivity;
import com.mmcs.trackapp.activity.MessageActivity;
import com.mmcs.trackapp.activity.MyScheduleActivity;
import com.mmcs.trackapp.activity.NewCustomerActivity;
import com.mmcs.trackapp.activity.PendingActivity;
import com.mmcs.trackapp.activity.SalesCheckingActivity;
import com.mmcs.trackapp.activity.SettingActivity;
import com.mmcs.trackapp.model.HomeItemModel;
import com.mmcs.trackapp.util.Shprefrences;

import java.util.ArrayList;

/**
 * Created by Lenovo on 08-09-2018.
 */

public class SideBarAdaptor extends BaseAdapter {
    ArrayList<HomeItemModel> list;
    Context ctx;
Shprefrences sh;
    public SideBarAdaptor(Context ctx, ArrayList<HomeItemModel> list) {
        this.ctx = ctx;
        this.list = list;
        sh=new Shprefrences(ctx);
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
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.menu_item_inf, null);
        }

        TextView title = view.findViewById(R.id.txtTitle);
        title.setText(list.get(i).getTitle() + "");

        ImageView icon = view.findViewById(R.id.ic);
        //icon.setBackground(ctx.getResources().getDrawable(list.get(i).getImage()));
        setIcons(list.get(i).getTitle(),icon);
        RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (list.get(i).getTitle()) {
                    case "Create Meeting":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent = new Intent(ctx, CreateMeetingActivity.class);
                            ctx.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;

                    case "Messages":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent =  new Intent(ctx, MessageActivity.class);
                            ctx.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;

                    case "My Schedule":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent2 = new Intent(ctx, MyScheduleActivity.class);
                            ctx.startActivity(intent2);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }

                        break;

                    case "Feedback":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent3 = new Intent(ctx, FeedbackListActivity.class);
                            ctx.startActivity(intent3);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;

                    case "Attendance":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent4 = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent4);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;

                    case "Clients":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent5 = new Intent(ctx, AddCustomerActivity.class);
                            ctx.startActivity(intent5);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;

                    case "Settings":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent6 = new Intent(ctx, SettingActivity.class);
                            ctx.startActivity(intent6);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;
                    case "Expense":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent7 = new Intent(ctx, ExpenseActivity.class);
                            ctx.startActivity(intent7);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;
                    case "Pre-Req Approval":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent8 = new Intent(ctx, PendingActivity.class);
                            ctx.startActivity(intent8);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;

                    case "Sales Checking":
                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent9 = new Intent(ctx, SalesCheckingActivity.class);
                            ctx.startActivity(intent9);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;
                    case "Exp Approval":

                        if(DrawerActivity.signinStatus.equalsIgnoreCase("signin")) {
                            Intent intent10 = new Intent(ctx, ExpenseApprovalActivity.class);
                            ctx.startActivity(intent10);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Attendance Pending!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, AttandanceActivity.class);
                            ctx.startActivity(intent);
                        }
                        break;
                    case "Logout":
                        sh.clearData();
                        Toast.makeText(ctx, ctx.getString(R.string.you_have_logged_out_successfully), Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(ctx, LoginActivity.class);
                        ctx.startActivity(in);
                        break;

                }

            }
        });
        return view;
    }

    public void setIcons(String page, ImageView img) {
        switch (page) {
            case "Create Meeting":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.ic_meeting));
                break;

            case "Messages":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.msgicon));
                break;

            case "My Schedule":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.schedule));
                break;

            case "Feedback":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.ic_feedback));
                break;

            case "Attendance":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.attaindance));
                break;

            case "Clients":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.ic_customer));
                break;

            case "Settings":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.ic_settings));
                break;
            case "Expense":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.ic_expense));
                break;
            case "Pre-Req Approval":

                img.setBackground(ctx.getResources().getDrawable(R.drawable.ic_pre_appr));
                break;

            case "Sales Checking":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.ic_sale));
                break;
            case "Exp Approval":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.ic_expense_app));
                break;
            case "Logout":
                img.setBackground(ctx.getResources().getDrawable(R.drawable.ic_logout));
                break;

        }
    }

}
