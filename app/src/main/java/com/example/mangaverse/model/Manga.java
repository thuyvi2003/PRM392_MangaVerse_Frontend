package com.example.mangaverse.model;

public class Manga {
    private String id;
    private String title;
    private String coverUrl;
    private double score;
    private int chapters;
    private String category;
    private boolean isFeatured;

    public Manga(String id, String title, String coverUrl, double score, int chapters, String category, boolean isFeatured) {
        this.id = id;
        this.title = title;
        this.coverUrl = coverUrl;
        this.score = score;
        this.chapters = chapters;
        this.category = category;
        this.isFeatured = isFeatured;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }
}
