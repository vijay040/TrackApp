package com.mmcs.trackapp.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mmcs.trackapp.R;

import java.util.Calendar;

public class FollowupActivity extends AppCompatActivity {

    Button button;
    EditText edt_comment, edtDate, edtTime;
    CheckBox chkBok;
    int H, M;
    Calendar calendar;
    int DD, MM, YY;
    RelativeLayout lay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.need_folloup);
        edt_comment = findViewById(R.id.edt_comment);
        chkBok = findViewById(R.id.chkFollowup);
        button = findViewById(R.id.btnSubmit);
        lay = findViewById(R.id.lay);
        edtTime = findViewById(R.id.edtTime);
        edtDate = findViewById(R.id.edtDate);
        calendar = Calendar.getInstance();
        DD = calendar.get(Calendar.DAY_OF_MONTH);
        MM = calendar.get(Calendar.MONTH);
        YY = calendar.get(Calendar.YEAR);

        if ((MM + 1) < 10)
            edtDate.setText(String.valueOf(YY) + "-0" + String.valueOf(MM + 1) + "-" + String.valueOf(DD));
        else
            edtDate.setText(String.valueOf(YY) + "-" + String.valueOf(MM + 1) + "-" + String.valueOf(DD));
        H = calendar.get(Calendar.HOUR_OF_DAY);
        M = calendar.get(Calendar.MINUTE);
        edtTime.setText(String.valueOf(H) + ":" + String.valueOf(M));

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(121);
            }
        });
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(111);
            }
        });

        setTitle();
        back();


        chkBok.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    lay.setVisibility(View.VISIBLE);
                } else {
                    lay.setVisibility(View.GONE);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((edt_comment.getText() + "").equalsIgnoreCase("")) {
                    Toast.makeText(FollowupActivity.this, "Please enter Minutes of Meeting!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String date = edtDate.getText().toString();
                String time = edtTime.getText().toString();
                Intent output = new Intent();
                output.putExtra("NEED_FOLLOWUP", "" + chkBok.isChecked());
                output.putExtra("MOM", edt_comment.getText() + "");
                output.putExtra("DATE", date + "");
                output.putExtra("TIME", time + "");
                setResult(Activity.RESULT_OK, output);
                finish();
            }
        });
    }

    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.feedback));
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

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 111) {
            return new DatePickerDialog(this, onDateSetListener, YY, MM, DD);
        } else if (id == 121) {
            return new TimePickerDialog(this, onTimeSetListener, H, M, true);
        }
        return onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            if ((m + 1) < 10)
                edtDate.setText(String.valueOf(y) + "-0" + String.valueOf(m + 1) + "-" + String.valueOf(d));
            else
                edtDate.setText(String.valueOf(y) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(d));
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int h, int m) {
            timePicker.is24HourView();

            edtTime.setText(String.valueOf(h) + ":" + String.valueOf(m));
        }
    };


}
