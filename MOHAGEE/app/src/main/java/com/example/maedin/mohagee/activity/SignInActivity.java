package com.example.maedin.mohagee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.maedin.mohagee.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin;
    Button btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        btnSignUp = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
            {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            }
            case R.id.btn_signup:
            {
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            }
        }
    }
}
