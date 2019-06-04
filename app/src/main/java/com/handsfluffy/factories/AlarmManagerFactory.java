package com.handsfluffy.factories;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.handsfluffy.backgroundServices.NotificationReceiver;
import com.handsfluffy.enums.AlarmTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class AlarmManagerFactory {

    private static List<AlarmManager> alarmManagers = new ArrayList<>();

    private static List<PendingIntent> pendingIntents = new ArrayList<>();

    private static List<Calendar> alarms = new ArrayList<>();

    private AlarmManagerFactory(){}

    private static final AlarmTime[] alarmTimes = new AlarmTime[]{
            new AlarmTime(9),
            new AlarmTime(15),
            new AlarmTime(9),
            new AlarmTime(12),
            new AlarmTime(15),
            new AlarmTime(19),
    };

    private static List<Calendar> getAlarmsHourBasedOnSkinTypes(int alarmsCnt){
        for (int i = 0; i < alarmsCnt; i++) {
            AlarmTime currentAlarmTime = alarmTimes[i];
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, currentAlarmTime.getHours());
            alarms.add(cal);
        }

        return alarms;
    }

    private static List<PendingIntent> getPendingIntents(int alarmsCnt, Context context){
        int uniqueIntentIndex = 100;

        for (int i = 0; i < alarmsCnt; i++) {
            Intent notificationIntent = new Intent(context, NotificationReceiver.class);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, uniqueIntentIndex++, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            pendingIntents.add(broadcast);
        }

        return pendingIntents;
    }

    private static void resetAlarmManagers(){
        int index = 0;

        if(alarmManagers == null || alarmManagers.size() == 0){
            return;
        }

        for (AlarmManager alarmManager : alarmManagers) {
            PendingIntent pendingIntent = pendingIntents.get(index);
            if(pendingIntent != null){
                alarmManager.cancel(pendingIntents.get(index));
            }
            index++;
        }

        alarmManagers.clear();
        pendingIntents.clear();
        alarms.clear();

    }

    public static void setAlarmManagers(int alarmsCnt, Context context){
        resetAlarmManagers();
        alarms = getAlarmsHourBasedOnSkinTypes(alarmsCnt);
        pendingIntents = getPendingIntents(alarmsCnt, context);

        for (int i = 0; i < alarmsCnt; i++) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarms.get(i).getTimeInMillis(), pendingIntents.get(i));
            alarmManagers.add(alarmManager);
        }

    }

}
