package com.senior.capstone.Investo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DailyTransactionActivity extends AppCompatActivity {
    //TESTING
    MyAdapter adapter;
    DailyTransAdapter dailyTransAdapter;
    ListView lv_test;
    ArrayList<User> userName;
    ArrayList<User> userArrayList;
    ArrayList<DailyTrans> dailyTransArrayList;

    EditText et_trans_amount, et_trans_note;
    Button btn_add_trans;
    DbHelper dbHelper;


    //FOR NAV BAR
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    //Toolbar toolbar;
    //toggle button to show nav view
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
        setContentView(R.layout.activity_daily_transaction);
        dbHelper = new DbHelper(DailyTransactionActivity.this);

        initWidgets();
        navBar();
        lvTransInfo();
        //lvTransDelete();

        btn_add_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = et_trans_amount.getText().toString();
                String note = et_trans_note.getText().toString();

                if (amount.equals("")) {
                    Toast.makeText(DailyTransactionActivity.this, "Please enter a value!", Toast.LENGTH_SHORT).show();
                } else {
                    //making new Daily Transaction object
                    DailyTrans dailyTrans;

                    try {
                        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                        String username = sharedPreferences.getString("username", null);
                        String val = dbHelper.getUserID(username);

                        dailyTrans = new DailyTrans(Integer.parseInt(val), Integer.parseInt(amount), note);
                        Toast.makeText(DailyTransactionActivity.this, "Transaction added!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        dailyTrans = new DailyTrans(0, -1, 0, "null");
                        Toast.makeText(DailyTransactionActivity.this, "ERROR: " + e, Toast.LENGTH_SHORT).show();
                    }

                    dbHelper = new DbHelper(DailyTransactionActivity.this);
                    boolean success = dbHelper.addDailyTrans(dailyTrans);
                    //Toast.makeText(DailyTransactionActivity.this, "Boolean success: " + success, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DailyTransactionActivity.this, OverviewActivity.class));

                    dbHelper.close();

                    lvTransInfo();
                }
            }
        });
    }

    //this is just for displaying the daily transactions for users
    private void lvTransInfo() {
        dailyTransArrayList = dbHelper.getDailyTrans(String.valueOf(getUserID()));

        //prevents activity from starting at the listview widget
        lv_test.setFocusable(false);

        dailyTransAdapter = new DailyTransAdapter(DailyTransactionActivity.this, dailyTransArrayList);
        lv_test.setAdapter(dailyTransAdapter);
        dailyTransAdapter.notifyDataSetChanged();
    }

    //NO LONGER USED
    //NOW BEING DELETED IN CUSTOM ADAPTER
    public void lvTransDelete() {
        lv_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DailyTrans dailyTrans;
                dailyTrans = dailyTransArrayList.get(position);
                long itemID = dailyTrans.getId();
                dbHelper.deleteDailyTrans(String.valueOf(itemID));
                lvTransInfo();

            }
        });
    }

    private String getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        //getting userID from username
        String val = dbHelper.getUserID(username);

        return val;
    }

    private void initWidgets() {
        //TESTING
        lv_test = findViewById(R.id.lv_test);

        //EDIT TEXT
        et_trans_amount = findViewById(R.id.et_trans_amount);
        et_trans_note = findViewById(R.id.et_trans_note);

        //BUTTON
        btn_add_trans = findViewById(R.id.btn_add_trans);

        //FOR NAVE BAR
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
    }

    private void navBar() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(DailyTransactionActivity.this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //Toast.makeText(DailyTransactionActivity.this, "NAV HOME", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DailyTransactionActivity.this, OverviewActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_daily_trans:
                        //Toast.makeText(DailyTransactionActivity.this, "NAV DAILY TRANS", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_month_bills:
                        //Toast.makeText(DailyTransactionActivity.this, "MONTHLY BILLS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DailyTransactionActivity.this, BillsActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        //Toast.makeText(DailyTransactionActivity.this, "NAV PROFILE", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DailyTransactionActivity.this, MainProfileActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_budget:
                        //Toast.makeText(DailyTransactionActivity.this, "NAV BUDGET", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DailyTransactionActivity.this, BudgetActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_calendar:
                        startActivity(new Intent(DailyTransactionActivity.this, CalendarActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        startActivity(new Intent(DailyTransactionActivity.this, AboutAcctivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_sign_out:
                        //Toast.makeText(DailyTransactionActivity.this, "NAV SIGN OUT", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        startActivity(new Intent(DailyTransactionActivity.this, SignInActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }
}