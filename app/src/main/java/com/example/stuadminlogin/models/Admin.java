// === models/Admin.java ===
package com.example.stuadminlogin.models;
import android.net.Uri;


public class Admin {
    private int adminId;
    private String username;
    private String fullName;
    private String password;
    private String createdAt;
    private String emailId, phoneNo, address, dob, dateOfJoining, profilePhotoUri, lastLogin;


    public Admin(int adminId, String username, String fullName, String password, String createdAt) {
        this.adminId = adminId;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.createdAt = createdAt;
    }


    public Admin(int adminId, String username, String fullName, String password, String createdAt,
            String emailId, String phoneNo, String address, String dob, 
            String dateOfJoining, String profilePhotoUri, String lastLogin) {
        this.adminId = adminId;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.createdAt = createdAt;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.address = address;
        this.dob = dob;
        this.dateOfJoining = dateOfJoining;
        this.profilePhotoUri = profilePhotoUri;
        this.lastLogin = lastLogin;
    }


    // Getters and setters
    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getProfilePhotoUri() {
        return profilePhotoUri;
    }

    public void setProfilePhotoUri(String profilePhotoUri) {
        this.profilePhotoUri = profilePhotoUri;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Uri getProfilePhotoUriAsUri() {
    return profilePhotoUri != null ? Uri.parse(profilePhotoUri) : null;
}


}

