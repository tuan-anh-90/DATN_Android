package com.example.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.calendar.Model.User;
import com.example.calendar.Model.WebAppInterface;

public class HomeFragment extends Fragment {
    private WebView webView;

    private Long userId;
    private String userEmail;
    private String userName;
    private String userRole;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // Lấy thông tin người dùng từ Intent
        Intent intent = requireActivity().getIntent();
        User loggedInUser = (User) intent.getSerializableExtra("user");

        if (loggedInUser != null) {
            userId = loggedInUser.getId();
            userEmail = loggedInUser.getEmail();
            userName = loggedInUser.getName();
            userRole = loggedInUser.getRole();
        }

        Log.d("HomeFragment", "User ID: " + userId);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (userId != null && userEmail != null && userName != null && userRole != null) {
                    String script = "javascript:(function() {" +
                            "   var userId = " + userId + ";" +
                            "   var userEmail = '" + userEmail + "';" +
                            "   var userName = '" + userName + "';" +
                            "   var userRole = '" + userRole + "';" +
                            "   Android.receiveUserData(userId, userEmail, userName, userRole);" +
                            "})()";
                    webView.loadUrl(script);
                }
            }
        });

        webView.loadUrl("http://10.0.2.2:8081/homeAndr");

        return view;
    }
}
