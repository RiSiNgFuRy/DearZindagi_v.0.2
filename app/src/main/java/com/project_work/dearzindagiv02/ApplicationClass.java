package com.project_work.dearzindagiv02;

import android.app.Application;
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
            times.add(new timings("9:00 PM", db.getData()));
            db.close();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
