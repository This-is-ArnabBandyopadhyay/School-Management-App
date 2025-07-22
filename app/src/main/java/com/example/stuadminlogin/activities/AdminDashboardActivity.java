package com.example.stuadminlogin.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.stuadminlogin.R;

public class AdminDashboardActivity extends Activity {

    Button  issueNoticeBtn, viewAllQueriesBtn;
    Button manageAdminsBtn, manageLeaveBtn, manageStudentsBtn, manageClassesBtn;

    int currentAdminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize buttons
       
        issueNoticeBtn = findViewById(R.id.issueNoticeButton);
        viewAllQueriesBtn = findViewById(R.id.btn_view_all_queries);
        manageAdminsBtn = findViewById(R.id.btn_manage_admins);
        manageLeaveBtn = findViewById(R.id.btn_manage_leaves);
        manageStudentsBtn = findViewById(R.id.btn_manage_students);
        manageClassesBtn = findViewById(R.id.btn_manage_classes);

        // Get admin ID (from intent or SharedPreferences)
        currentAdminId = getIntent().getIntExtra("admin_id", -1);
        if (currentAdminId == -1) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            currentAdminId = sharedPreferences.getInt("admin_id", -1);
        }

        // Validate admin
        if (currentAdminId == -1) {
            Toast.makeText(this, "Admin not identified. Please log in again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Button Click Listeners
      

        issueNoticeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoticeActivity.class);
            intent.putExtra("admin_id", currentAdminId);
            startActivity(intent);
        });

        viewAllQueriesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminViewQueriesActivity.class);
            intent.putExtra("admin_id", currentAdminId);
            startActivity(intent);
        });

        manageAdminsBtn.setOnClickListener(v -> startActivity(new Intent(this, EditAdminActivity.class)));

        manageClassesBtn.setOnClickListener(v -> startActivity(new Intent(this, ManageClassesActivity.class)));

        manageLeaveBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminLeaveReviewActivity.class);
            intent.putExtra("admin_id", currentAdminId);
            startActivity(intent);
        });

        // manageStudentsBtn.setOnClickListener(v ->
        //     startActivity(new Intent(this, EditStudentActivity.class))
        // );
    }
}
