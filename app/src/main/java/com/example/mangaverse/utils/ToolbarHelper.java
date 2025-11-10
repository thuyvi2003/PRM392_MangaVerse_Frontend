package com.example.mangaverse.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaverse.R;
import com.example.mangaverse.ProfileActivity;

public class ToolbarHelper {

    public interface ToolbarListener {
        void onBackClick();
        void onSearchClick();
        void onProfileClick();
    }

    public static void setupToolbar(Activity activity, View toolbarView, String title, ToolbarListener listener) {
        ImageButton btnBack = toolbarView.findViewById(R.id.btnBack);
        ImageButton btnSearch = toolbarView.findViewById(R.id.btnSearch);
        ImageButton btnProfile = toolbarView.findViewById(R.id.btnProfile);
        TextView tvTitle = toolbarView.findViewById(R.id.tvToolbarTitle);

        // Set title
        if (title != null && !title.isEmpty()) {
            tvTitle.setText(title);
        }

        // Back button
        btnBack.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBackClick();
            } else {
                activity.finish();
            }
        });

        // Search button
        btnSearch.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSearchClick();
            } else {
                Toast.makeText(activity, "Search clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnProfile.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProfileClick();
            } else {
                Intent intent = new Intent(activity, ProfileActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    // Simplified version without listener
    public static void setupToolbar(Activity activity, View toolbarView, String title) {
        setupToolbar(activity, toolbarView, title, null);
    }
}