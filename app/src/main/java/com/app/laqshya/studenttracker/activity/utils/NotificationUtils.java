package com.app.laqshya.studenttracker.activity.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import androidx.core.app.NotificationCompat;

import com.app.laqshya.studenttracker.activity.MainScreenNavigationDrawer;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;

    public static final String ANDROID_CHANNEL_ID = "com.laqshya.ANDROID";

    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";


    public NotificationUtils(Context base) {
        super(base);

        createChannels();

    }

    public void createChannels() {

        // create android channel
        NotificationChannel androidChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            // Sets whether notifications posted to thcamerais channel should display notification lights
            androidChannel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            androidChannel.enableVibration(true);
            // Sets the notification light color for notifications posted to this channel
            androidChannel.setLightColor(Color.GREEN);

            // Sets whether notifications posted to this channel appear on the lockscreen or not
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);


            getManager().createNotificationChannel(androidChannel);

            // create ios channel

        }
    }

    public NotificationManager getManager() {
        if (mManager == null) {

            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        }
        return mManager;
    }
    public NotificationCompat.Builder getAndroidChannelNotification(String title, String body) {


        return new NotificationCompat.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
//                .set
                .setOngoing(true)


                .setSmallIcon(android.R.drawable.stat_notify_more);

    }
    public NotificationCompat.Builder getAndroidWorkCompleteChannelNotification(String title, String body) {
        Intent intent = new Intent(getApplicationContext(), MainScreenNavigationDrawer.class);
        PendingIntent pendIntent = PendingIntent.getActivity(getApplicationContext(), 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)

                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendIntent)
                .setProgress(0,0,false)

                .setOngoing(false)
                .setSmallIcon(android.R.drawable.ic_notification_overlay);


    }

}


