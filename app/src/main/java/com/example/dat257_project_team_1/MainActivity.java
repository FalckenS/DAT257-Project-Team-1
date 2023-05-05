package com.example.dat257_project_team_1;

import android.location.Location;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.dat257_project_team_1.Constants.*;

public class MainActivity extends AppCompatActivity implements IRecyclingCentersObserver {

    private TextInputEditText searchBar;
    private Intent autoCompleteIntent;

    private boolean cardsExist;
    private ArrayList<CardView> cardList;
    private ArrayList<TextView> locationNameList;
    private ArrayList<TextView> cardAddressList;

    private ImageView maps;
    private ImageView sideMenu;
    private ScrollView scrollView;

    private ArrayList<RecyclingCenter> recyclingCenters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclingCenters = new ArrayList<>();

        cardsExist = false;
        cardList = new ArrayList<>();
        locationNameList = new ArrayList<>();
        cardAddressList = new ArrayList<>();

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), API_KEY);
        }

        maps = (ImageView) findViewById(R.id.maps);
        sideMenu = (ImageView) findViewById(R.id.sideMenu);
        scrollView = (ScrollView) findViewById(R.id.rv_list);

        scrollView.setVisibility(View.GONE);
        initializeCards();

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

        if (CurrentLocationHelper.isLocationPermissionGranted(this)) {
            CurrentLocationHelper.accessCurrentLocation(this, currentLocation -> PlacesAPIHandler.updateRecyclingCenters(currentLocation, this));
            /*
            currentLocationHandler.accessCurrentLocation(this, new ICurrentLocationTask() {
                @Override
                public void currentLocationTask(Location currentLocation) {
                    placesAPIHandler.updateRecyclingCenters(currentLocation);
                }
            });
            */
        }
    }

    /*--------------------------------------------------- Private ---------------------------------------------------*/

    private void autoCompleteIntentBuilder(){
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
        autoCompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).setCountry("se").build(this);
    }

    private void openMap(){
        // Code to open map view

        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }

    private void openSideMenu(){
        Intent intent = new Intent(this, SideMenuActivity.class);
        startActivity(intent);
    }

    private void initializeCards() {
        CardView card1 = (CardView) findViewById(R.id.card1);
        CardView card2 = (CardView) findViewById(R.id.card2);
        CardView card3 = (CardView) findViewById(R.id.card3);
        CardView card4 = (CardView) findViewById(R.id.card4);
        CardView card5 = (CardView) findViewById(R.id.card5);
        CardView card6 = (CardView) findViewById(R.id.card6);
        CardView card7 = (CardView) findViewById(R.id.card7);
        CardView card8 = (CardView) findViewById(R.id.card8);
        CardView card9 = (CardView) findViewById(R.id.card9);
        CardView card10 = (CardView) findViewById(R.id.card10);

        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);
        cardList.add(card6);
        cardList.add(card7);
        cardList.add(card8);
        cardList.add(card9);
        cardList.add(card10);

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
    }

    private void showCards(int numOfCardsToShow) {
        for (int i = 0; i < numOfCardsToShow; i++) {
            if (!(i < cardList.size())) {
                break;
            }
            cardList.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = numOfCardsToShow; i < 10; i++) {
            if (!(i < cardList.size())) {
                break;
            }
            cardList.get(i).setVisibility(View.GONE);
        }
    }

    /*--------------------------------------------------- Other ---------------------------------------------------*/

    @Override
    public void saveNewRecyclingCenters(ArrayList<RecyclingCenter> recyclingCenters) {
        this.recyclingCenters = recyclingCenters;
    }

    @Override
    public void updateRecyclingCenters() {
        if (!cardsExist) {
            scrollView.setVisibility(View.VISIBLE);
            cardsExist = true;
        }
        showCards(recyclingCenters.size());
        for (int i = 0; i < recyclingCenters.size(); i++) {
            if (!(i < locationNameList.size() && i < cardAddressList.size())) {
                break;
            }
            RecyclingCenter recyclingCenter = recyclingCenters.get(i);
            locationNameList.get(i).setText(recyclingCenter.getName());
            cardAddressList.get(i).setText(recyclingCenter.getAddress());
        }
    }
}