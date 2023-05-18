package com.example.dat257_project_team_1;

import android.location.Location;

class RecyclingCenter {

    private final String name;
    private final String address;
    private final float distance;
    private final Location location;

    public RecyclingCenter(String name, String address, float distance, Location location) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public float getDistance() {
        return distance;
    }

    public Location getLocation() {
        return location;
    }
}