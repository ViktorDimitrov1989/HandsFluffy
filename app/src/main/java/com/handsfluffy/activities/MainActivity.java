package com.handsfluffy.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.handsfluffy.R;
import com.handsfluffy.backgroundServices.NotificationReceiver;
import com.handsfluffy.enums.SkinType;
import com.handsfluffy.factories.AlarmManagerFactory;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int NORMAL_SKIN_ALARMS_CNT = 2;
    private static final int DRY_SKIN_ALARMS_CNT = 4;
    public static final String CHANNEL_ID = "notificationServiceChannel";

    private TextView infoMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        infoMessageTextView = findViewById(R.id.notification_info);
        handleApplyInfoMessage("");
        setSupportActionBar(toolbar);
        RadioGroup skinTypesRadioGroup = findViewById(R.id.skin_types_radio_group);

        skinTypesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.normalHandsRadioBtn){
                    AlarmManagerFactory.setAlarmManagers(NORMAL_SKIN_ALARMS_CNT, getBaseContext());
                    handleApplyInfoMessage(NORMAL_SKIN_ALARMS_CNT + "");
                    //TODO:comment this
                    Toast.makeText(MainActivity.this, "Normal Hands notifications", Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.dryHandsRadioBtn){
                    //TODO:comment this
                    AlarmManagerFactory.setAlarmManagers(DRY_SKIN_ALARMS_CNT, getBaseContext());
                    handleApplyInfoMessage(DRY_SKIN_ALARMS_CNT + "");
                    Toast.makeText(MainActivity.this, "Dry Hands notifications", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void handleApplyInfoMessage(String applyCnt) {
        String applyCntInfoMsg = String.format(getResources().getString(R.string.apply_cnt_info_message), applyCnt);
        infoMessageTextView.setText(applyCntInfoMsg);
    }

    private void sendNotifications(SkinType type){

        //first call
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        //second call
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.SECOND, 10);


        if(type == SkinType.DRY){

        }else if(type == SkinType.NORMAL){

        }


        /*cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 45);
        cal.set(Calendar.SECOND, 0);*/

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent notificationIntent1 = new Intent(this, NotificationReceiver.class);
        PendingIntent broadcast1 = PendingIntent.getBroadcast(this, 101, notificationIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager1.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), broadcast1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            Toast.makeText(this, "Изход асдасдасд", Toast.LENGTH_SHORT).show();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
