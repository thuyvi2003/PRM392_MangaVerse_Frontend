package com.example.mangaverse;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mangaverse.api.ApiService;
import com.example.mangaverse.api.RetrofitClient;
import com.example.mangaverse.model.auth.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etPhone, etUsername, etEmail;
    private Button btnSave;
    private Button btnLogout;
    private ImageButton btnBack;
    private TextView tvToolbarTitle;
    private UserModel currentUserData;
    private static final String PREF_NAME = "AppPrefs";
    private static final String KEY_TOKEN = "JWT_TOKEN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Ánh xạ View và Setup Toolbar
        initToolbarViews();
        initProfileViews();
        setupToolbarLogic();

        // Lấy Token và tải hồ sơ
        String token = retrieveToken();
        if (token != null) {
            fetchUserProfile(token);
            btnSave.setOnClickListener(v -> updateProfile(token));
            btnLogout.setOnClickListener(v -> performLogout());
        } else {
            Toast.makeText(this, "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show();
            // TODO: Chuyển hướng người dùng về LoginActivity
        }
    }

    /** Ánh xạ các thành phần Toolbar (btnBack, tvToolbarTitle) */
    private void initToolbarViews() {
        btnBack = findViewById(R.id.btnBack);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);

        ImageButton btnSearch = findViewById(R.id.btnSearch);
        ImageButton btnProfile = findViewById(R.id.btnProfile);

        if (btnSearch != null) btnSearch.setVisibility(View.GONE);
        if (btnProfile != null) btnProfile.setVisibility(View.GONE);
    }

    /** Ánh xạ các thành phần Profile chính */
    private void initProfileViews() {
        etName = findViewById(R.id.etProfileName);
        etUsername = findViewById(R.id.etProfileUsername);
        etEmail = findViewById(R.id.etProfileEmail);
        etPhone = findViewById(R.id.etProfilePhone);
        btnSave = findViewById(R.id.btnSaveProfile);
        btnLogout = findViewById(R.id.btnLogout);
    }

    /** Thiết lập tiêu đề và chức năng nút Back */
    private void setupToolbarLogic() {
        if (tvToolbarTitle != null) {
            tvToolbarTitle.setText("Profile");
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    /** Lấy JWT Token từ SharedPreferences */
    private String retrieveToken() {
        SharedPreferences sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_TOKEN, null);
    }

    /** 3. HÀM THỰC HIỆN ĐĂNG XUẤT */
    private void performLogout() {
        // 1. Xóa Token khỏi SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();

        Toast.makeText(this, "Đã đăng xuất thành công.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    /** 1. GET /api/profile/me: Lấy dữ liệu hồ sơ */
    private void fetchUserProfile(String token) {
        String authToken = "Bearer " + token;

        ApiService apiService = RetrofitClient.getApiService();
        btnSave.setEnabled(false);
        btnLogout.setEnabled(false);

        apiService.getProfile(authToken)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        btnSave.setEnabled(true);
                        btnLogout.setEnabled(true);

                        if (response.isSuccessful() && response.body() != null) {
                            currentUserData = response.body();

                            // Điền dữ liệu vào UI
                            etName.setText(currentUserData.getName());
                            etUsername.setText(currentUserData.getUsername());
                            etEmail.setText(currentUserData.getEmail());
                            etPhone.setText(currentUserData.getPhone());

                            Toast.makeText(ProfileActivity.this, "Đã tải hồ sơ thành công.", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {
                            Toast.makeText(ProfileActivity.this, "Phiên hết hạn, vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Không thể tải hồ sơ. Mã lỗi: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        btnSave.setEnabled(true);
                        btnLogout.setEnabled(true);
                        Toast.makeText(ProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    /** 2. PUT /api/profile: Cập nhật dữ liệu hồ sơ */
    private void updateProfile(String token) {
        String newName = etName.getText().toString().trim();
        String newPhone = etPhone.getText().toString().trim();

        UserModel updateData = new UserModel();
        updateData.setName(newName);
        updateData.setPhone(newPhone);

        if (currentUserData != null) {
            updateData.setUsername(currentUserData.getUsername());
            updateData.setEmail(currentUserData.getEmail());
        }

        String authToken = "Bearer " + token;

        ApiService apiService = RetrofitClient.getApiService();
        btnSave.setEnabled(false);
        btnLogout.setEnabled(false);

        apiService.updateProfile(authToken, updateData)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        btnSave.setEnabled(true);
                        btnLogout.setEnabled(true); // Kích hoạt lại nút

                        if (response.isSuccessful() && response.body() != null) {
                            currentUserData = response.body();
                            Toast.makeText(ProfileActivity.this, "Cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {
                            Toast.makeText(ProfileActivity.this, "Phiên hết hạn, vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Cập nhật thất bại. Vui lòng kiểm tra lại dữ liệu.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        btnSave.setEnabled(true);
                        btnLogout.setEnabled(true); // Kích hoạt lại nút
                        Toast.makeText(ProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}