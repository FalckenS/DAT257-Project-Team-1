package com.example.dat257_project_team_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_menu);

        ImageView closeLogin = (ImageView) findViewById(R.id.closeLogin);

        closeLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //should be somthing like
                finish();

            }
        });
    }
}
