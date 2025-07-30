package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem; // Import for MenuItem
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity; // Change this
import androidx.appcompat.widget.Toolbar; // Import for Toolbar

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.StudentModel;
import com.example.stuadminlogin.adapters.StudentListAdapterForParent;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsForParentActivity extends AppCompatActivity { // Change this

    private ListView studentListView;
    private TextView noStudentsText;
    private DatabaseHelper dbHelper;
    private int loggedInParentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students_for_parent);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Children"); // Set the title for the toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show the back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Make the back button clickable
        }
        // --- End Toolbar Setup ---

        dbHelper = new DatabaseHelper(this);
        studentListView = findViewById(R.id.studentListView);
        noStudentsText = findViewById(R.id.noStudentsText);

        loggedInParentId = getIntent().getIntExtra("parent_id", -1);

        if (loggedInParentId == -1) {
            Toast.makeText(this, "Parent ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadStudentsForParent();

        studentListView.setOnItemClickListener((parent, view, position, id) -> {
            StudentModel selectedStudent = (StudentModel) parent.getItemAtPosition(position);
            if (selectedStudent != null) {
                Intent intent = new Intent(ViewStudentsForParentActivity.this, StudentDashboardActivity.class);
                intent.putExtra("student_id", selectedStudent.getStudentId());
                startActivity(intent);
            }
        });
    }

    private void loadStudentsForParent() {
        List<StudentModel> studentList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT s.student_id, s.name, s.roll_no, s.registration_no, c.class_name AS class_name, sec.section_name AS section_name " +
                       "FROM students s " +
                       "JOIN parent_student_link psl ON s.student_id = psl.student_id " +
                       "JOIN classes c ON s.class_id = c.class_id " +
                       "JOIN sections sec ON s.section_id = sec.section_id " +
                       "WHERE psl.parent_id = ?";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(loggedInParentId)});

            if (cursor != null && cursor.moveToFirst()) {
                noStudentsText.setVisibility(TextView.GONE);
                do {
                    int studentId = cursor.getInt(cursor.getColumnIndexOrThrow("student_id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String rollNo = cursor.getString(cursor.getColumnIndexOrThrow("roll_no"));
                    String registrationNo = cursor.getString(cursor.getColumnIndexOrThrow("registration_no"));
                    String className = cursor.getString(cursor.getColumnIndexOrThrow("class_name"));
                    String sectionName = cursor.getString(cursor.getColumnIndexOrThrow("section_name"));

                    StudentModel student = new StudentModel();
                    student.setStudentId(studentId);
                    student.setName(name);
                    student.setRollNo(rollNo);
                    student.setRegistrationNo(registrationNo);
                    student.setStudentClass(className);
                    student.setSection(sectionName);
                    studentList.add(student);

                } while (cursor.moveToNext());
            } else {
                noStudentsText.setVisibility(TextView.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("ViewStudents", "Error loading students for parent: " + e.getMessage());
            Toast.makeText(this, "Error loading students.", Toast.LENGTH_SHORT).show();
            noStudentsText.setVisibility(TextView.VISIBLE);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        StudentListAdapterForParent adapter = new StudentListAdapterForParent(this, studentList);
        studentListView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the back button click in the toolbar
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // This will simulate a back press
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudentsForParent();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}