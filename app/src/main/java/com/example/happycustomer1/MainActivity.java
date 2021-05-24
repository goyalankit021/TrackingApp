package com.example.happycustomer1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //Drawer Layout
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    androidx.appcompat.widget.Toolbar toolbar;
    RelativeLayout contentView;

    static final float END_SCALE=0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);

        //To Remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Drawer hooks
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navmenu);
        toolbar=(androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        contentView=findViewById(R.id.content);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Setting home dashboard fragment on screen
        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new HomeFragment()).commit();

        //Navigation Drawer
        navigationView.setCheckedItem(R.id.home);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            boolean home=false;
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        temp=new HomeFragment();
                        home=true;
                        break;

                    case R.id.profile:
                        temp=new CoachProfileFragment();
                        break;

                    case R.id.home1:
                        temp=new GymWorkoutFragment();
                        break;


                    case R.id.profile1:
                        temp=new ProfileFragment();
                        break;

                }
                if(!home)
                    getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,temp).addToBackStack(null).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }


    public void onBackPressed() {
        DrawerLayout layout = (DrawerLayout)findViewById(R.id.drawerLayout);
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        }
        else if(count == 0){
            View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.exitdialog,null);
            Button cancelButton=view.findViewById(R.id.cancelExitButton);
            Button acceptButton=view.findViewById(R.id.acceptExitButton);
            AlertDialog dialog= new AlertDialog.Builder(this)
                    .setView(view)
                    .setCancelable(false)
                    .create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            dialog.show();

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    finish();
                }
            });
        }
        else{
            super.onBackPressed();
        }
    }

}