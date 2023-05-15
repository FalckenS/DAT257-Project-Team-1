package com.example.dat257_project_team_1;

import java.util.ArrayList;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends AppCompatActivity implements IRecyclingCentersObserver {

    private MapView mapView;
    private SearchView mapSearch;
    private GoogleMap googleMap;
    private ArrayList<RecyclingCenter> recyclingCenters;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        recyclingCenters = new ArrayList<>();

        mapSearch = findViewById(R.id.mapSearch);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            googleMapInit();
        });
    }

    private void googleMapInit() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            CurrentLocationHandler.requestLocationPermission(this);
            if (!CurrentLocationHandler.isLocationPermissionGranted(this)) {
                return;
            }
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setBuildingsEnabled(false);
        googleMap.setTrafficEnabled(true);
        CurrentLocationHandler.accessCurrentLocation(this, currentLocation -> {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 10));
            PlacesAPIHandler.updateRecyclingCenters(currentLocation, this);
        });
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onStart() {
        mapView.onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
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
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void saveNewRecyclingCenters(ArrayList<RecyclingCenter> recyclingCenters) {
        this.recyclingCenters = recyclingCenters;
    }

    @Override
    public void updateRecyclingCenters() {
        if (googleMap != null) {
            for(RecyclingCenter recyclingCenter : recyclingCenters) {
                Location location = recyclingCenter.getLocation();
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title(recyclingCenter.getName())
                        .snippet("Address: " + recyclingCenter.getAddress());
                googleMap.addMarker(markerOptions);
                //System.out.println(recyclingCenter.getAddress());
            }
        }
    }
}