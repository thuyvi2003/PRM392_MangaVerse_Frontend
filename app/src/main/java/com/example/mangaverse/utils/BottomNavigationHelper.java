package com.example.mangaverse.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.mangaverse.DiscoverActivity;
import com.example.mangaverse.HomeActivity;
import com.example.mangaverse.MyBooksActivity;
import com.example.mangaverse.R;
import com.example.mangaverse.ui.settings.SettingsActivity;

public class BottomNavigationHelper {

    public static void setupBottomNavigation(Activity activity, View bottomNavView, String currentPage) {
        View navHome = bottomNavView.findViewById(R.id.navHome);
        View navDiscover = bottomNavView.findViewById(R.id.navDiscover);
        View navMyBooks = bottomNavView.findViewById(R.id.navMyBooks);
        View navSettings = bottomNavView.findViewById(R.id.navSettings);

        ImageView iconHome = bottomNavView.findViewById(R.id.iconHome);
        TextView textHome = bottomNavView.findViewById(R.id.textHome);
        ImageView iconDiscover = bottomNavView.findViewById(R.id.iconDiscover);
        TextView textDiscover = bottomNavView.findViewById(R.id.textDiscover);
        ImageView iconMyBooks = bottomNavView.findViewById(R.id.iconMyBooks);
        TextView textMyBooks = bottomNavView.findViewById(R.id.textMyBooks);
        ImageView iconSettings = bottomNavView.findViewById(R.id.iconSettings);
        TextView textSettings = bottomNavView.findViewById(R.id.textSettings);

        // Set active state based on current page
        int activeColor = ContextCompat.getColor(activity, R.color.primary);
        int inactiveColor = ContextCompat.getColor(activity, R.color.gray_400);

        // Reset all to inactive
        iconHome.setColorFilter(inactiveColor);
        textHome.setTextColor(inactiveColor);
        textHome.setTypeface(null, android.graphics.Typeface.NORMAL);

        iconDiscover.setColorFilter(inactiveColor);
        textDiscover.setTextColor(inactiveColor);
        textDiscover.setTypeface(null, android.graphics.Typeface.NORMAL);

        iconMyBooks.setColorFilter(inactiveColor);
        textMyBooks.setTextColor(inactiveColor);
        textMyBooks.setTypeface(null, android.graphics.Typeface.NORMAL);

        iconSettings.setColorFilter(inactiveColor);
        textSettings.setTextColor(inactiveColor);
        textSettings.setTypeface(null, android.graphics.Typeface.NORMAL);

        // Set active page
        switch (currentPage) {
            case "home":
                iconHome.setColorFilter(activeColor);
                textHome.setTextColor(activeColor);
                textHome.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            case "discover":
                iconDiscover.setColorFilter(activeColor);
                textDiscover.setTextColor(activeColor);
                textDiscover.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            case "mybooks":
                iconMyBooks.setColorFilter(activeColor);
                textMyBooks.setTextColor(activeColor);
                textMyBooks.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            case "settings":
                iconSettings.setColorFilter(activeColor);
                textSettings.setTextColor(activeColor);
                textSettings.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
        }

        // Setup click listeners
        navHome.setOnClickListener(v -> {
            if (!currentPage.equals("home")) {
                Intent intent = new Intent(activity, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent);
            }
        });

        navDiscover.setOnClickListener(v -> {
            if (!currentPage.equals("discover")) {
                Intent intent = new Intent(activity, DiscoverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent);
            }
        });

        navMyBooks.setOnClickListener(v -> {
            if (!currentPage.equals("mybooks")) {
                Intent intent = new Intent(activity, MyBooksActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent);
            }
        });

        navSettings.setOnClickListener(v -> {
            if (!currentPage.equals("settings")) {
                Intent intent = new Intent(activity, SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent);
            }
        });
    }
}
