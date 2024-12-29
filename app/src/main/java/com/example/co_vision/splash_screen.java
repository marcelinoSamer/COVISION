package com.example.co_vision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class splash_screen extends AppCompatActivity {



    Animation top, bottom;
    ImageView iv1;
    TextView tv, iv2;
    public static String user_login_data = "com.example.co_vision.logindata";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        Intent tologin = new Intent(splash_screen.this, loginac.class);
        Intent tomaps = new Intent (splash_screen.this, drawer.class);

        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        iv1 = findViewById(R.id.logo);
        iv2= findViewById(R.id.COVISION);

        iv2.setAnimation(bottom);
        iv1.setAnimation(top);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                //detects if the user is already logged in
                File f = new File("/data/data/com.example.co_vision/shared_prefs/com.example.co_vision.logindata.xml");
                if(f.exists())
                {
                    SharedPreferences logindata = getSharedPreferences(loginac.user_login_data, MODE_PRIVATE);
                    String username = logindata.getString("username", "");
                    if (username.equals(""))
                    {
                        startActivity(tologin);
                        finish();
                    }
                    else
                    {
                        startActivity(tomaps);
                        finish();
                    }
                }
                else
                {
                    startActivity(tologin);
                    finish();
                    isDestroyed();
                }

            }
        }, 3250);




    }

}