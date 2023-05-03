package com.example.dat257_project_team_1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.MapStyleOptions;

public class MapViewActivity extends AppCompatActivity {

    private MapView mapView;
    private SearchView mapSearch;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        mapView = (MapView) findViewById(R.id.mapView);
        mapSearch = (SearchView) findViewById(R.id.mapSearch);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.setBuildingsEnabled(false);
            googleMap.setTrafficEnabled(true);
            googleMap.stopAnimation();
            if (    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                CurrentLocationHandler.requestLocationPermission(this);
                if (!CurrentLocationHandler.isLocationPermissionGranted(this)) {
                    return;
                }
            }
            googleMap.setMyLocationEnabled(true);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
