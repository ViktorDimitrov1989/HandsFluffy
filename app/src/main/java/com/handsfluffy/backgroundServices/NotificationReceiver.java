package com.handsfluffy.backgroundServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.handsfluffy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "Hands Fluffy Notifications";

    private int notificationCounter = 0;

    private List<String> messages = new ArrayList<String>(){{
        add("Няма да е лошо да се хидратираш.");
        add("Хей! Забрави да се напръскаш!");
        add("Здравей! Напомням ти да се напръскаш.");
        add("Хей! Спрейчето е! Напръскай се!");
    }};

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        Notification notification = builder
                .setContentTitle(this.getRandomMessage())
                .setSmallIcon(R.drawable.ic_hand_black_24dp)
                /*.setContentText("New Notification From Demo App..")
                .setTicker("New Message Alert!")*/
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Hands Fluffy",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(this.notificationCounter++, notification);
    }

    private String getRandomMessage(){
        int min = 0;
        int max = this.messages.size() - 1;

        Random rnd = new Random();
        int randomIndex = min + rnd.nextInt(max - min + 1);

        return this.messages.get(randomIndex);
    }

}
