package com.example.lenovo.trackapp.activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.actv.MeetingDetailsActivity;
import com.example.lenovo.trackapp.adaptor.MeetingDetailsAdapter;
import com.example.lenovo.trackapp.model.LoginModel;
import com.example.lenovo.trackapp.model.MeetingModel;
import com.example.lenovo.trackapp.model.ResMetaMeeting;
import com.example.lenovo.trackapp.util.Shprefrences;
import com.example.lenovo.trackapp.util.Singleton;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MyScheduleActivity extends AppCompatActivity {
    ListView listMeetingsView;
    Shprefrences sh;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        listMeetingsView = findViewById(R.id.listMeetingsView);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle("Scheduled Meetings");
        sh = new Shprefrences(this);
        getMeetingList();
        listMeetingsView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MeetingDetailsAdapter adapter = (MeetingDetailsAdapter) adapterView.getAdapter();
                MeetingModel model = adapter.list.get(i);
                Intent intent = new Intent(MyScheduleActivity.this, MeetingDetailsActivity.class);
                intent.putExtra("MEETINGMODEL", model);
                startActivity(intent);
            }
        });
    }
    private void getMeetingList() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getMeetingsList("" + model.getId()).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                if(response.body()!=null) {
                    ArrayList<MeetingModel> list = response.body().getResponse();
                    final MeetingDetailsAdapter adapter = new MeetingDetailsAdapter(MyScheduleActivity.this, list);
                    listMeetingsView.setAdapter(adapter);
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
}

