// package com.example.stuadminlogin.activities;

// import android.app.DatePickerDialog;
// import android.content.SharedPreferences;
// import android.os.Bundle;
// import android.widget.*;
// import androidx.appcompat.app.AppCompatActivity;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import java.text.SimpleDateFormat;
// import java.util.*;
// import android.view.View;


// public class UpdateAttendanceActivity extends AppCompatActivity {
//     TextView studentName, dateDisplay;
//     Button btnPresent, btnAbsent, btnLeave, btnPickDate;
//     int studentId;
//     int adminId; // 🔶 Add adminId variable
//     String selectedDate;
//     DatabaseHelper db;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_update_attendance);

//         studentName = findViewById(R.id.tvStudentName);
//         dateDisplay = findViewById(R.id.tvSelectedDate); // ✅ match XML ID

//         btnPresent = findViewById(R.id.btnPresent);
//         btnAbsent = findViewById(R.id.btnAbsent);
//         btnLeave = findViewById(R.id.btnLeave);
//         btnPickDate = findViewById(R.id.btnPickDate);

//         db = new DatabaseHelper(this);

//         // ✅ Get student data from intent
//         studentId = getIntent().getIntExtra("student_id", -1);
//         String name = getIntent().getStringExtra("student_name");
//         studentName.setText(name);

//         // ✅ Get adminId from SharedPreferences
//         SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
//         adminId = prefs.getInt("admin_id", -1);

//         if (adminId == -1) {
//             Toast.makeText(this, "Admin ID not found! Please log in again.", Toast.LENGTH_SHORT).show();
//             finish(); // Exit activity as admin is unknown
//             return;
//         }

//         // ✅ Set default date to today
//         selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//         dateDisplay.setText(selectedDate);

//         // ✅ Date Picker
//         btnPickDate.setOnClickListener(v -> {
//             Calendar c = Calendar.getInstance();
//             new DatePickerDialog(UpdateAttendanceActivity.this, (view, year, month, dayOfMonth) -> {
//                 Calendar chosen = Calendar.getInstance();
//                 chosen.set(year, month, dayOfMonth);
//                 selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(chosen.getTime());
//                 dateDisplay.setText(selectedDate);
//             }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
//         });

//         // ✅ Button click listeners for Present/Absent/Leave
//         View.OnClickListener setAttendance = v -> {
//             String status = "";
//             if (v.getId() == R.id.btnPresent) status = "Present";
//             else if (v.getId() == R.id.btnAbsent) status = "Absent";
//             else if (v.getId() == R.id.btnLeave) status = "Leave";

//             boolean success = db.markOrUpdateAttendance(studentId, selectedDate, status, adminId);

//             Toast.makeText(this, success ? "Updated Successfully" : "Failed to Update", Toast.LENGTH_SHORT).show();
//         };

//         btnPresent.setOnClickListener(setAttendance);
//         btnAbsent.setOnClickListener(setAttendance);
//         btnLeave.setOnClickListener(setAttendance);
//     }
// }



package com.example.stuadminlogin.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import java.text.SimpleDateFormat;
import java.util.*;
import android.view.View;


public class UpdateAttendanceActivity extends AppCompatActivity {
    TextView studentName, dateDisplay;
    Button btnPresent, btnAbsent, btnLeave, btnPickDate;
    int studentId;
    int adminId;
    String selectedDate;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_attendance);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Update Attendance"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        studentName = findViewById(R.id.tvStudentName);
        dateDisplay = findViewById(R.id.tvSelectedDate);

        btnPresent = findViewById(R.id.btnPresent);
        btnAbsent = findViewById(R.id.btnAbsent);
        btnLeave = findViewById(R.id.btnLeave);
        btnPickDate = findViewById(R.id.btnPickDate);

        db = new DatabaseHelper(this);

        // Get student data from intent
        studentId = getIntent().getIntExtra("student_id", -1);
        String name = getIntent().getStringExtra("student_name");
        studentName.setText(name);

        // Get adminId from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        adminId = prefs.getInt("admin_id", -1);

        if (adminId == -1) {
            Toast.makeText(this, "Admin ID not found! Please log in again.", Toast.LENGTH_SHORT).show();
            finish(); // Exit activity as admin is unknown
            return;
        }

        // Set default date to today
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dateDisplay.setText(selectedDate);

        // Date Picker
        btnPickDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(UpdateAttendanceActivity.this, (view, year, month, dayOfMonth) -> {
                Calendar chosen = Calendar.getInstance();
                chosen.set(year, month, dayOfMonth);
                selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(chosen.getTime());
                dateDisplay.setText(selectedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Button click listeners for Present/Absent/Leave
        View.OnClickListener setAttendance = v -> {
            String status = "";
            int id = v.getId();
            if (id == R.id.btnPresent) status = "Present";
            else if (id == R.id.btnAbsent) status = "Absent";
            else if (id == R.id.btnLeave) status = "Leave";

            // Basic validation for studentId
            if (studentId == -1) {
                Toast.makeText(this, "Error: Student ID is missing.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Basic validation for selectedDate
            if (selectedDate == null || selectedDate.isEmpty()) {
                Toast.makeText(this, "Error: Please select a date.", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = db.markOrUpdateAttendance(studentId, selectedDate, status, adminId);

            Toast.makeText(this, success ? "Attendance Updated Successfully" : "Failed to Update Attendance", Toast.LENGTH_SHORT).show();
        };

        btnPresent.setOnClickListener(setAttendance);
        btnAbsent.setOnClickListener(setAttendance);
        btnLeave.setOnClickListener(setAttendance);
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }
}