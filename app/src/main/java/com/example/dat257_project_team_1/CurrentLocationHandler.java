package com.example.dat257_project_team_1;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.*;
import org.jetbrains.annotations.NotNull;

import static com.example.dat257_project_team_1.Constants.*;

public class CurrentLocationHandler {

    private final Activity mainActivity;
    private boolean coarseLocationPermissionGranted;
    private boolean fineLocationPermissionGranted;
    private final FusedLocationProviderClient fusedLocationClient;

    public CurrentLocationHandler(Activity mainActivity) {
        this.mainActivity = mainActivity;
        coarseLocationPermissionGranted = false;
        fineLocationPermissionGranted = false;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity);
        requestLocationPermission();
    }

    boolean isLocationPermissionGranted() {
        return coarseLocationPermissionGranted && fineLocationPermissionGranted;
    }

    void accessCurrentLocation(ICurrentLocationTask currentLocationTask) {

        fusedLocationClient.flushLocations();
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            if (!isLocationPermissionGranted()) {
                return;
            }
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

    void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == COARSE_LOCATION_PERMISSION_CODE) {
            coarseLocationPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (requestCode == FINE_LOCATION_PERMISSION_CODE) {
            fineLocationPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestLocationPermission() {
        coarseLocationPermissionGranted = false;
        fineLocationPermissionGranted = false;

        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_PERMISSION_CODE);
        }
        else {
            coarseLocationPermissionGranted = true;
        }
        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_CODE);
        }
        else {
            fineLocationPermissionGranted = true;
        }
    }
}