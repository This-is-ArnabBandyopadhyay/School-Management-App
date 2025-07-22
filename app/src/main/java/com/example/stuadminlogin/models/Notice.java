package com.example.stuadminlogin.models;

public class Notice {
    private int noticeId;
    private String title;
    private String description;
    private String createdAt;

    public Notice(int noticeId, String title, String description, String createdAt) {
        this.noticeId = noticeId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
