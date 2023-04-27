package com.example.dat257_project_team_1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpandableCardFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpandableCardFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ADRESS = "";
    private static final String ARG_NAME = "";

    // TODO: Rename and change types of parameters
    private String address;
    private String name;

    public ExpandableCardFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param address Parameter 1.
     * @param name Parameter 2.
     * @return A new instance of fragment ExpandableCardFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpandableCardFrag newInstance(String address, String name) {
        ExpandableCardFrag fragment = new ExpandableCardFrag();
        Bundle args = new Bundle();
        args.putString(ARG_ADRESS, address);
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            address = getArguments().getString(ARG_ADRESS);
            name = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expandable_card, container, false);
    }
}