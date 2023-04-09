package com.senior.capstone.Investo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    EditText et_signing_username, et_signing_password;
    Button btn_signin_login, btn_signin_createuser;

    DbHelper dbHelper;


    public static final String EXTRA_SIGNING_FNAME = "firstname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //hides action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //initialize widgets
        initWidgets();

        dbHelper = new DbHelper(SignInActivity.this);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        btn_signin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_signing_username.getText().toString().trim();
                String password = et_signing_password.getText().toString().trim();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(SignInActivity.this, "Username or password missing", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean verifyUser = dbHelper.verifyUser(username, password);

                    if (verifyUser == true) {
                        //Toast.makeText(SignInActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();

                        //saving username to display the monthly bills/transactions/budget after sign in
                        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                        sharedPreferences.edit().putString("username", username).commit();

                        startActivity(new Intent(SignInActivity.this, OverviewActivity.class));

                    } else {
                        Toast.makeText(SignInActivity.this, "ERROR: Username and password do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_signin_createuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateUserActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void goToCreateUserActivity() {
        startActivity(new Intent(SignInActivity.this, CreateUser.class));
    }

    private void initWidgets() {
        et_signing_username = findViewById(R.id.et_signing_username);
        et_signing_password = findViewById(R.id.et_signing_password);

        btn_signin_login = findViewById(R.id.btn_signin_login);
        btn_signin_createuser = findViewById(R.id.btn_signin_createuser);
    }
}