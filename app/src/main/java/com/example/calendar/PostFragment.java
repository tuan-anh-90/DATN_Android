package com.example.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.calendar.Model.User;
import com.example.calendar.Model.WebAppInterface;

public class PostFragment extends Fragment {
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        webView = view.findViewById(R.id.webview);
        WebAppInterface webAppInterface = new WebAppInterface(requireActivity());
        webAppInterface.setOnLogoutListener(new WebAppInterface.OnLogoutListener() {
            @Override
            public void onLogout() {

                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        webView.addJavascriptInterface(webAppInterface, "Android");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://10.0.2.2:8081/baidangAndr");

        Intent intent = requireActivity().getIntent();
        User loggedInUser = (User) intent.getSerializableExtra("user");

        Long userId = null;
        if (loggedInUser != null) {
            userId = loggedInUser.getId();
            String userEmail = loggedInUser.getEmail();
            String userName = loggedInUser.getName();
            String userRole = loggedInUser.getRole();
            // Bây giờ bạn có thông tin người dùng và có thể sử dụng chúng ở đây.
        }
        Log.d("LoginActivity", "ID: " + userId);

        return view;
    }
}
