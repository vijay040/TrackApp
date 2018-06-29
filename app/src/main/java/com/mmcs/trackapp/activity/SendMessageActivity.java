package com.mmcs.trackapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.PurposePopupAdaptor;
import com.mmcs.trackapp.adaptor.UsersPopupAdaptor;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.LoginResMeta;
import com.mmcs.trackapp.model.PurposeModel;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.model.ResMetaUsers;
import com.mmcs.trackapp.model.ResponseMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    Button btnSend;
    EditText edtUserName, edt_comment;
    Shprefrences sh;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh = new Shprefrences(this);
        setContentView(R.layout.activity_send_message);
        btnSend = findViewById(R.id.btnSend);
        progress = findViewById(R.id.progressbar);
        progress.setVisibility(View.VISIBLE);
        edtUserName = findViewById(R.id.edtUserName);
        edt_comment = findViewById(R.id.edt_comment);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String createddate = df.format(Calendar.getInstance().getTime());
        getUserList();
        edtUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showUsersPopup();
            }
        });


        back();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtUserName.getText() + "";
                String message = edt_comment.getText() + "";
                if (name.equals("")) {
                    Toast.makeText(SendMessageActivity.this, "Select Name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (message.equals("")) {
                    Toast.makeText(SendMessageActivity.this, "Enter Your Message", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progress.setVisibility(View.VISIBLE);
                    sendMessage(message, createddate);
                }


            }
        });
    }

    AlertDialog alertDialog;
    ArrayList<LoginModel> userList = new ArrayList<>();
    UsersPopupAdaptor usersPopupAdaptor;
    private int popupId = 0;

    String userId;

    private void showUsersPopup() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        usersPopupAdaptor = new UsersPopupAdaptor(SendMessageActivity.this, userList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        final TextView title = dialogView.findViewById(R.id.title);
        title.setText("Select Contact Name");
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        popupId = 1;
        listPurpose.setAdapter(usersPopupAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                LoginModel obj = (LoginModel) listPurpose.getAdapter().getItem(position);
                edtUserName.setText(obj.getUser_name());
                userId = obj.getId();
                alertDialog.dismiss();
            }
        });

    }

    public void getUserList() {
        Singleton.getInstance().getApi().getUsers("").enqueue(new Callback<ResMetaUsers>() {
            @Override
            public void onResponse(Call<ResMetaUsers> call, Response<ResMetaUsers> response) {

                userList = response.body().getResponse();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResMetaUsers> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
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

    public void sendMessage(String msg, String createdOn) {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().postMessage(model.getId(), userId, msg, createdOn).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                Toast.makeText(SendMessageActivity.this, " Message sent successfully!", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
