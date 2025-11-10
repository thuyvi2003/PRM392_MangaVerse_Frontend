package com.example.mangaverse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mangaverse.api.ApiService;
import com.example.mangaverse.api.RetrofitClient;
import com.example.mangaverse.model.auth.AuthResponse;
import com.example.mangaverse.model.auth.RegisterRequest;
import com.example.mangaverse.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    private EditText etName, etPhone, etUsername, etEmail, etPassword;
    private Button btnCreateAccount;
    private TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Ánh xạ View
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        tvLoginLink = findViewById(R.id.tvLoginLink);
        tvLoginLink.setOnClickListener(v -> navigateToLogin());

        btnCreateAccount.setOnClickListener(v -> performRegister());
    }

    private void navigateToLogin() {
        Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void performRegister() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Kiểm tra dữ liệu bắt buộc (Backend yêu cầu name, username, email, password)
        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ các trường bắt buộc.", Toast.LENGTH_SHORT).show();
            return;
        }

        btnCreateAccount.setEnabled(false); // Ngăn click kép

        // Tạo Request Body
        RegisterRequest registerData = new RegisterRequest(name, phone, username, email, password);
        ApiService apiService = RetrofitClient.getApiService();

        apiService.registerUser(registerData)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        btnCreateAccount.setEnabled(true);

                        if (response.isSuccessful() && response.body() != null) {
                            String token = response.body().getToken();
                            saveToken(token);
                            Toast.makeText(AuthActivity.this, "Đăng ký thành công! Đã đăng nhập.", Toast.LENGTH_LONG).show();

                            // Chuyển sang màn hình chính
                            startActivity(new Intent(AuthActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // Backend trả về lỗi 400 nếu User/Email đã tồn tại
                            Toast.makeText(AuthActivity.this, "Đăng ký thất bại: Tài khoản/Email đã tồn tại.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        btnCreateAccount.setEnabled(true);
                        Toast.makeText(AuthActivity.this, "Lỗi kết nối Server: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveToken(String token) {
        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("JWT_TOKEN", token);
        editor.apply();
    }
}