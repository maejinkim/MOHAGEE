
        package com.example.maedin.mohagee.activity;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import android.widget.Toast;

        import com.example.maedin.mohagee.R;
        import com.example.maedin.mohagee.application.App;
        import com.example.maedin.mohagee.item.User;
        import com.example.maedin.mohagee.thread.ServerThread;

        import org.json.JSONArray;
        import org.json.JSONObject;
        import org.w3c.dom.Element;
        import org.xml.sax.SAXException;

        import java.io.IOException;
        import java.io.StringReader;

public class SignInActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnSignUp;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private int i = -1;

    EditText _idText;
    EditText _passwordText;
    User user=new User();


    private ServerThread serverThread=ServerThread.getInstance();
    private String myResult;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        serverThread.setFgHandler(mHandler);

        _idText = findViewById(R.id.signin_id);
        _passwordText = findViewById(R.id.signin_pw);
        btnSignUp = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        //TODO : ON LOGIN PROCESS

        String id = _idText.getText().toString();
        String password = _passwordText.getText().toString();

        serverThread.setLogin(id,password,myResult);
        serverThread.getFgHandler().sendEmptyMessage(1);
    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = _idText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _idText.setError("enter a valid ID");
            valid = false;
        } else {
            _idText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.obj.toString().equals("\n\n\n\nWrong Password!\n\n\n")) {       //비밀번호를 잘못 입력했을시
                    Toast.makeText(SignInActivity.this, "잘못된 비밀번호를 입력했습니다.", Toast.LENGTH_SHORT).show();
                } else if (msg.obj.toString().equals("\n\n\n\njava.sql.SQLException: Illegal operation on empty result set.\n\n\n"))   //아이디가 없을때
                {
                    Toast.makeText(SignInActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    String temp=msg.obj.toString().substring(5);

                    JSONObject jsonObject = new JSONObject(temp);
                    try {

                        String user_id = jsonObject.getString("user_id");
                        String user_pw = jsonObject.getString("user_pw");
                        String user_name = jsonObject.getString("user_name");
                        String bitthdate = jsonObject.getString("birthdate");
                        String gender = jsonObject.getString("gender");
                        user.setData(user_id,user_name,user_pw,gender,bitthdate);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(SignInActivity.this, "로그인 성공.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    ((App)getApplication()).setUser(user);
                    //intent.putExtra("user",user);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
}
