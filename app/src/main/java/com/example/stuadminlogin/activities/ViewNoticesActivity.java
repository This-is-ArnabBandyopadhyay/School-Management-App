package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

public class ViewNoticesActivity extends AppCompatActivity {

    LinearLayout container;
    DatabaseHelper dbHelper;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notices);

        container = findViewById(R.id.noticeList);
        dbHelper = new DatabaseHelper(this);

        // Get student ID from intent
        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            TextView errorText = new TextView(this);
            errorText.setText("Error: Student ID not found. Please login again.");
            container.addView(errorText);
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get class_id and section_id of the student
        int classId = -1;
        int sectionId = -1;
        Cursor stuCursor = db.rawQuery(
                "SELECT class_id, section_id FROM students WHERE student_id = ?",
                new String[]{String.valueOf(studentId)}
        );

        if (stuCursor.moveToFirst()) {
            classId = stuCursor.getInt(0);
            sectionId = stuCursor.getInt(1);
        }
        stuCursor.close();

        // Main notice query
        String query =
    "SELECT n.title, n.description, n.created_at FROM notices n " +
    "JOIN notice_to_individuals ni ON n.notice_id = ni.notice_id " +
    "WHERE ni.student_id = ? " +

    "UNION " +

    "SELECT n.title, n.description, n.created_at FROM notices n " +
    "JOIN notice_to_groups ng ON n.notice_id = ng.notice_id " +
    "JOIN group_members gm ON ng.group_id = gm.group_id " +
    "WHERE gm.student_id = ? " +

    "UNION " +

    "SELECT n.title, n.description, n.created_at FROM notices n " +
    "JOIN notice_to_classes nc ON n.notice_id = nc.notice_id " +
    "WHERE nc.class_id = ? " +

    "UNION " +

    "SELECT n.title, n.description, n.created_at FROM notices n " +
    "JOIN notice_to_sections ns ON n.notice_id = ns.notice_id " +
    "JOIN sections s ON ns.section_id = s.section_id " +
    "WHERE s.section_id = ? AND s.class_id = ? " +

    "UNION " +

    "SELECT n.title, n.description, n.created_at FROM notices n " +
    "JOIN notice_to_all na ON n.notice_id = na.notice_id " +

    "ORDER BY created_at DESC";

        Cursor cursor = db.rawQuery(query, new String[]{
    String.valueOf(studentId), // for individuals
    String.valueOf(studentId), // for group members
    String.valueOf(classId),   // for class
    String.valueOf(sectionId), // for section
    String.valueOf(classId)    // for class of section
});


        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                String content = cursor.getString(1);
                String date = cursor.getString(2);

                TextView tv = new TextView(this);
                tv.setText("Title: " + title + "\n" +
                        "Description: " + content + "\n" +
                        "Date: " + date);
                tv.setPadding(20, 20, 20, 20);
                tv.setBackgroundResource(R.drawable.notice_background);
                container.addView(tv);
            } while (cursor.moveToNext());
        } else {
            TextView tv = new TextView(this);
            tv.setText("No notices found.");
            container.addView(tv);
        }

        cursor.close();
    }
}
