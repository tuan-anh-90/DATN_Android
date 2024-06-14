package com.example.calendar.Model;

import java.time.LocalDateTime;

public class Post {
    private Long id;
    private String tieuDe;
    private String noiDung;
    private String anh;
    private LocalDateTime TGBD;

    public Long getId() {
        return id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public String getAnh() {
        return anh;
    }

    public LocalDateTime getTGBD() {
        return TGBD;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public void setTGBD(LocalDateTime TGBD) {
        this.TGBD = TGBD;
    }
}
