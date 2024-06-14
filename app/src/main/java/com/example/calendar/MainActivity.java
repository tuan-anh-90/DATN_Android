package com.example.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.calendar.Model.User;
import com.example.calendar.Model.WebAppInterface;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private WebView webView;
    private DrawerLayout drawerLayout;
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_post);
        }

        // Lấy thông tin người dùng từ Intent
        Intent intent = getIntent();
        loggedInUser = (User) intent.getSerializableExtra("user");

        View headerView = navigationView.getHeaderView(0);
        if (loggedInUser != null) {
            String userEmail = loggedInUser.getEmail();
            String userName = loggedInUser.getName();

            // Lấy thông tin người dùng và gắn vào header menu
            TextView tagName = headerView.findViewById(R.id.tag_name);
            TextView tagEmail = headerView.findViewById(R.id.tag_email);

            tagName.setText(userName);
            tagEmail.setText(userEmail);
        }

        // Khởi tạo WebView
        webView = findViewById(R.id.webview);
        WebAppInterface webAppInterface = new WebAppInterface(this);

        // Xử lý sự kiện đăng xuất
        webAppInterface.setOnLogoutListener(new WebAppInterface.OnLogoutListener() {
            @Override
            public void onLogout() {
                // Xử lý đăng xuất ở đây (đặt lại trạng thái đăng nhập, xóa dữ liệu người dùng)
                // Sau đó, sử dụng Intent để mở trang đăng nhập
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Kết thúc Activity hiện tại
            }
        });

        webView.addJavascriptInterface(webAppInterface, "Android");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://10.0.2.2:8081/homeAndr.html");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_post) {
            selectedFragment = new PostFragment();
        } else if (id == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (id == R.id.nav_form) {
            selectedFragment = new FormFragment();
        } else if (id == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", loggedInUser);
            selectedFragment.setArguments(bundle);
        } else if (id == R.id.nav_password) {
            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
            changePasswordFragment.setUserId(loggedInUser.getId());
            selectedFragment = changePasswordFragment;
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
