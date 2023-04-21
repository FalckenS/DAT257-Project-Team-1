package com.example.dat257_project_team_1;

import java.io.IOException;
import java.util.ArrayList;
import android.location.Location;
import okhttp3.*;

import static com.example.dat257_project_team_1.Constants.*;

public class PlacesAPIHandler {

    private ArrayList<RecyclingCenter> recyclingCenters;

    private final OkHttpClient okHttpClient;

    public PlacesAPIHandler() {
        okHttpClient = new OkHttpClient().newBuilder().build();
    }

    public ArrayList<RecyclingCenter> getRecyclingCenters() {
        return recyclingCenters;
    }

    public void updateRecyclingCenters(Location currentLocation) {
        // https://developers.google.com/maps/documentation/places/web-service/search-nearby
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        String placesAPIUrl = constructPlacesAPIUrl(currentLocation);
        Request request = new Request.Builder()
                .url(placesAPIUrl)
                .method("get", body)
                .build();

        try {
            executeRequest(this, request);
        }
        catch (Exception e)
        {
            System.out.println("PlacesAPI request failed");
        }
    }

    private String constructPlacesAPIUrl(Location currentLocation) {
        String latitude     = String.valueOf(currentLocation.getLatitude());
        String longitude    = String.valueOf(currentLocation.getLongitude());
        String location     = latitude + "%2C" + longitude;
        String keyword      = "recycling center";
        String opennow      = "true";
        String rankby       = "distance";
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "&location="    + location +
                "&keyword="     + keyword +
                "&opennow="     + opennow +
                "&rankby="      + rankby +
                "&key="         + API_KEY;
    }

    private void executeRequest(PlacesAPIHandler placesAPIHandler, Request request) {
        new Thread(() -> {
            try {
                placesAPIHandler.updateResults(okHttpClient.newCall(request).execute());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void updateResults(Response response) {
        recyclingCenters.clear();
        System.out.println(response.toString());

        // TODO add results to recyclingCenters
    }
}