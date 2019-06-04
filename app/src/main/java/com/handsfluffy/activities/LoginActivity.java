package com.handsfluffy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.handsfluffy.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PREFS_NAME = "prefs";
    public static final String IS_LOGGED_PROPERTY_NAME = "isLogged";

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences isLoggedPref = getSharedPreferences(PREFS_NAME, 0);
        boolean isLogged = isLoggedPref.getBoolean(IS_LOGGED_PROPERTY_NAME, false);

        if(isLogged){
            transitionToMainActivity();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginBtn = findViewById(R.id.login_btn);
        this.loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            login();
            transitionToMainActivity();
        }
    }

    private void login() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(IS_LOGGED_PROPERTY_NAME, true);
        editor.apply();
    }

    private void transitionToMainActivity(){
        Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(loginIntent);
    }

}
