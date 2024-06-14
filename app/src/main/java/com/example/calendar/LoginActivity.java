package com.example.calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.API.APIService;
import com.example.calendar.DTO.LoginDTO;
import com.example.calendar.Exception.LoginResponse;
import com.example.calendar.Model.User;
import com.example.calendar.Retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.login_button);

        // Khởi tạo Retrofit
        RetrofitService retrofitService = new RetrofitService();
        Retrofit retrofit = retrofitService.getRetrofit();

        // Khởi tạo interface APIService
        apiService = retrofit.create(APIService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                // Kiểm tra xem email và password có rỗng hay không
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Tạo đối tượng LoginDTO với email và password
                    LoginDTO loginDTO = new LoginDTO(email, password);

                    // Gọi phương thức loginUser để xác thực người dùng
                    loginUser(loginDTO);
                }
            }
        });
    }

    private void loginUser(LoginDTO loginDTO) {
        Call<LoginResponse> call = apiService.loginUser(loginDTO);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                Log.d("LoginActivity", "Response code: " + loginResponse.getStatus());
                if (response.isSuccessful()) {
                    if (loginResponse != null && loginResponse.getStatus().equals("200")) {
                        // Đăng nhập thành công
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Lấy dữ liệu người dùng
                        User loggedInUser = (User) loginResponse.getData();
                        if (loggedInUser != null) {
                            Long id = loggedInUser.getId();
                            String userEmail = loggedInUser.getEmail();
                            String userName = loggedInUser.getName();
                            String userRole = loggedInUser.getRole();

                            Log.d("LoginActivity", "ID 11: " + id);
                            Log.d("LoginActivity", "Email: " + userEmail);
                            Log.d("LoginActivity", "Name: " + userName);
                            Log.d("LoginActivity", "Role: " + userRole);

                            // Lưu email người dùng vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_email", userEmail);
                            editor.apply();

                            // Chuyển sang màn hình chính hoặc thực hiện các thao tác khác sau khi đăng nhập thành công
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user", loggedInUser);

                            startActivity(intent);
                            finish(); // Tùy chọn: Đóng LoginActivity sau khi chuyển màn hình
                        } else {
                            // Xử lý trường hợp dữ liệu không hợp lệ
                            Toast.makeText(LoginActivity.this, "Invalid user data", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Xử lý đăng nhập thất bại
                        Toast.makeText(LoginActivity.this, "Login failed: " + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý đăng nhập thất bại
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Xử lý lỗi
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("erro", "erro: " + t.getMessage());

            }
        });
    }
}
