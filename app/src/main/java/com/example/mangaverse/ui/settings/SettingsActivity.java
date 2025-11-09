package com.example.mangaverse.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.mangaverse.R;
import com.google.android.material.card.MaterialCardView;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    // Theme
    private TextView tvThemeValue;
    private MaterialCardView themeLight, themeSepia, themeDark;
    private LinearLayout themeOptionsContainer;
    private String selectedTheme = "Light";

    // Font Size
    private SeekBar seekBarFontSize;
    private TextView tvFontSizeValue;

    // Font Style
    private TextView tvFontStyleValue;

    // Reading Direction
    private TextView tvReadingDirectionValue;
    private String selectedReadingDirection = "Left to Right";

    // Page Turn Animation
    private TextView tvPageTurnValue;
    private String selectedPageTurn = "Slide";

    // Switches
    private SwitchCompat switchReadingProgress;
    private SwitchCompat switchNotifications;

    // Language
    private TextView tvLanguageValue;
    private String selectedLanguage = "English";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("AppMangaSettings", MODE_PRIVATE);

        initViews();
        loadSettings();
        setupListeners();
    }

    private void initViews() {
        // Back button
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        // Theme
        tvThemeValue = findViewById(R.id.tvThemeValue);
        themeOptionsContainer = findViewById(R.id.themeOptionsContainer);
        themeLight = findViewById(R.id.themeLight);
        themeSepia = findViewById(R.id.themeSepia);
        themeDark = findViewById(R.id.themeDark);

        // Font Size
        seekBarFontSize = findViewById(R.id.seekBarFontSize);
        tvFontSizeValue = findViewById(R.id.tvFontSizeValue);

        // Font Style
        tvFontStyleValue = findViewById(R.id.tvFontStyleValue);

        // Reading Direction
        tvReadingDirectionValue = findViewById(R.id.tvReadingDirectionValue);

        // Page Turn Animation
        tvPageTurnValue = findViewById(R.id.tvPageTurnValue);

        // Switches
        switchReadingProgress = findViewById(R.id.switchReadingProgress);
        switchNotifications = findViewById(R.id.switchNotifications);

        // Language
        tvLanguageValue = findViewById(R.id.tvLanguageValue);
    }

    private void loadSettings() {
        // Load Theme
        selectedTheme = sharedPreferences.getString("theme", "Light");
        tvThemeValue.setText(selectedTheme);
        updateThemeSelection();

        // Load Font Size
        int fontSize = sharedPreferences.getInt("fontSize", 16);
        seekBarFontSize.setProgress(fontSize - 10); // Range 10-30
        tvFontSizeValue.setText(fontSize + "pt");

        // Load Font Style
        String fontStyle = sharedPreferences.getString("fontStyle", "Inter");
        tvFontStyleValue.setText(fontStyle);

        // Load Reading Direction
        selectedReadingDirection = sharedPreferences.getString("readingDirection", "Left to Right");
        tvReadingDirectionValue.setText(selectedReadingDirection);

        // Load Page Turn Animation
        selectedPageTurn = sharedPreferences.getString("pageTurn", "Slide");
        tvPageTurnValue.setText(selectedPageTurn);

        // Load Switches
        switchReadingProgress.setChecked(sharedPreferences.getBoolean("showProgress", false));
        switchNotifications.setChecked(sharedPreferences.getBoolean("notifications", true));

        // Load Language
        selectedLanguage = sharedPreferences.getString("language", "English");
        tvLanguageValue.setText(selectedLanguage);
    }

    private void setupListeners() {
        // Theme toggle
        findViewById(R.id.layoutTheme).setOnClickListener(v -> {
            if (themeOptionsContainer.getVisibility() == View.VISIBLE) {
                themeOptionsContainer.setVisibility(View.GONE);
            } else {
                themeOptionsContainer.setVisibility(View.VISIBLE);
            }
        });

        // Theme selection
        themeLight.setOnClickListener(v -> {
            selectedTheme = "Light";
            saveTheme();
        });

        themeSepia.setOnClickListener(v -> {
            selectedTheme = "Sepia";
            saveTheme();
        });

        themeDark.setOnClickListener(v -> {
            selectedTheme = "Dark";
            saveTheme();
        });

        // Font Size
        seekBarFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int fontSize = progress + 10; // Range 10-30
                tvFontSizeValue.setText(fontSize + "pt");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int fontSize = seekBar.getProgress() + 10;
                sharedPreferences.edit().putInt("fontSize", fontSize).apply();
            }
        });

        // Font Style
        findViewById(R.id.layoutFontStyle).setOnClickListener(v -> {
            // TODO: Open font style picker dialog
            showFontStylePicker();
        });

        // Reading Direction
        findViewById(R.id.layoutReadingDirection).setOnClickListener(v -> {
            // TODO: Open reading direction picker dialog
            showReadingDirectionPicker();
        });

        // Page Turn Animation
        findViewById(R.id.layoutPageTurn).setOnClickListener(v -> {
            // TODO: Open page turn animation picker dialog
            showPageTurnPicker();
        });

        // Reading Progress Switch
        switchReadingProgress.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("showProgress", isChecked).apply();
        });

        // Notifications Switch
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("notifications", isChecked).apply();
        });

        // Language
        findViewById(R.id.layoutLanguage).setOnClickListener(v -> {
            // TODO: Open language picker dialog
            showLanguagePicker();
        });

        // About & Support
        findViewById(R.id.layoutAbout).setOnClickListener(v -> {
            // TODO: Open About & Support screen
        });
    }

    private void saveTheme() {
        tvThemeValue.setText(selectedTheme);
        updateThemeSelection();
        sharedPreferences.edit().putString("theme", selectedTheme).apply();
        // TODO: Apply theme change
    }

    private void updateThemeSelection() {
        // Reset all borders
        themeLight.setStrokeColor(getResources().getColor(android.R.color.transparent));
        themeSepia.setStrokeColor(getResources().getColor(android.R.color.transparent));
        themeDark.setStrokeColor(getResources().getColor(android.R.color.transparent));

        // Highlight selected theme
        switch (selectedTheme) {
            case "Light":
                themeLight.setStrokeColor(getResources().getColor(R.color.primary));
                break;
            case "Sepia":
                themeSepia.setStrokeColor(getResources().getColor(R.color.primary));
                break;
            case "Dark":
                themeDark.setStrokeColor(getResources().getColor(R.color.primary));
                break;
        }
    }

    private void showFontStylePicker() {
        // TODO: Implement font style picker dialog
        String[] fonts = {"Inter", "Roboto", "Open Sans", "Lato", "Montserrat"};
        
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select Font Style")
                .setItems(fonts, (dialog, which) -> {
                    String font = fonts[which];
                    tvFontStyleValue.setText(font);
                    sharedPreferences.edit().putString("fontStyle", font).apply();
                })
                .show();
    }

    private void showReadingDirectionPicker() {
        String[] directions = {"Left to Right", "Right to Left", "Vertical"};
        
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select Reading Direction")
                .setItems(directions, (dialog, which) -> {
                    selectedReadingDirection = directions[which];
                    tvReadingDirectionValue.setText(selectedReadingDirection);
                    sharedPreferences.edit().putString("readingDirection", selectedReadingDirection).apply();
                })
                .show();
    }

    private void showPageTurnPicker() {
        String[] animations = {"Slide", "Fade", "Curl", "None"};
        
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select Page Turn Animation")
                .setItems(animations, (dialog, which) -> {
                    selectedPageTurn = animations[which];
                    tvPageTurnValue.setText(selectedPageTurn);
                    sharedPreferences.edit().putString("pageTurn", selectedPageTurn).apply();
                })
                .show();
    }

    private void showLanguagePicker() {
        String[] languages = {"English", "Tiếng Việt", "日本語", "한국어", "中文"};
        
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select Language")
                .setItems(languages, (dialog, which) -> {
                    selectedLanguage = languages[which];
                    tvLanguageValue.setText(selectedLanguage);
                    sharedPreferences.edit().putString("language", selectedLanguage).apply();
                })
                .show();
    }
}
