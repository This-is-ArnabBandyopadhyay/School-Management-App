package com.example.stuadminlogin.models;

public class ParentModel {
    private int parentId;
    private String email;
    private String password; // Hashed password
    private String name;
    private String phoneNo;
    private String createdAt;
    private String lastLogin;
    private String profilePhotoUri; // <--- ADD THIS NEW FIELD

    public ParentModel() {
    }

    public ParentModel(int parentId, String email, String password, String name, String phoneNo, String createdAt, String lastLogin, String profilePhotoUri) {
        this.parentId = parentId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNo = phoneNo;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.profilePhotoUri = profilePhotoUri; // <--- INITIALIZE NEW FIELD
    }

    // Getters and Setters
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    // <--- ADD GETTER AND SETTER FOR profilePhotoUri ---
    public String getProfilePhotoUri() {
        return profilePhotoUri;
    }

    public void setProfilePhotoUri(String profilePhotoUri) {
        this.profilePhotoUri = profilePhotoUri;
    }
    // --- END NEW FIELD GETTER/SETTER ---
}