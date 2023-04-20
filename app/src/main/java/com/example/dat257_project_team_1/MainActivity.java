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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.*;
import com.google.android.libraries.places.api.model.*;
import com.google.android.libraries.places.api.net.*;
import com.google.android.libraries.places.api.Places;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.dat257_project_team_1.Constants.*;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private PlacesClient placesClient;
    OkHttpClient okHttpClient;
    private boolean locationPermissionGranted = false;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Places.initialize(getApplicationContext(), API_KEY);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        okHttpClient = new OkHttpClient().newBuilder().build();
        placesClient = Places.createClient(this);

        requestLocationPermission();

        if (locationPermissionGranted) {
            updateCurrentLocation();
        }

        getRecyclingCenters();
    }

    private void requestLocationPermission() {
        // Check if the app has permission to access the user's location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION_CODE);
        } else {
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
                        if (currentLocation == null) {
                            return;
                        }
                        // TODO update UI with new results
                    }
                },
                Looper.getMainLooper());
    }

    private void getRecyclingCenters() {
        // https://developers.google.com/maps/documentation/places/web-service/search-nearby
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        String placesAPIUrl = constructPlacesAPIUrl();

        Request request = new Request.Builder()
                .url(placesAPIUrl)
                .method("get", body)
                .build();

        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
        }
        catch (Exception e) {

        }
    }

    @NotNull
    private static String constructPlacesAPIUrl() {
        String coordinatesSouth = "";
        String coordinatesEast = "";
        String location = coordinatesSouth + "%2C" + coordinatesEast;
        String keyword = "recycling center";
        String opennow = "true";
        String rankby = "distance";
        String placesAPIUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "&location=" + location +
                "&keyword=" + keyword +
                "&opennow=" + opennow +
                "&rankby=" + rankby +
                "&key=" + API_KEY;
        return placesAPIUrl;
    }
}