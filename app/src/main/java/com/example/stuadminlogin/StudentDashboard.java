package com.example.stuadminlogin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

public class StudentDashboard extends AppCompatActivity {

    Button viewProfileButton, ReadNoticeButton, applyForLeaveButton,  logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        viewProfileButton = findViewById(R.id.viewProfileButton);
        ReadNoticeButton = findViewById(R.id.ReadNoticeButton);
        applyForLeaveButton = findViewById(R.id.applyForLeaveButton);
        logoutButton = findViewById(R.id.logoutButton);

        viewProfileButton.setOnClickListener(view ->
                Toast.makeText(this, "View profile Clicked", Toast.LENGTH_SHORT).show());

        ReadNoticeButton.setOnClickListener(view -> {
            Intent intent = new Intent(StudentDashboard.this, ReadNotices.class);
            startActivity(intent);
        });


        applyForLeaveButton.setOnClickListener(view ->
                Toast.makeText(this, "Apply for Leave Request Clicked", Toast.LENGTH_SHORT).show());

        logoutButton.setOnClickListener(view -> {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StudentDashboard.this, MainActivity.class));
            finish();
        });

    }}
