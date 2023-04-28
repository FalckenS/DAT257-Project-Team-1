package com.example.dat257_project_team_1;

import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import org.jetbrains.annotations.NotNull;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PlacesAPIHandler placesAPIHandler;
    private CurrentLocationHandler currentLocationHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        placesAPIHandler = new PlacesAPIHandler();
        currentLocationHandler = new CurrentLocationHandler(this);

        if (currentLocationHandler.isLocationPermissionGranted()) {
            currentLocationHandler.accessCurrentLocation(currentLocation -> placesAPIHandler.updateRecyclingCenters(currentLocation));
        }

        ImageView maps = (ImageView) findViewById(R.id.maps);
        ImageView sideMenu = (ImageView) findViewById(R.id.sideMenu);
        ScrollView scrollView = (ScrollView) findViewById(R.id.rv_list);

        TextView locationName1 = (TextView) findViewById(R.id.locationName1);
        TextView locationName2 = (TextView) findViewById(R.id.locationName2);
        TextView locationName3 = (TextView) findViewById(R.id.locationName3);
        TextView locationName4 = (TextView) findViewById(R.id.locationName4);
        TextView locationName5 = (TextView) findViewById(R.id.locationName5);
        TextView locationName6 = (TextView) findViewById(R.id.locationName6);
        TextView locationName7 = (TextView) findViewById(R.id.locationName7);
        TextView locationName8 = (TextView) findViewById(R.id.locationName8);
        TextView locationName9 = (TextView) findViewById(R.id.locationName9);
        TextView locationName10 = (TextView) findViewById(R.id.locationName10);

        ArrayList<TextView> locationNameList = new ArrayList<>();
        locationNameList.add(locationName1);
        locationNameList.add(locationName2);
        locationNameList.add(locationName3);
        locationNameList.add(locationName4);
        locationNameList.add(locationName5);
        locationNameList.add(locationName6);
        locationNameList.add(locationName7);
        locationNameList.add(locationName8);
        locationNameList.add(locationName9);
        locationNameList.add(locationName10);

        TextView cardAddress1 = (TextView) findViewById(R.id.cardAddress1);
        TextView cardAddress2 = (TextView) findViewById(R.id.cardAddress2);
        TextView cardAddress3 = (TextView) findViewById(R.id.cardAddress3);
        TextView cardAddress4 = (TextView) findViewById(R.id.cardAddress4);
        TextView cardAddress5 = (TextView) findViewById(R.id.cardAddress5);
        TextView cardAddress6 = (TextView) findViewById(R.id.cardAddress6);
        TextView cardAddress7 = (TextView) findViewById(R.id.cardAddress7);
        TextView cardAddress8 = (TextView) findViewById(R.id.cardAddress8);
        TextView cardAddress9 = (TextView) findViewById(R.id.cardAddress9);
        TextView cardAddress10 = (TextView) findViewById(R.id.cardAddress10);

        ArrayList<TextView> cardAddressList = new ArrayList<>();
        cardAddressList.add(cardAddress1);
        cardAddressList.add(cardAddress2);
        cardAddressList.add(cardAddress3);
        cardAddressList.add(cardAddress4);
        cardAddressList.add(cardAddress5);
        cardAddressList.add(cardAddress6);
        cardAddressList.add(cardAddress7);
        cardAddressList.add(cardAddress8);
        cardAddressList.add(cardAddress9);
        cardAddressList.add(cardAddress10);


        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        inflater.inflate(R.layout.expandable_card, null, false);

        populateCards(locationNameList, cardAddressList);


        maps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                openMap();
            }
        });
        sideMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                openSideMenu();
            }
        });
        TextInputEditText searchBar = (TextInputEditText) findViewById(R.id.searchBar);
        // Placeholder search bar text field event listener
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    textView.clearFocus();
                    return true;
                }
                return false;
            }
        });
        AppCompatButton searchButton = (AppCompatButton) findViewById(R.id.searchButton);
        // Placeholder search button event listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                } catch (Exception e) {
                    // handle exception
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        currentLocationHandler.onRequestPermissionsResult(requestCode, grantResults);
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

    private void populateCards(ArrayList<TextView> lnl, ArrayList<TextView> adl){
        for (int i = 0; i < placesAPIHandler.getRecyclingCenters().size(); i++) {
            System.out.println(placesAPIHandler.getRecyclingCenters().get(i).getAddress());
            if (i < lnl.size() && i < adl.size()){
                System.out.println(i);
                lnl.get(i).setText(placesAPIHandler.getRecyclingCenters().get(i).getName());
                adl.get(i).setText(placesAPIHandler.getRecyclingCenters().get(i).getAddress());
            }
        }

    }
}