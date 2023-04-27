package com.example.dat257_project_team_1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import org.jetbrains.annotations.NotNull;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;

import static com.example.dat257_project_team_1.Constants.*;

public class MainActivity extends AppCompatActivity {

    private PlacesAPIHandler placesAPIHandler;
    private CurrentLocationHandler currentLocationHandler;
    private TextInputEditText searchBar;
    private Intent autoCompleteIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placesAPIHandler = new PlacesAPIHandler();
        currentLocationHandler = new CurrentLocationHandler(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), API_KEY);
        }

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    Place place = Autocomplete.getPlaceFromIntent(result.getData());
                    searchBar.setText(place.getAddress());
                } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR){
                    // Todo: handle error
                } else if (result.getResultCode() == RESULT_CANCELED){
                    // user canceled the operation
                }
            }
        });

        autoCompleteIntentBuilder();
        ImageView maps = (ImageView) findViewById(R.id.maps);
        ImageView sideMenu = (ImageView) findViewById(R.id.sideMenu);
        maps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                openMap();
            }
        });
        sideMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //should be something like sideMenu.bringToFront();
                openSideMenu();
            }
        });
        searchBar = findViewById(R.id.searchBar);
        searchBar.setFocusable(false);
        // Placeholder search bar text field event listener
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher.launch(autoCompleteIntent);
            }
        });
        AppCompatButton searchButton = (AppCompatButton) findViewById(R.id.searchButton);
        // Placeholder search button event listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher.launch(autoCompleteIntent);
            }
        });

        if (currentLocationHandler.isLocationPermissionGranted()) {
            currentLocationHandler.accessCurrentLocation(currentLocation -> placesAPIHandler.updateRecyclingCenters(currentLocation));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        currentLocationHandler.onRequestPermissionsResult(requestCode, grantResults);
    }

    private void autoCompleteIntentBuilder(){
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);

        autoCompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).setCountry("se").build(this);
    }

    private void openMap(){
        //TODO/DONE
        //code to open map view goes here.
        Intent intent = new Intent(this, MapViewAct.class);
        startActivity(intent);
    }

    private void openSideMenu(){
        Intent intent = new Intent(this, SideMenu.class);
        startActivity(intent);
    }
}