package com.example.dat257_project_team_1;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Objects;

import android.os.Handler;
import android.os.Looper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.location.Location;
import okhttp3.*;

import static com.example.dat257_project_team_1.Constants.*;

class PlacesAPIHandler {

    private final MainActivity mainActivity;

    private final ArrayList<RecyclingCenter> recyclingCenters;
    private final OkHttpClient okHttpClient;

    public PlacesAPIHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        okHttpClient = new OkHttpClient().newBuilder().build();
        recyclingCenters = new ArrayList<>();
    }

    /**
     * Getter for the recycling centers. Does not call the API and updates the results, only returns the list
     * PlacesAPIHandler.recyclingCenters. Use PlacesAPIHandler.updateRecyclingCenters to update the list.
     *
     * @return the list of the 10 nearest recycling centers.
     */
    public ArrayList<RecyclingCenter> getRecyclingCenters() {
        return recyclingCenters;
    }

    /**
     * Calls the API and updates the list PlacesAPIHandler.recyclingCenters with the 10 nearest recycling centers to the
     * given location. To get the recycling centers, use PlacesAPIHandler.getRecyclingCenters().
     *
     * @param location the location to search from.
     */
    public void updateRecyclingCenters(Location location) {
        // https://developers.google.com/maps/documentation/places/web-service/search-nearby
        String placesAPIUrl = constructPlacesAPIUrl(location);
        Request request = new Request.Builder()
                .url(placesAPIUrl)
                .method("GET", null)
                .build();
        try {
            executeRequest(this, request);
        }
        catch (Exception e)
        {
            System.out.println("PlacesAPI request failed");
            // TODO
        }
    }

    private String constructPlacesAPIUrl(Location currentLocation) {
        String latitude     = String.valueOf(currentLocation.getLatitude());
        String longitude    = String.valueOf(currentLocation.getLongitude());
        String location     = latitude + "%2C" + longitude;
        String keyword      = "Recycling center";
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
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            try {
                placesAPIHandler.updateResults(okHttpClient.newCall(request).execute());
            }
            catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
            handler.post(mainActivity::populateCards);
        }).start();
    }

    private void updateResults(Response response) throws IOException, JSONException {
        recyclingCenters.clear();
        assert response.body() != null;
        String responseJsonString = response.body().string();
        JSONObject responseJsonObject = new JSONObject(responseJsonString);
        JSONArray results = responseJsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject recyclingCenterJsonObject = results.getJSONObject(i);

            String address = recyclingCenterJsonObject.getString("vicinity");
            if (!recyclingCenters.isEmpty()) {
                if (Objects.equals(recyclingCenters.get(recyclingCenters.size()-1).getAddress(), address)) {
                    // Sometimes the same recycling center shows up several times. If this is the case, skip to the next one
                    continue;
                }
            }
            recyclingCenters.add(new RecyclingCenter(
                    recyclingCenterJsonObject.getString("name"),
                    address,
                    LocationFactory.createLocation(
                            recyclingCenterJsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                            recyclingCenterJsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng"))));
            if (recyclingCenters.size() == 10) {
                break;
            }
        }
    }
}