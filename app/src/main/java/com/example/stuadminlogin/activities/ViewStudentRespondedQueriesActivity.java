// package com.example.stuadminlogin.activities;

// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.RespondedQueryAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.Query;
// import java.util.ArrayList;
// import java.util.List;

// public class ViewStudentRespondedQueriesActivity extends AppCompatActivity {

//     RecyclerView recyclerView;
//     DatabaseHelper dbHelper;
//     List<Query> respondedQueries;
//     RespondedQueryAdapter adapter;
//     int studentId;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_view_student_responded_queries);

//         recyclerView = findViewById(R.id.recyclerRespondedQueries);
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));

//         dbHelper = new DatabaseHelper(this);
//         studentId = getIntent().getIntExtra("student_id", -1);
//         respondedQueries = new ArrayList<>();

//         SQLiteDatabase db = dbHelper.getReadableDatabase();

//         Cursor cursor = db.rawQuery("SELECT q.query_text, q.generated_at, r.response_text, r.responded_at " +
//                 "FROM queries q JOIN query_responses r ON q.query_id = r.query_id " +
//                 "WHERE q.student_id = ?", new String[]{String.valueOf(studentId)});

//         while (cursor.moveToNext()) {
//             Query query = new Query();
//             query.setQueryText(cursor.getString(0));
//             query.setGeneratedAt(cursor.getString(1));
//             query.setResponseText(cursor.getString(2));
//             query.setRespondedAt(cursor.getString(3));
//             respondedQueries.add(query);
//         }
//         cursor.close();

//         adapter = new RespondedQueryAdapter(respondedQueries);
//         recyclerView.setAdapter(adapter);
//     }
// }



package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View; // Import View for setVisibility
import android.widget.TextView; // Import TextView
import android.widget.Toast; // Import Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.RespondedQueryAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.Query;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentRespondedQueriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    List<Query> respondedQueries;
    RespondedQueryAdapter adapter;
    int studentId;
    TextView noQueriesText; // Added TextView for no queries state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_responded_queries);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Responded Queries"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        recyclerView = findViewById(R.id.recyclerRespondedQueries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        studentId = getIntent().getIntExtra("student_id", -1);
        noQueriesText = findViewById(R.id.no_queries_text); // Initialize noQueriesText

        respondedQueries = new ArrayList<>();

        if (studentId == -1) {
            Toast.makeText(this, "Student ID not found. Cannot load queries.", Toast.LENGTH_SHORT).show();
            noQueriesText.setText("Error: Student ID not available.");
            noQueriesText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        loadRespondedQueries();
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }

    private void loadRespondedQueries() {
        // Use try-with-resources for SQLiteDatabase and Cursor for automatic closing
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery(
                     "SELECT q.query_text, q.generated_at, r.response_text, r.responded_at " +
                             "FROM queries q JOIN query_responses r ON q.query_id = r.query_id " +
                             "WHERE q.student_id = ? ORDER BY r.responded_at DESC", // Order by response time
                     new String[]{String.valueOf(studentId)})) {

            if (cursor.getCount() == 0) {
                noQueriesText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                noQueriesText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                while (cursor.moveToNext()) {
                    Query query = new Query();
                    query.setQueryText(cursor.getString(0)); // query_text
                    query.setGeneratedAt(cursor.getString(1)); // generated_at
                    query.setResponseText(cursor.getString(2)); // response_text
                    query.setRespondedAt(cursor.getString(3)); // responded_at
                    query.setResponseStatus("Responded"); // Manually set status for consistency if Query model needs it
                    respondedQueries.add(query);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading responded queries: " + e.getMessage(), Toast.LENGTH_LONG).show();
            noQueriesText.setText("Failed to load queries: " + e.getMessage());
            noQueriesText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        // Initialize and set adapter only once after data is loaded
        if (adapter == null) {
            adapter = new RespondedQueryAdapter(respondedQueries);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged(); // Notify if data changes
        }
    }
}