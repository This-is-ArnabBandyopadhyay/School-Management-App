package com.example.stuadminlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {

    Button viewStudentsButton, sendNoticeButton, viewLeaveRequestsButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        viewStudentsButton = findViewById(R.id.viewStudentsButton);
        sendNoticeButton = findViewById(R.id.sendNoticeButton);
        viewLeaveRequestsButton = findViewById(R.id.viewLeaveRequestsButton);
        logoutButton = findViewById(R.id.logoutButton);

        viewStudentsButton.setOnClickListener(view ->
                Toast.makeText(this, "View Students Clicked", Toast.LENGTH_SHORT).show());

        sendNoticeButton.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDashboard.this, SendNoticeActivity.class);
            startActivity(intent);
        });


        viewLeaveRequestsButton.setOnClickListener(view ->
                Toast.makeText(this, "View Leave Requests Clicked", Toast.LENGTH_SHORT).show());

        logoutButton.setOnClickListener(view -> {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminDashboard.this, MainActivity.class));
            finish(); // close dashboard
        });

}}
