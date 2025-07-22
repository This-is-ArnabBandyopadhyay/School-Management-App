package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.StudentAttendanceAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.Attendance;
import java.util.List;

public class ViewAttendanceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentAttendanceAdapter adapter;
    DatabaseHelper db;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        recyclerView = findViewById(R.id.recyclerViewAttendance);
        db = new DatabaseHelper(this);

        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
    Toast.makeText(this, "Invalid student ID!", Toast.LENGTH_SHORT).show();
    finish();
    return;
}

        List<Attendance> attendanceList = db.getAttendanceByStudent(studentId);

        adapter = new StudentAttendanceAdapter(this, attendanceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}