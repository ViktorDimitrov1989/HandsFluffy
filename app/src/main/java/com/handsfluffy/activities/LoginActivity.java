package com.handsfluffy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.handsfluffy.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginBtn = findViewById(R.id.login_btn);
        this.loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login_btn){
            Intent loginIntent = new Intent(v.getContext(), MainActivity.class);
            startActivity(loginIntent);
        }

    }
}
