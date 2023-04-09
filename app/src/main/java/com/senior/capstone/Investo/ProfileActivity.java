//package com.senior.capstone.Investo;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import com.google.android.material.navigation.NavigationView;
//
//public class ProfileActivity extends AppCompatActivity {
//    //FOR NAV BAR
//    private DrawerLayout profile_drawerLayout;
//    private NavigationView profile_navigationView;
//    //Toolbar toolbar;
//    //toggle button to show nav view
//    private ActionBarDrawerToggle actionBarDrawerToggle;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        DbHelper dbHelper = DbHelper.getInstance(this);
//
//        initWidgets();
//        navBar();
//    }
//
//    //FOR NAV BAR
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

package com.senior.capstone.Investo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    DbHelper dbHelper;
    TextView first_name, last_name, tv_username, tv_password;
    ArrayList<User> userArrayList;

    //FOR NAV BAR
    private DrawerLayout profile_drawerLayout;
    private NavigationView profile_navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public boolean tigerIsSelected, discordIsSelected, forestIsSelected, logoIsSelected,
            pistonsIsSelected, lionsIsSelected = false;

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
        setContentView(R.layout.activity_profile);

        dbHelper = DbHelper.getInstance(this);

        initWidgets();
        navBar();
    }


    public void editProfile (View view) {

        Button edit_profile = findViewById(R.id.edit_profile_btn);
        Button choose_pic = findViewById(R.id.change_pic_btn);

        choose_pic.setVisibility(View.VISIBLE);
        edit_profile.setVisibility(View.GONE);
        //TextView first = findViewById(R.id.first_name);
        TextView last = findViewById(R.id.last_name);
        TextView user = findViewById(R.id.username);
        TextView pass = findViewById(R.id.password);
        //first.setVisibility(View.GONE);
        last.setVisibility(View.GONE);
        user.setVisibility(View.GONE);
        pass.setVisibility(View.GONE);
    }

    /*Check for already in place usernames*/
    public void choosePicture (View view) {
        ImageView tiger_pic = findViewById(R.id.tigers_propic);
        ImageView discord_pic = findViewById(R.id.discord_profile);
        ImageView forest = findViewById(R.id.forest_pic);
        ImageView logo = findViewById(R.id.investo_logo);
        ImageView pistons = findViewById(R.id.detroit_pistons);
        ImageView lions = findViewById(R.id.detroit_lions);

        tiger_pic.setVisibility(View.VISIBLE);
        discord_pic.setVisibility(View.VISIBLE);
        forest.setVisibility(View.VISIBLE);
        logo.setVisibility(View.VISIBLE);
        pistons.setVisibility(View.VISIBLE);
        lions.setVisibility(View.VISIBLE);
        LinearLayout submitLayout = findViewById(R.id.align_center);
        submitLayout.setVisibility(View.VISIBLE);
        Button choose_pic = findViewById(R.id.change_pic_btn);
        choose_pic.setVisibility(View.GONE);
    }

    /*Consider making these imageViews and such all public so I don't have to import in each method*/
    public void imageSelectedTigers (View view) {
        ImageView tiger_pic = findViewById(R.id.tigers_propic);
        ImageView discord_pic = findViewById(R.id.discord_profile);
        ImageView forest = findViewById(R.id.forest_pic);
        ImageView logo = findViewById(R.id.investo_logo);
        ImageView pistons = findViewById(R.id.detroit_pistons);
        ImageView lions = findViewById(R.id.detroit_lions);

        tiger_pic.setColorFilter(Color.argb(90, 0, 0, 0));

        forest.setColorFilter(null);
        discord_pic.setColorFilter(null);
        logo.setColorFilter(null);
        pistons.setColorFilter(null);
        lions.setColorFilter(null);
        tigerIsSelected = true;
        discordIsSelected = false;
        forestIsSelected = false;
        logoIsSelected = false;
        pistonsIsSelected = false;
        lionsIsSelected = false;
    }

    public void imageSelectedDiscord (View view) {
        ImageView tiger_pic = findViewById(R.id.tigers_propic);
        ImageView discord_pic = findViewById(R.id.discord_profile);
        ImageView forest = findViewById(R.id.forest_pic);
        ImageView logo = findViewById(R.id.investo_logo);
        ImageView pistons = findViewById(R.id.detroit_pistons);
        ImageView lions = findViewById(R.id.detroit_lions);

        discord_pic.setColorFilter(Color.argb(90, 0, 0, 0));
        /*Need to set all the other pictures to their original colors too*/
        forest.setColorFilter(null);
        tiger_pic.setColorFilter(null);
        logo.setColorFilter(null);
        pistons.setColorFilter(null);
        lions.setColorFilter(null);
        discordIsSelected = true;
        tigerIsSelected = false;
        forestIsSelected = false;
        logoIsSelected = false;
        pistonsIsSelected = false;
        lionsIsSelected = false;
    }

    public void imageSelectedForest (View view) {
        ImageView tiger_pic = findViewById(R.id.tigers_propic);
        ImageView discord_pic = findViewById(R.id.discord_profile);
        ImageView forest = findViewById(R.id.forest_pic);
        ImageView logo = findViewById(R.id.investo_logo);
        ImageView pistons = findViewById(R.id.detroit_pistons);
        ImageView lions = findViewById(R.id.detroit_lions);

        forest.setColorFilter(Color.argb(90, 0, 0, 0));
        /*Need to set all the other pictures to their original colors too*/
        /*Resetting the effects that occur when the image is selected*/
        tiger_pic.setColorFilter(null);
        discord_pic.setColorFilter(null);
        logo.setColorFilter(null);
        pistons.setColorFilter(null);
        lions.setColorFilter(null);
        forestIsSelected = true;
        discordIsSelected = false;
        tigerIsSelected = false;
        logoIsSelected = false;
        pistonsIsSelected = false;
        lionsIsSelected = false;
    }

    public void imageSelectedLogo (View view) {
        ImageView tiger_pic = findViewById(R.id.tigers_propic);
        ImageView discord_pic = findViewById(R.id.discord_profile);
        ImageView forest = findViewById(R.id.forest_pic);
        ImageView logo = findViewById(R.id.investo_logo);
        ImageView pistons = findViewById(R.id.detroit_pistons);
        ImageView lions = findViewById(R.id.detroit_lions);

        logo.setColorFilter(Color.argb(90, 0, 0, 0));

        tiger_pic.setColorFilter(null);
        discord_pic.setColorFilter(null);
        forest.setColorFilter(null);
        pistons.setColorFilter(null);
        lions.setColorFilter(null);
        logoIsSelected = true;
        discordIsSelected = false;
        tigerIsSelected = false;
        forestIsSelected = false;
        pistonsIsSelected = false;
        lionsIsSelected = false;
    }

    public void imageSelectedPistons (View view) {
        ImageView tiger_pic = findViewById(R.id.tigers_propic);
        ImageView discord_pic = findViewById(R.id.discord_profile);
        ImageView forest = findViewById(R.id.forest_pic);
        ImageView logo = findViewById(R.id.investo_logo);
        ImageView pistons = findViewById(R.id.detroit_pistons);
        ImageView lions = findViewById(R.id.detroit_lions);

        pistons.setColorFilter(Color.argb(90, 0, 0, 0));

        tiger_pic.setColorFilter(null);
        discord_pic.setColorFilter(null);
        forest.setColorFilter(null);
        logo.setColorFilter(null);
        lions.setColorFilter(null);
        pistonsIsSelected = true;
        logoIsSelected = false;
        discordIsSelected = false;
        tigerIsSelected = false;
        forestIsSelected = false;
        lionsIsSelected = false;
    }

    public void imageSelectedLions (View view) {
        ImageView tiger_pic = findViewById(R.id.tigers_propic);
        ImageView discord_pic = findViewById(R.id.discord_profile);
        ImageView forest = findViewById(R.id.forest_pic);
        ImageView logo = findViewById(R.id.investo_logo);
        ImageView pistons = findViewById(R.id.detroit_pistons);
        ImageView lions = findViewById(R.id.detroit_lions);

        lions.setColorFilter(Color.argb(90, 0, 0, 0));

        tiger_pic.setColorFilter(null);
        discord_pic.setColorFilter(null);
        forest.setColorFilter(null);
        logo.setColorFilter(null);
        pistons.setColorFilter(null);
        lionsIsSelected = true;
        pistonsIsSelected = false;
        logoIsSelected = false;
        discordIsSelected = false;
        tigerIsSelected = false;
        forestIsSelected = false;
    }

    /*Here we want to detect which image the user has selected with the public booleans, while also hiding everything that popped up when
     * the user clicked Edit Profile/Choose Picture, and showing everything to make it go back to normal*/
    /*Also, soon document all of this code for ease*/
    /*Code for when the Done/Submit button is clicked; selected Profile Picture is displayed, and all TextViews and ImageViews that were
     * hidden return*/
    public void confirmPicture (View view) {
        ImageView tiger_pic = findViewById(R.id.tigers_propic);
        ImageView discord_pic = findViewById(R.id.discord_profile);
        ImageView forest = findViewById(R.id.forest_pic);
        ImageView logo = findViewById(R.id.investo_logo);
        ImageView pistons = findViewById(R.id.detroit_pistons);
        ImageView lions = findViewById(R.id.detroit_lions);

        tiger_pic.setVisibility(View.GONE);
        discord_pic.setVisibility(View.GONE);
        forest.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        pistons.setVisibility(View.GONE);
        lions.setVisibility(View.GONE);
        Button edit_profile = findViewById(R.id.edit_profile_btn);
        Button choose_pic = findViewById(R.id.change_pic_btn);
        choose_pic.setVisibility(View.GONE);
        edit_profile.setVisibility(View.VISIBLE);
        TextView first = findViewById(R.id.first_name);
        TextView last = findViewById(R.id.last_name);
        TextView user = findViewById(R.id.username);
        TextView pass = findViewById(R.id.password);
        first.setVisibility(View.VISIBLE);
        last.setVisibility(View.VISIBLE);
        user.setVisibility(View.VISIBLE);
        pass.setVisibility(View.VISIBLE);

        float density = getResources().getDisplayMetrics().density;
        /*Original blank-ish picture for Profile*/
        ImageView defaultpic = findViewById(R.id.default_pic);
        ViewGroup.LayoutParams layoutParams = defaultpic.getLayoutParams();

        float default_size = 200;
        int sizeInPixels = (int) (default_size * density + 0.5f);
        /*Default Size for the default profile picture*/

        if (discordIsSelected) {
            defaultpic.setImageResource(R.drawable.discord_final_playstore);
            defaultpic.setImageTintList(null);
            /*Resetting the default size parameters for the images that have
             * already acceptable aspect ratios (aka non-forest)*/
            layoutParams.width = sizeInPixels;
            layoutParams.height = sizeInPixels;
            defaultpic.setLayoutParams(layoutParams);
        } else if (tigerIsSelected) {
            defaultpic.setImageResource(R.drawable.detroit_tigers_playstore);
            defaultpic.setImageTintList(null);
            layoutParams.width = sizeInPixels;
            layoutParams.height = sizeInPixels;
            defaultpic.setLayoutParams(layoutParams);
        } else if (forestIsSelected) {
            defaultpic.setImageResource(R.drawable.forest_playstore);
            defaultpic.setImageTintList(null);
            /*Since the default aspect ratio for this picture is larger than the others,
             * I'm manually adjusting the size of the photo instead of editing the original file*/
            layoutParams.width = 375;
            layoutParams.height = 375;
            defaultpic.setLayoutParams(layoutParams);
        } else if (logoIsSelected) {
            defaultpic.setImageResource(R.drawable.ic_launcher);
            defaultpic.setImageTintList(null);
            layoutParams.width = sizeInPixels;
            layoutParams.height = sizeInPixels;
            defaultpic.setLayoutParams(layoutParams);
        } else if (pistonsIsSelected) {
            defaultpic.setImageResource(R.drawable.detroit_pistons_playstore);
            defaultpic.setImageTintList(null);
            layoutParams.width = 400;
            layoutParams.height = 400;
            defaultpic.setLayoutParams(layoutParams);
        } else if (lionsIsSelected) {
            defaultpic.setImageResource(R.drawable.detroit_lions_playstore);
            defaultpic.setImageTintList(null);
            layoutParams.width = 550;
            layoutParams.height = 550;
            defaultpic.setLayoutParams(layoutParams);
        }

        LinearLayout submitLayout = findViewById(R.id.align_center);
        submitLayout.setVisibility(View.GONE);
    }


    private void navBar() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(ProfileActivity.this, profile_drawerLayout, R.string.menu_open, R.string.menu_close);
        profile_drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profile_navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(ProfileActivity.this, "NAV HOME", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this, OverviewActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_daily_trans:
                        Toast.makeText(ProfileActivity.this, "NAV DAILY TRANS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this, DailyTransactionActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_month_bills:
                        Toast.makeText(ProfileActivity.this, "MONTHLY BILLS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this, BillsActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        Toast.makeText(ProfileActivity.this, "NAV PROFILE", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this, MainProfileActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_budget:
                        Toast.makeText(ProfileActivity.this, "NAV BUDGET", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this, BudgetActivity.class));
                        profile_drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_sign_out:
                        Toast.makeText(ProfileActivity.this, "NAV SIGN OUT", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
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
//        first_name = findViewById(R.id.first_name);
//        last_name = findViewById(R.id.last_name);
//        tv_username = findViewById(R.id.username);
//        tv_password = findViewById(R.id.password);

        //NAV BAR
        profile_drawerLayout = findViewById(R.id.drawer_layout);
        profile_navigationView = findViewById(R.id.profile_navigation_view);
    }
}