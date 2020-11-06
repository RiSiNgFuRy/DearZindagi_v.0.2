package com.project_work.dearzindagiv02;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.sql.SQLException;
import java.util.List;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification() {
        Intent repeat_intent =new Intent(getApplicationContext(),HomeActivity.class);
        repeat_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,repeat_intent,PendingIntent.FLAG_UPDATE_CURRENT);

   /*     Listdb db = new Listdb(this);
        String message = null;
        try {
            db.open();
            String []time= db.getTime().split(",");
            int i=0;
            while(!time[i].equals("end"))
            {
                message = db.getData(time[i]);
                i++;
            }
            db.close();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/

        return new NotificationCompat.Builder(this, channelID)
                .setContentIntent(pendingIntent)
                .setContentTitle("Dear Zindagi")
                .setContentText("It is time for your medicines.")
                .setSmallIcon(R.drawable.ic_android)
                .setAutoCancel(true);
    }
}
