package com.example.calendar.Model;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    Context mContext;
    private OnLogoutListener onLogoutListener;

    public WebAppInterface(Context c) {
        mContext = c;
    }

    public void setOnLogoutListener(OnLogoutListener listener) {
        this.onLogoutListener = listener;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void receiveUserData(long userId, String email, String name, String role) {
        // Nhận dữ liệu từ web và xử lý nó trong Android
        Log.d("WebAppInterface", "User ID: " + userId + ", Email: " + email + ", Name: " + name + ", Role: " + role);
    }

    @JavascriptInterface
    public void logout() {
        if (onLogoutListener != null) {
            onLogoutListener.onLogout();
        }
    }

    public interface OnLogoutListener {
        void onLogout();
    }
}
