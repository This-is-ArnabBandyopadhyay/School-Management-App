package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.stuadminlogin.R;

public class ViewStudentDetailsActivity extends AppCompatActivity {

    int studentId; // Received from ManageStudentsActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_details);

        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Invalid student ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Button viewNoticesButton = findViewById(R.id.btn_view_notices);
        Button btnViewRespondedQueries = findViewById(R.id.btnViewRespondedQueries);
        Button btnViewPendingQueries = findViewById(R.id.btnViewPendingQueries);

        // New separate attendance buttons
        Button btnUpdateAttendance = findViewById(R.id.btn_update_attendance);
        Button btnCheckAttendance = findViewById(R.id.btn_check_attendance);

        // Notices
        viewNoticesButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewStudentNoticesActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });

        // Responded Queries
        btnViewRespondedQueries.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewStudentRespondedQueriesActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });

        // Pending Queries
        btnViewPendingQueries.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewStudentPendingQueriesActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });

        // ✅ Update Attendance
        btnUpdateAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateAttendanceActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });

        // ✅ Check Attendance
        btnCheckAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewAttendanceActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });
    }
}
