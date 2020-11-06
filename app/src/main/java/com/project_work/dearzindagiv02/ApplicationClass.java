package com.project_work.dearzindagiv02;

import android.app.Application;
import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationClass extends Application {
    public static ArrayList<timings> times;
    @Override
    public void onCreate() {
        super.onCreate();
        times=new ArrayList<timings>();
        try {
            Listdb db=new Listdb(this);
            db.open();
            if(!db.isEmpty()) {
                String[] time = db.getTime().split(",");
                int i = 0;
                while (!time[i].equals("end")) {
                    String[] rowIDs = db.getKeyRowid(time[i]).split(",");
                    times.add(new timings(time[i], db.getData(time[i]), db.getKeyName(time[i]), db.getKeyNumoftimes(time[i]), db.getKeyExpiry(time[i]), "null"));
                    i++;
                }
            }
            db.close();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
