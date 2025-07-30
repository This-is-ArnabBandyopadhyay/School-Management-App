// package com.example.stuadminlogin.activities;

// import android.content.ContentValues;
// import android.content.Intent;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.view.View;
// import android.widget.Button;
// import android.widget.EditText;
// import android.widget.Toast;
// import java.text.SimpleDateFormat;
// import java.util.*;

// import androidx.appcompat.app.AppCompatActivity;
// import androidx.appcompat.widget.Toolbar;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// public class SubmitQueryActivity extends AppCompatActivity {

//     EditText queryInput;
//     Button submitQueryBtn;
//     int studentId;
//     int parentId; // Added parentId
//     String generatedAt;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_submit_query);

//         // --- Toolbar Setup ---
//         Toolbar toolbar = findViewById(R.id.toolbar);
//         setSupportActionBar(toolbar);

//         if (getSupportActionBar() != null) {
//             getSupportActionBar().setTitle("Submit Query");
//             getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//             getSupportActionBar().setDisplayShowHomeEnabled(true);
//         }
//         // --- End Toolbar Setup ---

//         queryInput = findViewById(R.id.query_input);
//         submitQueryBtn = findViewById(R.id.submit_query_btn);
//         generatedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

//         // Get studentId or parentId from intent
//         // Prioritize studentId if present (meaning a student is submitting)
//         studentId = getIntent().getIntExtra("student_id", -1);
//         parentId = getIntent().getIntExtra("parent_id", -1); // Get parentId

//         // Check if at least one ID is present
//         if (studentId == -1 && parentId == -1) {
//             Toast.makeText(this, "Error: User ID not found. Please log in again.", Toast.LENGTH_LONG).show();
//             finish(); // Close activity if neither ID is missing
//             return;
//         }

//         submitQueryBtn.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 String queryText = queryInput.getText().toString().trim();

//                 if (queryText.isEmpty()) {
//                     Toast.makeText(SubmitQueryActivity.this, "Query cannot be empty.", Toast.LENGTH_SHORT).show();
//                     return;
//                 }

//                 DatabaseHelper dbHelper = new DatabaseHelper(SubmitQueryActivity.this);
//                 try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
//                     ContentValues values = new ContentValues();

//                     if (studentId != -1) {
//                         values.put("student_id", studentId);
//                         values.putNull("parent_id"); // Ensure parent_id is null if student_id is used
//                     } else if (parentId != -1) {
//                         values.put("parent_id", parentId);
//                         values.putNull("student_id"); // Ensure student_id is null if parent_id is used
//                     } else {
//                         // This case should ideally be caught by the initial check, but good for robustness
//                         Toast.makeText(SubmitQueryActivity.this, "Error: Cannot determine user type for query.", Toast.LENGTH_LONG).show();
//                         return;
//                     }

//                     values.put("query_text", queryText);
//                     values.put("response_status", "Pending");
//                     values.put("generated_at", generatedAt);

//                     long result = db.insert("queries", null, values);
//                     if (result != -1) {
//                         Toast.makeText(SubmitQueryActivity.this, "Query submitted successfully.", Toast.LENGTH_SHORT).show();
//                         queryInput.setText(""); // Clear the input field
//                         finish(); // Close the activity
//                     } else {
//                         Toast.makeText(SubmitQueryActivity.this, "Failed to submit query.", Toast.LENGTH_SHORT).show();
//                     }
//                 } catch (Exception e) {
//                     Toast.makeText(SubmitQueryActivity.this, "Database error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                     e.printStackTrace();
//                 }
//             }
//         });
//     }

//     // Handle back button press on the Toolbar
//     @Override
//     public boolean onSupportNavigateUp() {
//         onBackPressed();
//         return true;
//     }
// }





package com.example.stuadminlogin.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView; // Added for the label
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.StudentModel; // Import StudentModel

public class SubmitQueryActivity extends AppCompatActivity {

    EditText queryInput;
    Button submitQueryBtn;
    TextView selectChildLabel; // Label for the spinner
    Spinner childSpinner; // New Spinner for child selection
    
    int studentId; // If a student is logged in directly
    int parentId;  // If a parent is logged in
    
    // For parent's children list
    private List<StudentModel> parentChildren = new ArrayList<>();
    private ArrayAdapter<String> childSpinnerAdapter;

    String generatedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_query);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Submit Query");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // --- End Toolbar Setup ---

        queryInput = findViewById(R.id.query_input);
        submitQueryBtn = findViewById(R.id.submit_query_btn);
        selectChildLabel = findViewById(R.id.selectChildLabel); // Initialize label
        childSpinner = findViewById(R.id.childSpinner); // Initialize spinner
        
        generatedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Get studentId or parentId from intent
        studentId = getIntent().getIntExtra("student_id", -1);
        parentId = getIntent().getIntExtra("parent_id", -1);

        // Check if at least one ID is present
        if (studentId == -1 && parentId == -1) {
            Toast.makeText(this, "Error: User ID not found. Please log in again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Configure UI based on user type
        if (parentId != -1) { // If a parent is submitting
            selectChildLabel.setVisibility(View.VISIBLE);
            childSpinner.setVisibility(View.VISIBLE);
            loadParentChildren(); // Load children for the spinner
        } else { // If a student is submitting, hide spinner
            selectChildLabel.setVisibility(View.GONE);
            childSpinner.setVisibility(View.GONE);
        }

        submitQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryText = queryInput.getText().toString().trim();

                if (queryText.isEmpty()) {
                    Toast.makeText(SubmitQueryActivity.this, "Query cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseHelper dbHelper = new DatabaseHelper(SubmitQueryActivity.this);
                try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
                    ContentValues values = new ContentValues();
                    int linkedStudentId = -1; // Default to no linked student

                    if (studentId != -1) { // Student submitting query
                        values.put("student_id", studentId);
                        values.putNull("parent_id");
                        values.putNull("linked_student_id"); // No linked student for direct student queries
                    } else if (parentId != -1) { // Parent submitting query
                        values.put("parent_id", parentId);
                        values.putNull("student_id");

                        // Get selected linked student ID from spinner
                        if (childSpinner.getSelectedItemPosition() > 0) { // Check if a child is selected (index 0 is "General Query")
                            linkedStudentId = parentChildren.get(childSpinner.getSelectedItemPosition() - 1).getStudentId();
                            values.put("linked_student_id", linkedStudentId);
                        } else {
                            values.putNull("linked_student_id"); // General query by parent
                        }
                    } else {
                        Toast.makeText(SubmitQueryActivity.this, "Error: Cannot determine user type for query.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    values.put("query_text", queryText);
                    values.put("response_status", "Pending");
                    values.put("generated_at", generatedAt);

                    long result = db.insert("queries", null, values);
                    if (result != -1) {
                        Toast.makeText(SubmitQueryActivity.this, "Query submitted successfully.", Toast.LENGTH_SHORT).show();
                        queryInput.setText(""); // Clear the input field
                        finish(); // Close the activity
                    } else {
                        Toast.makeText(SubmitQueryActivity.this, "Failed to submit query.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SubmitQueryActivity.this, "Database error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadParentChildren() {
        parentChildren.clear();
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("General Query (Not about a specific child)"); // Option for general query

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT s.student_id, s.name, c.class_name, sec.section_name " +
                       "FROM students s " +
                       "JOIN parent_student_link psl ON s.student_id = psl.student_id " +
                       "JOIN classes c ON s.class_id = c.class_id " +
                       "JOIN sections sec ON s.section_id = sec.section_id " +
                       "WHERE psl.parent_id = ?";

        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(parentId)})) {
            while (cursor.moveToNext()) {
                int sId = cursor.getInt(cursor.getColumnIndexOrThrow("student_id"));
                String sName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String className = cursor.getString(cursor.getColumnIndexOrThrow("class_name"));
                String sectionName = cursor.getString(cursor.getColumnIndexOrThrow("section_name"));

                StudentModel student = new StudentModel();
                student.setStudentId(sId);
                student.setName(sName);
                student.setStudentClass(className);
                student.setSection(sectionName);
                parentChildren.add(student);

                spinnerItems.add(sName + " (Class: " + className + "-" + sectionName + ")");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading children for parent: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            db.close();
        }

        childSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        childSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        childSpinner.setAdapter(childSpinnerAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}