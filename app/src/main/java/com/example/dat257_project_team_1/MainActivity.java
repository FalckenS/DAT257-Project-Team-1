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

    private boolean locationPermissionGranted;
    private Location currentLocation;

    private FusedLocationProviderClient fusedLocationClient;
    private PlacesAPIHandler placesAPIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        placesAPIHandler = new PlacesAPIHandler();

        requestLocationPermission();
        if (locationPermissionGranted) {
            currentLocationInit();
        }

        placesAPIHandler.updateRecyclingCenters(currentLocation);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                locationPermissionGranted = true;
            } else {
                // Permission denied
                locationPermissionGranted = false;
            }
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
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        Location lastLocation = locationResult.getLastLocation();
                        if (lastLocation != null) {
                            currentLocation = lastLocation;
                        }
                    }
                },
                Looper.getMainLooper());
    }
}