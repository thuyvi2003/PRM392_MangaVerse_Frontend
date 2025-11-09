package com.example.mangaverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;

import com.example.mangaverse.adapter.BannerAdapter;
import com.example.mangaverse.adapter.MangaAdapter;
import com.example.mangaverse.model.Banner;
import com.example.mangaverse.model.Manga;
import com.example.mangaverse.utils.BottomNavigationHelper;
import com.example.mangaverse.utils.ToolbarHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvNewChapters, rvTrending;
    private MangaAdapter newChaptersAdapter, trendingAdapter;
    private List<Manga> newChaptersList, trendingList;

    private ViewPager2 bannerViewPager;
    private BannerAdapter bannerAdapter;
    private List<Banner> bannerList;
    private View dot1, dot2, dot3;
    
    private Handler autoScrollHandler;
    private Runnable autoScrollRunnable;
    private boolean isUserInteracting = false;

    private View tabBrowse, tabLiked, tabRanking;
    private View tvCalendar;
    private View genreRomantic, genreAdventure, genreFiction, genreFantasy, genreComedy, genreDrama, genreHorror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        setupToolbar();
        setupBannerCarousel();
        setupRecyclerView();
        setupListeners();
        setupCategoryTabs();
        setupBottomNavigation();
        loadMangaData();
    }

    private void initViews() {
        rvNewChapters = findViewById(R.id.rvNewChapters);
        rvTrending = findViewById(R.id.rvTrending);
        bannerViewPager = findViewById(R.id.bannerViewPager);
        
        // Dots indicator
        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
        
        // Category tabs
        tabBrowse = findViewById(R.id.tabBrowse);
        tabLiked = findViewById(R.id.tabLiked);
        tabRanking = findViewById(R.id.tabRanking);
        
        // Calendar link
        tvCalendar = findViewById(R.id.tvCalendar);
        
        // Genre buttons
        genreRomantic = findViewById(R.id.genreRomantic);
        genreAdventure = findViewById(R.id.genreAdventure);
        genreFiction = findViewById(R.id.genreFiction);
        genreFantasy = findViewById(R.id.genreFantasy);
        genreComedy = findViewById(R.id.genreComedy);
        genreDrama = findViewById(R.id.genreDrama);
        genreHorror = findViewById(R.id.genreHorror);
    }

    private void setupToolbar() {
        View toolbarView = findViewById(R.id.commonToolbar);
        ToolbarHelper.setupToolbar(this, toolbarView, "Featured", new ToolbarHelper.ToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onSearchClick() {
                Toast.makeText(HomeActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
                // TODO: Open search activity
            }

            @Override
            public void onProfileClick() {
                Toast.makeText(HomeActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
                // TODO: Open profile activity
            }
        });
    }

    private void setupRecyclerView() {
        // Setup New Chapters RecyclerView
        newChaptersList = new ArrayList<>();
        newChaptersAdapter = new MangaAdapter(this, newChaptersList);
        
        androidx.recyclerview.widget.LinearLayoutManager newChaptersLayoutManager = 
            new androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false);
        rvNewChapters.setLayoutManager(newChaptersLayoutManager);
        rvNewChapters.setAdapter(newChaptersAdapter);

        newChaptersAdapter.setOnMangaClickListener(manga -> {
            Toast.makeText(this, "Clicked: " + manga.getTitle(), Toast.LENGTH_SHORT).show();
            // Navigate to manga detail or reader activity
        });
        
        // Setup Trending RecyclerView
        trendingList = new ArrayList<>();
        trendingAdapter = new MangaAdapter(this, trendingList);
        
        androidx.recyclerview.widget.LinearLayoutManager trendingLayoutManager = 
            new androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false);
        rvTrending.setLayoutManager(trendingLayoutManager);
        rvTrending.setAdapter(trendingAdapter);

        trendingAdapter.setOnMangaClickListener(manga -> {
            Toast.makeText(this, "Clicked: " + manga.getTitle(), Toast.LENGTH_SHORT).show();
            // Navigate to manga detail or reader activity
        });
    }

    private void setupBannerCarousel() {
        bannerList = new ArrayList<>();
        bannerList.add(new Banner("", R.drawable.banner_home4, "1"));
        bannerList.add(new Banner("", R.drawable.shinobu, "2")); // Thay bằng banner_2 khi có ảnh
        bannerList.add(new Banner("", R.drawable.howl, "3")); // Thay bằng banner_3 khi có ảnh

        bannerAdapter = new BannerAdapter(this, bannerList);
        bannerViewPager.setAdapter(bannerAdapter);

        // Setup dots indicator
        bannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateDots(position);
            }
            
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    // User bắt đầu vuốt, dừng auto scroll
                    isUserInteracting = true;
                    stopAutoScroll();
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    // User thả tay, tiếp tục auto scroll
                    isUserInteracting = false;
                    startAutoScroll();
                }
            }
        });

        // Handle banner click
        bannerAdapter.setOnBannerClickListener(banner -> {
            Toast.makeText(this, "Read " + banner.getTitle(), Toast.LENGTH_SHORT).show();
            // Navigate to manga reader
        });
        
        // Bắt đầu auto scroll
        startAutoScroll();
    }

    private void updateDots(int position) {
        dot1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                position == 0 ? 0xFFFFFFFF : 0x80FFFFFF));
        dot2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                position == 1 ? 0xFFFFFFFF : 0x80FFFFFF));
        dot3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                position == 2 ? 0xFFFFFFFF : 0x80FFFFFF));
    }
    
    private void startAutoScroll() {
        if (autoScrollHandler == null) {
            autoScrollHandler = new Handler(Looper.getMainLooper());
        }
        
        if (autoScrollRunnable == null) {
            autoScrollRunnable = new Runnable() {
                @Override
                public void run() {
                    if (!isUserInteracting && bannerViewPager != null && bannerList != null) {
                        int currentItem = bannerViewPager.getCurrentItem();
                        int nextItem = (currentItem + 1) % bannerList.size();
                        bannerViewPager.setCurrentItem(nextItem, true);
                    }
                    autoScrollHandler.postDelayed(this, 10000); // Chuyển sau mỗi 10 giây
                }
            };
        }
        
        autoScrollHandler.postDelayed(autoScrollRunnable, 10000);
    }
    
    private void stopAutoScroll() {
        if (autoScrollHandler != null && autoScrollRunnable != null) {
            autoScrollHandler.removeCallbacks(autoScrollRunnable);
        }
    }

    private void setupListeners() {
        // Calendar link
        tvCalendar.setOnClickListener(v -> {
            Toast.makeText(this, "Calendar clicked", Toast.LENGTH_SHORT).show();
            // TODO: Open calendar activity
        });
        
        // Genre buttons
        genreRomantic.setOnClickListener(v -> {
            Toast.makeText(this, "Romantic genre selected", Toast.LENGTH_SHORT).show();
            // TODO: Filter by Romantic genre
        });
        
        genreAdventure.setOnClickListener(v -> {
            Toast.makeText(this, "Adventure genre selected", Toast.LENGTH_SHORT).show();
            // TODO: Filter by Adventure genre
        });
        
        genreFiction.setOnClickListener(v -> {
            Toast.makeText(this, "Fiction genre selected", Toast.LENGTH_SHORT).show();
            // TODO: Filter by Fiction genre
        });
        
        genreFantasy.setOnClickListener(v -> {
            Toast.makeText(this, "Fantasy genre selected", Toast.LENGTH_SHORT).show();
            // TODO: Filter by Fantasy genre
        });
        
        genreComedy.setOnClickListener(v -> {
            Toast.makeText(this, "Comedy genre selected", Toast.LENGTH_SHORT).show();
            // TODO: Filter by Comedy genre
        });
        
        genreDrama.setOnClickListener(v -> {
            Toast.makeText(this, "Drama genre selected", Toast.LENGTH_SHORT).show();
            // TODO: Filter by Drama genre
        });
        
        genreHorror.setOnClickListener(v -> {
            Toast.makeText(this, "Horror genre selected", Toast.LENGTH_SHORT).show();
            // TODO: Filter by Horror genre
        });
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        stopAutoScroll(); // Dừng auto scroll khi app bị pause
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Re-setup bottom navigation when returning to this activity
        setupBottomNavigation();
        // Tiếp tục auto scroll khi app resume
        if (!isUserInteracting) {
            startAutoScroll();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAutoScroll(); // Cleanup khi activity bị destroy
    }

    private void setupCategoryTabs() {
        tabBrowse.setOnClickListener(v -> {
            Toast.makeText(this, "Browse selected", Toast.LENGTH_SHORT).show();
            // Filter manga by browse category
        });

        tabLiked.setOnClickListener(v -> {
            Toast.makeText(this, "Liked it selected", Toast.LENGTH_SHORT).show();
            // Show liked manga
        });

        tabRanking.setOnClickListener(v -> {
            Toast.makeText(this, "Ranking selected", Toast.LENGTH_SHORT).show();
            // Show ranking manga
        });
    }

    private void setupBottomNavigation() {
        View bottomNavView = findViewById(R.id.bottomNavigationCard);
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavView, "home");
    }

    private void loadMangaData() {
        // Load New Chapters data - 5 items
        newChaptersList.clear();
        newChaptersList.add(new Manga("1", "Tokyo Ghoul", "", 8.6, 143, "Action", false));
        newChaptersList.add(new Manga("2", "Vinland Saga", "", 9.1, 195, "Drama", false));
        newChaptersList.add(new Manga("3", "Steins;Gate", "", 8.8, 52, "Sci-Fi", false));
        newChaptersList.add(new Manga("4", "One Piece", "", 9.0, 1000, "Adventure", false));
        newChaptersList.add(new Manga("5", "Naruto", "", 8.7, 700, "Action", false));
        newChaptersAdapter.notifyDataSetChanged();
        
        // Load Trending data - 5 items
        trendingList.clear();
        trendingList.add(new Manga("6", "Apple Black", "", 8.5, 80, "Fantasy", false));
        trendingList.add(new Manga("7", "Attack on Titan", "", 9.2, 139, "Action", false));
        trendingList.add(new Manga("8", "Demon Slayer", "", 8.9, 205, "Action", false));
        trendingList.add(new Manga("9", "Jujutsu Kaisen", "", 8.8, 150, "Action", false));
        trendingList.add(new Manga("10", "Chainsaw Man", "", 8.9, 97, "Action", false));
        trendingAdapter.notifyDataSetChanged();
    }
}
