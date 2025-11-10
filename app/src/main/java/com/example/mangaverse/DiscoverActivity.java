package com.example.mangaverse;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaverse.ui.settings.SettingsActivity;
import com.example.mangaverse.ProfileActivity;
import com.example.mangaverse.utils.BottomNavigationHelper;
import com.example.mangaverse.utils.ToolbarHelper;

import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {

    private RecyclerView booksRecyclerView;
    private BooksAdapter booksAdapter;
    private List<Book> booksList;
    private List<Book> allBooksList;
    private EditText etSearchDiscover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        setupToolbar();
        setupRecyclerView();
        loadBooks();
        setupBottomNavigation();
    }

    private void setupToolbar() {
        View toolbarView = findViewById(R.id.commonToolbar);
        ToolbarHelper.setupToolbar(this, toolbarView, "Discover", new ToolbarHelper.ToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onSearchClick() {
                Toast.makeText(DiscoverActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
                // TODO: Open search activity
            }

            @Override
            public void onProfileClick() {
                Toast.makeText(DiscoverActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DiscoverActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Re-setup bottom navigation when returning to this activity
        setupBottomNavigation();
    }

    private void initViews() {
        booksRecyclerView = findViewById(R.id.booksRecyclerView);
        etSearchDiscover = findViewById(R.id.etSearchDiscover);
    }

    private void setupRecyclerView() {
        booksRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        booksList = new ArrayList<>();
        allBooksList = new ArrayList<>();
        booksAdapter = new BooksAdapter(booksList, this);
        booksRecyclerView.setAdapter(booksAdapter);
        
        // Setup search
        etSearchDiscover.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBooks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadBooks() {
        // Sample book data
        allBooksList.add(new Book("The Silent Patient", "Alex Michaelides"));
        allBooksList.add(new Book("Atomic Habits", "James Clear"));
        allBooksList.add(new Book("Circe", "Madeline Miller"));
        allBooksList.add(new Book("Educated", "Tara Westover"));
        allBooksList.add(new Book("The Midnight Library", "Matt Haig"));
        allBooksList.add(new Book("Where the Crawdads Sing", "Delia Owens"));
        
        booksList.addAll(allBooksList);
        booksAdapter.notifyDataSetChanged();
    }
    private void filterBooks(String query) {
        booksList.clear();
        if (query.isEmpty()) {
            booksList.addAll(allBooksList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Book book : allBooksList) {
                if (book.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                    book.getAuthor().toLowerCase().contains(lowerCaseQuery)) {
                    booksList.add(book);
                }
            }
        }
        
        booksAdapter.notifyDataSetChanged();
    }

    private void setupBottomNavigation() {
        View bottomNavView = findViewById(R.id.bottomNavigationCard);
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavView, "discover");
    }

    public static class Book {
        private String title;
        private String author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }
    }
}