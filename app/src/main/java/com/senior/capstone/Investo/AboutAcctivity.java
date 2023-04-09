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
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Base64;

public class AboutAcctivity extends AppCompatActivity {
    WebView myWebView;

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
        setContentView(R.layout.activity_about_acctivity);

        initWidgets();
        navBar();
        myHTML();
    }

    public void myHTML() {
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("https://brianevans96.github.io/csi499CapstonePage/aboutpage.html");
    }

    public void initWidgets() {
        //NAV BAR
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        myWebView = findViewById(R.id.myWebView);

    }

    private void navBar() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(AboutAcctivity.this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(AboutAcctivity.this, OverviewActivity.class));
                        //Toast.makeText(OverviewActivity.this, "NAV HOME", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_daily_trans:
                        //Toast.makeText(OverviewActivity.this, "NAV DAILY TRANS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AboutAcctivity.this, DailyTransactionActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_month_bills:
                        //Toast.makeText(OverviewActivity.this, "MONTHLY BILLS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AboutAcctivity.this, BillsActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        //Toast.makeText(OverviewActivity.this, "NAV PROFILE", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AboutAcctivity.this, MainProfileActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_budget:
                        //Toast.makeText(OverviewActivity.this, "NAV BUDGET", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AboutAcctivity.this, BudgetActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_calendar:
                        startActivity(new Intent(AboutAcctivity.this, CalendarActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_about:
                        //startActivity(new Intent(OverviewActivity.this, AboutAcctivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_sign_out:
                        //Toast.makeText(OverviewActivity.this, "NAV SIGN OUT", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        startActivity(new Intent(AboutAcctivity.this, SignInActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }
}