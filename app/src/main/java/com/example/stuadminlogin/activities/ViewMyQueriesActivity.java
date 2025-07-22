package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

public class ViewMyQueriesActivity extends AppCompatActivity {

    TextView queriesDisplay;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_queries);

        queriesDisplay = findViewById(R.id.queries_display);
        studentId = getIntent().getIntExtra("student_id", -1);

        if (studentId != -1) {
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

            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()) {
                String queryText = cursor.getString(0);
                String responseStatus = cursor.getString(1);
                String generatedAt = cursor.getString(2);
                String responseText = cursor.getString(3);
                String respondedAt = cursor.getString(4);
                String adminName = cursor.getString(5);

                builder.append("📌 Query: ").append(queryText).append("\n")
                       .append("📅 Generated At: ").append(generatedAt).append("\n")
                       .append("📌 Status: ").append(responseStatus).append("\n");

                if ("Responded".equalsIgnoreCase(responseStatus) && responseText != null) {
                    builder.append("✅ Response: ").append(responseText).append("\n")
                           .append("👨‍🏫 Responded By: ").append(adminName != null ? adminName : "Unknown").append("\n")
                           .append("📅 Responded At: ").append(respondedAt != null ? respondedAt : "N/A").append("\n");
                }
                builder.append("---------------------------\n");
            }

            cursor.close();
            queriesDisplay.setText(builder.toString());
        } else {
            queriesDisplay.setText("No student ID provided.");
        }
    }
}
