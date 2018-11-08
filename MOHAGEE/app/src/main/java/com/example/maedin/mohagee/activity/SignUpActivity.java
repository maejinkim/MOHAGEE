package com.example.maedin.mohagee.activity;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.os.Handler;
import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.thread.ServerThread;


public class SignUpActivity extends AppCompatActivity{

    EditText m_name;
    EditText m_age;
    EditText m_id;
    EditText m_password;
    Button btn_id_ok;
    Button btn_join_ok;
    Button btn_join_cancle;
    RadioButton checkmale;
    RadioButton checkfemale;
    boolean id_variable=false;
    String id_checked="";
    private ServerThread serverThread=ServerThread.getInstance();
    private static final String TAG = "SignupActivity";
    private String myResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        serverThread.setFgHandler(mHandler);


        m_name = (EditText) findViewById(R.id.m_name);
        m_age = (EditText) findViewById(R.id.m_age);
        m_id = (EditText) findViewById(R.id.m_id);
        m_password = (EditText) findViewById(R.id.m_password);
        btn_id_ok = (Button) findViewById(R.id.id_ok);
        checkmale = (RadioButton) findViewById(R.id.checkBox1);
        checkfemale = (RadioButton) findViewById(R.id.checkBox2);
        btn_join_ok = (Button) findViewById(R.id.btn_join_ok);
        btn_join_cancle = (Button) findViewById(R.id.btn_join_cancle);


        btn_join_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        btn_id_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_check();
            }
        });
        btn_join_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void id_check() {

        if (m_id.getText()==null) {
            Toast.makeText(getBaseContext(), "please enter your id", Toast.LENGTH_SHORT).show();
            return;
        }
        String id = m_id.getText().toString();

        serverThread.setIdCheck(id,myResult);
        serverThread.getFgHandler().sendEmptyMessage(3);

        if(validate_id())
            return;

    }

    public boolean validate_id() {
        boolean valid = true;

        String id = m_id.getText().toString();

        if (id.isEmpty()) {
            m_id.setError("Enter Valid ID");
            valid = false;
        } else {
            m_id.setError(null);
        }

        return valid;
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }
        String name = m_name.getText().toString();
        String id = m_id.getText().toString();
        String password = m_password.getText().toString();
        String age=m_age.getText().toString();
        String gender;
        if(checkfemale.isChecked())
            gender="여";
        else
            gender="남";

        // TODO: Implement your own signup logic here.
        if(id_variable==true && id_checked.equals(id))
        {
            serverThread.setRegister(id,password,name,age,gender,myResult);
            serverThread.getFgHandler().sendEmptyMessage(2);
        }
        else
        {

            Toast.makeText(getBaseContext(), "Signup failed... Please check your id again", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_SHORT).show();

        btn_id_ok.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = m_name.getText().toString();
        String id = m_id.getText().toString();
        String password = m_password.getText().toString();
        String age=m_age.getText().toString();
        String gender;
        if(checkfemale.isChecked())
            gender="여";
        else
            gender="남";

        if (name.isEmpty() || name.length() < 3) {
            m_name.setError("at least 3 characters");
            valid = false;
        } else {
            m_name.setError(null);
        }

        if (id.isEmpty()) {
            m_id.setError("Enter Valid ID");
            valid = false;
        } else {
            m_id.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            m_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            m_password.setError(null);
        }
        if (age.isEmpty()) {
            m_age.setError("Enter your birthdate XX.XX.XX");
            valid = false;
        } else {
            m_age.setError(null);
        }
        if (!checkfemale.isChecked() && !checkmale.isChecked()) {
            checkmale.setError("Check your gender");
            valid = false;
        } else {
            m_id.setError(null);
        }
        return valid;
    }

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.obj.toString().trim().equals("No value present") && validate_id()==true) {
                id_variable=true;
                id_checked+=m_id.getText().toString();
                Toast.makeText(getBaseContext(), "ID check!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(msg.obj.toString().trim().contains("success"))
                {
                    setResult(RESULT_OK, null);
                    finish();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Signup failed... Please enter your info again", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
