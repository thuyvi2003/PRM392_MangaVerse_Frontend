package com.example.mangaverse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mangaverse.databinding.ActivityMainBinding;
import com.example.mangaverse.ui.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MangaVersePrefs";
    private static final String KEY_FIRST_TIME = "isFirstTime";
    
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        // ✅ Thêm xử lý mở SettingsActivity khi bấm tab Settings
        navView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.navigation_notifications) { // hoặc id của Settings
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return false; // không giữ trạng thái tab (vì nó là Activity riêng)
            } else {
                NavigationUI.onNavDestinationSelected(item, navController);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reset_onboarding) {
            resetOnboarding();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetOnboarding() {
        // Reset the first time flag
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_FIRST_TIME, true).apply();
        
        // Restart app to onboarding
        Intent intent = new Intent(this, OnboardingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}