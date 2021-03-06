package com.example.happycustomer1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.happycustomer1.ConstantFragments.CoachDetailsFragment;
import com.example.happycustomer1.ConstantFragments.FirstAidFragment;
import com.example.happycustomer1.ConstantFragments.GymAbsFragment;
import com.example.happycustomer1.ConstantFragments.GymBicepsAndBackFragment;
import com.example.happycustomer1.ConstantFragments.GymChestAndTricepsFragment;
import com.example.happycustomer1.ConstantFragments.GymLegsFragment;
import com.example.happycustomer1.ConstantFragments.GymShouldersFragment;
import com.example.happycustomer1.ConstantFragments.HomeAbsFragment;
import com.example.happycustomer1.ConstantFragments.HomeBicepsAndBackFragment;
import com.example.happycustomer1.ConstantFragments.HomeChestAndTricepsFragment;
import com.example.happycustomer1.ConstantFragments.HomeLegsFragment;
import com.example.happycustomer1.ConstantFragments.HomeShouldersFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    //Drawer Layout
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    androidx.appcompat.widget.Toolbar toolbar;
    RelativeLayout contentView;
    Fragment ProfileClassObject=new ProfileFragment();
    boolean IS_LOGIN=false;

    static final float END_SCALE=0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        getPermissions();

        //To Remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Drawer hooks
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navmenu);
        Menu menu = navigationView.getMenu();
        MenuItem Login = menu.getItem(2);
        MenuItem Logout = menu.getItem(3);

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Logout.setVisible(true);
                    Login.setVisible(false);
                    ProfileClassObject = new UserProfileAfterLoginFragment();
                }
                else {
                    Login.setVisible(true);
                    Logout.setVisible(false);
                    ProfileClassObject = new ProfileFragment();
                }
            }
        });


        toolbar=(androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        contentView=findViewById(R.id.content);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Setting home dashboard fragment on screen
        getSupportFragmentManager().beginTransaction().add(R.id.wrapper,new HomeFragment()).commit();

        //Navigation Drawer
        navigationView.setCheckedItem(R.id.home);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        temp=new HomeFragment();
                        break;

                    case R.id.First_Aid_Techniques:
                        temp=new FirstAidFragment();
                        break;

                    case R.id.Coach_Details:
                        temp=new CoachDetailsFragment();
                        break;

                    case R.id.Trace_Me:
                        temp=new TrackMeFragment();
                        break;

                    case R.id.Profile:
                        temp=(Fragment)ProfileClassObject;
                        break;

                    case R.id.Biceps_and_Back:
                        temp=new GymBicepsAndBackFragment();
                        break;

                    case R.id.Chest_and_Triceps:
                        temp=new GymChestAndTricepsFragment();
                        break;
                    case R.id.Shoulders:
                        temp=new GymShouldersFragment();
                        break;
                    case R.id.Abs:
                        temp=new GymAbsFragment();
                        break;
                    case R.id.Legs:
                        temp=new GymLegsFragment();
                        break;
                    case R.id.Biceps_and_Back_h:
                        temp=new HomeBicepsAndBackFragment();
                        break;

                    case R.id.Chest_and_Triceps_h:
                        temp=new HomeChestAndTricepsFragment();
                        break;
                    case R.id.Shoulders_h:
                        temp=new HomeShouldersFragment();
                        break;
                    case R.id.Abs_h:
                        temp=new HomeAbsFragment();
                        break;
                    case R.id.Legs_h:
                        temp=new HomeLegsFragment();
                        break;

                    case R.id.Login:
                        temp=new ProfileLoginFragment();
                        break;

                    case R.id.Logout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(),"You have been logged out",Toast.LENGTH_SHORT).show();
                        temp=new HomeFragment();
                        break;

                        //Yet to add login and logout
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        animateNavigationDrawer();
    }

    private void getPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if ((permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && (permissions[1].equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                return;
            }
            else{
                this.finishAffinity();
            }
        }
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