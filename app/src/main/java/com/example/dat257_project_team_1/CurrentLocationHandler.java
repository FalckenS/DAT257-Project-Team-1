package com.example.dat257_project_team_1;

import org.jetbrains.annotations.NotNull;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.*;

import static com.example.dat257_project_team_1.Constants.*;

class CurrentLocationHandler {

    static void accessCurrentLocation(Activity activity, ICurrentLocationTask currentLocationTask) {
        requestLocationPermission(activity);
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
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

    static boolean isLocationPermissionGranted(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
               ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    static void requestLocationPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_PERMISSION_CODE);
        }
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_CODE);
        }
    }
}