package com.example.dat257_project_team_1;

import java.util.ArrayList;

public interface IRecyclingCentersObserver {

    void saveNewRecyclingCenters(ArrayList<RecyclingCenter> recyclingCenters);

    void updateRecyclingCenters();
}