package com.example.mangaverse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Manga Reader Activity - Hiển thị danh sách ảnh manga
 * Vertical scroll với snap để đọc từng trang
 */
public class MangaReaderActivity extends AppCompatActivity {
    
    private static final String EXTRA_MANGA_TITLE = "manga_title";
    private static final String EXTRA_MANGA_FOLDER = "manga_folder";
    private static final String EXTRA_START_PAGE = "start_page";
    
    private RecyclerView recyclerView;
    private MangaPageAdapter adapter;
    private TextView pageIndicator;
    private ImageButton btnBack;
    private View overlayControls;
    
    private String mangaTitle;
    private String mangaFolder;
    private List<String> imageUrls;
    private int currentPage = 0;
    private boolean controlsVisible = true;
    
    /**
     * Create intent to open manga reader with folder from assets
     * @param context Context
     * @param title Manga title for display
     * @param mangaFolder Folder name in assets/manga/ (e.g., "sample_manga")
     * @return Intent
     */
    public static Intent createIntent(Context context, String title, String mangaFolder) {
        return createIntent(context, title, mangaFolder, 0);
    }
    
    /**
     * Create intent to open manga reader with folder from assets
     * @param context Context
     * @param title Manga title for display
     * @param mangaFolder Folder name in assets/manga/ (e.g., "sample_manga")
     * @param startPage Starting page number (0-based)
     * @return Intent
     */
    public static Intent createIntent(Context context, String title, String mangaFolder, int startPage) {
        Intent intent = new Intent(context, MangaReaderActivity.class);
        intent.putExtra(EXTRA_MANGA_TITLE, title);
        intent.putExtra(EXTRA_MANGA_FOLDER, mangaFolder);
        intent.putExtra(EXTRA_START_PAGE, startPage);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_reader);
        
        // Get data from intent
        mangaTitle = getIntent().getStringExtra(EXTRA_MANGA_TITLE);
        mangaFolder = getIntent().getStringExtra(EXTRA_MANGA_FOLDER);
        currentPage = getIntent().getIntExtra(EXTRA_START_PAGE, 0);
        
        // Validate manga folder
        if (mangaFolder == null || mangaFolder.isEmpty()) {
            Toast.makeText(this, "Manga folder not specified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        // Load images from assets using MangaAssetLoader
        MangaAssetLoader assetLoader = new MangaAssetLoader(this);
        imageUrls = assetLoader.getMangaPages(mangaFolder);
        
        if (imageUrls == null || imageUrls.isEmpty()) {
            Toast.makeText(this, "No manga images found in folder: " + mangaFolder, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        // Initialize views
        initViews();
        
        // Setup RecyclerView
        setupRecyclerView();
        
        // Setup controls
        setupControls();
    }
    
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewMangaPages);
        pageIndicator = findViewById(R.id.tvPageIndicator);
        btnBack = findViewById(R.id.btnBack);
        overlayControls = findViewById(R.id.overlayControls);
    }
    
    private void setupRecyclerView() {
        // Linear layout for vertical scrolling
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        // Adapter
        adapter = new MangaPageAdapter(this, imageUrls);
        recyclerView.setAdapter(adapter);
        
        // Snap helper - snap to each page
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        
        // Scroll listener to update page indicator
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updatePageIndicator();
            }
        });
        
        // Tap listener to toggle controls
        adapter.setOnPageClickListener(() -> toggleControls());
        
        // Scroll to start page
        if (currentPage > 0 && currentPage < imageUrls.size()) {
            recyclerView.scrollToPosition(currentPage);
        }
        
        updatePageIndicator();
    }
    
    private void setupControls() {
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void updatePageIndicator() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager != null) {
            int position = layoutManager.findFirstVisibleItemPosition();
            if (position >= 0 && position < imageUrls.size()) {
                currentPage = position;
                pageIndicator.setText(String.format("%d / %d", position + 1, imageUrls.size()));
            }
        }
    }
    
    private void toggleControls() {
        controlsVisible = !controlsVisible;
        overlayControls.setVisibility(controlsVisible ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Save current page for next time
        // You can implement SharedPreferences here
    }
}
