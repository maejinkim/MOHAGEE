package com.example.maedin.mohagee.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.maedin.mohagee.R;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

        EditText m_name;
        EditText m_age;
        EditText m_id;
        EditText m_password;
        Button btn_id_ok;
        Button btn_join_ok;
        Button btn_join_cancle;
        CheckBox checkmale;
        CheckBox checkfemale;

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);

    m_name = (EditText) findViewById(R.id.m_name);
    m_age = (EditText) findViewById(R.id.m_age);
    m_id = (EditText) findViewById(R.id.m_id);
    m_password = (EditText) findViewById(R.id.m_password);
    btn_id_ok = (Button) findViewById(R.id.id_ok);
    checkmale = (CheckBox) findViewById(R.id.checkBox1);
    checkfemale = (CheckBox) findViewById(R.id.checkBox2);
    btn_join_ok = (Button) findViewById(R.id.btn_join_ok);
    btn_join_cancle = (Button) findViewById(R.id.btn_join_cancle);

    btn_join_ok.setOnClickListener(this);
    btn_join_cancle.setOnClickListener(this);
}

@Override
public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_join_ok:
                {
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                }
            case R.id.btn_join_cancle:
            {
                startActivity(new Intent(this,SignInActivity.class));
            }

        }
        }
        }
