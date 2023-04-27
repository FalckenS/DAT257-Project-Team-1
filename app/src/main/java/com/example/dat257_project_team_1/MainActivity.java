package com.example.dat257_project_team_1;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import org.jetbrains.annotations.NotNull;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import static com.example.dat257_project_team_1.Constants.*;
import com.example.dat257_project_team_1.ExpandableCard;

public class MainActivity extends AppCompatActivity {

    private PlacesAPIHandler placesAPIHandler;
    private CurrentLocationHandler currentLocationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placesAPIHandler = new PlacesAPIHandler();
        currentLocationHandler = new CurrentLocationHandler(this);

        ImageView maps = (ImageView) findViewById(R.id.maps);
        ImageView sideMenu = (ImageView) findViewById(R.id.sideMenu);
        CardView card1 = (CardView) findViewById(R.id.card1);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View cardView = inflater.inflate(R.layout.expandable_card, null);
        ArrayList<ExpandableCard> cardsList = new ArrayList<>();
        addCardViews(cardsList);

        card1.addView(cardView);

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

        if (currentLocationHandler.isLocationPermissionGranted()) {
            currentLocationHandler.accessCurrentLocation(currentLocation -> placesAPIHandler.updateRecyclingCenters(currentLocation));
        }
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

    private void addCardViews(ArrayList<ExpandableCard> x){
        for (int i = 0; i < 10; i++){
            x.add(new ExpandableCard());
        }
    }

}