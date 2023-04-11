package com.senior.capstone.Investo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    Button btn_getStarted;
    String alreadyStarted = "yes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hides action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btn_getStarted = findViewById(R.id.btn_getStarted);
        btn_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create admin user
                createAdmin();
            }
        });
    }

    private void createAdmin(){
        dbHelper = new DbHelper(MainActivity.this);
        User user;
        user = new User("Admin",
                "Admin",
                "admin@investo.com",
                "admin",
                "root",
                String.valueOf(R.drawable.baseline_account_circle_24),
                true);

        boolean success = dbHelper.addUser(user);
        //Toast.makeText(this, "ADMIN: " + success, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(MainActivity.this, CreateUser.class));
    }


    @Override
    protected void onResume() {
        super.onResume();

        //this makes MainActivity only display once after the app opens for the first time
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        if (!sharedPreferences.getBoolean(alreadyStarted, false)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(alreadyStarted, true);
            editor.apply();

        } else {
            goToCreateUser();
        }
    }

    private void goToCreateUser() {
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
    }


    public void btnGetStarted(View view) {
        startActivity(new Intent(MainActivity.this, CreateUser.class));
    }


}