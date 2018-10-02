package com.example.maedin.mohagee.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.maedin.mohagee.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnOK;
    Button btnCancle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnOK = findViewById(R.id.btn_join_ok);
        btnCancle = findViewById(R.id.btn_join_cancle);

        btnOK.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_join_ok:
            {
                Toast.makeText(this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_join_cancle:
            {
                Toast.makeText(this, "회원가입 취소!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        finish();
    }
}
