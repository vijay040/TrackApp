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
    String user_name;

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
        DateFormat df = new SimpleDateFormat(getString(R.string.date_formate));
        final String createddate = df.format(Calendar.getInstance().getTime());
        getUserList();
        edtUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showUsersPopup();
            }
        });


        back();
        setTitle();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtUserName.getText() + "";
                String message = edt_comment.getText() + "";
                if (name.equals("")) {
                    Toast.makeText(SendMessageActivity.this, getString(R.string.select_name), Toast.LENGTH_SHORT).show();
                    return;
                } else if (message.equals("")) {
                    Toast.makeText(SendMessageActivity.this, getString(R.string.enter_your_message), Toast.LENGTH_SHORT).show();
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
        title.setText(getString(R.string.select_contact_name));
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

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
      LoginModel  model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getUsers(model.getId()).enqueue(new Callback<ResMetaUsers>() {
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
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        user_name=edtUserName.getText()+"";
        Singleton.getInstance().getApi().postMessage(model.getId(), userId, msg, createdOn,user_name).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                Toast.makeText(SendMessageActivity.this, getString(R.string.message_sent_successfully), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.new_message));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();

                ArrayList<LoginModel> newlist = new ArrayList<>();
                for (LoginModel list : userList) {

                    {
                        String name=list.getUser_name().toLowerCase();

                        if(name.contains(s)) {
                            newlist.add(list);
                        }
                    }
                }
        usersPopupAdaptor.filter(newlist);


        return false;
    }
}
