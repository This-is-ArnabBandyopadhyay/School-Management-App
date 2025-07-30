// // package com.example.stuadminlogin.activities;

// // import android.app.Activity;
// // import android.app.DatePickerDialog;
// // import android.os.Bundle;
// // import android.widget.*;
// // import androidx.recyclerview.widget.LinearLayoutManager;
// // import androidx.recyclerview.widget.RecyclerView;

// // import com.example.stuadminlogin.R;
// // import com.example.stuadminlogin.adapters.BulkAttendanceAdapter;
// // import com.example.stuadminlogin.database.DatabaseHelper;
// // import com.example.stuadminlogin.models.StudentModel;

// // import java.util.*;

// // public class BulkUpdateAttendanceActivity extends Activity {

// //     private Button btnSelectDate, btnSubmitAttendance;
// //     private TextView tvSelectedDate;
// //     private RecyclerView recyclerView;
// //     private BulkAttendanceAdapter adapter;
// //     private String selectedDate;
// //     private int sectionId;
// //     private DatabaseHelper db;

// //     @Override
// //     protected void onCreate(Bundle savedInstanceState) {
// //         super.onCreate(savedInstanceState);
// //         setContentView(R.layout.activity_bulk_update_attendance);

// //         btnSelectDate = findViewById(R.id.btnSelectDate);
// //         btnSubmitAttendance = findViewById(R.id.btnSubmitAttendance);
// //         tvSelectedDate = findViewById(R.id.tvSelectedDate);
// //         recyclerView = findViewById(R.id.recyclerViewStudents);

// //         sectionId = getIntent().getIntExtra("section_id", -1);
// //         db = new DatabaseHelper(this);

// //         btnSelectDate.setOnClickListener(v -> {
// //             Calendar calendar = Calendar.getInstance();
// //             DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
// //                 selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
// //                 tvSelectedDate.setText("Selected: " + selectedDate);
// //             }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
// //             dialog.show();
// //         });

// //        List<StudentModel> students = db.getStudentsBySection(sectionId); // ✅ Correct

// //         adapter = new BulkAttendanceAdapter(students);
// //         recyclerView.setLayoutManager(new LinearLayoutManager(this));
// //         recyclerView.setAdapter(adapter);

// //         btnSubmitAttendance.setOnClickListener(v -> {
// //             if (selectedDate == null) {
// //                 Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
// //                 return;
// //             }

// //             Map<Integer, String> attendanceMap = adapter.getAttendanceMap();
// //             int successCount = 0;

// //             for (Map.Entry<Integer, String> entry : attendanceMap.entrySet()) {
// //                boolean inserted = db.insertOrUpdateAttendance(entry.getKey(), selectedDate, entry.getValue(), null);

// //                 if (inserted) successCount++;
// //             }

// //             Toast.makeText(this, "Attendance updated for " + successCount + " students.", Toast.LENGTH_LONG).show();
// //         });
// //     }
// // }


// package com.example.stuadminlogin.activities;

// import android.app.Activity;
// import android.app.DatePickerDialog;
// import android.content.Intent;
// import android.os.Bundle;
// import android.widget.*;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.BulkAttendanceAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.StudentModel;

// import java.util.*;

// public class BulkUpdateAttendanceActivity extends Activity {

//     private Button btnSelectDate, btnSubmitAttendance;
//     private TextView tvSelectedDate;
//     private RecyclerView recyclerView;
//     private BulkAttendanceAdapter adapter;
//     private String selectedDate;
//     private int sectionId;
//     private DatabaseHelper db;
//     private List<StudentModel> students;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_bulk_update_attendance);

//         btnSelectDate = findViewById(R.id.btnSelectDate);
//         btnSubmitAttendance = findViewById(R.id.btnSubmitAttendance);
//         tvSelectedDate = findViewById(R.id.tvSelectedDate);
//         recyclerView = findViewById(R.id.recyclerViewStudents);

//         sectionId = getIntent().getIntExtra("section_id", -1);
//         db = new DatabaseHelper(this);

//         students = db.getStudentsBySection(sectionId);
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));

//         btnSelectDate.setOnClickListener(v -> {
//             Calendar calendar = Calendar.getInstance();
//             DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
//                 selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
//                 tvSelectedDate.setText("Selected: " + selectedDate);
//                 loadAttendanceForSelectedDate();
//             }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//             dialog.show();
//         });

//         btnSubmitAttendance.setOnClickListener(v -> {
//             if (selectedDate == null) {
//                 Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
//                 return;
//             }

//             Map<Integer, String> attendanceMap = adapter.getAttendanceMap();
//             int successCount = 0;

//             for (Map.Entry<Integer, String> entry : attendanceMap.entrySet()) {
//                 boolean inserted = db.insertOrUpdateAttendance(entry.getKey(), selectedDate, entry.getValue(), null);
//                 if (inserted) successCount++;
//             }

//             Toast.makeText(this, "Attendance updated for " + successCount + " students.", Toast.LENGTH_LONG).show();

//             // Redirect back to previous screen
//             finish();
//         });
//     }

//     private void loadAttendanceForSelectedDate() {
//         Map<Integer, String> existingAttendance = db.getAttendanceForDate(selectedDate);
//         adapter = new BulkAttendanceAdapter(this, students, existingAttendance);
//         recyclerView.setAdapter(adapter);
//     }
// }


package com.example.stuadminlogin.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity; // CHANGED FROM android.app.Activity
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.BulkAttendanceAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.StudentModel;

import java.util.*;

public class BulkUpdateAttendanceActivity extends AppCompatActivity { // CHANGED THIS LINE

    private Button btnSelectDate, btnSubmitAttendance;
    private TextView tvSelectedDate;
    private RecyclerView recyclerView;
    private BulkAttendanceAdapter adapter;
    private String selectedDate;
    private int sectionId;
    private DatabaseHelper db;
    private List<StudentModel> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_update_attendance);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Optional: Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Bulk Attendance Update"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSubmitAttendance = findViewById(R.id.btnSubmitAttendance);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        recyclerView = findViewById(R.id.recyclerViewStudents);

        sectionId = getIntent().getIntExtra("section_id", -1);
        db = new DatabaseHelper(this);

        students = db.getStudentsBySection(sectionId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnSelectDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                tvSelectedDate.setText("Selected: " + selectedDate);
                loadAttendanceForSelectedDate();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        btnSubmitAttendance.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<Integer, String> attendanceMap = adapter.getAttendanceMap();
            int successCount = 0;

            for (Map.Entry<Integer, String> entry : attendanceMap.entrySet()) {
                boolean inserted = db.insertOrUpdateAttendance(entry.getKey(), selectedDate, entry.getValue(), null);
                if (inserted) successCount++;
            }

            Toast.makeText(this, "Attendance updated for " + successCount + " students.", Toast.LENGTH_LONG).show();

            // Redirect back to previous screen
            finish();
        });
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadAttendanceForSelectedDate() {
        Map<Integer, String> existingAttendance = db.getAttendanceForDate(selectedDate);
        adapter = new BulkAttendanceAdapter(this, students, existingAttendance);
        recyclerView.setAdapter(adapter);
    }
}