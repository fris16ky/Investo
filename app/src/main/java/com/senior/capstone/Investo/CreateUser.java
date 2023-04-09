package com.senior.capstone.Investo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUser extends AppCompatActivity {
    EditText et_firstName, et_lastName, et_email, et_password, et_repassword, et_username;
    Button btn_createUser;

    DbHelper dbHelper;

    int defaultProfilePic = R.drawable.baseline_account_circle_24;

    //Intent keys NEED TO DELETE but im holding onto it because idk lol
    public static final String EXTRA_CREATE_FNAME = "firstname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        //hides action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        dbHelper = new DbHelper(CreateUser.this);

        initWidgets();

        btn_createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = et_firstName.getText().toString();
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String rePassword = et_repassword.getText().toString();

                if (!username.equals("") || !password.equals("") || !rePassword.equals("")) {
                    //Toast.makeText(CreateUser.this, "ALL FIELDS FILLED", Toast.LENGTH_SHORT).show();

                    if (!password.equals(rePassword)) {
                        Toast.makeText(CreateUser.this, "PASSWORDS DOES NOT MATCH", Toast.LENGTH_SHORT).show();

                    } else {
                        //both password match, adding new user account
                        //Toast.makeText(CreateUser.this, "PASSWORDS MATCH", Toast.LENGTH_SHORT).show();

                        Boolean verifyUser = dbHelper.verifyUserName(username);

                        if (verifyUser == false) {
                            User user;

                            try {
                                user = new User(et_firstName.getText().toString().trim(),
                                        et_lastName.getText().toString().trim(),
                                        et_email.getText().toString().trim(),
                                        et_username.getText().toString().trim(),
                                        et_password.getText().toString().trim(),
                                        String.valueOf(defaultProfilePic));
                                //Toast.makeText(CreateUser.this, user.toString(), Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Toast.makeText(CreateUser.this, "Error creating user", Toast.LENGTH_SHORT).show();
                                //default values if something goes wrong so it doesn't crash app
                                user = new User("error", "error", "error");

                            }

                            DbHelper dbHelper = new DbHelper(CreateUser.this);
                            boolean success = dbHelper.addUser(user);
                            Toast.makeText(CreateUser.this, "SUCCESS: User created", Toast.LENGTH_SHORT).show();

                            SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                            sharedPreferences.edit().putString("username", username).commit();

                            startActivity(new Intent(CreateUser.this, OverviewActivity.class));

                        } else {
                            Toast.makeText(CreateUser.this, "Username already exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    //username, password, or rePassword missing
                    Toast.makeText(CreateUser.this, "ERROR: username or password missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void initWidgets() {
        et_firstName = findViewById(R.id.et_firstName);
        et_lastName = findViewById(R.id.et_lastName);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_repassword = findViewById(R.id.et_repassword);
        et_username = findViewById(R.id.et_username);

        btn_createUser = findViewById(R.id.btn_createActivity_createUser);
    }
}