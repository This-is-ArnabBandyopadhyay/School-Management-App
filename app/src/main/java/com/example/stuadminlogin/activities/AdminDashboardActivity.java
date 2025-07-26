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

public class AdminDashboardActivity extends Activity {

    Button issueNoticeBtn, viewAllQueriesBtn;
    Button manageAdminsBtn, manageLeaveBtn, manageClassesBtn, btnManageHolidays, btnViewHolidays, btnViewProfile, btnLogout;
    TextView welcomeText;
    int currentAdminId;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        issueNoticeBtn = findViewById(R.id.issueNoticeButton);
        viewAllQueriesBtn = findViewById(R.id.btn_view_all_queries);
        manageAdminsBtn = findViewById(R.id.btn_manage_admins);
        manageLeaveBtn = findViewById(R.id.btn_manage_leaves);
        manageClassesBtn = findViewById(R.id.btn_manage_classes);
        btnManageHolidays = findViewById(R.id.btnManageHolidays);
        btnViewHolidays = findViewById(R.id.btnViewHolidays);
        btnViewProfile = findViewById(R.id.btnViewProfile);
        btnLogout = findViewById(R.id.btnLogout);
        welcomeText = findViewById(R.id.welcomeText);

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

        // Fetch and display admin name
        displayAdminName();

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

        btnManageHolidays.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ManageHolidaysActivity.class);
            intent.putExtra("admin_id", currentAdminId);
            startActivity(intent);
        });

        btnViewHolidays.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ViewHolidaysActivity.class);
            startActivity(intent);
        });

        btnViewProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminProfileActivity.class);
            intent.putExtra("admin_id", currentAdminId);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(AdminDashboardActivity.this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }

    private void displayAdminName() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT full_name FROM admins WHERE admin_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(currentAdminId)});

        if (cursor != null && cursor.moveToFirst()) {
            String adminName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
            welcomeText.setText("Welcome back, " + adminName);
            cursor.close();
        } else {
            welcomeText.setText("Welcome back, Administrator");
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