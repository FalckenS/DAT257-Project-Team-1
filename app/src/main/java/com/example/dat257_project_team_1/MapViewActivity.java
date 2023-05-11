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
        mapView = findViewById(R.id.mapView);
        mapSearch = findViewById(R.id.mapSearch);

        Location locationToSearchFrom = getIntent().getParcelableExtra("locationToSearchFrom", Location.class);
        boolean focusOnRecyclingCenter = getIntent().getBooleanExtra("focusOnRecyclingCenter", false);
        Location locationOfRecyclingCenter;
        if (focusOnRecyclingCenter) {
            locationOfRecyclingCenter = getIntent().getParcelableExtra("locationOfRecyclingCenter", Location.class);
        }
        else {
            locationOfRecyclingCenter = null;
        }

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                CurrentLocationHandler.requestLocationPermission(this);
                if (!CurrentLocationHandler.isLocationPermissionGranted(this)) {
                    return;
                }
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.setBuildingsEnabled(false);
            googleMap.setTrafficEnabled(true);
            PlacesAPIHandler.updateRecyclingCenters(locationToSearchFrom, this);

            if (focusOnRecyclingCenter) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationOfRecyclingCenter.getLatitude(), locationOfRecyclingCenter.getLongitude()), 15));
            }
            else {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationToSearchFrom.getLatitude(), locationToSearchFrom.getLongitude()), 12));
            }
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
                System.out.println(recyclingCenter.getAddress());
            }
        }
    }
}