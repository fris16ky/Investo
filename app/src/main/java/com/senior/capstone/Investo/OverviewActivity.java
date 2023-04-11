package com.senior.capstone.Investo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Shared Preferences Use Cases:
 *
 * getSharedPreferences() - Us this if you need multiple preferences files identified by name, which you define with the first parameter
 *
 * getPreferences() - Use this if you need only one preference file for your activity. Do not need to define name with parameter as it will only make one preferences file
 */

public class OverviewActivity extends AppCompatActivity {
    DbHelper dbHelper;
    TextView user_first_name, tv_budget, tv_bills, tv_trans, tv_overviewDate, tvBudgetLeftOver, header_username;
    PieChart pieChart;

    //FOR NAV BAR
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    //TESTING
    MyAdapter adapter;
    BudgetAdapter budgetAdapter;
    ArrayList<User> userName;
    ArrayList<Budget> budgetArrayList;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    NumberFormat format = new DecimalFormat("$#,###");
    private String currentDate;

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
        setContentView(R.layout.activity_overview);

        dbHelper = new DbHelper(OverviewActivity.this);

        initWidgets();
        getUserFName();
        piechartVals();
        navBar();
        currentDate();
        budgetLeftOver();

        /**
         * TESTING
         * NEED TO REMOVE LATER TO CLEAN UP CODE
         * REVIEWW BEFORE DELETING!!!!!
         */
        //int total_user_budget = dbHelper.getUserBudgetTotal(getUserID());
        //tv_budget_test.setText(String.valueOf(total_user_budget));
        //lvTest(dbHelper);
        //lvTestTwo(getUserID());
    }

    private void budgetLeftOver() {
        int budget = dbHelper.getUserBudgetTotal(getUserID());
        int dailyTrans = dbHelper.getUserDailyTransTotal(getUserID());
        int bills = dbHelper.getUserBillTotal(getUserID());

        int res = budget - (dailyTrans + bills);

        if (res >= 0) {
            tvBudgetLeftOver.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            tvBudgetLeftOver.setText(format.format(res));
        } else {
            tvBudgetLeftOver.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            tvBudgetLeftOver.setText(format.format(res));
        }

    }

    private String getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        //getting userid from username
        String val = dbHelper.getUserID(username);

        return val;
    }

    private void piechartVals() {
        //BUDGET AMOUNT TOTAL
        int total_user_budget = dbHelper.getUserBudgetTotal(getUserID());

        //BIL AMOUNT TOTAL
        int total_user_bill = dbHelper.getUserBillTotal(getUserID());

        //DAILY TRANSACTION TOTAL
        int total_user_daily_trans = dbHelper.getUserDailyTransTotal(getUserID());

        // Set the percentage of language used
        tv_budget.setText((format.format(total_user_budget)));
        tv_bills.setText(format.format(total_user_bill));
        tv_trans.setText(format.format(total_user_daily_trans));

        //tvJava.setText(Integer.toString(25));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Budget",
                        Integer.parseInt(String.valueOf(total_user_budget)),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Monthly Bills",
                        Integer.parseInt(String.valueOf(total_user_bill)),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Daily Transactions",
                        Integer.parseInt(String.valueOf(total_user_daily_trans)),
                        Color.parseColor("#FFA726")));
        pieChart.startAnimation();
    }

    private void getUserFName() {
        DbHelper dbHelper = DbHelper.getInstance(OverviewActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        //REMOVE THIS LATER
        //Toast.makeText(this, "username: " + username, Toast.LENGTH_SHORT).show();

        //Capitalize only the first letter then rest lower case
        String val = dbHelper.getFName(username);
        String res = val.substring(0,1).toUpperCase() + val.substring(1).toLowerCase();
        user_first_name.setText("Welcome back, " + res + "!");
        //header_username.setText(res);
    }

    private void navBar() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(OverviewActivity.this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //Toast.makeText(OverviewActivity.this, "NAV HOME", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_daily_trans:
                        //Toast.makeText(OverviewActivity.this, "NAV DAILY TRANS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OverviewActivity.this, DailyTransactionActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_month_bills:
                        //Toast.makeText(OverviewActivity.this, "MONTHLY BILLS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OverviewActivity.this, BillsActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        //Toast.makeText(OverviewActivity.this, "NAV PROFILE", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OverviewActivity.this, MainProfileActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_budget:
                        //Toast.makeText(OverviewActivity.this, "NAV BUDGET", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OverviewActivity.this, BudgetActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_calendar:
                        startActivity(new Intent(OverviewActivity.this, CalendarActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        startActivity(new Intent(OverviewActivity.this, AboutAcctivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_sign_out:
                        //Toast.makeText(OverviewActivity.this, "NAV SIGN OUT", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        startActivity(new Intent(OverviewActivity.this, SignInActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    private void initWidgets() {
        //TEXT VIEW
        user_first_name = findViewById(R.id.tv_welcomeBack);
        tv_overviewDate = findViewById(R.id.tv_overviewDate);
        tvBudgetLeftOver = findViewById(R.id.tvBudgetLeftOver);
        header_username = findViewById(R.id.header_username);

        //PIE CHART
        tv_budget = findViewById(R.id.overview_budget_tv);
        tv_bills = findViewById(R.id.overview_bills_tv);
        tv_trans = findViewById(R.id.overview_trans_tv);
        pieChart = findViewById(R.id.piechart);

        //NAV BAR
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
    }

    public void currentDate() {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        currentDate = simpleDateFormat.format(calendar.getTime());
        tv_overviewDate.setText(currentDate);
    }
}