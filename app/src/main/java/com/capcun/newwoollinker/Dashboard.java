package com.capcun.newwoollinker;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    StageAdapter stageAdapter;
    List<StageModelClass> data_list = new ArrayList<>();
    RecyclerView recycler;
    BottomNavigationView bottomNavigationView;
    LinearLayoutManager layoutManager;
    final String sharedPreferencesFileTitle = "wool_linker";

    ImageView profile,lang;
    private TradingFragment tradingFragment;
    private ChatFragment chatFragment;


    private HomeFragment homeFragment;

    private Fragment activeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,UserProfile.class);
                startActivity(intent);
            }
        });

        ImageView langButton = findViewById(R.id.lang);

        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a PopupMenu
                PopupMenu popupMenu = new PopupMenu(Dashboard.this, v);

                // Inflate the menu from a resource file (e.g., res/menu/languages_menu.xml)
                popupMenu.getMenuInflater().inflate(R.menu.languages_menu, popupMenu.getMenu());

                // Set an item click listener for the menu items

                // Show the PopupMenu
                popupMenu.show();
            }
        });

// Function to set the locale and update the UI with the new language



        // Initialize fragments
        tradingFragment = new TradingFragment();
        chatFragment = new ChatFragment();
        homeFragment = new HomeFragment();

        // Set the initial active fragment
        activeFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, activeFragment).commit();

        // Retrieve BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_nav1);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                Fragment selectedFragment;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = homeFragment;
                        break;
                    case R.id.navigation_trading:
                        selectedFragment = tradingFragment;
                        break;
                    case R.id.navigation_chat:
                        selectedFragment = chatFragment;
                        break;
                    default:
                        return false;
                }
                if (selectedFragment != activeFragment) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment).commit();
                    activeFragment = selectedFragment;
                }
                return true;
            }
        });


    }
}