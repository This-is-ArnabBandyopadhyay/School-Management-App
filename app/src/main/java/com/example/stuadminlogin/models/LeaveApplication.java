package com.example.stuadminlogin.models;

public class LeaveApplication {
    private int leaveId;
    private int studentId;
    private int adminId;

    private String studentName;
    private String fromDate;
    private String toDate;
    private String reason;
    private String status;
    private String adminResponse;
    private String appliedAt;
    private String reviewedAt;
    private String adminName;

    // Newly added student details
    private String rollNo;
    private String studentClass;
    private String section;

    // --- Constructors ---

    // Constructor for Admin view (pending or all applications)
   public LeaveApplication(int leaveId, int studentId, String studentName,
                        String rollNo, String studentClass, String section,
                        String fromDate, String toDate, String reason,
                        String status, String appliedAt) {
    this.leaveId = leaveId;
    this.studentId = studentId;
    this.studentName = studentName;
    this.rollNo = rollNo;
    this.studentClass = studentClass;
    this.section = section;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.reason = reason;
    this.status = status;
    this.appliedAt = appliedAt;
}


    // Constructor for Student View (Admin details shown)
    public LeaveApplication(int leaveId, String fromDate, String toDate,
                            String reason, String status, String appliedAt,
            String adminName, String adminResponse, String reviewedAt) {
        this.leaveId = leaveId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
        this.appliedAt = appliedAt;
        this.adminName = adminName;
        this.adminResponse = adminResponse;
        this.reviewedAt = reviewedAt;
    }
    
    // Constructor used in database for admin leave list with student name
public LeaveApplication(int leaveId, int studentId, String studentName,
                        String fromDate, String toDate, String reason,
        String status, String appliedAt) {
    this.leaveId = leaveId;
    this.studentId = studentId;
    this.studentName = studentName;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.reason = reason;
    this.status = status;
    this.appliedAt = appliedAt;
}

public LeaveApplication(int leaveId, int studentId, String fromDate, String toDate, String reason,
        String status, int adminId, String adminResponse, String appliedAt, String reviewedAt) {
    this.leaveId = leaveId;
    this.studentId = studentId;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.reason = reason;
    this.status = status;
    this.adminId = adminId;
    this.adminResponse = adminResponse;
    this.appliedAt = appliedAt;
    this.reviewedAt = reviewedAt;
}



    // --- Getters ---
    public int getLeaveId() { return leaveId; }
    public int getStudentId() { return studentId; }
    public int getAdminId() { return adminId; }
    public String getStudentName() { return studentName; }
    public String getFromDate() { return fromDate; }
    public String getToDate() { return toDate; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public String getAdminResponse() { return adminResponse; }
    public String getAppliedAt() { return appliedAt; }
    public String getReviewedAt() { return reviewedAt; }
    public String getAdminName() { return adminName; }

    public String getRollNo() { return rollNo; }
    public String getStudentClass() { return studentClass; }
    public String getSection() { return section; }

    // --- Setters ---
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }
    public void setSection(String section) { this.section = section; }
}
