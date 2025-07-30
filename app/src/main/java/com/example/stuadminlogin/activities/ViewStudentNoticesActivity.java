// package com.example.stuadminlogin.activities;

// import android.os.Bundle;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.NoticeListAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.Notice;

// import java.util.List;

// public class ViewStudentNoticesActivity extends AppCompatActivity {

//     RecyclerView notice_list_view;
//     DatabaseHelper dbHelper;
//     int studentId;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_view_student_notices);

//         notice_list_view = findViewById(R.id.notice_list_view);
//         dbHelper = new DatabaseHelper(this);
//         studentId = getIntent().getIntExtra("student_id", -1);

//         List<Notice> notices = dbHelper.getNoticesForStudent(studentId);
//         NoticeListAdapter adapter = new NoticeListAdapter(this, notices);
//         notice_list_view.setLayoutManager(new LinearLayoutManager(this));
//         notice_list_view.setAdapter(adapter);
//     }
// }



package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.widget.Toast; // Import Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.NoticeListAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.Notice;

import java.util.ArrayList; // Import ArrayList
import java.util.List;

public class ViewStudentNoticesActivity extends AppCompatActivity {

    RecyclerView notice_list_view;
    DatabaseHelper dbHelper;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_notices);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Student Notices"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        notice_list_view = findViewById(R.id.notice_list_view);
        dbHelper = new DatabaseHelper(this);
        studentId = getIntent().getIntExtra("student_id", -1);

        List<Notice> notices;

        if (studentId == -1) {
            Toast.makeText(this, "Student ID not found. Cannot load notices.", Toast.LENGTH_SHORT).show();
            notices = new ArrayList<>(); // Initialize with empty list to prevent NullPointerException
        } else {
            notices = dbHelper.getNoticesForStudent(studentId);
            if (notices.isEmpty()) {
                Toast.makeText(this, "No notices found for this student.", Toast.LENGTH_SHORT).show();
            }
        }

        NoticeListAdapter adapter = new NoticeListAdapter(this, notices);
        notice_list_view.setLayoutManager(new LinearLayoutManager(this));
        notice_list_view.setAdapter(adapter);
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }
}