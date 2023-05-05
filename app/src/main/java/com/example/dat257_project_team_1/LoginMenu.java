package com.example.dat257_project_team_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_menu);

        ImageView closeLogin = (ImageView) findViewById(R.id.closeLogin);
        TextView userId = (TextView) findViewById(R.id.userId);
        TextView userPassword = (TextView) findViewById(R.id.userPassword);
        Button logIn = (Button) findViewById(R.id.logIn);
        Button signUp = (Button) findViewById(R.id.signUp);
        closeLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //should be somthing like
                finish();

            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO//
                //login
            }

        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO//
                //sign up
            }

        });
    }
}
