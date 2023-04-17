package com.example.dat257_project_team_1;

public final class Constants {

    public static int REQUEST_LOCATION_PERMISSION_CODE = 0;

    // MIN_LOCATION_UPDATE_MILLIS <= LOCATION_UPDATE_INTERVAL_MILLIS <= MAX_LOCATION_UPDATE_MILLIS
    public static long MIN_LOCATION_UPDATE_MILLIS = 10000; // 10 s
    public static long LOCATION_UPDATE_INTERVAL_MILLIS = 10000; // 10 s
    public static long MAX_LOCATION_UPDATE_MILLIS = 60000; // 60 s
}