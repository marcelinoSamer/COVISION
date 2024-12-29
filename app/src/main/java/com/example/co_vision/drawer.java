package com.example.co_vision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.StatusBarManager;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.io.File;


public class drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView Name;
    TextView username;
    public static String user_login_data = "com.example.co_vision.logindata";
    private DrawerLayout dl;
    NavigationView nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        //adding a toolbar to make the activity applicable with the navigation drawer
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        dl = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar,
                R.string.opne_drawer, R.string.close_drawer);
        dl.addDrawerListener(toggle);
        toggle.syncState();




        nv = (NavigationView) findViewById(R.id.NavigationView);
        View headerView = nv.getHeaderView(0);
        nv.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container
                , new Home()).commit();
        nv.setCheckedItem(R.id.nav_Home);
        Name = (TextView) headerView.findViewById(R.id.name);
        username = (TextView) headerView.findViewById(R.id.username);

        SharedPreferences getusername = getSharedPreferences(user_login_data, MODE_PRIVATE);
        String Sfirstname = getusername.getString("firstname", "glitch");
        String Slastname = getusername.getString("lastname", "glitch");
        String Susername = getusername.getString("username", "glitch");
        Name.setText(Sfirstname +" "+ Slastname);
        username.setText(Susername);
    }

    //handling click events on navigation drawer items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //opens map
            case R.id.nav_Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container
                        , new Home()).commit();
                break;
            //opens profile
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container
                , new Frag_message()).commit();
                nv.setCheckedItem(R.id.nav_profile);
                break;
            //opens the survey to check whether the user is infected or not
            case R.id.nav_Condition:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container
                        , new Conditions()).commit();
                break;
            //forwards user to latest covid data website
            case R.id.nav_KeppMeDated:

                String url = "https://coronavirus.jhu.edu/region/egypt";
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);

                break;
            //logs out user of account
            case R.id.nav_Logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(drawer.this);


                builder.setMessage("Are you sure you want to log out ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences prf = getSharedPreferences("com.example.co_vision.logindata", 0);
                                File f = new File("/data/data/com.example.co_vision/shared_prefs/com.example.co_vision.logindata.xml");
                                f.delete();
                                Intent tologin = new Intent(drawer.this, loginac.class);
                                startActivity(tologin);
                                finish();
                                isDestroyed();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}