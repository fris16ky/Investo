package com.senior.capstone.Investo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

//import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.navigation.NavigationView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    DbHelper dbHelper;
    ArrayList<Bills> billsArrayList;
    ListView test_LV;

    CalendarView bills_calendar;

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
        setContentView(R.layout.activity_calendar);
        dbHelper = new DbHelper(CalendarActivity.this);

        initWidgets();
        navBar();
        billCalendarDueDate();

        //TESTING
        calendarClickedDate();
    }

    private String getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        //getting userid from username
        String val = dbHelper.getUserID(username);

        return val;
    }

    private void billCalendarDueDate() {
        List<String> billDates = dbHelper.getBillDates(getUserID());
        List<EventDay> events = new ArrayList<>();

        for (String billDate : billDates) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy");
                Date date = dateFormat.parse(billDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                events.add(new EventDay(calendar, R.drawable.baseline_attach_money_24));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        bills_calendar.setEvents(events);
    }

    public void calendarClickedDate() {
        bills_calendar.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(@NonNull EventDay eventDay) {
                /**
                 * IMPORTANT!!!
                 * Make sure for the date format that it is M-d-yyyy
                 * this will make sure to cut off any leading 0's because
                 * the date is saved without leading 0's in the
                 */
                SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy");
                String clickedDate = dateFormat.format(eventDay.getCalendar().getTime());

                String amount = dbHelper.getBillAmount(clickedDate, getUserID());
                String note = dbHelper.getBillNote(clickedDate, getUserID());

                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
                builder.setTitle("Bill Details");
                builder.setMessage("Amount: " + amount + "\nNote: " + note);
                builder.setPositiveButton("CLOSE", null);
                builder.show();
            }
        });
    }

    private void navBar() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(CalendarActivity.this, profile_drawerLayout, R.string.menu_open, R.string.menu_close);
        profile_drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profile_navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //Toast.makeText(CalendarActivity.this, "NAV HOME", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CalendarActivity.this, OverviewActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_daily_trans:
                        //Toast.makeText(CalendarActivity.this, "NAV DAILY TRANS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CalendarActivity.this, DailyTransactionActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_month_bills:
                        //Toast.makeText(CalendarActivity.this, "MONTHLY BILLS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CalendarActivity.this, BillsActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        //Toast.makeText(CalendarActivity.this, "NAV PROFILE", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CalendarActivity.this, MainProfileActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_budget:
                        //Toast.makeText(CalendarActivity.this, "NAV BUDGET", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CalendarActivity.this, BudgetActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        startActivity(new Intent(CalendarActivity.this, AboutAcctivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_sign_out:
                        //Toast.makeText(CalendarActivity.this, "NAV SIGN OUT", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CalendarActivity.this, SignInActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    private void initWidgets() {
        //NAV BAR
        profile_drawerLayout = findViewById(R.id.drawer_layout);
        profile_navigationView = findViewById(R.id.profile_navigation_view);

        //CALENDAR VIEW
        bills_calendar = findViewById(R.id.bills_calendar);
    }
}