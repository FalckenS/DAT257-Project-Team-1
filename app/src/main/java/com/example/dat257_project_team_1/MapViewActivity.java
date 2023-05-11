package com.example.dat257_project_team_1;

import java.util.*;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

public class MapViewActivity extends AppCompatActivity implements IRecyclingCentersObserver {

    private MapView mapView;
    private EditText mapSearch;
    private GoogleMap googleMap;
    private ArrayList<RecyclingCenter> recyclingCenters;
    private Intent autoCompleteIntent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        recyclingCenters = new ArrayList<>();
        mapView = findViewById(R.id.mapView);
        mapSearch = findViewById(R.id.mapSearch);
        ImageView mapSetCurrentLocationMarker = findViewById(R.id.mapSetCurrentLocationMarker);
        autoCompleteIntentBuilder();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK){
                assert result.getData() != null;
                Place place = Autocomplete.getPlaceFromIntent(result.getData());
                mapSearch.setText(place.getAddress());
                Location searchBarLocation = buildLocationObject(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude);
                PlacesAPIHandler.updateRecyclingCenters(searchBarLocation, MapViewActivity.this);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 10));
            }
            else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR){
                // Todo: handle error
            }
            else if (result.getResultCode() == RESULT_CANCELED){
                // user canceled the operation
            }
        });

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

        mapSearch.setFocusable(false);
        mapSearch.setOnClickListener(view -> activityResultLauncher.launch(autoCompleteIntent));

        mapSetCurrentLocationMarker.setOnClickListener(view -> {
            if (CurrentLocationHandler.isLocationPermissionGranted(this)) {
                mapSearch.setText("Current location");
                CurrentLocationHandler.accessCurrentLocation(this, currentLocation -> {
                    PlacesAPIHandler.updateRecyclingCenters(currentLocation, this);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 10));
                });
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

    private void autoCompleteIntentBuilder(){
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
        autoCompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).setCountries(Collections.singletonList("se")).build(this);
    }

    private Location buildLocationObject(double latitude, double longitude){
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
    }
}