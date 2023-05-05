package com.example.dat257_project_team_1;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SideMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_menu);
        ImageView closeSideMenu = (ImageView) findViewById(R.id.closeLogin);
        Button settingsButton = (Button) findViewById(R.id.Settings_button);
        Button logIn = (Button) findViewById(R.id.logIn);

        closeSideMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //should be somthing like
                finish();

            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsMenu();
            }

        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }

        });
    }

    private void openSettingsMenu(){
        Intent intent = new Intent(this, SettingsMenu.class);
        startActivity(intent);
    }
    private void openLogin(){
        Intent intent = new Intent(this, LoginMenu.class);
        startActivity(intent);
    }
}
