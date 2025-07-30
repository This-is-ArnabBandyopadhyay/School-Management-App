package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

public class ViewQueryDetailsActivity extends AppCompatActivity {

    private TextView queryTextTv, senderInfoTv, statusTv, responseTextTv, responseLabelTv;
    private TextView respondedByLabelTv, respondedByTv, responseTimeLabelTv, responseTimeTv;
    private DatabaseHelper dbHelper;
    private int queryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_query_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Query Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        queryTextTv = findViewById(R.id.queryTextDetails);
        senderInfoTv = findViewById(R.id.senderInfoDetails);
        statusTv = findViewById(R.id.queryStatusDetails);
        responseTextTv = findViewById(R.id.responseTextDetails);
        responseLabelTv = findViewById(R.id.responseLabel);

        respondedByLabelTv = findViewById(R.id.respondedByLabel);
        respondedByTv = findViewById(R.id.respondedByDetails);
        responseTimeLabelTv = findViewById(R.id.responseTimeLabel);
        responseTimeTv = findViewById(R.id.responseTimeDetails);

        dbHelper = new DatabaseHelper(this);

        queryId = getIntent().getIntExtra("query_id", -1);
        if (queryId == -1) {
            Toast.makeText(this, "Query not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadQueryDetails();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadQueryDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query =
                "SELECT q.query_text, q.response_status, " +
                "s.name AS linked_student_name, c.class_name AS linked_student_class, sec.section_name AS linked_student_section, " +
                "p.name AS parent_name, " +
                "qr.response_text, qr.responded_at, " + // Get response text and time from query_responses
                "a.full_name AS admin_name " + // Get admin name from admins
                "FROM queries q " +
                "LEFT JOIN students s ON q.linked_student_id = s.student_id " +
                "LEFT JOIN classes c ON s.class_id = c.class_id " +
                "LEFT JOIN sections sec ON s.section_id = sec.section_id " +
                "LEFT JOIN parents p ON q.parent_id = p.parent_id " +
                "LEFT JOIN query_responses qr ON q.query_id = qr.query_id " + // LEFT JOIN with query_responses
                "LEFT JOIN admins a ON qr.admin_id = a.admin_id " + // LEFT JOIN with admins
                "WHERE q.query_id = ?";

        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(queryId)})) {
            if (cursor.moveToFirst()) {
                String qText = cursor.getString(cursor.getColumnIndexOrThrow("query_text"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("response_status"));

                // Get response details from query_responses table
                String response = cursor.getString(cursor.getColumnIndexOrThrow("response_text"));
                String respondedAt = cursor.getString(cursor.getColumnIndexOrThrow("responded_at"));
                String adminName = cursor.getString(cursor.getColumnIndexOrThrow("admin_name"));

                String linkedStudentName = cursor.getString(cursor.getColumnIndexOrThrow("linked_student_name"));
                String linkedStudentClass = cursor.getString(cursor.getColumnIndexOrThrow("linked_student_class"));
                String linkedStudentSection = cursor.getString(cursor.getColumnIndexOrThrow("linked_student_section"));
                String parentName = cursor.getString(cursor.getColumnIndexOrThrow("parent_name"));

                queryTextTv.setText(qText);
                statusTv.setText("Status: " + status);

                String senderInfo = "";
                // Prioritize parent name for parent queries, then link to student if available
                if (parentName != null) {
                    senderInfo = "From: " + parentName;
                    if (linkedStudentName != null) {
                        senderInfo += " (About: " + linkedStudentName;
                        if (linkedStudentClass != null && linkedStudentSection != null) {
                            senderInfo += " | Class: " + linkedStudentClass + "-" + linkedStudentSection;
                        }
                        senderInfo += ")";
                    } else {
                        senderInfo += " (General Query)";
                    }
                } else if (linkedStudentName != null) { // Fallback for student-only queries
                    senderInfo = "From: " + linkedStudentName;
                    if (linkedStudentClass != null && linkedStudentSection != null) {
                        senderInfo += " (Class: " + linkedStudentClass + "-" + linkedStudentSection + ")";
                    }
                } else {
                    senderInfo = "Unknown Sender"; // Should not happen if data is consistent
                }
                senderInfoTv.setText(senderInfo);

                // Display response details only if the query has been responded to
                if ("Responded".equalsIgnoreCase(status)) { // Use equalsIgnoreCase for robustness
                    responseTextTv.setText(response != null && !response.isEmpty() ? response : "No response text provided.");
                    responseLabelTv.setVisibility(View.VISIBLE);
                    responseTextTv.setVisibility(View.VISIBLE);

                    respondedByTv.setText(adminName != null ? adminName : "N/A");
                    respondedByLabelTv.setVisibility(View.VISIBLE);
                    respondedByTv.setVisibility(View.VISIBLE);

                    // Format the timestamp if needed, otherwise display as is
                    responseTimeTv.setText(respondedAt != null ? respondedAt : "N/A");
                    responseTimeLabelTv.setVisibility(View.VISIBLE);
                    responseTimeTv.setVisibility(View.VISIBLE);

                } else {
                    // Hide response details if not responded
                    responseLabelTv.setVisibility(View.GONE);
                    responseTextTv.setVisibility(View.GONE);
                    respondedByLabelTv.setVisibility(View.GONE);
                    respondedByTv.setVisibility(View.GONE);
                    responseTimeLabelTv.setVisibility(View.GONE);
                    responseTimeTv.setVisibility(View.GONE);
                }

            } else {
                Toast.makeText(this, "Query details not found.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading query details: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}