package com.example.dat257_project_team_1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.*;

import static com.example.dat257_project_team_1.Constants.*;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private boolean locationPermissionGranted = false;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        requestLocationPermission();

        if (locationPermissionGranted) {
            updateCurrentLocation();
        }
    }

    private void requestLocationPermission() {
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

    private void updateCurrentLocation() {

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
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        currentLocation = locationResult.getLastLocation();
                        // TODO update UI with new results
                    }
                },
                Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                locationPermissionGranted = true;
            }
            else {
                // Permission denied
                locationPermissionGranted = false;
            }
        }
    }
}