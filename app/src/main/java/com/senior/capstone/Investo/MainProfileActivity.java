package com.senior.capstone.Investo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainProfileActivity extends AppCompatActivity {
    DbHelper dbHelper;
    TextView first_name, last_name, tv_username, tv_password, tv_email;
    Button edit_profile_btn, main_profile_change_pass_btn;
    ImageView main_profile_pic;
    EditText main_profile_curr_pass, main_profile_new_pass, main_profile_curr_pass_check;

    //FOR NAV BAR
    private DrawerLayout profile_drawerLayout;
    private NavigationView profile_navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    //FOR NAV BAR
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);
        dbHelper = new DbHelper(MainProfileActivity.this);

        initWidgets();
        navBar();
        displayUserInfo();
        setProfilePicture();
        getUserEmail();
        test();


        edit_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePicturePicker();
            }
        });
    }

    public void displayUserInfo() {
        DbHelper dbHelper = DbHelper.getInstance(MainProfileActivity.this);
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        //Capitalize only the first letter then rest lower case
        String firstName = dbHelper.getFName(username);
        String resFName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        first_name.setText("First Name: " + resFName);

        String lastName = dbHelper.getLName(username);
        String resLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        last_name.setText("Last Name: " + resLastName);

        tv_username.setText("Username: " + username);
    }

    public void setProfilePicture() {
        Bitmap profilePicture = dbHelper.getProfilePicture(getUserID());
        if (profilePicture == null) {
            main_profile_pic.setImageResource(R.drawable.ic_launcher);
        } else {
            main_profile_pic.setImageBitmap(profilePicture);
        }
    }

    private String getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        //getting userID from username
        String val = dbHelper.getUserID(username);

        return val;
    }

    private String getUserPAss() {
        String dbCurrPass = dbHelper.getUserPass(getUserID());

        return dbCurrPass;
    }

    public void getUserEmail() {
        String email = dbHelper.getUserEmail(getUserID());
        tv_email.setText("Email: " + email);
    }

    //this calls and intent to start the profile picker process
    private void profilePicturePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        profilePickerActivity.launch(intent);
    }

    //This opens the photos application that is built into the device
    //It also will call the dbhelper function to set the profile picture
    ActivityResultLauncher<Intent> profilePickerActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent intent = result.getData();
            // do your operation from here....

            if (intent != null && intent.getData() != null) {

                Uri selectedProfilePictureURI = intent.getData();

                Bitmap selectedProfilePicture = null;


                try {
                    selectedProfilePicture = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedProfilePictureURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(this, "profile pic: " + String.valueOf(selectedProfilePictureURI), Toast.LENGTH_SHORT).show();
                dbHelper.updateProfilePicture(getUserID(), selectedProfilePicture);
                main_profile_pic.setImageBitmap(selectedProfilePicture);
            }
        }
    });

    public void test() {
        main_profile_change_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etCurrPass = main_profile_curr_pass.getText().toString().trim();
                String etNewPass = main_profile_new_pass.getText().toString().trim();
                String etNewPassCheck = main_profile_curr_pass_check.getText().toString().trim();
                String DbCurrPass = getUserPAss();

                if (etCurrPass.contains(" ") || etNewPass.contains(" ") || etNewPassCheck.contains(" ")) {
                    Toast.makeText(MainProfileActivity.this, "Spaces found in password(s)", Toast.LENGTH_SHORT).show();
                } else {
                    if (DbCurrPass.equals(etCurrPass) && etNewPass.equals(etNewPassCheck)) {
                        try {
                            dbHelper.updateUserPass(getUserID(), etNewPass);
                            Toast.makeText(MainProfileActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainProfileActivity.this, SignInActivity.class));
                        } catch (Exception e) {
                            Toast.makeText(MainProfileActivity.this, "ERROR: " + e, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainProfileActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void navBar() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainProfileActivity.this, profile_drawerLayout, R.string.menu_open, R.string.menu_close);
        profile_drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profile_navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //Toast.makeText(MainProfileActivity.this, "NAV HOME", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainProfileActivity.this, OverviewActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_daily_trans:
                        //Toast.makeText(MainProfileActivity.this, "NAV DAILY TRANS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainProfileActivity.this, DailyTransactionActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_month_bills:
                        //Toast.makeText(MainProfileActivity.this, "MONTHLY BILLS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainProfileActivity.this, BillsActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        //Toast.makeText(MainProfileActivity.this, "NAV PROFILE", Toast.LENGTH_SHORT).show();
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_budget:
                        //Toast.makeText(MainProfileActivity.this, "NAV BUDGET", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainProfileActivity.this, BudgetActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_calendar:
                        startActivity(new Intent(MainProfileActivity.this, CalendarActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        startActivity(new Intent(MainProfileActivity.this, AboutAcctivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_sign_out:
                        //Toast.makeText(MainProfileActivity.this, "NAV SIGN OUT", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainProfileActivity.this, SignInActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    private void initWidgets() {
        //TEXT VIEW
        //first_name, last_name, username, password;
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        tv_username = findViewById(R.id.username);
        tv_password = findViewById(R.id.password);
        tv_email = findViewById(R.id.tv_email);

        //BUTTON
        edit_profile_btn = findViewById(R.id.edit_profile_btn);
        main_profile_change_pass_btn = findViewById(R.id.main_profile_change_pass_btn);

        //IMAGEVIEW
        main_profile_pic = findViewById(R.id.main_profile_pic);

        //EDITTEXT
        main_profile_curr_pass = findViewById(R.id.main_profile_curr_pass);
        main_profile_new_pass = findViewById(R.id.main_profile_new_pass);
        main_profile_curr_pass_check = findViewById(R.id.main_profile_curr_pass_check);

        //NAV BAR
        profile_drawerLayout = findViewById(R.id.drawer_layout);
        profile_navigationView = findViewById(R.id.profile_navigation_view);
    }

}