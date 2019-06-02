package com.handsfluffy.activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.handsfluffy.R;
import com.handsfluffy.backgroundServices.NotificationReceiver;
import com.handsfluffy.enums.SkinType;

import java.util.Calendar;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String CHANNEL_ID = "notificationServiceChannel";

    private RadioGroup skinTypesRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.skinTypesRadioGroup = findViewById(R.id.skin_types_radio_group);
        //TODO: set onclick listeners to the buttons and create custom method to handle it
        this.skinTypesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.normalHandsRadioBtn){
                    Toast.makeText(MainActivity.this, "Normal Hands notifications", Toast.LENGTH_SHORT).show();
                    sendNotifications(SkinType.NORMAL);
                    //TODO: start background service to popup notifications for normal skin type
                }else if(checkedId == R.id.dryHandsRadioBtn){
                    Toast.makeText(MainActivity.this, "Dry Hands notifications", Toast.LENGTH_SHORT).show();
                    sendNotifications(SkinType.DRY);
                    //TODO: start background service to popup notifications for dry skin type
                }
            }
        });

        //cal.add(Calendar.SECOND, 5);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void sendNotifications(SkinType type){
        //TODO:start notification receiver
        //first call
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        //second call
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.SECOND, 10);

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
