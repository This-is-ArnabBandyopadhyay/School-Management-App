package com.example.stuadminlogin.models;

public class Holiday {
    private int id;
    private String title;
    private String description;
    private String holidayDate;
    private String createdAt;
    private String adminName;

    public Holiday(int id, String title, String description, String holidayDate, String createdAt, String adminName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.holidayDate = holidayDate;
        this.createdAt = createdAt;
        this.adminName = adminName;
    }

    // Getters and Setters
    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getHolidayDate() { return holidayDate; }

    public String getCreatedAt() { return createdAt; }

    public String getAdminName() { return adminName; }
}
