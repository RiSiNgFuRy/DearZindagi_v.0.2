package com.project_work.dearzindagiv02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements timings_adapter.ItemSelected, TimePickerDialog.OnTimeSetListener {

    TextView med_name, med_exp_date, description,for_check,state_text;//------for_check using for testing purpose(not part of project)------
    ElegantNumberButton num_of_tabs;
    Button add_alarm, del_alarm, save_btn;
    ImageButton next_page_btn, back_page_btn;
    outputData outputData;
    FragmentManager fragmentManager;
    String time;
    RadioGroup state_check;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        state_check=findViewById(R.id.state_check);
        state_text=findViewById(R.id.state_text);
        for_check=findViewById(R.id.for_check);
        save_btn = findViewById(R.id.save_btn);
        add_alarm = findViewById(R.id.add_alarm);
        del_alarm = findViewById(R.id.del_alarm);
        next_page_btn = findViewById(R.id.next_page_btn);
        med_name = findViewById(R.id.med_name);
        med_exp_date = findViewById(R.id.med_exp_date);
        description = findViewById(R.id.description);
        num_of_tabs = findViewById(R.id.num_of_tabs);
        back_page_btn = findViewById(R.id.back_page_btn);

        fragmentManager = this.getSupportFragmentManager();
        outputData = (outputData) fragmentManager.findFragmentById(R.id.list_frag);//------to get access to output fragment------

        Listdb db=new Listdb(this);
        try {
            db.open();
            if(db.isEmpty())
                findViewById(R.id.list).setVisibility(View.GONE);
            else
                findViewById(R.id.no_entry_txt).setVisibility(View.GONE);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        findViewById(R.id.list_frag).setVisibility(View.GONE);
        findViewById(R.id.quantity_area).setVisibility(View.GONE);
        next_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  findViewById(R.id.input_frag).setVisibility(View.GONE);
                  findViewById(R.id.list_frag).setVisibility(View.VISIBLE);
            }
        });
        back_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  findViewById(R.id.input_frag).setVisibility(View.VISIBLE);
                  findViewById(R.id.list_frag).setVisibility(View.GONE);
            }
        });

        Button buttonTimePicker = findViewById(R.id.add_alarm);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        Button buttonCancelAlarm = findViewById(R.id.del_alarm);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (med_name.getText().toString().isEmpty() && med_exp_date.getText().toString().isEmpty()) {
                    med_name.setError("This field cannot be empty");
                    med_exp_date.setError("This field cannot be empty");
                } else if (med_name.getText().toString().isEmpty())
                    med_name.setError("This field cannot be empty");
                else if (med_exp_date.getText().toString().isEmpty())
                    med_exp_date.setError("This field cannot be empty");
                else {
                    try {
                        Listdb db = new Listdb(HomeActivity.this);
                        db.open();
                        db.createEntry(time, med_name.getText().toString().trim(), String.valueOf(num_of_tabs.getNumber()), med_exp_date.getText().toString().trim(), description.getText().toString().trim());
                        db.close();
                        med_name.setText(null);
                        med_exp_date.setText(null);
                        num_of_tabs.setNumber("1");
                        description.setText(null);
                        add_alarm.setText("SET ALARM");
                        outputData.getNotifiedDataChanged();//------Don't know why this is not working(declaration is in outputData) Code=0------
                        Toast.makeText(HomeActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                    } catch (SQLException e) {
                        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int index) {                 //------Don't know why this is not working(declaration is in timings_adapter) Code=1------
        //for_check.setText(ApplicationClass.times.get(index).getTime());
        ApplicationClass.times.remove(index);              //------Content inside this is perfectly working(you can test it by copying same thing in onItemClicked)------
        outputData.getNotifiedDataChanged();
        Listdb db=new Listdb(this);
        try {
            db.open();
            String []rowIDs=db.getKeyRowid(ApplicationClass.times.get(index).getTime()).split(",");
            int i=0;
            if(i<rowIDs.length)
            {
                db.deleteEntry("i");
                for_check.setText(rowIDs[i]);
                i++;
            }
            outputData.getNotifiedDataChanged();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
        startAlarm(c);
    }
    private void startAlarm (Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }
    private void cancelAlarm () {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        add_alarm.setText("SET ALARM");
    }
    private void updateTimeText(Calendar c) {
        time = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        add_alarm.setText(time);
    }
    public void check_option(android.view.View v)
    {
        int radioId=state_check.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
        findViewById(R.id.quantity_area).setVisibility(View.VISIBLE);
        if(radioButton.getText().equals("Solution"))
            state_text.setText("Volume of Solution(ml) at a time:");
        else
            state_text.setText("Number of "+radioButton.getText()+"s at a time:");
    }
}

