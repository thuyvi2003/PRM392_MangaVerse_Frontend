package com.example.mangaverse;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaverse.adapter.MangaAdapter;
import com.example.mangaverse.model.Manga;
import com.example.mangaverse.utils.BottomNavigationHelper;
import com.example.mangaverse.utils.ToolbarHelper;
import com.example.mangaverse.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class MyBooksActivity extends AppCompatActivity {

    private RecyclerView rvMyBooks;
    private MangaAdapter mangaAdapter;
    private List<Manga> mangaList;
    private List<Manga> allMangaList;

    private TextView tabReadingNow, tabMyFavourites, tabToRead;
    private ImageView btnAddNew;
    private EditText etSearch;
    private String currentTab = "reading_now";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupTabs();
        setupBottomNavigation();
        loadMangaData();
    }

    private void initViews() {
        rvMyBooks = findViewById(R.id.rvMyBooks);
        // Tabs
        tabReadingNow = findViewById(R.id.tabReadingNow);
        tabMyFavourites = findViewById(R.id.tabMyFavourites);
        tabToRead = findViewById(R.id.tabToRead);
        btnAddNew = findViewById(R.id.btnAddNew);
        // Search
        etSearch = findViewById(R.id.etSearch);
    }

    private void setupToolbar() {
        View toolbarView = findViewById(R.id.commonToolbar);
        ToolbarHelper.setupToolbar(this, toolbarView, "My Manga", new ToolbarHelper.ToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onSearchClick() {
                Toast.makeText(MyBooksActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProfileClick() {
                // === CẬP NHẬT LOGIC: MỞ PROFILE ACTIVITY ===
                Toast.makeText(MyBooksActivity.this, "Profile clicked, opening...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyBooksActivity.this, ProfileActivity.class);
                startActivity(intent);
                // ==========================================
            }
        });
    }

    private void setupRecyclerView() {
        mangaList = new ArrayList<>();
        allMangaList = new ArrayList<>();
        mangaAdapter = new MangaAdapter(this, mangaList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvMyBooks.setLayoutManager(gridLayoutManager);
        rvMyBooks.setAdapter(mangaAdapter);

        mangaAdapter.setOnMangaClickListener(manga -> {
            Toast.makeText(this, "Clicked: " + manga.getTitle(), Toast.LENGTH_SHORT).show();
            // Navigate to manga detail or reader activity
        });
        // Setup search
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterManga(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupTabs() {
        tabReadingNow.setOnClickListener(v -> selectTab("reading_now"));
        tabMyFavourites.setOnClickListener(v -> selectTab("favourites"));
        tabToRead.setOnClickListener(v -> selectTab("to_read"));
        btnAddNew.setOnClickListener(v -> {
            Toast.makeText(this, "Add new manga", Toast.LENGTH_SHORT).show();
            // TODO: Open add manga dialog or activity
        });
    }

    private void selectTab(String tab) {
        currentTab = tab;
        // Reset all tabs
        tabReadingNow.setBackgroundResource(android.R.color.transparent);
        tabReadingNow.setTextColor(getResources().getColor(android.R.color.white));
        tabReadingNow.setTypeface(null, android.graphics.Typeface.NORMAL);

        tabMyFavourites.setBackgroundResource(android.R.color.transparent);
        tabMyFavourites.setTextColor(getResources().getColor(android.R.color.white));
        tabMyFavourites.setTypeface(null, android.graphics.Typeface.NORMAL);

        tabToRead.setBackgroundResource(android.R.color.transparent);
        tabToRead.setTextColor(getResources().getColor(android.R.color.white));
        tabToRead.setTypeface(null, android.graphics.Typeface.NORMAL);

        // Highlight selected tab
        switch (tab) {
            case "reading_now":
                tabReadingNow.setBackgroundResource(R.drawable.bg_tab_active);
                tabReadingNow.setTextColor(getResources().getColor(R.color.primary));
                tabReadingNow.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            case "favourites":
                tabMyFavourites.setBackgroundResource(R.drawable.bg_tab_active);
                tabMyFavourites.setTextColor(getResources().getColor(R.color.primary));
                tabMyFavourites.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            case "to_read":
                tabToRead.setBackgroundResource(R.drawable.bg_tab_active);
                tabToRead.setTextColor(getResources().getColor(R.color.primary));
                tabToRead.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
        }
        loadMangaData();
    }

    private void setupBottomNavigation() {
        View bottomNavView = findViewById(R.id.bottomNavigationCard);
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavView, "mybooks");
    }

    private void loadMangaData() {
        mangaList.clear();
        allMangaList.clear();
        switch (currentTab) {
            case "reading_now":
                // Reading Now data
                allMangaList.add(new Manga("1", "19 Tian", "", 8.9, 328, "Romance", false));
                allMangaList.add(new Manga("2", "Solo Leveling", "", 9.2, 179, "Action", false));
                allMangaList.add(new Manga("3", "One Piece", "", 9.0, 1000, "Adventure", false));
                allMangaList.add(new Manga("4", "Naruto", "", 8.7, 700, "Action", false));
                allMangaList.add(new Manga("5", "Liar Game", "", 8.8, 201, "Psychological", false));
                break;
            case "favourites":
                // Favourites data
                allMangaList.add(new Manga("6", "Attack on Titan", "", 9.2, 139, "Action", false));
                allMangaList.add(new Manga("7", "Death Note", "", 9.0, 108, "Thriller", false));
                allMangaList.add(new Manga("8", "Fullmetal Alchemist", "", 9.1, 116, "Adventure", false));
                break;
            case "to_read":
                // To Read data
                allMangaList.add(new Manga("9", "Demon Slayer", "", 8.9, 205, "Action", false));
                allMangaList.add(new Manga("10", "Jujutsu Kaisen", "", 8.8, 150, "Action", false));
                break;
        }
        mangaList.addAll(allMangaList);
        mangaAdapter.notifyDataSetChanged();
        etSearch.setText(""); // Clear search when switching tabs
    }

    private void filterManga(String query) {
        mangaList.clear();

        if (query.isEmpty()) {
            mangaList.addAll(allMangaList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Manga manga : allMangaList) {
                if (manga.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        manga.getCategory().toLowerCase().contains(lowerCaseQuery)) {
                    mangaList.add(manga);
                }
            }
        }
        mangaAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBottomNavigation();
    }
}
