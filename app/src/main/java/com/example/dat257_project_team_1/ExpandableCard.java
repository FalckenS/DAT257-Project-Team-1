package com.example.dat257_project_team_1;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
/*import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.hellodroid.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

 */


public class ExpandableCard extends AppCompatActivity{
    TextView cardContent;
    LinearLayout cardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_card);

        cardContent = findViewById(R.id.cardContent);
        cardLayout = findViewById(R.id.cardLayout);
    }

    public void expand(View view) {
        int v = (cardContent.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(cardLayout, new AutoTransition());
        cardContent.setVisibility(v);
    }
}
