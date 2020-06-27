package com.adisalagic.fuelratsirc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.pircbotx.Configuration;

public class Login extends AppCompatActivity {


    private ImageView mLogo;
    private EditText  mLogin;
    private EditText  mPassword;
    private CheckBox  mRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        mLogo = findViewById(R.id.logo);
        mLogin = findViewById(R.id.login);
        mPassword = findViewById(R.id.password);
        mRemember = findViewById(R.id.remember);
    }

    public void onSubmitClick(View view){
        IRClient.getInstance().setLoginAndPassword(mLogin.getText().toString(), mPassword.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}