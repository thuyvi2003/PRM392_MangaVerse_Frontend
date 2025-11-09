package com.example.mangaverse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MangaVersePrefs";
    private static final String KEY_FIRST_TIME = "isFirstTime";

    private ViewPager2 viewPager;
    private LinearLayout indicatorsLayout;
    private Button btnNext, btnSkip;
    private List<OnboardingPage> pages;
    private View[] indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Always go to login (skip onboarding for now)
        // TODO: Restore onboarding for production
        navigateToLogin();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        indicatorsLayout = findViewById(R.id.indicatorsLayout);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
    }

    private void setupPages() {
        pages = new ArrayList<>();
        pages.add(new OnboardingPage(
                R.drawable.ic_book_stories,
                "Welcome to Your Personal Library",
                "Carry all your favorite books with you, right in your pocket. Start building your collection today."
        ));
        pages.add(new OnboardingPage(
                R.drawable.ic_reading,
                "Read Anywhere, Anytime",
                "Enjoy your favorite manga and comics with our smooth reading experience, offline or online."
        ));
        pages.add(new OnboardingPage(
                R.drawable.ic_bookmark,
                "Never Lose Your Place",
                "Automatic bookmarks and reading progress sync across all your devices seamlessly."
        ));
        pages.add(new OnboardingPage(
                R.drawable.ic_library,
                "Organize Your Collection",
                "Create custom collections, add favorites, and keep track of everything you want to read."
        ));
    }

    private void setupViewPager() {
        OnboardingAdapter adapter = new OnboardingAdapter(pages);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);
                updateNextButton(position);
            }
        });
    }

    private void setupIndicators() {
        indicators = new View[pages.size()];
        indicatorsLayout.removeAllViews();

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(8), dpToPx(8)
            );
            params.setMargins(dpToPx(4), 0, dpToPx(4), 0);
            indicators[i].setLayoutParams(params);
            indicators[i].setBackgroundResource(R.drawable.indicator_inactive);
            indicatorsLayout.addView(indicators[i]);
        }

        updateIndicators(0);
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicators[i].getLayoutParams();
            if (i == position) {
                params.width = dpToPx(20);
                indicators[i].setBackgroundResource(R.drawable.indicator_active);
            } else {
                params.width = dpToPx(8);
                indicators[i].setBackgroundResource(R.drawable.indicator_inactive);
            }
            indicators[i].setLayoutParams(params);
        }
    }

    private void updateNextButton(int position) {
        if (position == pages.size() - 1) {
            btnNext.setText(R.string.get_started);
        } else {
            btnNext.setText(R.string.next);
        }
    }

    private void setupButtons() {
        btnNext.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < pages.size() - 1) {
                viewPager.setCurrentItem(currentItem + 1, true);
            } else {
                navigateToLogin();
            }
        });

        btnSkip.setOnClickListener(v -> navigateToLogin());
    }

    private void navigateToLogin() {
        markAsNotFirstTime();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void markAsNotFirstTime() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_FIRST_TIME, false).apply();
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    // Inner classes
    static class OnboardingPage {
        int iconResId;
        String title;
        String description;

        OnboardingPage(int iconResId, String title, String description) {
            this.iconResId = iconResId;
            this.title = title;
            this.description = description;
        }
    }

    class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {
        private List<OnboardingPage> pages;

        OnboardingAdapter(List<OnboardingPage> pages) {
            this.pages = pages;
        }

        @NonNull
        @Override
        public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.page_onboarding, parent, false);
            return new OnboardingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
            OnboardingPage page = pages.get(position);
            holder.bind(page);
        }

        @Override
        public int getItemCount() {
            return pages.size();
        }

        class OnboardingViewHolder extends RecyclerView.ViewHolder {
            ImageView imgIcon;
            TextView tvTitle, tvDescription;

            OnboardingViewHolder(@NonNull View itemView) {
                super(itemView);
                imgIcon = itemView.findViewById(R.id.imgIcon);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvDescription = itemView.findViewById(R.id.tvDescription);
            }

            void bind(OnboardingPage page) {
                imgIcon.setImageResource(page.iconResId);
                tvTitle.setText(page.title);
                tvDescription.setText(page.description);
            }
        }
    }
}
