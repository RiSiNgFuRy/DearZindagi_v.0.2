package com.project_work.dearzindagiv02;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements timings_adapter.ItemSelected, TimePickerDialog.OnTimeSetListener {
    TextView med_name,med_exp_date,description;
    ElegantNumberButton num_of_tabs;
    Button add_alarm,del_alarm,save_btn;
    ImageButton next_page_btn,back_page_btn;
    outputData outputData;
    FragmentManager fragmentManager;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save_btn=findViewById(R.id.save_btn);
        add_alarm = findViewById(R.id.add_alarm);
        del_alarm = findViewById(R.id.del_alarm);
        next_page_btn =findViewById(R.id.next_page_btn);
        med_name = findViewById(R.id.med_name);
        med_exp_date = findViewById(R.id.med_exp_date);
        description = findViewById(R.id.description);
        num_of_tabs = findViewById(R.id.num_of_tabs);
        back_page_btn=findViewById(R.id.back_page_btn);

        fragmentManager=this.getSupportFragmentManager();
        outputData=(outputData) fragmentManager.findFragmentById(R.id.list_frag);

        findViewById(R.id.list_frag).setVisibility(View.GONE);
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

                try {
                    Listdb db = new Listdb(MainActivity.this);
                    db.open();
                    db.createEntry(time, med_name.getText().toString().trim(), String.valueOf(num_of_tabs.getNumber()), med_exp_date.getText().toString().trim(), description.getText().toString().trim());
                    db.close();
                    med_name.setText(null);
                    med_exp_date.setText(null);
                    num_of_tabs.setNumber("1");
                    description.setText(null);
                    med_name.setError("Enter this feild");
                    Toast.makeText(MainActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                outputData.getNotifiedDataChanged();
            }
        });
    }


    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
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
    private void updateTimeText(Calendar c) {
        time = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        add_alarm.setText(time);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        add_alarm.setText("Alarm canceled");
    }
}
