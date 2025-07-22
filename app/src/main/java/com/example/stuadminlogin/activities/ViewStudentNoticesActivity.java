package com.example.stuadminlogin.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.NoticeListAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.Notice;

import java.util.List;

public class ViewStudentNoticesActivity extends AppCompatActivity {

    RecyclerView notice_list_view;
    DatabaseHelper dbHelper;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_notices);

        notice_list_view = findViewById(R.id.notice_list_view);
        dbHelper = new DatabaseHelper(this);
        studentId = getIntent().getIntExtra("student_id", -1);

        List<Notice> notices = dbHelper.getNoticesForStudent(studentId);
        NoticeListAdapter adapter = new NoticeListAdapter(this, notices);
        notice_list_view.setLayoutManager(new LinearLayoutManager(this));
        notice_list_view.setAdapter(adapter);
    }
}
