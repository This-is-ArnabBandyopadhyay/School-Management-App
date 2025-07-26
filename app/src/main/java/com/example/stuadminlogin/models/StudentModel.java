package com.example.stuadminlogin.models;
import android.net.Uri;


public class StudentModel {
    private int studentId;
    private String rollNo;
    private String registrationNo;
    private String name;
    private String email;
    private String phoneNo;
    private String address;
    private String admissionDate;
    private String profilePhotoUri;
    private String lastLogin;
    private String fatherName;
    private String motherName;
    private String dob;
    private String password;
    private String createdAt;

    private int sectionId;
    private int classId; // ✅ Added for DB relation

    private String studentClass; // Optional: Only used for display
    private String section;      // Optional: Only used for display

    public StudentModel() {}

    public StudentModel(int studentId, String rollNo, String registrationNo, String name,
                        int sectionId, int classId, String email, String phoneNo,
                        String fatherName, String motherName, String dob, String password, String createdAt) {
        this.studentId = studentId;
        this.rollNo = rollNo;
        this.registrationNo = registrationNo;
        this.name = name;
        this.sectionId = sectionId;
        this.classId = classId;
        this.email = email;
        this.phoneNo = phoneNo;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.dob = dob;
        this.password = password;
        this.createdAt = createdAt;
    }

    public StudentModel(int studentId, String rollNo, String registrationNo, String name,
                        String email, String phoneNo, String fatherName, String motherName,
            String dob, String password, String createdAt) {
        this.studentId = studentId;
        this.rollNo = rollNo;
        this.registrationNo = registrationNo;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.dob = dob;
        this.password = password;
        this.createdAt = createdAt;
    }
    
    public StudentModel(int studentId, String rollNo, String registrationNo, String name,
                        int sectionId, int classId, String email, String phoneNo,String address,String admissionDate, String profilePhotoUri,String lastLogin,
                        String fatherName, String motherName, String dob, String password, String createdAt) {
        this.studentId = studentId;
        this.rollNo = rollNo;
        this.registrationNo = registrationNo;
        this.name = name;
        this.sectionId = sectionId;
        this.classId = classId;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
        this.admissionDate = admissionDate;
        this.profilePhotoUri = profilePhotoUri;
        this.lastLogin = lastLogin;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.dob = dob;
        this.password = password;
        this.createdAt = createdAt;
    }



    // --- Getters and Setters ---

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getRegistrationNo() { return registrationNo; }
    public void setRegistrationNo(String registrationNo) { this.registrationNo = registrationNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSectionId() { return sectionId; }
    public void setSectionId(int sectionId) { this.sectionId = sectionId; }

    public int getClassId() { return classId; } // ✅ Getter
    public void setClassId(int classId) { this.classId = classId; } // ✅ Setter

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNo() { return phoneNo; }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    public String getAddress() { return address; }
public void setAddress(String address) { this.address = address; }

public String getAdmissionDate() { return admissionDate; }
public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }

public String getProfilePhotoUri() { return profilePhotoUri; }
public void setProfilePhotoUri(String profilePhotoUri) { this.profilePhotoUri = profilePhotoUri; }

public String getLastLogin() { return lastLogin; }
public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    // --- Optional Display Data ---

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    // --- Filtering/Search Utility ---
    public boolean matches(String keyword) {
        keyword = keyword.toLowerCase();
        return (rollNo != null && rollNo.toLowerCase().contains(keyword)) ||
                (registrationNo != null && registrationNo.toLowerCase().contains(keyword)) ||
                (name != null && name.toLowerCase().contains(keyword)) ||
                (email != null && email.toLowerCase().contains(keyword)) ||
                (phoneNo != null && phoneNo.toLowerCase().contains(keyword)) ||
                (fatherName != null && fatherName.toLowerCase().contains(keyword)) ||
                (motherName != null && motherName.toLowerCase().contains(keyword)) ||
                (dob != null && dob.toLowerCase().contains(keyword));
    }
    
    public Uri getProfilePhotoUriAsUri() {
    return profilePhotoUri != null ? Uri.parse(profilePhotoUri) : null;
}

}
