package com.example.stuadminlogin.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

public class StudentDashboardActivity extends Activity {

    Button viewNoticesButton, submitQueryBtn, viewQueriesBtn, applyLeaveBtn, viewLeaveStatusBtn, 
           checkAttendanceBtn, btnViewHolidays, btnViewMyDetails, btnLogout;
    TextView welcomeText;
    int loggedInStudentId;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize Views
        viewNoticesButton = findViewById(R.id.viewNoticesButton);
        submitQueryBtn = findViewById(R.id.btn_submit_query);
        viewQueriesBtn = findViewById(R.id.btn_view_queries);
        applyLeaveBtn = findViewById(R.id.btn_apply_leave);
        viewLeaveStatusBtn = findViewById(R.id.btn_view_leave_status);
        checkAttendanceBtn = findViewById(R.id.btn_check_attendance);
        btnViewHolidays = findViewById(R.id.btnViewHolidays);
        btnViewMyDetails = findViewById(R.id.btnViewMyDetails);
        btnLogout = findViewById(R.id.btnLogout);
        welcomeText = findViewById(R.id.welcomeText);

        // Retrieve student_id
        loggedInStudentId = getIntent().getIntExtra("student_id", -1);
        if (loggedInStudentId == -1) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            loggedInStudentId = sharedPreferences.getInt("student_id", -1);
        }

        if (loggedInStudentId == -1) {
            Toast.makeText(this, "Student not identified. Please log in again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Fetch and display student name
        displayStudentName();

        // Button Actions (keep all your existing button click listeners)
        viewNoticesButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, ViewNoticesActivity.class);
            intent.putExtra("student_id", loggedInStudentId);
            startActivity(intent);
        });

        submitQueryBtn.setOnClickListener(view -> {
            Intent submitIntent = new Intent(StudentDashboardActivity.this, SubmitQueryActivity.class);
            submitIntent.putExtra("student_id", loggedInStudentId);
            startActivity(submitIntent);
        });

        viewQueriesBtn.setOnClickListener(view -> {
            Intent viewIntent = new Intent(StudentDashboardActivity.this, ViewMyQueriesActivity.class);
            viewIntent.putExtra("student_id", loggedInStudentId);
            startActivity(viewIntent);
        });

        applyLeaveBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, LeaveApplicationFormActivity.class);
            intent.putExtra("student_id", loggedInStudentId);
            startActivity(intent);
        });

        viewLeaveStatusBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, StudentLeaveListActivity.class);
            intent.putExtra("student_id", loggedInStudentId);
            startActivity(intent);
        });

        checkAttendanceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, ViewAttendanceActivity.class);
            intent.putExtra("student_id", loggedInStudentId);
            startActivity(intent);
        });

        btnViewHolidays.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, ViewHolidaysActivity.class);
            startActivity(intent);
        });

        btnViewMyDetails.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentProfileActivity.class);
            intent.putExtra("student_id", loggedInStudentId);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(StudentDashboardActivity.this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();

                    Intent intent = new Intent(StudentDashboardActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }

    private void displayStudentName() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT name FROM students WHERE student_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(loggedInStudentId)});

        if (cursor != null && cursor.moveToFirst()) {
            String studentName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            welcomeText.setText("Welcome back, " + studentName);
            cursor.close();
        } else {
            welcomeText.setText("Welcome back, Student");
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}