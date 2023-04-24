package com.example.dat257_project_team_1;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.MapView;

public class MapViewAct extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        SearchView mapSearch = (SearchView) findViewById(R.id.mapSearch);
        MapView mapView = (MapView) findViewById(R.id.mapView);

        //TODO
        //try finding the location in the search bar and show it on the map
        //mapSearch.setOnQueryTextListener();
    }
}
