package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ParentViewNoticesActivity extends AppCompatActivity {

    private LinearLayout container;
    private DatabaseHelper dbHelper;
    private int parentId; // Changed from studentId to parentId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view_notices); // Use a new layout file

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Notices for Parents"); // Specific title for parents
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // --- End Toolbar Setup ---

        container = findViewById(R.id.noticeListForParents); // Use a new ID for the container
        dbHelper = new DatabaseHelper(this);

        // Get parent ID from intent
        parentId = getIntent().getIntExtra("parent_id", -1); // Get parent_id
        if (parentId == -1) {
            showErrorView("Parent ID not found. Please login again.");
            Toast.makeText(this, "Parent ID missing!", Toast.LENGTH_LONG).show();
            return;
        }

        loadNoticesForParent();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadNoticesForParent() {
        container.removeAllViews(); // Clear previous notices

        // Use try-with-resources for SQLiteDatabase and Cursor for automatic closing
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            // Query for notices specific to this parent AND notices sent to all
            String query = "SELECT n.title, n.description, n.created_at FROM notices n " +
                    "JOIN notice_to_parents np ON n.notice_id = np.notice_id " +
                    "WHERE np.parent_id = ? " + // Notices specifically for this parent
                    "UNION " +
                    "SELECT n.title, n.description, n.created_at FROM notices n " +
                    "JOIN notice_to_all na ON n.notice_id = na.notice_id " + // Notices sent to all
                    "ORDER BY created_at DESC";

            try (Cursor cursor = db.rawQuery(query, new String[]{
                    String.valueOf(parentId)
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorView("Error loading notices: " + e.getMessage());
            Toast.makeText(this, "Failed to load notices.", Toast.LENGTH_LONG).show();
        }
    }

    private void addNoticeCard(String title, String content, String date) {
        CardView cardView = (CardView) LayoutInflater.from(this)
                .inflate(R.layout.item_notice_card, container, false); // Reuse existing card layout

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
            return rawDate;
        }
    }

    private void showEmptyState() {
        container.removeAllViews();
        View emptyView = LayoutInflater.from(this)
                .inflate(R.layout.empty_notices_state, container, false);
        container.addView(emptyView);
    }

    private void showErrorView(String message) {
        container.removeAllViews();
        View errorView = LayoutInflater.from(this)
                .inflate(R.layout.error_state, container, false);

        TextView errorText = errorView.findViewById(R.id.error_message);
        errorText.setText(message);

        container.addView(errorView);
    }
}