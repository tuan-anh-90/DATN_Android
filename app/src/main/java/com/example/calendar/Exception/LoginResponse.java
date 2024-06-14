package com.example.calendar.Exception;

import com.example.calendar.Model.User;

public class LoginResponse {
    private String status;
    private String message;
    private User data;


    public LoginResponse(String status, String message, User data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
