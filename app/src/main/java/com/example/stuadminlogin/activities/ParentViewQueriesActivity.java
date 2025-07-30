package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.QueryListAdapter; // Assuming you have this adapter
import com.example.stuadminlogin.database.DatabaseHelper;

import java.util.ArrayList;

public class ParentViewQueriesActivity extends AppCompatActivity {

    ListView queryListView;
    TextView emptyListText; // To display when no queries
    ArrayList<String> queryTexts;
    ArrayList<String> querySenderInfo; // Will store "About: Student Name" or "General Query"
    ArrayList<Integer> queryIds;
    int parentId;
    String queryStatusFilter; // Will be "Pending" or "Responded"
    QueryListAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view_queries); // This layout will be created next

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // --- End Toolbar Setup ---

        queryListView = findViewById(R.id.query_list);
        emptyListText = findViewById(R.id.emptyListText); // Assuming this is in your layout
        queryTexts = new ArrayList<>();
        querySenderInfo = new ArrayList<>();
        queryIds = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        parentId = getIntent().getIntExtra("parent_id", -1);
        queryStatusFilter = getIntent().getStringExtra("query_status"); // Get the filter status

        if (parentId == -1 || queryStatusFilter == null || (!queryStatusFilter.equals("Pending") && !queryStatusFilter.equals("Responded"))) {
            Toast.makeText(this, "Error: Invalid user or query status.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Set toolbar title dynamically based on the filter
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My " + queryStatusFilter + " Queries");
        }

        loadParentQueries(); // Load queries based on the filter

        queryListView.setOnItemClickListener((adapterView, view, position, id) -> {
            // For parents, clicking on a query might show details (including response if available)
            // You'll need a new activity for this, e.g., ViewQueryDetailsActivity
            Intent intent = new Intent(ParentViewQueriesActivity.this, ViewQueryDetailsActivity.class); // Create this new activity
            intent.putExtra("query_id", queryIds.get(position));
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadParentQueries() {
        queryTexts.clear();
        querySenderInfo.clear();
        queryIds.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // SQL Query to fetch parent's queries based on the status filter
        String query =
                "SELECT q.query_id, q.query_text, " +
                "s.name AS linked_student_name, c.class_name AS linked_student_class, sec.section_name AS linked_student_section " +
                "FROM queries q " +
                "LEFT JOIN students s ON q.linked_student_id = s.student_id " + // LEFT JOIN to get student info if available
                "LEFT JOIN classes c ON s.class_id = c.class_id " +
                "LEFT JOIN sections sec ON s.section_id = sec.section_id " +
                "WHERE q.parent_id = ? AND q.response_status = ? " + // Filter by parent_id and status
                "ORDER BY q.generated_at DESC";

        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(parentId), queryStatusFilter})) {
            if (cursor.getCount() == 0) {
                emptyListText.setVisibility(View.VISIBLE);
                queryListView.setVisibility(View.GONE);
                emptyListText.setText("No " + queryStatusFilter.toLowerCase() + " queries found.");
            } else {
                emptyListText.setVisibility(View.GONE);
                queryListView.setVisibility(View.VISIBLE);
            }

            while (cursor.moveToNext()) {
                int qId = cursor.getInt(cursor.getColumnIndexOrThrow("query_id"));
                String qText = cursor.getString(cursor.getColumnIndexOrThrow("query_text"));

                String linkedStudentName = cursor.getString(cursor.getColumnIndexOrThrow("linked_student_name"));
                String linkedStudentClass = cursor.getString(cursor.getColumnIndexOrThrow("linked_student_class"));
                String linkedStudentSection = cursor.getString(cursor.getColumnIndexOrThrow("linked_student_section"));

                String senderInfo = "";
                if (linkedStudentName != null) {
                    senderInfo = "About: " + linkedStudentName;
                    if (linkedStudentClass != null && linkedStudentSection != null) {
                        senderInfo += " (Class: " + linkedStudentClass + "-" + linkedStudentSection + ")";
                    }
                } else {
                    senderInfo = "General Query";
                }

                queryIds.add(qId);
                queryTexts.add(qText);
                querySenderInfo.add(senderInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading queries: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }

        if (adapter == null) {
            // Assuming QueryListAdapter takes Context, List<String> for query_text, and List<String> for sender_info
            adapter = new QueryListAdapter(this, queryTexts, querySenderInfo);
            queryListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload queries when returning to this activity (e.g., after viewing details)
        loadParentQueries();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}