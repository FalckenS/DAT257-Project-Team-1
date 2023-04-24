package com.example.dat257_project_team_1;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import org.jetbrains.annotations.NotNull;
import android.os.Looper;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.*;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.example.dat257_project_team_1.Constants.*;
import com.example.dat257_project_team_1.ExpandableCard;

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
        CardView card1 = (CardView) findViewById(R.id.card1);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View cardView = inflater.inflate(R.layout.expandable_card, null);
        ArrayList<ExpandableCard> cardsList = new ArrayList<>();
        addCardViews(cardsList);

        card1.addView(cardView);

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
        //TODO
        //code to open map view goes here.
    }
    private void openSideMenu(){
        Intent intent = new Intent(this, SideMenu.class);
        startActivity(intent);
    }

    private void addCardViews(ArrayList<ExpandableCard> x){
        for (int i = 0; i < 10; i++){
            x.add(new ExpandableCard());
        }
    }

}