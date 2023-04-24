package com.example.dat257_project_team_1;

import android.location.Location;

public class LocationFactory {

    public static Location createLocation(double latitude, double longitude) {
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }
}