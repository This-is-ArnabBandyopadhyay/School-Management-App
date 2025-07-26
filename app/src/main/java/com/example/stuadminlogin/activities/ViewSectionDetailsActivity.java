package com.example.stuadminlogin.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.stuadminlogin.R;

public class ViewSectionDetailsActivity extends Activity {

    Button btnViewStudents, btnUpdateAttendance;
    int sectionId, classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_section_details);

        btnViewStudents = findViewById(R.id.btnViewStudents);
        btnUpdateAttendance = findViewById(R.id.btnUpdateAttendance);

        sectionId = getIntent().getIntExtra("section_id", -1);
        classId = getIntent().getIntExtra("class_id", -1);

        btnViewStudents.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageStudentsActivity.class);
            intent.putExtra("section_id", sectionId);
            intent.putExtra("class_id", classId);
            startActivity(intent);
        });

        btnUpdateAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(this, BulkUpdateAttendanceActivity.class);
            intent.putExtra("section_id", sectionId);
            intent.putExtra("class_id", classId);
            startActivity(intent);
        });
    }
}
