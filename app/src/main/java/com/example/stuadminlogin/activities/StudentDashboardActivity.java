package com.example.stuadminlogin.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.stuadminlogin.R;

public class StudentDashboardActivity extends Activity {

    Button viewNoticesButton, submitQueryBtn, viewQueriesBtn, applyLeaveBtn, viewLeaveStatusBtn, checkAttendanceBtn;
    int loggedInStudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        // ✅ Initialize Buttons
        viewNoticesButton = findViewById(R.id.viewNoticesButton);
        submitQueryBtn = findViewById(R.id.btn_submit_query);
        viewQueriesBtn = findViewById(R.id.btn_view_queries);
        applyLeaveBtn = findViewById(R.id.btn_apply_leave);
        viewLeaveStatusBtn = findViewById(R.id.btn_view_leave_status);
        checkAttendanceBtn = findViewById(R.id.btn_check_attendance); // ✅ New button

        // ✅ Retrieve student_id
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

        // ✅ Button Actions
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

        // ✅ Check Attendance
        checkAttendanceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, ViewAttendanceActivity.class); // make sure this class exists
            intent.putExtra("student_id", loggedInStudentId);
            startActivity(intent);
        });
    }
}
