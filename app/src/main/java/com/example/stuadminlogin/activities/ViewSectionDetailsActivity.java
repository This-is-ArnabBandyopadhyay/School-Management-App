// package com.example.stuadminlogin.activities;

// import android.app.Activity;
// import android.content.Intent;
// import android.os.Bundle;
// import android.view.View;
// import android.widget.Button;

// import com.example.stuadminlogin.R;

// public class ViewSectionDetailsActivity extends Activity {

//     Button btnViewStudents, btnUpdateAttendance;
//     int sectionId, classId;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_view_section_details);

//         btnViewStudents = findViewById(R.id.btnViewStudents);
//         btnUpdateAttendance = findViewById(R.id.btnUpdateAttendance);

//         sectionId = getIntent().getIntExtra("section_id", -1);
//         classId = getIntent().getIntExtra("class_id", -1);

//         btnViewStudents.setOnClickListener(v -> {
//             Intent intent = new Intent(this, ManageStudentsActivity.class);
//             intent.putExtra("section_id", sectionId);
//             intent.putExtra("class_id", classId);
//             startActivity(intent);
//         });

//         btnUpdateAttendance.setOnClickListener(v -> {
//             Intent intent = new Intent(this, BulkUpdateAttendanceActivity.class);
//             intent.putExtra("section_id", sectionId);
//             intent.putExtra("class_id", classId);
//             startActivity(intent);
//         });
//     }
// }



package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast; // Added for error messages

import androidx.appcompat.app.AppCompatActivity; // Change base class
import androidx.appcompat.widget.Toolbar; // Import Toolbar

import com.example.stuadminlogin.R;

public class ViewSectionDetailsActivity extends AppCompatActivity { // Changed to AppCompatActivity

    Button btnViewStudents, btnUpdateAttendance;
    int sectionId, classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_section_details);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            // You might want to set a dynamic title based on section/class name if available
            // For now, using a static title.
            getSupportActionBar().setTitle("Section Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        btnViewStudents = findViewById(R.id.btnViewStudents);
        btnUpdateAttendance = findViewById(R.id.btnUpdateAttendance);

        sectionId = getIntent().getIntExtra("section_id", -1);
        classId = getIntent().getIntExtra("class_id", -1);

        // Basic validation for received IDs
        if (sectionId == -1 || classId == -1) {
            Toast.makeText(this, "Invalid Section or Class ID.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if essential data is missing
            return;
        }

        btnViewStudents.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageStudentsActivity.class);
            intent.putExtra("section_id", sectionId);
            intent.putExtra("class_id", classId);
            startActivity(intent);
        });

        btnUpdateAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(this, BulkUpdateAttendanceActivity.class);
            intent.putExtra("section_id", sectionId);
            intent.putExtra("class_id", classId);
            startActivity(intent);
        });
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }
}
