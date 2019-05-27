package com.handsfluffy.backgroundServices;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.handsfluffy.R;
import com.handsfluffy.activities.MainActivity;

import static com.handsfluffy.backgroundServices.NotificationActivity.CHANNEL_ID;

public class NotificationService extends Service {

    @Override
    public void onCreate() {
        //initialization
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String message = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                notificationIntent,
                0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Notification service")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_pan_tool_black_24dp)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
