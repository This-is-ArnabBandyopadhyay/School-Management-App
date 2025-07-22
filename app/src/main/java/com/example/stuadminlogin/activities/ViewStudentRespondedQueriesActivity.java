package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_responded_queries);

        recyclerView = findViewById(R.id.recyclerRespondedQueries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        studentId = getIntent().getIntExtra("student_id", -1);
        respondedQueries = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT q.query_text, q.generated_at, r.response_text, r.responded_at " +
                "FROM queries q JOIN query_responses r ON q.query_id = r.query_id " +
                "WHERE q.student_id = ?", new String[]{String.valueOf(studentId)});

        while (cursor.moveToNext()) {
            Query query = new Query();
            query.setQueryText(cursor.getString(0));
            query.setGeneratedAt(cursor.getString(1));
            query.setResponseText(cursor.getString(2));
            query.setRespondedAt(cursor.getString(3));
            respondedQueries.add(query);
        }
        cursor.close();

        adapter = new RespondedQueryAdapter(respondedQueries);
        recyclerView.setAdapter(adapter);
    }
}
