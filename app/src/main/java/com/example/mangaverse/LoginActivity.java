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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignupLink, tvForgotPassword;
    private MaterialCardView btnGoogleLogin, btnAppleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        setupClickListeners();
        setupSignupLink();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignupLink = findViewById(R.id.tvSignupLink);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        btnAppleLogin = findViewById(R.id.btnAppleLogin);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());

        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Forgot password feature coming soon", Toast.LENGTH_SHORT).show();
        });

        btnGoogleLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Google login coming soon", Toast.LENGTH_SHORT).show();
            navigateToHome();
        });

        btnAppleLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Apple login coming soon", Toast.LENGTH_SHORT).show();
            navigateToHome();
        });
    }

    private void setupSignupLink() {
        String text = "Don't have an account? Sign Up";
        SpannableString spannableString = new SpannableString(text);
        
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                navigateToAuth();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.primary, null));
                ds.setUnderlineText(false);
            }
        };

        spannableString.setSpan(clickableSpan, text.indexOf("Sign Up"), text.length(), 
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        tvSignupLink.setText(spannableString);
        tvSignupLink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void handleLogin() {
        // Không cần validation, bấm là vào thẳng
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
        navigateToHome();
    }

    private void navigateToAuth() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
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
