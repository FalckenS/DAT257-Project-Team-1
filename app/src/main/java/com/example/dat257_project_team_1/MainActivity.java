package com.example.dat257_project_team_1;

import java.text.DecimalFormat;
import java.util.*;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.location.Location;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import static com.example.dat257_project_team_1.Constants.*;

public class MainActivity extends AppCompatActivity implements IRecyclingCentersObserver {

    private ScrollView scrollView;
    private EditText searchBar;
    private Intent autoCompleteIntent;
    private ArrayList<RecyclingCenter> recyclingCenters;
    private boolean cardsExist;
    private ArrayList<CardView> cardList;
    private ArrayList<TextView> cardNameList;
    private ArrayList<TextView> cardAddressList;
    private ArrayList<TextView> cardDistanceList;
    private ArrayList<Location> cardLocationList;
    private Location locationSearchPoint;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclingCenters = new ArrayList<>();
        cardsExist = false;

        cardList = new ArrayList<>();
        cardNameList = new ArrayList<>();
        cardAddressList = new ArrayList<>();
        cardDistanceList = new ArrayList<>();
        cardLocationList = new ArrayList<>();

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), API_KEY);
        }

        scrollView = findViewById(R.id.rv_list);
        scrollView.setVisibility(View.GONE);
        initializeCards();

        findViewById(R.id.maps).setOnClickListener(v -> {if (locationSearchPoint != null) {openMap(locationSearchPoint);}});
        findViewById(R.id.sideMenu).setOnClickListener(v -> openSideMenu());

        autoCompleteIntentBuilder();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK){
                assert result.getData() != null;
                Place place = Autocomplete.getPlaceFromIntent(result.getData());
                searchBar.setText(place.getAddress());
                locationSearchPoint = buildLocationObject(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude);
                PlacesAPIHandler.updateRecyclingCenters(locationSearchPoint, this);
            }
            else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR){
                // Todo: handle error
            }
            else if (result.getResultCode() == RESULT_CANCELED){
                // user canceled the operation
            }
        });

        searchBar = findViewById(R.id.searchBar);
        searchBar.setFocusable(false);
        // Placeholder search bar text field event listener
        searchBar.setOnClickListener(view -> activityResultLauncher.launch(autoCompleteIntent));

        //AppCompatButton searchButton = findViewById(R.id.searchButton);
        // Placeholder search button event listener
        //searchButton.setOnClickListener(view -> activityResultLauncher.launch(autoCompleteIntent));

        findViewById(R.id.setCurrentLocationMarker).setOnClickListener(view -> {
            if (CurrentLocationHandler.isLocationPermissionGranted(this)) {
                searchBar.setText("Current location");
                updateRecyclingCentersFromCurrentLocation();
            }
        });

        if (CurrentLocationHandler.isLocationPermissionGranted(this)) {
            updateRecyclingCentersFromCurrentLocation();
        }
    }

    /*--------------------------------------------------- Private ---------------------------------------------------*/

    private void initializeCards() {
        CardView card1 = findViewById(R.id.card1);
        CardView card2 = findViewById(R.id.card2);
        CardView card3 = findViewById(R.id.card3);
        CardView card4 = findViewById(R.id.card4);
        CardView card5 = findViewById(R.id.card5);
        CardView card6 = findViewById(R.id.card6);
        CardView card7 = findViewById(R.id.card7);
        CardView card8 = findViewById(R.id.card8);
        CardView card9 = findViewById(R.id.card9);
        CardView card10 = findViewById(R.id.card10);

        card1.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(0)));
        card2.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(1)));
        card3.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(2)));
        card4.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(3)));
        card5.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(4)));
        card6.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(5)));
        card7.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(6)));
        card8.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(7)));
        card9.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(8)));
        card10.setOnClickListener(view -> openMap(locationSearchPoint, cardLocationList.get(9)));

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

        /*TextView locationName1 = findViewById(R.id.locationName1);
        TextView locationName2 = findViewById(R.id.locationName2);
        TextView locationName3 = findViewById(R.id.locationName3);
        TextView locationName4 = findViewById(R.id.locationName4);
        TextView locationName5 = findViewById(R.id.locationName5);
        TextView locationName6 = findViewById(R.id.locationName6);
        TextView locationName7 = findViewById(R.id.locationName7);
        TextView locationName8 = findViewById(R.id.locationName8);
        TextView locationName9 = findViewById(R.id.locationName9);
        TextView locationName10 = findViewById(R.id.locationName10);*/

        cardNameList.add(findViewById(R.id.cardName1));
        cardNameList.add(findViewById(R.id.cardName2));
        cardNameList.add(findViewById(R.id.cardName3));
        cardNameList.add(findViewById(R.id.cardName4));
        cardNameList.add(findViewById(R.id.cardName5));
        cardNameList.add(findViewById(R.id.cardName6));
        cardNameList.add(findViewById(R.id.cardName7));
        cardNameList.add(findViewById(R.id.cardName8));
        cardNameList.add(findViewById(R.id.cardName9));
        cardNameList.add(findViewById(R.id.cardName10));

        /*TextView cardAddress1 = findViewById(R.id.cardAddress1);
        TextView cardAddress2 = findViewById(R.id.cardAddress2);
        TextView cardAddress3 = findViewById(R.id.cardAddress3);
        TextView cardAddress4 = findViewById(R.id.cardAddress4);
        TextView cardAddress5 = findViewById(R.id.cardAddress5);
        TextView cardAddress6 = findViewById(R.id.cardAddress6);
        TextView cardAddress7 = findViewById(R.id.cardAddress7);
        TextView cardAddress8 = findViewById(R.id.cardAddress8);
        TextView cardAddress9 = findViewById(R.id.cardAddress9);
        TextView cardAddress10 = findViewById(R.id.cardAddress10);*/

        cardAddressList.add(findViewById(R.id.cardAddress1));
        cardAddressList.add(findViewById(R.id.cardAddress2));
        cardAddressList.add(findViewById(R.id.cardAddress3));
        cardAddressList.add(findViewById(R.id.cardAddress4));
        cardAddressList.add(findViewById(R.id.cardAddress5));
        cardAddressList.add(findViewById(R.id.cardAddress6));
        cardAddressList.add(findViewById(R.id.cardAddress7));
        cardAddressList.add(findViewById(R.id.cardAddress8));
        cardAddressList.add(findViewById(R.id.cardAddress9));
        cardAddressList.add(findViewById(R.id.cardAddress10));

        cardDistanceList.add(findViewById(R.id.cardDistance1));
        cardDistanceList.add(findViewById(R.id.cardDistance2));
        cardDistanceList.add(findViewById(R.id.cardDistance3));
        cardDistanceList.add(findViewById(R.id.cardDistance4));
        cardDistanceList.add(findViewById(R.id.cardDistance5));
        cardDistanceList.add(findViewById(R.id.cardDistance6));
        cardDistanceList.add(findViewById(R.id.cardDistance7));
        cardDistanceList.add(findViewById(R.id.cardDistance8));
        cardDistanceList.add(findViewById(R.id.cardDistance9));
        cardDistanceList.add(findViewById(R.id.cardDistance10));
    }

    private void openMap(Location locationToSearchFrom){
        Intent intent = new Intent(this, MapViewActivity.class);
        intent.putExtra("focusOnRecyclingCenter", false);
        intent.putExtra("locationToSearchFrom", locationToSearchFrom);
        startActivity(intent);
    }

    private void openMap(Location locationToSearchFrom, Location locationOfRecyclingCenter) {
        Intent intent = new Intent(this, MapViewActivity.class);
        intent.putExtra("focusOnRecyclingCenter", true);
        intent.putExtra("locationToSearchFrom", locationToSearchFrom);
        intent.putExtra("locationOfRecyclingCenter", locationOfRecyclingCenter);
        startActivity(intent);
    }

    private void openSideMenu(){
        Intent intent = new Intent(this, SideMenuActivity.class);
        startActivity(intent);
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

    private void updateRecyclingCentersFromCurrentLocation() {
        CurrentLocationHandler.accessCurrentLocation(this, currentLocation -> {
            locationSearchPoint = currentLocation;
            PlacesAPIHandler.updateRecyclingCenters(currentLocation, this);
        });
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

    @SuppressLint("SetTextI18n")
    @Override
    public void updateRecyclingCenters() {
        cardLocationList.clear();
        if (!cardsExist) {
            scrollView.setVisibility(View.VISIBLE);
            cardsExist = true;
        }
        showCards(recyclingCenters.size());
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (int i = 0; i < recyclingCenters.size(); i++) {
            if (!(i < cardList.size())) {
                break;
            }
            RecyclingCenter recyclingCenter = recyclingCenters.get(i);
            cardNameList.get(i).setText(recyclingCenter.getName());
            cardAddressList.get(i).setText(recyclingCenter.getAddress());
            cardDistanceList.get(i).setText(decimalFormat.format(recyclingCenter.getDistance()/1000) + " km");
            cardLocationList.add(recyclingCenter.getLocation());
        }
    }
}