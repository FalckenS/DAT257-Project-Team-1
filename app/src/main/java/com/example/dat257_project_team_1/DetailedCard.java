package com.example.dat257_project_team_1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailedCard extends AppCompatActivity {
    private ImageView Photo;
    private TextView Address, Details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_card);

        Photo = (ImageView) findViewById(R.id.Photo);
        Address = (TextView) findViewById(R.id.Address);
        Details = (TextView) findViewById(R.id.Details);
    }

    public void setPhoto(ImageView photo) {
        Photo = photo;
    }

    public void setAddress(String address) {
        Address.setText(address);
    }

    public void setDetails(String details) {
        Details.setText(details);
    }
}
