// // File: SubmitQueryActivity.java
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

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// public class SubmitQueryActivity extends AppCompatActivity {

//     EditText queryInput;
//     Button submitQueryBtn;
//     int studentId;
//     String generatedAt;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_submit_query);

//         queryInput = findViewById(R.id.query_input);
//         submitQueryBtn = findViewById(R.id.submit_query_btn);
//         generatedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

//         // Get studentId from intent
//         studentId = getIntent().getIntExtra("student_id", -1);

//         submitQueryBtn.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 String queryText = queryInput.getText().toString().trim();
//                 if (!queryText.isEmpty() && studentId != -1) {
//                     DatabaseHelper dbHelper = new DatabaseHelper(SubmitQueryActivity.this);
//                     SQLiteDatabase db = dbHelper.getWritableDatabase();

//                     ContentValues values = new ContentValues();
//                     values.put("student_id", studentId);
//                     values.put("query_text", queryText);
//                     values.put("response_status", "Pending");
//                     values.put("generated_at", generatedAt);

//                     long result = db.insert("queries", null, values);
//                     if (result != -1) {
//                         Toast.makeText(SubmitQueryActivity.this, "Query submitted.", Toast.LENGTH_SHORT).show();
//                         finish();
//                     } else {
//                         Toast.makeText(SubmitQueryActivity.this, "Failed to submit query.", Toast.LENGTH_SHORT).show();
//                     }
//                 }
//             }
//         });
//     }
// }


// File: SubmitQueryActivity.java
package com.example.stuadminlogin.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

public class SubmitQueryActivity extends AppCompatActivity {

    EditText queryInput;
    Button submitQueryBtn;
    int studentId;
    String generatedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_query);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Submit Query"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        queryInput = findViewById(R.id.query_input);
        submitQueryBtn = findViewById(R.id.submit_query_btn);
        generatedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Get studentId from intent
        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Error: Student ID not found. Please log in again.", Toast.LENGTH_LONG).show();
            finish(); // Close activity if student ID is missing
            return;
        }

        submitQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryText = queryInput.getText().toString().trim();

                if (queryText.isEmpty()) {
                    Toast.makeText(SubmitQueryActivity.this, "Query cannot be empty.", Toast.LENGTH_SHORT).show();
                    return; // Stop if query text is empty
                }

                // Student ID check already done above, but can be re-checked for robustness
                if (studentId == -1) {
                     Toast.makeText(SubmitQueryActivity.this, "Error: Student ID is missing. Cannot submit query.", Toast.LENGTH_LONG).show();
                     return;
                }

                DatabaseHelper dbHelper = new DatabaseHelper(SubmitQueryActivity.this);
                try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
                    ContentValues values = new ContentValues();
                    values.put("student_id", studentId);
                    values.put("query_text", queryText);
                    values.put("response_status", "Pending");
                    values.put("generated_at", generatedAt);

                    long result = db.insert("queries", null, values);
                    if (result != -1) {
                        Toast.makeText(SubmitQueryActivity.this, "Query submitted successfully.", Toast.LENGTH_SHORT).show();
                        // Optionally clear the input field after successful submission
                        queryInput.setText("");
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

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }
}