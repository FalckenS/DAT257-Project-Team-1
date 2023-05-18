package com.example.dat257_project_team_1;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import android.os.Handler;
import android.os.Looper;
import android.location.Location;
import okhttp3.*;

import static com.example.dat257_project_team_1.Constants.*;

class PlacesAPIHandler {

    static public void updateRecyclingCenters(Location currentLocation, IRecyclingCentersObserver recyclingCentersObserver) {
        // https://developers.google.com/maps/documentation/places/web-service/search-nearby
        String placesAPIUrl = constructPlacesAPIUrl(currentLocation);
        Request request = new Request.Builder()
                .url(placesAPIUrl)
                .method("GET", null)
                .build();
        try {
            executeRequest(request, recyclingCentersObserver, currentLocation);
        }
        catch (Exception e)
        {
            System.out.println("PlacesAPI request failed");
            // TODO
        }
    }

    static private void executeRequest(Request request, IRecyclingCentersObserver recyclingCentersObserver, Location currentLocation) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            try {
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
                ArrayList<RecyclingCenter> recyclingCenters = getResults(okHttpClient.newCall(request).execute(), currentLocation);
                recyclingCentersObserver.saveNewRecyclingCenters(recyclingCenters);
            }
            catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
            handler.post(recyclingCentersObserver::updateRecyclingCenters);
        }).start();
    }

    static private ArrayList<RecyclingCenter> getResults(Response response, Location currentLocation) throws IOException, JSONException {
        ArrayList<RecyclingCenter> recyclingCenters = new ArrayList<>();
        assert response.body() != null;
        String responseJsonString = response.body().string();
        JSONObject responseJsonObject = new JSONObject(responseJsonString);
        JSONArray results = responseJsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject recyclingCenterJsonObject = results.getJSONObject(i);

            Location location = LocationFactory.createLocation(
                    recyclingCenterJsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                    recyclingCenterJsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
            recyclingCenters.add(new RecyclingCenter(
                    recyclingCenterJsonObject.getString("name"),
                    recyclingCenterJsonObject.getString("vicinity"),
                   currentLocation.distanceTo(location),
                    location));
        }
        return recyclingCenters;
    }


    static private String constructPlacesAPIUrl(Location currentLocation) {
        String latitude     = String.valueOf(currentLocation.getLatitude());
        String longitude    = String.valueOf(currentLocation.getLongitude());
        String location     = latitude + "%2C" + longitude;
        String keyword      = "Återvinningscenter";
        String opennow      = "true";
        String rankby       = "distance";
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "&location="    + location +
                "&keyword="     + keyword +
                "&opennow="     + opennow +
                "&rankby="      + rankby +
                "&key="         + API_KEY;
    }
}