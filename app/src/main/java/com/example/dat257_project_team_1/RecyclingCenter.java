package com.example.dat257_project_team_1;

import android.location.Location;

class RecyclingCenter {

    private final String name;
    private final String address;
    private final Location location;

    public RecyclingCenter(String name, String address, Location location) {
        this.name = name;
        this.address = address;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Location getLocation() {
        return location;
    }
}