package com.example.co_vision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;


public class loginac extends AppCompatActivity
{

    EditText usernameedt;
    EditText passwordedt;
    Button loginbtn;
    Button signupbtn;
    FirebaseDatabase userDB;
    DatabaseReference DBreference;
    public static String user_login_data = "com.example.co_vision.logindata";
    public static Activity fa;
    TextView warning1txt, warning2txt;
    FusedLocationProviderClient locationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionGranted = false;
    private Location Clocation;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            fa = this;
            File deletePrefFile = new File("com.example.co_vision.logindata");
            deletePrefFile.delete();
            usernameedt = findViewById(R.id.TextPersonName);
            passwordedt = findViewById(R.id.TextPassword);
            loginbtn = findViewById(R.id.loginbtn);
            signupbtn = findViewById(R.id.signupbtn);
            warning1txt = findViewById(R.id.warning1txt);
            warning2txt = findViewById(R.id.warning2txt);

            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //warning if text boxes are empty
                    warning2txt.setVisibility(View.GONE);
                    warning1txt.setVisibility(View.GONE);
                    String Susername = usernameedt.getText().toString().trim();
                    String Spassword = passwordedt.getText().toString().trim();
                    if(Susername.isEmpty() && Spassword.isEmpty())
                    {
                        passwordedt.setError("This field is required");
                        usernameedt.setError("This field is required");
                    }
                    else if(Spassword.isEmpty())
                    {
                        passwordedt.setError("This field is required");
                    }
                    else if(Susername.isEmpty())
                    {
                       usernameedt.setError("This field is required");
                    }
                    else if (!Spassword.isEmpty() && !Susername.isEmpty())
                    {


                        userDB = FirebaseDatabase.getInstance("FIREBASE_DATABASE_LINK");
                        DBreference = userDB.getInstance("FIREBASE_DATABASE_LINK").getReference().child("userdata");
                        DBreference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                                if (snapshot.child(Susername).child("username").exists())
                                {

                                    if( snapshot.child(Susername).child("username").getValue().equals(Susername)&& snapshot.child(Susername).child("password").getValue().equals(Spassword))
                                    {

                                        //saves data to firebase database
                                        SharedPreferences.Editor parseinlogindata = getSharedPreferences(user_login_data, MODE_PRIVATE).edit();
                                        parseinlogindata.putString("username", Susername);
                                        parseinlogindata.putString("password", Spassword);
                                        String savedusername = snapshot.child(Susername).child("username").getValue().toString();
                                        String savedpassword = snapshot.child(Susername).child("password").getValue().toString();
                                        parseinlogindata.putString("firstname", snapshot.child(Susername).child("firstname").getValue().toString());
                                        parseinlogindata.putString("lastname", snapshot.child(Susername).child("lastname").getValue().toString());
                                        parseinlogindata.putString("email", snapshot.child(Susername).child("email").getValue().toString());
                                        parseinlogindata.putString("age", snapshot.child(Susername).child("age").getValue().toString());
                                        Intent tomaps = new Intent(loginac.this, com.example.co_vision.drawer.class);
                                        parseinlogindata.commit();

                                        startActivity(tomaps);
                                        finish();
                                    }

                                    else if (!snapshot.child(Susername).child("password").getValue().equals(Spassword))
                                    {
                                        warning2txt.setVisibility(View.VISIBLE);
                                    }

                                }
                                else if (!snapshot.child(Susername).child("username").exists())
                                {
                                    warning2txt.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                warning2txt.setVisibility(View.VISIBLE);

                            }
                        });



                    }

                }
            });

            signupbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //opens signup
                    Intent tosignup = new Intent(loginac.this, com.example.co_vision.signupac.class);
                    startActivity(tosignup);


                }
            });



    }

}
