package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.QueryListAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;

import java.util.ArrayList;

public class AdminViewParentQueriesActivity extends AppCompatActivity {

    ListView queryListView;
    ArrayList<String> queryTexts;
    ArrayList<String> parentInfos; // This will now include linked student info
    ArrayList<Integer> queryIds;
    int adminId;
    QueryListAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_parent_queries);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Parent Queries");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // --- End Toolbar Setup ---

        queryListView = findViewById(R.id.query_list);
        queryTexts = new ArrayList<>();
        parentInfos = new ArrayList<>();
        queryIds = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        adminId = getIntent().getIntExtra("admin_id", -1);
        if (adminId == -1) {
            Toast.makeText(this, "Admin ID not found. Please log in again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        loadPendingParentQueries();

        queryListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(AdminViewParentQueriesActivity.this, RespondToQueryActivity.class);
            intent.putExtra("query_id", queryIds.get(position));
            intent.putExtra("admin_id", adminId);
            intent.putExtra("item_position", position);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadPendingParentQueries() {
        queryTexts.clear();
        parentInfos.clear();
        queryIds.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // SQL Query to fetch pending queries from parents, including linked student details
        String query =
                "SELECT q.query_id, q.query_text, p.name AS parent_name, " +
                "s.name AS linked_student_name, c.class_name AS linked_student_class, sec.section_name AS linked_student_section " +
                "FROM queries q " +
                "JOIN parents p ON q.parent_id = p.parent_id " +
                "LEFT JOIN students s ON q.linked_student_id = s.student_id " + // LEFT JOIN to get student info if available
                "LEFT JOIN classes c ON s.class_id = c.class_id " +
                "LEFT JOIN sections sec ON s.section_id = sec.section_id " +
                "WHERE q.response_status = 'Pending' AND q.parent_id IS NOT NULL " +
                "ORDER BY q.generated_at DESC";

        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No pending parent queries.", Toast.LENGTH_SHORT).show();
            }
            while (cursor.moveToNext()) {
                int queryId = cursor.getInt(cursor.getColumnIndexOrThrow("query_id"));
                String queryText = cursor.getString(cursor.getColumnIndexOrThrow("query_text"));
                String parentName = cursor.getString(cursor.getColumnIndexOrThrow("parent_name"));

                String linkedStudentName = cursor.getString(cursor.getColumnIndexOrThrow("linked_student_name"));
                String linkedStudentClass = cursor.getString(cursor.getColumnIndexOrThrow("linked_student_class"));
                String linkedStudentSection = cursor.getString(cursor.getColumnIndexOrThrow("linked_student_section"));

                String infoString = "Parent: " + parentName;
                if (linkedStudentName != null) {
                    infoString += " (About: " + linkedStudentName;
                    if (linkedStudentClass != null && linkedStudentSection != null) {
                        infoString += " | Class: " + linkedStudentClass + "-" + linkedStudentSection;
                    }
                    infoString += ")";
                } else {
                    infoString += " (General Query)";
                }

                queryIds.add(queryId);
                queryTexts.add(queryText);
                parentInfos.add(infoString); // Add the formatted info
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading parent queries: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (adapter == null) {
            adapter = new QueryListAdapter(this, queryTexts, parentInfos);
            queryListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadPendingParentQueries();
            Toast.makeText(this, "Query responded and list refreshed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}