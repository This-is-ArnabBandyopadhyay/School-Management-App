package com.example.stuadminlogin.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import java.text.SimpleDateFormat;
import java.util.*;
import android.view.View;


public class UpdateAttendanceActivity extends AppCompatActivity {
    TextView studentName, dateDisplay;
    Button btnPresent, btnAbsent, btnLeave, btnPickDate;
    int studentId;
    int adminId; // 🔶 Add adminId variable
    String selectedDate;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_attendance);

        studentName = findViewById(R.id.tvStudentName);
        dateDisplay = findViewById(R.id.tvSelectedDate); // ✅ match XML ID

        btnPresent = findViewById(R.id.btnPresent);
        btnAbsent = findViewById(R.id.btnAbsent);
        btnLeave = findViewById(R.id.btnLeave);
        btnPickDate = findViewById(R.id.btnPickDate);

        db = new DatabaseHelper(this);

        // ✅ Get student data from intent
        studentId = getIntent().getIntExtra("student_id", -1);
        String name = getIntent().getStringExtra("student_name");
        studentName.setText(name);

        // ✅ Get adminId from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        adminId = prefs.getInt("admin_id", -1);

        if (adminId == -1) {
            Toast.makeText(this, "Admin ID not found! Please log in again.", Toast.LENGTH_SHORT).show();
            finish(); // Exit activity as admin is unknown
            return;
        }

        // ✅ Set default date to today
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dateDisplay.setText(selectedDate);

        // ✅ Date Picker
        btnPickDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(UpdateAttendanceActivity.this, (view, year, month, dayOfMonth) -> {
                Calendar chosen = Calendar.getInstance();
                chosen.set(year, month, dayOfMonth);
                selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(chosen.getTime());
                dateDisplay.setText(selectedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // ✅ Button click listeners for Present/Absent/Leave
        View.OnClickListener setAttendance = v -> {
            String status = "";
            if (v.getId() == R.id.btnPresent) status = "Present";
            else if (v.getId() == R.id.btnAbsent) status = "Absent";
            else if (v.getId() == R.id.btnLeave) status = "Leave";

            boolean success = db.markOrUpdateAttendance(studentId, selectedDate, status, adminId);

            Toast.makeText(this, success ? "Updated Successfully" : "Failed to Update", Toast.LENGTH_SHORT).show();
        };

        btnPresent.setOnClickListener(setAttendance);
        btnAbsent.setOnClickListener(setAttendance);
        btnLeave.setOnClickListener(setAttendance);
    }
}
