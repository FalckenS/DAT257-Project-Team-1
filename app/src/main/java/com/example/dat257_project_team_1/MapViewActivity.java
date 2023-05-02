package com.example.dat257_project_team_1;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.*;

public class MapViewActivity extends AppCompatActivity {

    private MapView mapView;
    private SearchView mapSearch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        GoogleMapOptions options = new GoogleMapOptions()
                .compassEnabled(true)
                .mapType(GoogleMap.MAP_TYPE_NORMAL)
                .zoomControlsEnabled(true);
        mapView.getMapAsync(googleMap -> {
            // Customize the map's features here
        });

        mapSearch = (SearchView) findViewById(R.id.mapSearch);

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
