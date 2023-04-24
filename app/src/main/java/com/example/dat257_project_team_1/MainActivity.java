package com.example.dat257_project_team_1;

import org.jetbrains.annotations.NotNull;
import android.os.Looper;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.*;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import static com.example.dat257_project_team_1.Constants.*;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private boolean locationPermissionGranted;
    private Location currentLocation;

    private FusedLocationProviderClient fusedLocationClient;
    private PlacesAPIHandler placesAPIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView maps = (ImageView) findViewById(R.id.maps);
        ImageView sideMenu = (ImageView) findViewById(R.id.sideMenu);
        maps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                openMap();
            }
        });

        sideMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //should be somthing like sideMinu.bringToFront();
                openSideMenu();
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        placesAPIHandler = new PlacesAPIHandler();

        requestLocationPermission();

        // TEMP
        locationPermissionGranted = false;
        currentLocation = LocationFactory.createLocation(57.35494277871453, 12.126514588022303);
        placesAPIHandler.updateRecyclingCenters(currentLocation);

        if (locationPermissionGranted) {
            currentLocationInit();
            while (currentLocation == null) {
                // Wait for location
            }
            placesAPIHandler.updateRecyclingCenters(currentLocation);
        }

        TextInputEditText searchBar = (TextInputEditText) findViewById(R.id.searchBar);
        // Placeholder search bar text field event listener
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    InputMethodManager imm = (InputMethodManager)textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    textView.clearFocus();
                    return true;
                }
                return false;
            }
        });

        AppCompatButton searchButton = (AppCompatButton) findViewById(R.id.searchButton);
        // Placeholder search button event listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                } catch (Exception e) {
                    // handle exception
                }
            }
        });
    }

    private void requestLocationPermission() {
        locationPermissionGranted = false;
        // Check if the app has permission to access the user's location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION_CODE);
        }
        else {
            // Permission is already granted
            locationPermissionGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            locationPermissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void currentLocationInit() {
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATE_INTERVAL_MILLIS)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(MIN_LOCATION_UPDATE_MILLIS)
                .setMaxUpdateDelayMillis(MAX_LOCATION_UPDATE_MILLIS)
                .build();

        // Another check for location permission, needed to use requestLocationUpdates. Ignore.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(@NotNull LocationResult locationResult) {
                        Location lastLocation = locationResult.getLastLocation();
                        if (lastLocation != null) {
                            currentLocation = lastLocation;
                        }
                    }
                },
                Looper.getMainLooper());
    }

    private void openMap(){
        //TODO/DONE
        //code to open map view goes here.
        Intent intent = new Intent(this, MapViewAct.class);
        startActivity(intent);
    }
    private void openSideMenu(){
        Intent intent = new Intent(this, SideMenu.class);
        startActivity(intent);
    }
}