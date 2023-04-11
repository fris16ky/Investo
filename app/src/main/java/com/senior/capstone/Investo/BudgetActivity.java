package com.senior.capstone.Investo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

public class BudgetActivity extends AppCompatActivity {
    Button btn_add_amound;
    EditText et_amount, et_budget_note;
    ListView lv_budget_info;
    DbHelper dbHelper;
    ArrayList<Budget> budgetArrayList;
    BudgetAdapter budgetAdapter;

    //FOR NAV BAR
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
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
        setContentView(R.layout.activity_budget);

        //dbHelper = DbHelper.getInstance(BudgetActivity.this);

        dbHelper = new DbHelper(BudgetActivity.this);

        initWidgets();
        navBar();
        lvBudgetInfo();
        lvDeleteBudget();

        //add budget to db
        btn_add_amound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = et_amount.getText().toString();
                String note = et_budget_note.getText().toString();
                
                //Toast.makeText(BudgetActivity.this, "Amount test: " + String.valueOf(amount), Toast.LENGTH_SHORT).show();
                //Toast.makeText(BudgetActivity.this, "Note test: " + note, Toast.LENGTH_SHORT).show();
                
                if (amount.equals("")) {
                    Toast.makeText(BudgetActivity.this, "Please enter a value!", Toast.LENGTH_SHORT).show();
                    
                } else {
                    //making new budget object
                    Budget budget;

                    try {
                        //adding new budget
                        //replace hard coded value!! FIXED 3/5/2023**************************
                        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                        String username = sharedPreferences.getString("username", null);
                        String val = dbHelper.getUserID(username);

                        budget = new Budget(Integer.parseInt(val), Integer.parseInt(amount), note);
                        Toast.makeText(BudgetActivity.this, "Budget added!", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        //if adding budget fails, these values will be inserted so app doesn't crash
                        budget= new Budget(0, -1, 0, "null");
                        Toast.makeText(BudgetActivity.this, "ERROR: " + e, Toast.LENGTH_SHORT).show();
                    }

                    dbHelper = new DbHelper(BudgetActivity.this);
                    boolean success = dbHelper.addBudget(budget);
                    //tells if the new budget was added
                    //Toast.makeText(BudgetActivity.this, "Boolen Success " + success, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BudgetActivity.this, OverviewActivity.class));

                    dbHelper.close();
                }
            }
        });
    }

    private void initWidgets() {
        //NAV BAR
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //BUTTON
        btn_add_amound = findViewById(R.id.btn_add_amound);

        //EDIT TEXT
        et_amount = findViewById(R.id.et_amount);
        et_budget_note = findViewById(R.id.et_budget_note);

        //LIST VIEW
        lv_budget_info = findViewById(R.id.lv_budget_info);

        //TESTING
        //lv_test2 = findViewById(R.id.lv_test2);
    }

    private String getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        //getting useris from username
        String val = dbHelper.getUserID(username);

        return val;
    }

    private void lvBudgetInfo() {
        budgetArrayList = dbHelper.getBudget(String.valueOf(getUserID()));
        lv_budget_info.setFocusable(false);
        budgetAdapter = new BudgetAdapter(BudgetActivity.this, budgetArrayList);
        lv_budget_info.setAdapter(budgetAdapter);
    }

    private void lvDeleteBudget() {
        lv_budget_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Budget budget;
                budget = budgetArrayList.get(position);
                long itemID = budget.getId();
                dbHelper.deleteBudget(String.valueOf(itemID));
                lvBudgetInfo();
            }
        });
    }

    private void navBar() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(BudgetActivity.this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //Toast.makeText(BudgetActivity.this, "NAV HOME", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BudgetActivity.this, OverviewActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_daily_trans:
                        //Toast.makeText(BudgetActivity.this, "NAV DAILY TRANS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BudgetActivity.this, DailyTransactionActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_month_bills:
                        //Toast.makeText(BudgetActivity.this, "MONTHLY BILLS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BudgetActivity.this, BillsActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        //Toast.makeText(BudgetActivity.this, "NAV PROFILE", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BudgetActivity.this, MainProfileActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_budget:
                        //Toast.makeText(BudgetActivity.this, "NAV BUDGET", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_calendar:
                        startActivity(new Intent(BudgetActivity.this, CalendarActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        startActivity(new Intent(BudgetActivity.this, AboutAcctivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_sign_out:
                        //Toast.makeText(BudgetActivity.this, "NAV SIGN OUT", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BudgetActivity.this, SignInActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }
}