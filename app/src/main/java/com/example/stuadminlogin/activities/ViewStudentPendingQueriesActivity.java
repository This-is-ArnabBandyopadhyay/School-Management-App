package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.PendingQueryAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.Query;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentPendingQueriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    List<Query> pendingQueries;
    PendingQueryAdapter adapter;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_pending_queries);

        recyclerView = findViewById(R.id.recyclerPendingQueries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        studentId = getIntent().getIntExtra("student_id", -1);
        pendingQueries = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT query_text, generated_at FROM queries WHERE student_id = ? AND response_status = 'Pending'", new String[]{String.valueOf(studentId)});

        while (cursor.moveToNext()) {
            Query query = new Query();
            query.setQueryText(cursor.getString(0));
            query.setGeneratedAt(cursor.getString(1));
            pendingQueries.add(query);
        }
        cursor.close();

        adapter = new PendingQueryAdapter(pendingQueries);
        recyclerView.setAdapter(adapter);
    }
}
