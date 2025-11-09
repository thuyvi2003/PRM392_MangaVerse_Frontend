package com.example.mangaverse.model;

public class Banner {
    private String title;
    private int imageResId;
    private String mangaId;

    public Banner(String title, int imageResId, String mangaId) {
        this.title = title;
        this.imageResId = imageResId;
        this.mangaId = mangaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getMangaId() {
        return mangaId;
    }

    public void setMangaId(String mangaId) {
        this.mangaId = mangaId;
    }
}
