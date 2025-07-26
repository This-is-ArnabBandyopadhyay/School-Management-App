// package com.example.stuadminlogin.activities;

// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.widget.TextView;
// import androidx.appcompat.app.AppCompatActivity;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// public class ViewMyQueriesActivity extends AppCompatActivity {

//     TextView queriesDisplay;
//     int studentId;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_view_my_queries);

//         queriesDisplay = findViewById(R.id.queries_display);
//         studentId = getIntent().getIntExtra("student_id", -1);

//         if (studentId != -1) {
//             DatabaseHelper dbHelper = new DatabaseHelper(this);
//             SQLiteDatabase db = dbHelper.getReadableDatabase();

//             String sql = "SELECT q.query_text, q.response_status, q.generated_at, " +
//                          "r.response_text, r.responded_at, a.full_name " +
//                          "FROM queries q " +
//                          "LEFT JOIN query_responses r ON q.query_id = r.query_id " +
//                          "LEFT JOIN admins a ON r.admin_id = a.admin_id " +
//                          "WHERE q.student_id = ? " +
//                          "ORDER BY q.generated_at DESC";

//             Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(studentId)});

//             StringBuilder builder = new StringBuilder();
//             while (cursor.moveToNext()) {
//                 String queryText = cursor.getString(0);
//                 String responseStatus = cursor.getString(1);
//                 String generatedAt = cursor.getString(2);
//                 String responseText = cursor.getString(3);
//                 String respondedAt = cursor.getString(4);
//                 String adminName = cursor.getString(5);

//                 builder.append("📌 Query: ").append(queryText).append("\n")
//                        .append("📅 Generated At: ").append(generatedAt).append("\n")
//                        .append("📌 Status: ").append(responseStatus).append("\n");

//                 if ("Responded".equalsIgnoreCase(responseStatus) && responseText != null) {
//                     builder.append("✅ Response: ").append(responseText).append("\n")
//                            .append("👨‍🏫 Responded By: ").append(adminName != null ? adminName : "Unknown").append("\n")
//                            .append("📅 Responded At: ").append(respondedAt != null ? respondedAt : "N/A").append("\n");
//                 }
//                 builder.append("---------------------------\n");
//             }

//             cursor.close();
//             queriesDisplay.setText(builder.toString());
//         } else {
//             queriesDisplay.setText("No student ID provided.");
//         }
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

import androidx.appcompat.app.AppCompatActivity;

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

        queriesContainer = findViewById(R.id.queries_container);
        noQueriesText = findViewById(R.id.no_queries_text);
        studentId = getIntent().getIntExtra("student_id", -1);

        if (studentId == -1) {
            showError("No student ID provided.");
            return;
        }

        loadQueries();
    }

    private void loadQueries() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT q.query_text, q.response_status, q.generated_at, " +
                     "r.response_text, r.responded_at, a.full_name " +
                     "FROM queries q " +
                     "LEFT JOIN query_responses r ON q.query_id = r.query_id " +
                     "LEFT JOIN admins a ON r.admin_id = a.admin_id " +
                     "WHERE q.student_id = ? " +
                     "ORDER BY q.generated_at DESC";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(studentId)});

        if (cursor.getCount() == 0) {
            noQueriesText.setVisibility(View.VISIBLE);
        } else {
            noQueriesText.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                addQueryCard(
                    cursor.getString(0), // query_text
                    cursor.getString(1), // response_status
                    cursor.getString(2), // generated_at
                    cursor.getString(3), // response_text
                    cursor.getString(4), // responded_at
                    cursor.getString(5)  // admin_name
                );
            }
        }
        cursor.close();
    }

    private void addQueryCard(String queryText, String responseStatus, 
                            String generatedAt, String responseText, 
                            String respondedAt, String adminName) {
        
        // Create card container
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.query_card_background);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 16);
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
        if ("Responded".equalsIgnoreCase(responseStatus) && responseText != null) {
            addStyledText(card, "Response:", responseText, "#212121", false);
            addStyledText(card, "Admin:", adminName != null ? adminName : "Unknown", "#757575", false);
            addStyledText(card, "Responded:", 
                respondedAt != null ? formatDate(respondedAt) : "N/A", 
                "#757575", false);
        }

        // Add divider
        View divider = new View(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            1
        ));
        divider.setBackgroundColor(Color.parseColor("#E0E0E0"));
        card.addView(divider);

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
        row.setPadding(0, 0, 0, 8);

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
            return rawDate;
        }
    }

    private void showError(String message) {
        TextView errorView = new TextView(this);
        errorView.setText(message);
        errorView.setTextColor(Color.parseColor("#B00020"));
        errorView.setTextSize(18);
        errorView.setGravity(Gravity.CENTER);
        errorView.setPadding(0, 32, 0, 0);
        
        queriesContainer.addView(errorView);
    }
}