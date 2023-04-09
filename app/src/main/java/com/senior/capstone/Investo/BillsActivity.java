package com.senior.capstone.Investo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BillsActivity extends AppCompatActivity {
    DbHelper dbHelper;
    EditText et_bill_amount, et_bill_note, et_bill_date;
    Button btn_add_bill;
    ListView lv_monthlyBills;
    ArrayList<Bills> billsArrayList;
    BillsAdapter billsAdapter;

    //FOR NAV BAR
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
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
        setContentView(R.layout.activity_bills);

        dbHelper = new DbHelper(BillsActivity.this);

        initWidgets();
        lvBillInfo();
        navBar();
        datePickerDialog();

        btn_add_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = et_bill_amount.getText().toString();
                String note = et_bill_note.getText().toString();
                String date = et_bill_date.getText().toString();

                if (amount.equals("")) {
                    Toast.makeText(BillsActivity.this, "Please enter a value!", Toast.LENGTH_SHORT).show();
                } else {
                    Bills bill;
                    try {
                        //adding new bill
                        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                        String username = sharedPreferences.getString("username", null);

                        String val = dbHelper.getUserID(username);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("M-dd-yyyy");


                        bill = new Bills(Integer.parseInt(val), Integer.parseInt(amount), note, date);

                    } catch (Exception e) {
                        bill = new Bills(0, -1, 0, "null");
                        Toast.makeText(BillsActivity.this, "ERROR: " + e, Toast.LENGTH_SHORT).show();
                    }

                    dbHelper = new DbHelper(BillsActivity.this);
                    boolean success = dbHelper.addBill(bill);
                    startActivity(new Intent(BillsActivity.this, OverviewActivity.class));
                }
            }
        });
    }

    public void datePickerDialog() {
        et_bill_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BillsActivity.this, R.style.MyDatePickerStyle, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_bill_date.setText((month + 1) + "-" + dayOfMonth  + "-" + year);
                        //et_bill_date.setText(year + "-" + dayOfMonth + "-" + (month + 1));
                    }
                    }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void lvBillInfo() {
        billsArrayList = dbHelper.getBillsV2(String.valueOf(getUserID()));
        lv_monthlyBills.setFocusable(false);
        billsAdapter = new BillsAdapter(BillsActivity.this, billsArrayList);
        lv_monthlyBills.setAdapter(billsAdapter);
        billsAdapter.notifyDataSetChanged();
    }

    private String getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        //getting userid from username
        String val = dbHelper.getUserID(username);

        return val;
    }

    private void navBar() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(BillsActivity.this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //Toast.makeText(BillsActivity.this, "NAV HOME", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BillsActivity.this, OverviewActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_daily_trans:
                        //Toast.makeText(BillsActivity.this, "NAV DAILY TRANS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BillsActivity.this, DailyTransactionActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_month_bills:
                        //Toast.makeText(BillsActivity.this, "MONTHLY BILLS", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        //Toast.makeText(BillsActivity.this, "NAV PROFILE", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BillsActivity.this, MainProfileActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_budget:
                        //Toast.makeText(BillsActivity.this, "NAV BUDGET", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BillsActivity.this, BudgetActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_calendar:
                        startActivity(new Intent(BillsActivity.this, CalendarActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        startActivity(new Intent(BillsActivity.this, AboutAcctivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_sign_out:
                        //Toast.makeText(BillsActivity.this, "NAV SIGN OUT", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        startActivity(new Intent(BillsActivity.this, SignInActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    private void initWidgets() {
        //EDIT TEXT
        et_bill_amount = findViewById(R.id.et_bill_amount);
        et_bill_note = findViewById(R.id.et_bill_note);
        et_bill_date = findViewById(R.id.et_bill_date);

        //BUTTON
        btn_add_bill = findViewById(R.id.btn_add_bill);

        //LIST VIEW
        lv_monthlyBills = findViewById(R.id.lv_monthlyBills);

        //FOR NAV BAR
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

    }
}