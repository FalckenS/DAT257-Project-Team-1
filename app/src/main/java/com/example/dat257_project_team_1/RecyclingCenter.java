package com.example.dat257_project_team_1;

import android.location.Location;

class RecyclingCenter {

    private final String name;
    private final String address;
    private final Location location;
    private String[] recyclableMaterials;
    private boolean accessibleByCar;
    private boolean accessibleForDisabled;

    public RecyclingCenter(String name, String address, Location location) {
        this.name = name;
        this.address = address;
        this.location = location;
        //this.recyclableMaterials = String["All"];
        //this.accessibleByCar = true;
        //this.accessibleForDisabled = true;
    }

    /*
    public RecyclingCenter(String name, String address, Location location
   String[] recycleableMaterials, boolean accessibleByCar, boolean accessibleForDisabled )
    {
        this.name = name;
        this.address = address;
        this.location = location;
        this.recyclableMaterials = recycleableMaterials;
        this.accessibleByCar = accessibleByCar;
        this.accessibleForDisabled = accessibleForDisabled;
    }
    */


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Location getLocation() {
        return location;
    }

    /*

    public String[] getRecyclableMaterials() {
        return recyclableMaterials;
    }

    public void setRecyclableMaterials(String[] recyclableMaterials) {
        this.recyclableMaterials = recyclableMaterials;
    }

    public boolean isAccessibleByCar() {
        return accessibleByCar;
    }

    public void setAccessibleByCar(boolean accessibleByCar) {
        this.accessibleByCar = accessibleByCar;
    }

    public boolean isAccessibleForDisabled() {
        return accessibleByCar;
    }

    public void setAccessibleForDisabled(boolean accessibleByCar) {
        this.accessibleByCar = accessibleByCar;
    }
    */
}