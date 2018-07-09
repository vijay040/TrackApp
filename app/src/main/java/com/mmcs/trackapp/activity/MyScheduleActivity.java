package com.mmcs.trackapp.activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.MeetingDetailsAdapter;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MyScheduleActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener {
    ListView listMeetingsView;
    Shprefrences sh;
    ProgressBar progressBar;
    ArrayList<MeetingModel>  meetinglist = new ArrayList<>();
    MeetingDetailsAdapter meetingadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        sh = new Shprefrences(this);
        SearchView editTextName=(SearchView) findViewById(R.id.edt);
        listMeetingsView = findViewById(R.id.listMeetingsView);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        editTextName.setQueryHint(getString(R.string.search_by_purpose_customer_name));
        editTextName.setOnQueryTextListener(this);
       // getSupportActionBar().setTitle("Scheduled Meetings");
        // getMeetingList();
        back();
        setTitle();
        listMeetingsView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MeetingDetailsAdapter adapter = (MeetingDetailsAdapter) adapterView.getAdapter();
                MeetingModel model = adapter.list.get(i);
                Intent intent = new Intent(MyScheduleActivity.this, MeetingDetailsActivity.class);
                intent.putExtra(getString(R.string.meeting_model), model);
                startActivity(intent);
            }
        });
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.meeting_list));
    }

    private void back() {
        RelativeLayout drawerIcon = (RelativeLayout) findViewById(R.id.drawerIcon);
        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void getMeetingList() {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getMeetingsList("" + model.getId()).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                if(response.body()!=null) {
                    meetinglist = response.body().getResponse();
                    meetingadapter = new MeetingDetailsAdapter(MyScheduleActivity.this, meetinglist);
                    listMeetingsView.setAdapter(meetingadapter);
                    listMeetingsView.setEmptyView(findViewById(R.id.txt_nodata));
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getMeetingList();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        s=s.toLowerCase();
        ArrayList<MeetingModel> newlist=new ArrayList<>();
        for(MeetingModel filterlist:meetinglist)
        {
            String purpose=filterlist.getPurpose().toLowerCase();
            String address =filterlist.getCustomer_name().toLowerCase();
            if(purpose.contains(s)||address.contains(s)) {
                newlist.add(filterlist);
            }
        }
        meetingadapter.filter(newlist);
        return true;
    }
}

