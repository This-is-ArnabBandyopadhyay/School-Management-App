package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.example.stuadminlogin.adapters.StudentLeaveAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.LeaveApplication;
import com.example.stuadminlogin.R;
import java.util.List;

public class StudentLeaveListActivity extends AppCompatActivity {
    private RecyclerView rv;
    private Button btnNew;
    private DatabaseHelper db;

    // logged-in student ID
    private int currentStudentId;

    @Override
    protected void onCreate(Bundle s) {
    super.onCreate(s);
    setContentView(R.layout.activity_student_leave_list);

    // ✅ Get student ID from Intent
    currentStudentId = getIntent().getIntExtra("student_id", -1);
    if (currentStudentId == -1) {
        // student ID not found — optionally handle error
        finish();
        return;
    }

    db = new DatabaseHelper(this);
    rv = findViewById(R.id.recyclerStudentLeaves);
    rv.setLayoutManager(new LinearLayoutManager(this));
    btnNew = findViewById(R.id.btnNewLeave);

    btnNew.setOnClickListener(v -> {
        // ✅ Pass student ID to the form as well
        Intent intent = new Intent(this, LeaveApplicationFormActivity.class);
        intent.putExtra("student_id", currentStudentId);
        startActivity(intent);
    });
}

    @Override
    protected void onResume() {
        super.onResume();
        List<LeaveApplication> list = db.getLeavesByStudent(currentStudentId);
        rv.setAdapter(new StudentLeaveAdapter(list));
    }
}
