// package com.example.stuadminlogin.activities;

// import android.os.Bundle;
// import android.widget.*;
// import androidx.appcompat.app.AppCompatActivity;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.R;

// public class LeaveApplicationFormActivity extends AppCompatActivity {
//     private EditText etFrom, etTo, etReason;
//     private Button btnSubmit;
//     private DatabaseHelper db;

//     // Assume studentId stored after login
//     private int currentStudentId; 

//     @Override
//     protected void onCreate(Bundle s) {
//         super.onCreate(s);
//         setContentView(R.layout.activity_leave_application_form);

//         currentStudentId = getIntent().getIntExtra("student_id", -1);
//     if (currentStudentId == -1) {
//         finish(); // ID missing, cannot proceed
//         return;
//     }
//         db = new DatabaseHelper(this);
//         etFrom = findViewById(R.id.etFrom);
//         etTo = findViewById(R.id.etTo);
//         etReason = findViewById(R.id.etReason);
//         btnSubmit = findViewById(R.id.btnSubmit);

//         btnSubmit.setOnClickListener(v -> {
//             boolean ok = db.submitLeave(currentStudentId,
//                 etFrom.getText().toString(),
//                 etTo.getText().toString(),
//                 etReason.getText().toString());
//             if (ok) {
//                 Toast.makeText(this, "Leave submitted!", Toast.LENGTH_SHORT).show();
//                 finish();
//             } else {
//                 Toast.makeText(this, "Submission failed.", Toast.LENGTH_SHORT).show();
//             }
//         });
//     }
// }



package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.R;

public class LeaveApplicationFormActivity extends AppCompatActivity {
    private EditText etFrom, etTo, etReason;
    private Button btnSubmit;
    private DatabaseHelper db;

    // Assume studentId stored after login
    private int currentStudentId;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_leave_application_form);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Optional: Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Leave Application"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        currentStudentId = getIntent().getIntExtra("student_id", -1);
        if (currentStudentId == -1) {
            Toast.makeText(this, "Student ID missing. Cannot submit leave.", Toast.LENGTH_LONG).show();
            finish(); // ID missing, cannot proceed
            return;
        }
        db = new DatabaseHelper(this);
        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        etReason = findViewById(R.id.etReason);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            boolean ok = db.submitLeave(currentStudentId,
                etFrom.getText().toString(),
                etTo.getText().toString(),
                etReason.getText().toString());
            if (ok) {
                Toast.makeText(this, "Leave submitted!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Submission failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}