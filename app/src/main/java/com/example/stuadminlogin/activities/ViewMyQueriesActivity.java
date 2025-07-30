// // package com.example.stuadminlogin.activities;

// // import android.database.Cursor;
// // import android.database.sqlite.SQLiteDatabase;
// // import android.os.Bundle;
// // import android.widget.TextView;
// // import androidx.appcompat.app.AppCompatActivity;
// // import com.example.stuadminlogin.R;
// // import com.example.stuadminlogin.database.DatabaseHelper;

// // public class ViewMyQueriesActivity extends AppCompatActivity {

// //     TextView queriesDisplay;
// //     int studentId;

// //     @Override
// //     protected void onCreate(Bundle savedInstanceState) {
// //         super.onCreate(savedInstanceState);
// //         setContentView(R.layout.activity_view_my_queries);

// //         queriesDisplay = findViewById(R.id.queries_display);
// //         studentId = getIntent().getIntExtra("student_id", -1);

// //         if (studentId != -1) {
// //             DatabaseHelper dbHelper = new DatabaseHelper(this);
// //             SQLiteDatabase db = dbHelper.getReadableDatabase();

// //             String sql = "SELECT q.query_text, q.response_status, q.generated_at, " +
// //                          "r.response_text, r.responded_at, a.full_name " +
// //                          "FROM queries q " +
// //                          "LEFT JOIN query_responses r ON q.query_id = r.query_id " +
// //                          "LEFT JOIN admins a ON r.admin_id = a.admin_id " +
// //                          "WHERE q.student_id = ? " +
// //                          "ORDER BY q.generated_at DESC";

// //             Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(studentId)});

// //             StringBuilder builder = new StringBuilder();
// //             while (cursor.moveToNext()) {
// //                 String queryText = cursor.getString(0);
// //                 String responseStatus = cursor.getString(1);
// //                 String generatedAt = cursor.getString(2);
// //                 String responseText = cursor.getString(3);
// //                 String respondedAt = cursor.getString(4);
// //                 String adminName = cursor.getString(5);

// //                 builder.append("📌 Query: ").append(queryText).append("\n")
// //                        .append("📅 Generated At: ").append(generatedAt).append("\n")
// //                        .append("📌 Status: ").append(responseStatus).append("\n");

// //                 if ("Responded".equalsIgnoreCase(responseStatus) && responseText != null) {
// //                     builder.append("✅ Response: ").append(responseText).append("\n")
// //                            .append("👨‍🏫 Responded By: ").append(adminName != null ? adminName : "Unknown").append("\n")
// //                            .append("📅 Responded At: ").append(respondedAt != null ? respondedAt : "N/A").append("\n");
// //                 }
// //                 builder.append("---------------------------\n");
// //             }

// //             cursor.close();
// //             queriesDisplay.setText(builder.toString());
// //         } else {
// //             queriesDisplay.setText("No student ID provided.");
// //         }
// //     }
// // }






// package com.example.stuadminlogin.activities;

// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.graphics.Color;
// import android.graphics.Typeface;
// import android.os.Bundle;
// import android.view.Gravity;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.LinearLayout;
// import android.widget.TextView;

// import androidx.appcompat.app.AppCompatActivity;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.Locale;

// public class ViewMyQueriesActivity extends AppCompatActivity {

//     private LinearLayout queriesContainer;
//     private TextView noQueriesText;
//     private int studentId;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_view_my_queries);

//         queriesContainer = findViewById(R.id.queries_container);
//         noQueriesText = findViewById(R.id.no_queries_text);
//         studentId = getIntent().getIntExtra("student_id", -1);

//         if (studentId == -1) {
//             showError("No student ID provided.");
//             return;
//         }

//         loadQueries();
//     }

//     private void loadQueries() {
//         DatabaseHelper dbHelper = new DatabaseHelper(this);
//         SQLiteDatabase db = dbHelper.getReadableDatabase();

//         String sql = "SELECT q.query_text, q.response_status, q.generated_at, " +
//                      "r.response_text, r.responded_at, a.full_name " +
//                      "FROM queries q " +
//                      "LEFT JOIN query_responses r ON q.query_id = r.query_id " +
//                      "LEFT JOIN admins a ON r.admin_id = a.admin_id " +
//                      "WHERE q.student_id = ? " +
//                      "ORDER BY q.generated_at DESC";

//         Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(studentId)});

//         if (cursor.getCount() == 0) {
//             noQueriesText.setVisibility(View.VISIBLE);
//         } else {
//             noQueriesText.setVisibility(View.GONE);
//             while (cursor.moveToNext()) {
//                 addQueryCard(
//                     cursor.getString(0), // query_text
//                     cursor.getString(1), // response_status
//                     cursor.getString(2), // generated_at
//                     cursor.getString(3), // response_text
//                     cursor.getString(4), // responded_at
//                     cursor.getString(5)  // admin_name
//                 );
//             }
//         }
//         cursor.close();
//     }

//     private void addQueryCard(String queryText, String responseStatus, 
//                             String generatedAt, String responseText, 
//                             String respondedAt, String adminName) {
        
//         // Create card container
//         LinearLayout card = new LinearLayout(this);
//         card.setOrientation(LinearLayout.VERTICAL);
//         card.setBackgroundResource(R.drawable.query_card_background);
        
//         LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//             ViewGroup.LayoutParams.MATCH_PARENT,
//             ViewGroup.LayoutParams.WRAP_CONTENT
//         );
//         params.setMargins(0, 0, 0, 16);
//         card.setLayoutParams(params);
//         card.setPadding(16, 16, 16, 16);

//         // Add query text
//         addStyledText(card, "Query:", queryText, "#6200EE", true);
        
//         // Add date
//         addStyledText(card, "Submitted:", formatDate(generatedAt), "#757575", false);
        
//         // Add status - fixed color assignment
//         String statusColor = "Responded".equalsIgnoreCase(responseStatus) ? "#4CAF50" : "#FF9800";
//         addStyledText(card, "Status:", responseStatus, statusColor, true);

//         // Add response if available
//         if ("Responded".equalsIgnoreCase(responseStatus) && responseText != null) {
//             addStyledText(card, "Response:", responseText, "#212121", false);
//             addStyledText(card, "Admin:", adminName != null ? adminName : "Unknown", "#757575", false);
//             addStyledText(card, "Responded:", 
//                 respondedAt != null ? formatDate(respondedAt) : "N/A", 
//                 "#757575", false);
//         }

//         // Add divider
//         View divider = new View(this);
//         divider.setLayoutParams(new LinearLayout.LayoutParams(
//             ViewGroup.LayoutParams.MATCH_PARENT,
//             1
//         ));
//         divider.setBackgroundColor(Color.parseColor("#E0E0E0"));
//         card.addView(divider);

//         queriesContainer.addView(card);
//     }

//     private void addStyledText(LinearLayout parent, String label, String value, 
//                              String color, boolean bold) {
//         LinearLayout row = new LinearLayout(this);
//         row.setOrientation(LinearLayout.HORIZONTAL);
//         row.setLayoutParams(new LinearLayout.LayoutParams(
//             ViewGroup.LayoutParams.MATCH_PARENT,
//             ViewGroup.LayoutParams.WRAP_CONTENT
//         ));
//         row.setPadding(0, 0, 0, 8);

//         // Label
//         TextView labelView = new TextView(this);
//         labelView.setText(label + " ");
//         labelView.setTextColor(Color.parseColor("#212121"));
//         labelView.setTextSize(16);
//         if (bold) labelView.setTypeface(null, Typeface.BOLD);
//         row.addView(labelView);

//         // Value
//         TextView valueView = new TextView(this);
//         valueView.setText(value);
//         valueView.setTextColor(Color.parseColor(color));
//         valueView.setTextSize(16);
//         row.addView(valueView);

//         parent.addView(row);
//     }

//     private String formatDate(String rawDate) {
//         try {
//             SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//             SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault());
//             Date date = inputFormat.parse(rawDate);
//             return outputFormat.format(date);
//         } catch (ParseException e) {
//             return rawDate;
//         }
//     }

//     private void showError(String message) {
//         TextView errorView = new TextView(this);
//         errorView.setText(message);
//         errorView.setTextColor(Color.parseColor("#B00020"));
//         errorView.setTextSize(18);
//         errorView.setGravity(Gravity.CENTER);
//         errorView.setPadding(0, 32, 0, 0);
        
//         queriesContainer.addView(errorView);
//     }
// }



package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast; // Added import for Toast

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewMyQueriesActivity extends AppCompatActivity {

    private LinearLayout queriesContainer;
    private TextView noQueriesText;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_queries);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Queries"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        queriesContainer = findViewById(R.id.queries_container);
        noQueriesText = findViewById(R.id.no_queries_text);
        studentId = getIntent().getIntExtra("student_id", -1);

        if (studentId == -1) {
            showError("Student ID not provided. Cannot load queries."); // Use the existing showError method
            Toast.makeText(this, "Student ID missing!", Toast.LENGTH_SHORT).show(); // Also show a toast
            return;
        }

        loadQueries();
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }

    private void loadQueries() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        // Use try-with-resources for SQLiteDatabase and Cursor for automatic closing
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery(
                     "SELECT q.query_text, q.response_status, q.generated_at, " +
                             "r.response_text, r.responded_at, a.full_name " +
                             "FROM queries q " +
                             "LEFT JOIN query_responses r ON q.query_id = r.query_id " +
                             "LEFT JOIN admins a ON r.admin_id = a.admin_id " +
                             "WHERE q.student_id = ? " +
                             "ORDER BY q.generated_at DESC",
                     new String[]{String.valueOf(studentId)})) {

            if (cursor.getCount() == 0) {
                noQueriesText.setVisibility(View.VISIBLE);
                queriesContainer.setVisibility(View.GONE); // Hide container if no queries
            } else {
                noQueriesText.setVisibility(View.GONE);
                queriesContainer.setVisibility(View.VISIBLE); // Show container if queries exist
                queriesContainer.removeAllViews(); // Clear existing views before adding new ones
                while (cursor.moveToNext()) {
                    addQueryCard(
                            cursor.getString(cursor.getColumnIndexOrThrow("query_text")),
                            cursor.getString(cursor.getColumnIndexOrThrow("response_status")),
                            cursor.getString(cursor.getColumnIndexOrThrow("generated_at")),
                            cursor.getString(cursor.getColumnIndexOrThrow("response_text")),
                            cursor.getString(cursor.getColumnIndexOrThrow("responded_at")),
                            cursor.getString(cursor.getColumnIndexOrThrow("full_name"))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading queries: " + e.getMessage());
            Toast.makeText(this, "Error loading queries.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addQueryCard(String queryText, String responseStatus,
                              String generatedAt, String responseText,
                              String respondedAt, String adminName) {

        // Create card container
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.query_card_background); // Ensure this drawable exists and provides rounded corners/shadow
        card.setElevation(4f); // Add elevation for a card-like effect

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 16); // Margin between cards
        card.setLayoutParams(params);
        card.setPadding(16, 16, 16, 16);

        // Add query text
        addStyledText(card, "Query:", queryText, "#6200EE", true);

        // Add date
        addStyledText(card, "Submitted:", formatDate(generatedAt), "#757575", false);

        // Add status - fixed color assignment
        String statusColor = "Responded".equalsIgnoreCase(responseStatus) ? "#4CAF50" : "#FF9800";
        addStyledText(card, "Status:", responseStatus, statusColor, true);

        // Add response if available
        if ("Responded".equalsIgnoreCase(responseStatus) && responseText != null && !responseText.isEmpty()) {
            addStyledText(card, "Response:", responseText, "#212121", false);
            addStyledText(card, "Admin:", adminName != null ? adminName : "Unknown", "#757575", false);
            addStyledText(card, "Responded:",
                    respondedAt != null ? formatDate(respondedAt) : "N/A",
                    "#757575", false);
        }

        // Add divider (optional, but good for separation)
        View divider = new View(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                1
        ));
        divider.setBackgroundColor(Color.parseColor("#E0E0E0"));
        // Only add divider if it's not the last element, or if you want a divider after every card
        // For simplicity, adding after every card for now, but you might want to adjust this.
        queriesContainer.addView(card);
    }

    private void addStyledText(LinearLayout parent, String label, String value,
                               String color, boolean bold) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        row.setPadding(0, 0, 0, 8); // Padding between rows in a card

        // Label
        TextView labelView = new TextView(this);
        labelView.setText(label + " ");
        labelView.setTextColor(Color.parseColor("#212121"));
        labelView.setTextSize(16);
        if (bold) labelView.setTypeface(null, Typeface.BOLD);
        row.addView(labelView);

        // Value
        TextView valueView = new TextView(this);
        valueView.setText(value);
        valueView.setTextColor(Color.parseColor(color));
        valueView.setTextSize(16);
        row.addView(valueView);

        parent.addView(row);
    }

    private String formatDate(String rawDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault());
            Date date = inputFormat.parse(rawDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return rawDate; // Return original if parsing fails
        }
    }

    private void showError(String message) {
        // Clear existing content to show error prominently
        queriesContainer.removeAllViews();
        noQueriesText.setVisibility(View.GONE); // Hide "No queries found" text

        TextView errorView = new TextView(this);
        errorView.setText(message);
        errorView.setTextColor(Color.parseColor("#B00020")); // Material Design Error Red
        errorView.setTextSize(18);
        errorView.setGravity(Gravity.CENTER);
        errorView.setPadding(0, 32, 0, 0);

        queriesContainer.addView(errorView);
    }
}