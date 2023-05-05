package com.example.dat257_project_team_1;

import android.location.Location;
import org.jetbrains.annotations.NotNull;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.*;

import static com.example.dat257_project_team_1.Constants.*;

class CurrentLocationHelper {

    static void accessCurrentLocation(Activity activity, ICurrentLocationTask currentLocationTask) {
        requestLocationPermission(activity);
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

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

    static double calculateDistance(Location location1, Location location2) {
        double endLat = location1.getLatitude();
        double endLng = location1.getLongitude();
        double startLat = location2.getLatitude();
        double startLng = location2.getLongitude();

        double dLat = Math.toRadians(endLat - startLat);
        double dLng = Math.toRadians(endLng - startLng);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat)) * Math.sin(dLng/2) * Math.sin(dLng/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return 6371 * c;
    }

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