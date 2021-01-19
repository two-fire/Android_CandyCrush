package com.xample.candycrush.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xample.candycrush.MainActivity;
import com.xample.candycrush.R;


public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText editTextName,editTextPwd;
    TextView textViewRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.buttonLogin);
        editTextName = findViewById(R.id.editTextName);
        editTextPwd = findViewById(R.id.editTextPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        SharedPreferences sp = this.getSharedPreferences("candycrush", Context.MODE_PRIVATE);
        String name = sp.getString("name",null);
        editTextName.setText(name);

    }

    private void register() {
        Intent in = new Intent();
        in.setClass(this,RegisterActivity.class);
        startActivity(in);
    }

    private void login() {
        String name = editTextName.getText().toString();
        String pwd = editTextPwd.getText().toString();

        SharedPreferences sp = this.getSharedPreferences("candycrush",Context.MODE_PRIVATE);
        String password = sp.getString("password",null);

        if (password==null || !pwd.equals(password)) {
            Toast.makeText(this,"登录失败！",Toast.LENGTH_LONG).show();

        } else {
            this.finish();
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }
    }

}