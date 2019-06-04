package com.handsfluffy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.handsfluffy.util.AlarmManagerUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int NORMAL_SKIN_ALARMS_CNT = 2;
    private static final int DRY_SKIN_ALARMS_CNT = 4;
    public static final String CHANNEL_ID = "notificationServiceChannel";

    private TextView infoMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!isLoggedIn()){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }


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
                    AlarmManagerUtil.setAlarmManagers(NORMAL_SKIN_ALARMS_CNT, getBaseContext());
                    handleApplyInfoMessage(NORMAL_SKIN_ALARMS_CNT + "");
                    //TODO:comment this
                    Toast.makeText(MainActivity.this, "Normal Hands notifications", Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.dryHandsRadioBtn){
                    //TODO:comment this
                    AlarmManagerUtil.setAlarmManagers(DRY_SKIN_ALARMS_CNT, getBaseContext());
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(!isLoggedIn()){
                super.onBackPressed();
            }else{
               this.exitApp();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.exit_side_nav) {
            this.clearIsLoggedIn();
            this.exitApp();
        }else if(id == R.id.info_side_nav){
            Intent aboutUsIntent = new Intent(this, ForUsActivity.class);
            startActivity(aboutUsIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isLoggedIn(){
        SharedPreferences isLoggedPref = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        return isLoggedPref.getBoolean(LoginActivity.IS_LOGGED_PROPERTY_NAME, false);
    }

    private void clearIsLoggedIn() {
        //cancel all alarms
        AlarmManagerUtil.resetExactAlarmManager(this);
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(LoginActivity.IS_LOGGED_PROPERTY_NAME, false);
        editor.apply();
        finish();
    }

    public void exitApp(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
