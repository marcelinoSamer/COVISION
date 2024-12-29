package com.example.co_vision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class signupac extends AppCompatActivity {

    FirebaseDatabase userDB;
    DatabaseReference DBreference;
    EditText firstname;
    EditText lastname;
    EditText username;
    EditText email, age;
    Button signupbtn;
    String Sfirstname, Slastname, Susername, Semail, Spassword, Sage;
    int aage = 0;
    TextView warning;
    EditText password;
    private static final int LOCATION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionGranted = false;
    private Location Clocation;
    FusedLocationProviderClient locationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupac);



        warning = findViewById(R.id.warningtxt);

        password = findViewById(R.id.passwordedt);

        signupbtn = findViewById(R.id.signupbtn);

        firstname = findViewById(R.id.editTextfirstnamesgnup);

        lastname = findViewById(R.id.editTextlastnamesgnup);

        username = findViewById(R.id.editTextusernamesgnup);

        email = findViewById(R.id.editTextEmailsgnup);

        age = findViewById(R.id.editTextAgesgnup);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sfirstname = firstname.getText().toString().trim();
                Slastname = lastname.getText().toString().trim();
                Susername = username.getText().toString().trim();
                Semail = email.getText().toString().trim();
                Spassword = password.getText().toString().trim();
                loginac.fa.finish();


                if(!Sfirstname.isEmpty() && !Slastname.isEmpty() && !Susername.isEmpty() && !Semail.isEmpty() && !age.equals("") && !Spassword.isEmpty())
                {
                    userDB = FirebaseDatabase.getInstance("FIREBASE_DATABASE_LINK");
                    DBreference = userDB.getReference("userdata");
                    String Sage = age.getText().toString().trim();
                    aage = Integer.parseInt(Sage);
                    int code = 1;

                    //saving data into a sharedpreference file
                    SharedPreferences.Editor signupdata = getSharedPreferences(loginac.user_login_data, MODE_PRIVATE).edit();
                    signupdata.putString("username" , Susername);
                    signupdata.putString("firstname", Sfirstname);
                    signupdata.putString("lastname", Slastname);
                    signupdata.putString("email", Semail);
                    signupdata.putString("password", Spassword);
                    signupdata.putString("age", Sage);
                    signupdata.commit();
                    userdata user = new userdata(Sfirstname, Slastname, Susername, Semail, aage, Spassword);
                    DBreference.child(Susername).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                            locationCallback = new LocationCallback(){
                                @Override
                                public void onLocationAvailability(LocationAvailability locationAvailability) {
                                    super.onLocationAvailability(locationAvailability);
                                    if(locationAvailability.isLocationAvailable()){

                                    }else {

                                    }
                                }

                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                }
                            };

                            locationRequest = LocationRequest.create();
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            locationRequest.setInterval(60000);

                            if (ActivityCompat.checkSelfPermission(signupac.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(signupac.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(signupac.this);
                            locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, signupac.this.getMainLooper());
                            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {

                                    //gets user current location
                                    Clocation = location;
                                    Double dlatitude = Clocation.getLatitude();
                                    String Slatitude = String.valueOf(dlatitude);
                                    Double dlongititude = Clocation.getLongitude();
                                    String Slongititude = String.valueOf(dlongititude);
                                    SharedPreferences.Editor saveloc = getSharedPreferences("com.example.co_vision.logindata", 0).edit();
                                    saveloc.putString("latitude", Slatitude);
                                    saveloc.putString("longtitude", Slongititude);
                                    saveloc.putString("state", "Safe");
                                    saveloc.commit();

                                    SharedPreferences getlogin = getSharedPreferences("com.example.co_vision.logindata", 0);
                                    String Susername = getlogin.getString("username", "no such user");

                                    //saves location to firebase database
                                    userDB = FirebaseDatabase.getInstance("FIREBASE_DATABASE_LINK");
                                    DBreference = userDB.getReference("userdata");
                                    DBreference.child(Susername).child("Latitude").setValue(Slatitude);
                                    DBreference.child(Susername).child("Longtitude").setValue(Slongititude);
                                    DBreference.child(Susername).child("state").setValue("Safe");
                                }
                            });

                            locationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });

                        }
                    });

                    //proceeds to maps activity
                    Intent tomap = new Intent(signupac.this, com.example.co_vision.drawer.class);
                    startActivity(tomap);
                    finish();

                }
                else
                {
                    warning.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}