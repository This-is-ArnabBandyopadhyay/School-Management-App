// // package com.example.stuadminlogin.activities;

// // import android.content.Intent;
// // import android.os.Bundle;
// // import android.widget.Button;
// // import android.widget.Toast;
// // import androidx.appcompat.app.AppCompatActivity;
// // import com.example.stuadminlogin.R;

// // public class ViewStudentDetailsActivity extends AppCompatActivity {

// //     int studentId; // Received from ManageStudentsActivity

// //     @Override
// //     protected void onCreate(Bundle savedInstanceState) {
// //         super.onCreate(savedInstanceState);
// //         setContentView(R.layout.activity_view_student_details);

// //         studentId = getIntent().getIntExtra("student_id", -1);
// //         if (studentId == -1) {
// //             Toast.makeText(this, "Invalid student ID", Toast.LENGTH_SHORT).show();
// //             finish();
// //             return;
// //         }

// //         Button viewNoticesButton = findViewById(R.id.btn_view_notices);
// //         Button btnViewRespondedQueries = findViewById(R.id.btnViewRespondedQueries);
// //         Button btnViewPendingQueries = findViewById(R.id.btnViewPendingQueries);

// //         // New separate attendance buttons
// //         Button btnUpdateAttendance = findViewById(R.id.btn_update_attendance);
// //         Button btnCheckAttendance = findViewById(R.id.btn_check_attendance);

// //         // Notices
// //         viewNoticesButton.setOnClickListener(v -> {
// //             Intent intent = new Intent(this, ViewStudentNoticesActivity.class);
// //             intent.putExtra("student_id", studentId);
// //             startActivity(intent);
// //         });

// //         // Responded Queries
// //         btnViewRespondedQueries.setOnClickListener(v -> {
// //             Intent intent = new Intent(this, ViewStudentRespondedQueriesActivity.class);
// //             intent.putExtra("student_id", studentId);
// //             startActivity(intent);
// //         });

// //         // Pending Queries
// //         btnViewPendingQueries.setOnClickListener(v -> {
// //             Intent intent = new Intent(this, ViewStudentPendingQueriesActivity.class);
// //             intent.putExtra("student_id", studentId);
// //             startActivity(intent);
// //         });

// //         // ✅ Update Attendance
// //         btnUpdateAttendance.setOnClickListener(v -> {
// //             Intent intent = new Intent(this, UpdateAttendanceActivity.class);
// //             intent.putExtra("student_id", studentId);
// //             startActivity(intent);
// //         });

// //         // ✅ Check Attendance
// //         btnCheckAttendance.setOnClickListener(v -> {
// //             Intent intent = new Intent(this, ViewAttendanceActivity.class);
// //             intent.putExtra("student_id", studentId);
// //             startActivity(intent);
// //         });
// //     }
// // }




// package com.example.stuadminlogin.activities;

// import android.content.Intent;
// import android.os.Bundle;
// import android.widget.Button;
// import android.widget.Toast;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.appcompat.widget.Toolbar; // Import Toolbar
// import com.example.stuadminlogin.R;

// public class ViewStudentDetailsActivity extends AppCompatActivity {

//     int studentId; // Received from ManageStudentsActivity

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_view_student_details);

//         // --- Toolbar Setup ---
//         Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
//         setSupportActionBar(toolbar); // Set it as the Activity's support action bar

//         // Customize Toolbar title and back button
//         if (getSupportActionBar() != null) {
//             getSupportActionBar().setTitle("Student Details"); // Set your desired title
//             getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
//             getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
//             // You could potentially fetch the student's name from the database here
//             // and set it as the toolbar title for a more personalized experience.
//             // e.g., getSupportActionBar().setTitle("Details for " + studentName);
//         }
//         // --- End Toolbar Setup ---

//         studentId = getIntent().getIntExtra("student_id", -1);
//         if (studentId == -1) {
//             Toast.makeText(this, "Invalid student ID. Please try again.", Toast.LENGTH_SHORT).show();
//             finish();
//             return;
//         }

//         Button viewNoticesButton = findViewById(R.id.btn_view_notices);
//         Button btnViewRespondedQueries = findViewById(R.id.btnViewRespondedQueries);
//         Button btnViewPendingQueries = findViewById(R.id.btnViewPendingQueries);

//         // New separate attendance buttons
//         Button btnUpdateAttendance = findViewById(R.id.btn_update_attendance);
//         Button btnCheckAttendance = findViewById(R.id.btn_check_attendance);

//         // Notices
//         viewNoticesButton.setOnClickListener(v -> {
//             Intent intent = new Intent(this, ViewStudentNoticesActivity.class);
//             intent.putExtra("student_id", studentId);
//             startActivity(intent);
//         });

//         // Responded Queries
//         btnViewRespondedQueries.setOnClickListener(v -> {
//             Intent intent = new Intent(this, ViewStudentRespondedQueriesActivity.class);
//             intent.putExtra("student_id", studentId);
//             startActivity(intent);
//         });

//         // Pending Queries
//         btnViewPendingQueries.setOnClickListener(v -> {
//             Intent intent = new Intent(this, ViewStudentPendingQueriesActivity.class);
//             intent.putExtra("student_id", studentId);
//             startActivity(intent);
//         });

//         // Update Attendance
//         btnUpdateAttendance.setOnClickListener(v -> {
//             Intent intent = new Intent(this, UpdateAttendanceActivity.class);
//             intent.putExtra("student_id", studentId);
//             startActivity(intent);
//         });

//         // Check Attendance
//         btnCheckAttendance.setOnClickListener(v -> {
//             Intent intent = new Intent(this, ViewAttendanceActivity.class);
//             intent.putExtra("student_id", studentId);
//             startActivity(intent);
//         });
//     }

//     // Handle back button press on the Toolbar
//     @Override
//     public boolean onSupportNavigateUp() {
//         onBackPressed(); // This will navigate back to the previous activity
//         return true;
//     }
// }


package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.stuadminlogin.R;

public class ViewStudentDetailsActivity extends AppCompatActivity {

    int studentId; // Received from ManageStudentsActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_details);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Student Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // --- End Toolbar Setup ---

        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Invalid student ID. Please try again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Button viewNoticesButton = findViewById(R.id.btn_view_notices);
        Button btnViewRespondedQueries = findViewById(R.id.btnViewRespondedQueries);
        Button btnViewPendingQueries = findViewById(R.id.btnViewPendingQueries);
        Button btnUpdateAttendance = findViewById(R.id.btn_update_attendance);
        Button btnCheckAttendance = findViewById(R.id.btn_check_attendance);
        // New: Manage Parents Button
        Button btnManageParents = findViewById(R.id.btn_manage_parents); // Assuming this ID in your XML

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

        // Update Attendance
        btnUpdateAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateAttendanceActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });

        // Check Attendance
        btnCheckAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewAttendanceActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });

        // New: Manage Parents
        btnManageParents.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageParentsActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}