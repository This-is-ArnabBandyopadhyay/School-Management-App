// package com.example.stuadminlogin.activities;

// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.graphics.Typeface;
// import android.os.Bundle;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.widget.LinearLayout;
// import android.widget.TextView;

// import androidx.appcompat.app.AppCompatActivity;
// import androidx.cardview.widget.CardView;
// import androidx.core.content.ContextCompat;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.Locale;

// public class ViewNoticesActivity extends AppCompatActivity {

//     private LinearLayout container;
//     private DatabaseHelper dbHelper;
//     private int studentId;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_view_notices);

//         container = findViewById(R.id.noticeList);
//         dbHelper = new DatabaseHelper(this);

//         // Get student ID from intent
//         studentId = getIntent().getIntExtra("student_id", -1);
//         if (studentId == -1) {
//             showErrorView("Student ID not found. Please login again.");
//             return;
//         }

//         SQLiteDatabase db = dbHelper.getReadableDatabase();

//         // Get class_id and section_id of the student
//         int classId = -1;
//         int sectionId = -1;
//         Cursor stuCursor = db.rawQuery(
//                 "SELECT class_id, section_id FROM students WHERE student_id = ?",
//                 new String[]{String.valueOf(studentId)}
//         );

//         if (stuCursor.moveToFirst()) {
//             classId = stuCursor.getInt(0);
//             sectionId = stuCursor.getInt(1);
//         }
//         stuCursor.close();

//         // Main notice query (same as original)
//         String query = "SELECT n.title, n.description, n.created_at FROM notices n " +
//                 "JOIN notice_to_individuals ni ON n.notice_id = ni.notice_id " +
//                 "WHERE ni.student_id = ? " +
//                 "UNION " +
//                 "SELECT n.title, n.description, n.created_at FROM notices n " +
//                 "JOIN notice_to_groups ng ON n.notice_id = ng.notice_id " +
//                 "JOIN group_members gm ON ng.group_id = gm.group_id " +
//                 "WHERE gm.student_id = ? " +
//                 "UNION " +
//                 "SELECT n.title, n.description, n.created_at FROM notices n " +
//                 "JOIN notice_to_classes nc ON n.notice_id = nc.notice_id " +
//                 "WHERE nc.class_id = ? " +
//                 "UNION " +
//                 "SELECT n.title, n.description, n.created_at FROM notices n " +
//                 "JOIN notice_to_sections ns ON n.notice_id = ns.notice_id " +
//                 "JOIN sections s ON ns.section_id = s.section_id " +
//                 "WHERE s.section_id = ? AND s.class_id = ? " +
//                 "UNION " +
//                 "SELECT n.title, n.description, n.created_at FROM notices n " +
//                 "JOIN notice_to_all na ON n.notice_id = na.notice_id " +
//                 "ORDER BY created_at DESC";

//         Cursor cursor = db.rawQuery(query, new String[]{
//                 String.valueOf(studentId),
//                 String.valueOf(studentId),
//                 String.valueOf(classId),
//                 String.valueOf(sectionId),
//                 String.valueOf(classId)
//         });

//         if (cursor.moveToFirst()) {
//             do {
//                 String title = cursor.getString(0);
//                 String content = cursor.getString(1);
//                 String date = formatDate(cursor.getString(2));

//                 addNoticeCard(title, content, date);
//             } while (cursor.moveToNext());
//         } else {
//             showEmptyState();
//         }

//         cursor.close();
//     }

//     private void addNoticeCard(String title, String content, String date) {
//         CardView cardView = (CardView) LayoutInflater.from(this)
//                 .inflate(R.layout.item_notice_card, container, false);

//         TextView titleView = cardView.findViewById(R.id.notice_title);
//         TextView contentView = cardView.findViewById(R.id.notice_content);
//         TextView dateView = cardView.findViewById(R.id.notice_date);

//         titleView.setText(title);
//         contentView.setText(content);
//         dateView.setText(date);

//         container.addView(cardView);
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

//     private void showEmptyState() {
//         View emptyView = LayoutInflater.from(this)
//                 .inflate(R.layout.empty_notices_state, container, false);
//         container.addView(emptyView);
//     }

//     private void showErrorView(String message) {
//         View errorView = LayoutInflater.from(this)
//                 .inflate(R.layout.error_state, container, false);
        
//         TextView errorText = errorView.findViewById(R.id.error_message);
//         errorText.setText(message);
        
//         container.addView(errorView);
//     }
// }



package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface; // This import might not be needed anymore if Typeface.BOLD is used directly
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast; // Added for error messages

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat; // Keep if still used, otherwise can be removed

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewNoticesActivity extends AppCompatActivity {

    private LinearLayout container;
    private DatabaseHelper dbHelper;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notices);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Your Notices"); // Set the title for the Toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        container = findViewById(R.id.noticeList);
        dbHelper = new DatabaseHelper(this);

        // Get student ID from intent
        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            showErrorView("Student ID not found. Please login again.");
            Toast.makeText(this, "Student ID missing!", Toast.LENGTH_LONG).show();
            return;
        }

        loadNotices();
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }

    private void loadNotices() {
        // Clear previous notices if any
        container.removeAllViews();

        int classId = -1;
        int sectionId = -1;

        // Use try-with-resources for SQLiteDatabase and Cursor for automatic closing
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            // Get class_id and section_id of the student
            try (Cursor stuCursor = db.rawQuery(
                    "SELECT class_id, section_id FROM students WHERE student_id = ?",
                    new String[]{String.valueOf(studentId)}
            )) {
                if (stuCursor.moveToFirst()) {
                    classId = stuCursor.getInt(0);
                    sectionId = stuCursor.getInt(1);
                } else {
                    showErrorView("Student details not found for ID: " + studentId);
                    return; // Exit if student details are not found
                }
            }

            // Main notice query
            // Use UNION ALL for performance if duplicates are acceptable, or just UNION for distinct
            // Ordering by created_at DESC to show newest notices first
            String query = "SELECT n.title, n.description, n.created_at FROM notices n " +
                    "JOIN notice_to_individuals ni ON n.notice_id = ni.notice_id " +
                    "WHERE ni.student_id = ? " +
                    "UNION " +
                    "SELECT n.title, n.description, n.created_at FROM notices n " +
                    "JOIN notice_to_groups ng ON n.notice_id = ng.notice_id " +
                    "JOIN group_members gm ON ng.group_id = gm.group_id " +
                    "WHERE gm.student_id = ? " +
                    "UNION " +
                    "SELECT n.title, n.description, n.created_at FROM notices n " +
                    "JOIN notice_to_classes nc ON n.notice_id = nc.notice_id " +
                    "WHERE nc.class_id = ? " +
                    "UNION " +
                    "SELECT n.title, n.description, n.created_at FROM notices n " +
                    "JOIN notice_to_sections ns ON n.notice_id = ns.notice_id " +
                    "WHERE ns.section_id = ? " + // Simplified, assuming section_id is unique enough
                    "UNION " +
                    "SELECT n.title, n.description, n.created_at FROM notices n " +
                    "JOIN notice_to_all na ON n.notice_id = na.notice_id " +
                    "ORDER BY created_at DESC"; // Order the final combined result

            try (Cursor cursor = db.rawQuery(query, new String[]{
                    String.valueOf(studentId),
                    String.valueOf(studentId),
                    String.valueOf(classId),
                    String.valueOf(sectionId) // Only section_id for ns table
            })) {

                if (cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(0);
                        String content = cursor.getString(1);
                        String date = formatDate(cursor.getString(2));

                        addNoticeCard(title, content, date);
                    } while (cursor.moveToNext());
                } else {
                    showEmptyState();
                }
            } // Cursor 'cursor' is automatically closed here
        } catch (Exception e) {
            e.printStackTrace();
            showErrorView("Error loading notices: " + e.getMessage());
            Toast.makeText(this, "Failed to load notices.", Toast.LENGTH_LONG).show();
        }
    }

    private void addNoticeCard(String title, String content, String date) {
        CardView cardView = (CardView) LayoutInflater.from(this)
                .inflate(R.layout.item_notice_card, container, false);

        TextView titleView = cardView.findViewById(R.id.notice_title);
        TextView contentView = cardView.findViewById(R.id.notice_content);
        TextView dateView = cardView.findViewById(R.id.notice_date);

        titleView.setText(title);
        contentView.setText(content);
        dateView.setText(date);

        container.addView(cardView);
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

    private void showEmptyState() {
        container.removeAllViews(); // Clear any existing views
        View emptyView = LayoutInflater.from(this)
                .inflate(R.layout.empty_notices_state, container, false);
        container.addView(emptyView);
    }

    private void showErrorView(String message) {
        container.removeAllViews(); // Clear any existing views
        View errorView = LayoutInflater.from(this)
                .inflate(R.layout.error_state, container, false);

        TextView errorText = errorView.findViewById(R.id.error_message);
        errorText.setText(message);

        container.addView(errorView);
    }
}