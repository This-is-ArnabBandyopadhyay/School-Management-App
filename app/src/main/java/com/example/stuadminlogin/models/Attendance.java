package com.example.stuadminlogin.models;

public class Attendance {
    private int attendanceId;
    private int studentId;
    private String date;
    private String status;

    public Attendance(int attendanceId, int studentId, String date, String status) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.date = date;
        this.status = status;
    }

    public int getAttendanceId() { return attendanceId; }
    public int getStudentId() { return studentId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }

    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setDate(String date) { this.date = date; }
    public void setStatus(String status) { this.status = status; }
}
