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

class CurrentLocationHandler {

    private final Activity activity;
    private final FusedLocationProviderClient fusedLocationClient;

    public CurrentLocationHandler(Activity activity) {
        this.activity = activity;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        requestLocationPermission(activity);
    }

    void accessCurrentLocation(ICurrentLocationTask currentLocationTask) {

        fusedLocationClient.flushLocations();

        if (    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission(activity);
            if (!isLocationPermissionGranted(activity)) {
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

    /*---------------------------------------------------- Static ----------------------------------------------------*/

    static boolean isLocationPermissionGranted(Activity activity) {
        if (    ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        else {
            return true;
        }
    }

    static void requestLocationPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_PERMISSION_CODE);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_CODE);
        }
    }
}