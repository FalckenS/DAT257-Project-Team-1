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
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.gms.location.*;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.dat257_project_team_1.Constants.*;

public class MainActivity extends AppCompatActivity {

    private boolean coarseLocationPermissionGranted = false;
    private boolean fineLocationPermissionGranted = false;

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

        TextInputEditText searchBar = (TextInputEditText) findViewById(R.id.searchBar);
        // Placeholder search bar text field event listener
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                } catch (Exception e) {
                    // handle exception
                }
            }
        });

        requestLocationPermission();

        if (coarseLocationPermissionGranted && fineLocationPermissionGranted) {
            updateCurrentLocation(new ICurrentLocationTask() {
                @Override
                public void currentLocationTask(Location currentLocation) {
                    placesAPIHandler.updateRecyclingCenters(currentLocation);
                }
            });
        }
    }

    private void requestLocationPermission() {

        coarseLocationPermissionGranted = false;
        fineLocationPermissionGranted = false;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_PERMISSION_CODE);
        }
        else {
            coarseLocationPermissionGranted = true;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_CODE);
        }
        else {
            fineLocationPermissionGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == COARSE_LOCATION_PERMISSION_CODE) {
            coarseLocationPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (requestCode == FINE_LOCATION_PERMISSION_CODE) {
            fineLocationPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void updateCurrentLocation(ICurrentLocationTask currentLocationTask) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(
                new LocationRequest.Builder(0)
                        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                        .build(),
                new LocationCallback() {
                    @Override
                    public void onLocationResult(@NotNull LocationResult locationResult) {
                        currentLocationTask.currentLocationTask(locationResult.getLastLocation());
                        fusedLocationClient.removeLocationUpdates(this);
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