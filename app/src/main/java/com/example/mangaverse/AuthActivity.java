package com.example.mangaverse;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class AuthActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnCreateAccount;
    private TextView tvLoginLink;
    private MaterialCardView btnGoogleLogin, btnAppleLogin;
    private View btnAddPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        setupClickListeners();
        setupLoginLink();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        tvLoginLink = findViewById(R.id.tvLoginLink);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        btnAppleLogin = findViewById(R.id.btnAppleLogin);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);
    }

    private void setupClickListeners() {
        btnCreateAccount.setOnClickListener(v -> handleCreateAccount());
        
        btnAddPhoto.setOnClickListener(v -> {
            Toast.makeText(this, "Add photo feature coming soon", Toast.LENGTH_SHORT).show();
        });

        btnGoogleLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Google login coming soon", Toast.LENGTH_SHORT).show();
            navigateToDiscover();
        });

        btnAppleLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Apple login coming soon", Toast.LENGTH_SHORT).show();
            navigateToDiscover();
        });
    }

    private void setupLoginLink() {
        String text = "Already have an account? Log In";
        SpannableString spannableString = new SpannableString(text);
        
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                navigateToLogin();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.primary, null));
                ds.setUnderlineText(false);
            }
        };

        spannableString.setSpan(clickableSpan, text.indexOf("Log In"), text.length(), 
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        tvLoginLink.setText(spannableString);
        tvLoginLink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void handleCreateAccount() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Basic validation
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        // TODO: Implement actual account creation logic
        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
        navigateToDiscover();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToDiscover() {
        Intent intent = new Intent(this, DiscoverActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
