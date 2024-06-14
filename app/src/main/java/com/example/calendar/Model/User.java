package com.example.calendar.Model;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String password;
    private String email;
    private String name;
    private String chucVu;
    private String maNV;
    private String gioiTinh;
    private String role;
    private String resetPasswordToken;

    public User() {
    }

    public User(Long id, String password, String email, String name, String chucVu, String maNV, String gioiTinh, String role, String resetPasswordToken) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.chucVu = chucVu;
        this.maNV = maNV;
        this.gioiTinh = gioiTinh;
        this.role = role;
        this.resetPasswordToken = resetPasswordToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}
