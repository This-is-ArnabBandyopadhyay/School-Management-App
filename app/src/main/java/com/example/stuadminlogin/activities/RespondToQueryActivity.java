// // // File: RespondToQueryActivity.java
// // package com.example.stuadminlogin.activities;

// // import android.content.ContentValues;
// // import android.database.sqlite.SQLiteDatabase;
// // import android.os.Bundle;
// // import android.view.View;
// // import android.widget.Button;
// // import android.widget.EditText;
// // import android.widget.Toast;
// // import androidx.appcompat.app.AppCompatActivity;
// // import com.example.stuadminlogin.R;
// // import com.example.stuadminlogin.database.DatabaseHelper;
// // import java.text.SimpleDateFormat;
// // import java.util.*;

// // public class RespondToQueryActivity extends AppCompatActivity {

// //     EditText responseInput;
// //     Button submitResponseBtn;
// //     int queryId, adminId;
// //     String respondedAt;

// //     @Override
// //     protected void onCreate(Bundle savedInstanceState) {
// //         super.onCreate(savedInstanceState);
// //         setContentView(R.layout.activity_respond_to_query);

// //         responseInput = findViewById(R.id.response_input);
// //         submitResponseBtn = findViewById(R.id.submit_response_btn);

// //         queryId = getIntent().getIntExtra("query_id", -1);
// //         adminId = getIntent().getIntExtra("admin_id", -1);
// //         respondedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

// //         submitResponseBtn.setOnClickListener(new View.OnClickListener() {
// //             @Override
// //             public void onClick(View view) {
// //                 String responseText = responseInput.getText().toString().trim();
// //                 if (!responseText.isEmpty() && queryId != -1 && adminId != -1) {
// //                     DatabaseHelper dbHelper = new DatabaseHelper(RespondToQueryActivity.this);
// //                     SQLiteDatabase db = dbHelper.getWritableDatabase();

// //                     ContentValues values = new ContentValues();
// //                     values.put("query_id", queryId);
// //                     values.put("admin_id", adminId);
// //                     values.put("response_text", responseText);
// //                     values.put("responded_at", respondedAt);

// //                     long result = db.insert("query_responses", null, values);

// //                     if (result != -1) {
// //                         db.execSQL("UPDATE queries SET response_status = 'Responded' WHERE query_id = " + queryId);
// //                         Toast.makeText(RespondToQueryActivity.this, "Response submitted.", Toast.LENGTH_SHORT).show();
// //                         finish();
// //                     } else {
// //                         Toast.makeText(RespondToQueryActivity.this, "Failed to submit response.", Toast.LENGTH_SHORT).show();
// //                     }
// //                 }
// //             }
// //         });
// //     }
// // }



// package com.example.stuadminlogin.activities;

// import android.content.ContentValues;
// import android.content.Intent;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.view.View;
// import android.widget.Button;
// import android.widget.EditText;
// import android.widget.Toast;

// import androidx.appcompat.app.AppCompatActivity;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.Locale;

// public class RespondToQueryActivity extends AppCompatActivity {

//     EditText responseInput;
//     Button submitResponseBtn;
//     int queryId, adminId, itemPosition;
//     String respondedAt;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_respond_to_query);

//         responseInput = findViewById(R.id.response_input);
//         submitResponseBtn = findViewById(R.id.submit_response_btn);

//         queryId = getIntent().getIntExtra("query_id", -1);
//         adminId = getIntent().getIntExtra("admin_id", -1);
//         itemPosition = getIntent().getIntExtra("item_position", -1);
//         respondedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

//         submitResponseBtn.setOnClickListener(view -> {
//             String responseText = responseInput.getText().toString().trim();
//             if (!responseText.isEmpty() && queryId != -1 && adminId != -1) {
//                 DatabaseHelper dbHelper = new DatabaseHelper(this);
//                 try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
//                     ContentValues values = new ContentValues();
//                     values.put("query_id", queryId);
//                     values.put("admin_id", adminId);
//                     values.put("response_text", responseText);
//                     values.put("responded_at", respondedAt);

//                     long result = db.insert("query_responses", null, values);

//                     if (result != -1) {
//                         db.execSQL("UPDATE queries SET response_status = 'Responded' WHERE query_id = " + queryId);
//                         Toast.makeText(this, "Response submitted.", Toast.LENGTH_SHORT).show();
                        
//                         // Return with position to remove
//                         Intent resultIntent = new Intent();
//                         resultIntent.putExtra("item_position", itemPosition);
//                         setResult(RESULT_OK, resultIntent);
//                         finish();
//                     } else {
//                         Toast.makeText(this, "Failed to submit response.", Toast.LENGTH_SHORT).show();
//                     }
//                 }
//             }
//         });
//     }
// }




package com.example.stuadminlogin.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RespondToQueryActivity extends AppCompatActivity {

    EditText responseInput;
    Button submitResponseBtn;
    int queryId, adminId, itemPosition;
    String respondedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respond_to_query);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Respond to Query"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        responseInput = findViewById(R.id.response_input);
        submitResponseBtn = findViewById(R.id.submit_response_btn);

        queryId = getIntent().getIntExtra("query_id", -1);
        adminId = getIntent().getIntExtra("admin_id", -1);
        itemPosition = getIntent().getIntExtra("item_position", -1);
        respondedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        submitResponseBtn.setOnClickListener(view -> {
            String responseText = responseInput.getText().toString().trim();
            if (responseText.isEmpty()) {
                Toast.makeText(this, "Response cannot be empty.", Toast.LENGTH_SHORT).show();
                return; // Prevent submitting empty response
            }

            if (queryId != -1 && adminId != -1) {
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
                    ContentValues values = new ContentValues();
                    values.put("query_id", queryId);
                    values.put("admin_id", adminId);
                    values.put("response_text", responseText);
                    values.put("responded_at", respondedAt);

                    long result = db.insert("query_responses", null, values);

                    if (result != -1) {
                        // Update the query status
                        ContentValues queryUpdateValues = new ContentValues();
                        queryUpdateValues.put("response_status", "Responded");
                        int rowsAffected = db.update("queries", queryUpdateValues, "query_id = ?", new String[]{String.valueOf(queryId)});

                        if (rowsAffected > 0) {
                            Toast.makeText(this, "Response submitted and query status updated.", Toast.LENGTH_SHORT).show();

                            // Return with position to notify previous activity (e.g., to remove item)
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("item_position", itemPosition);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            Toast.makeText(this, "Response submitted, but failed to update query status.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed to submit response.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Error: Missing query or admin ID.", Toast.LENGTH_LONG).show();
                finish(); // Close if crucial data is missing
            }
        });
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity (e.g., ViewQueriesActivity)
        return true;
    }
}