package com.example.co_vision;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class Home extends Fragment {


    //declarations
    private GoogleMap mMap;
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionGranted = false;
    private Location Clocation;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    FirebaseDatabase userDB;
    DatabaseReference DBreference;
    LatLng latlng = new LatLng(-34, 151);
    LatLng latlng2 = new LatLng(30.083332, 30.916672);
    LatLng latlng3 = new LatLng(29.916668, 30.750000);
    LatLng latlng4 = new LatLng(29.900000, 30.860000);
    LatLng latlng5 = new LatLng(29.816128, 30.550520);
    LatLng latlng6 = new LatLng(29.711348, 30.661300);
    LatLng latlng7 = new LatLng(29.016456, 30.850455);
    LatLng latlng8 = new LatLng(29.165973, 30.12345);
    LatLng latlng9 = new LatLng(29.861189, 30.455034);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        //requests permission for GPS usage
        getDeviceLocation();

        //getting user location
        LocationManager locationManager =
                (LocationManager) Home.this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationProvider provider =
                locationManager.getProvider(LocationManager.GPS_PROVIDER);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //saving latitudes and longitudes
            Clocation = locationManager.getLastKnownLocation("gps");
            double Clatitude = Clocation.getLatitude();
            double Clongtitude = Clocation.getLongitude();
            String Slatitude = String.valueOf(Clatitude);
            String Slongititude = String.valueOf(Clongtitude);
            SharedPreferences.Editor saveloc = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0).edit();
            saveloc.putString("latitude", Slatitude);
            saveloc.putString("longtitude", Slongititude);
            saveloc.commit();

        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (locationAvailability.isLocationAvailable()) {

                    //checking availability of user location
                    if (ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Clocation = locationManager.getLastKnownLocation("gps");
                    double Clatitude = Clocation.getLatitude();
                    double Clongtitude = Clocation.getLongitude();
                    String Slatitude = String.valueOf(Clatitude);
                    String Slongititude = String.valueOf(Clongtitude);
                    SharedPreferences.Editor saveloc = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0).edit();
                    saveloc.putString("latitude", Slatitude);
                    saveloc.putString("longtitude", Slongititude);
                } else {
                    if (ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Clocation = locationManager.getLastKnownLocation("gps");
                    double Clatitude = Clocation.getLatitude();
                    double Clongtitude = Clocation.getLongitude();
                    String Slatitude = String.valueOf(Clatitude);
                    String Slongititude = String.valueOf(Clongtitude);
                    SharedPreferences.Editor saveloc = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0).edit();
                    saveloc.putString("latitude", Slatitude);
                    saveloc.putString("longtitude", Slongititude);
                }
            }

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

            }
        };

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);

        if (ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(3000);
        }
        FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(Home.this.getActivity());
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //Toast.makeText(Home.this.getActivity(), "alo", Toast.LENGTH_LONG).show();
                if (ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Clocation = locationManager.getLastKnownLocation("gps");
                locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(3000);
                Double dlatitude = Clocation.getLatitude();
                String Slatitude = String.valueOf(dlatitude);
                Double dlongititude = Clocation.getLongitude();
                String Slongititude = String.valueOf(dlongititude);


                SharedPreferences getlogin = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0);
                String Susername = getlogin.getString("username", "no such user");

                //saving user location in firebase real time database
                userDB = FirebaseDatabase.getInstance("FIREBASE_DATABASE_LINK");
                DBreference = userDB.getReference("userdata");
                DBreference.child(Susername).child("Latitude").setValue(Slatitude);
                DBreference.child(Susername).child("Longtitude").setValue(Slongititude);
            }
        });

        locationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

        return view;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map_fragment);

        //automatically intialize the map and view it
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                if (ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(Home.this.getActivity());
                locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(3000);
                locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        //gets user last known location
                        Clocation = location;
                        locationRequest = LocationRequest.create();
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationRequest.setInterval(3000);
                        Double dlatitude = Clocation.getLatitude();
                        String Slatitude = String.valueOf(dlatitude);
                        Double dlongititude = Clocation.getLongitude();
                        String Slongititude = String.valueOf(dlongititude);
                        SharedPreferences.Editor saveloc = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0).edit();
                        saveloc.putString("latitude", Slatitude);
                        saveloc.putString("longtitude", Slongititude);
                        saveloc.commit();

                        SharedPreferences getlogin = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0);
                        String Susername = getlogin.getString("username", "no such user");

                        userDB = FirebaseDatabase.getInstance("FIREBASE_DATABASE_LINK");
                        DBreference = userDB.getReference("userdata");
                        DBreference.child(Susername).child("Latitude").setValue(Slatitude);
                        DBreference.child(Susername).child("Longtitude").setValue(Slongititude);
                    }
                });

                locationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                //draws the circle around everyuser
                ArrayList<LatLng> locations = new ArrayList<>();
                locations.add(latlng);
                locations.add(latlng2);
                locations.add(latlng3);
                locations.add(latlng4);
                locations.add(latlng5);
                locations.add(latlng6);
                locations.add(latlng7);
                locations.add(latlng8);
                locations.add(latlng9);
                mMap = googleMap;
                for (int i = 0; i < locations.size(); i++) {
                    CircleOptions circle = new CircleOptions();
                    circle.center(locations.get(i))
                            .radius(10000)
                            .fillColor(0x55ff0000)
                            .strokeWidth(2)
                            .strokeColor(Color.BLACK);
                    mMap.addCircle(circle);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations.get(i), 7));

                }


            }
        });
    }

    private void getDeviceLocation() {

        //gets permission for GPS usage
        String[] permissions = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(Home.this.getActivity(), ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(Home.this.getActivity(), ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;

                initMap();
            } else {
                ActivityCompat.requestPermissions(Home.this.getActivity(),
                        permissions
                        , LOCATION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(Home.this.getActivity(),
                    permissions
                    , LOCATION_REQUEST_CODE);
        }
    }


}
