// package com.example.stuadminlogin.activities;

// import android.content.Intent;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.view.View;
// import android.widget.AdapterView;
// import android.widget.ListView;

// import androidx.appcompat.app.AppCompatActivity;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.QueryListAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;

// import java.util.ArrayList;

// public class AdminViewQueriesActivity extends AppCompatActivity {

//     ListView queryListView;
//     ArrayList<String> queryTexts;
//     ArrayList<String> studentInfos;
//     ArrayList<Integer> queryIds;
//     int adminId;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_admin_view_queries);

//         queryListView = findViewById(R.id.query_list);
//         queryTexts = new ArrayList<>();
//         studentInfos = new ArrayList<>();
//         queryIds = new ArrayList<>();

//         adminId = getIntent().getIntExtra("admin_id", -1);

//         DatabaseHelper dbHelper = new DatabaseHelper(this);
//         SQLiteDatabase db = dbHelper.getReadableDatabase();

//         String query = "SELECT q.query_id, q.query_text, s.name, c.class_name, sec.section_name " +
//                        "FROM queries q " +
//                        "JOIN students s ON q.student_id = s.student_id " +
//                        "JOIN classes c ON s.class_id = c.class_id " +
//                        "JOIN sections sec ON s.section_id = sec.section_id " +
//                        "WHERE q.response_status = 'Pending'";

//         Cursor cursor = db.rawQuery(query, null);
//         while (cursor.moveToNext()) {
//             int queryId = cursor.getInt(0);
//             String queryText = cursor.getString(1);
//             String studentName = cursor.getString(2);
//             String className = cursor.getString(3);
//             String sectionName = cursor.getString(4);

//             queryIds.add(queryId);
//             queryTexts.add(queryText);
//             studentInfos.add("Student: " + studentName + " | " + className + " | Section: " + sectionName);
//         }
//         cursor.close();

//         QueryListAdapter adapter = new QueryListAdapter(this, queryTexts, studentInfos);
//         queryListView.setAdapter(adapter);

//         queryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//             @Override
//             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                 Intent intent = new Intent(AdminViewQueriesActivity.this, RespondToQueryActivity.class);
//                 intent.putExtra("query_id", queryIds.get(i));
//                 intent.putExtra("admin_id", adminId);
//                 startActivity(intent);
//             }
//         });
//     }
// }


package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.QueryListAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;

import java.util.ArrayList;

public class AdminViewQueriesActivity extends AppCompatActivity {

    ListView queryListView;
    ArrayList<String> queryTexts;
    ArrayList<String> studentInfos;
    ArrayList<Integer> queryIds;
    int adminId;
    QueryListAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_queries);

        queryListView = findViewById(R.id.query_list);
        queryTexts = new ArrayList<>();
        studentInfos = new ArrayList<>();
        queryIds = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        adminId = getIntent().getIntExtra("admin_id", -1);
        loadPendingQueries();

        queryListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(AdminViewQueriesActivity.this, RespondToQueryActivity.class);
            intent.putExtra("query_id", queryIds.get(position));
            intent.putExtra("admin_id", adminId);
            // Add position to identify which item to remove later
            intent.putExtra("item_position", position);
            startActivityForResult(intent, 1);
        });
    }

    private void loadPendingQueries() {
        queryTexts.clear();
        studentInfos.clear();
        queryIds.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT q.query_id, q.query_text, s.name, c.class_name, sec.section_name " +
                "FROM queries q " +
                "JOIN students s ON q.student_id = s.student_id " +
                "JOIN classes c ON s.class_id = c.class_id " +
                "JOIN sections sec ON s.section_id = sec.section_id " +
                "WHERE q.response_status = 'Pending'";

        try (Cursor cursor = db.rawQuery(query, null)) {
            while (cursor.moveToNext()) {
                queryIds.add(cursor.getInt(0));
                queryTexts.add(cursor.getString(1));
                studentInfos.add("Student: " + cursor.getString(2) + " | " + 
                               cursor.getString(3) + " | Section: " + cursor.getString(4));
            }
        }

        if (adapter == null) {
            adapter = new QueryListAdapter(this, queryTexts, studentInfos);
            queryListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Query was responded to, remove it from the list
            int position = data.getIntExtra("item_position", -1);
            if (position != -1) {
                queryIds.remove(position);
                queryTexts.remove(position);
                studentInfos.remove(position);
                adapter.notifyDataSetChanged();
            }
        }
    }
}