package com.app.laqshya.studenttracker.activity.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.MainActivity;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Timber.d("%s", remoteMessage.getData().get("message"));
        sendNotification(remoteMessage.getData().get("message"));


    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.PRIMARY_NOTIFICATION_CHANNEL, "Laqshya",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLightColor(Color.GREEN);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getNotificationManager().createNotificationChannel(channel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, Constants.PRIMARY_NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.web_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.laqshya_logo))
                .setContentTitle("New Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId(Constants.PRIMARY_NOTIFICATION_CHANNEL)
                .setContentIntent(pendingIntent);

        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }

    private NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(
                    Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

}

