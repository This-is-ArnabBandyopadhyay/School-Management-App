package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.R;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.text.SimpleDateFormat;
import java.util.*;

public class NoticeActivity extends AppCompatActivity {

    EditText titleEdit, descEdit, individualIdsEdit, groupIdsEdit, classNamesEdit, classSectionPairsEdit;
    CheckBox sendToAllCheck;
    Button sendNoticeBtn;
    DatabaseHelper dbHelper;
    int adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        titleEdit = findViewById(R.id.noticeTitle);
        descEdit = findViewById(R.id.noticeDesc);
        individualIdsEdit = findViewById(R.id.studentIds);
        groupIdsEdit = findViewById(R.id.groupIds);
        classNamesEdit = findViewById(R.id.classNames);
        classSectionPairsEdit = findViewById(R.id.classSectionPairs);
        sendToAllCheck = findViewById(R.id.sendToAllCheck);
        sendNoticeBtn = findViewById(R.id.sendNoticeBtn);
        dbHelper = new DatabaseHelper(this);

        adminId = getIntent().getIntExtra("admin_id", -1);
        if (adminId == -1) {
            Toast.makeText(this, "Admin ID not found. Please login again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // ✅ Disable/Enable fields based on SendToAll checkbox
        EditText[] allInputs = new EditText[]{
                individualIdsEdit, groupIdsEdit, classNamesEdit, classSectionPairsEdit
        };

        sendToAllCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (EditText field : allInputs) {
                field.setEnabled(!isChecked);
            }
        });

        sendNoticeBtn.setOnClickListener(v -> {
            String title = titleEdit.getText().toString().trim();
            String desc = descEdit.getText().toString().trim();
            String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

            if (title.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Title and description are required", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // ✅ Individuals by roll_no
            List<Integer> validStudentIds = new ArrayList<>();
            for (String roll : individualIdsEdit.getText().toString().split(",")) {
                roll = roll.trim();
                if (!roll.isEmpty()) {
                    Cursor cursor = db.rawQuery("SELECT student_id FROM students WHERE roll_no = ?",
                            new String[]{roll});
                    if (cursor.moveToFirst()) {
                        validStudentIds.add(cursor.getInt(0));
                    } else {
                        Toast.makeText(this, "Roll number not found: " + roll, Toast.LENGTH_SHORT).show();
                        cursor.close();
                        return;
                    }
                    cursor.close();
                }
            }

            // ✅ Class name input: allow "1" or "Class 1"
            List<Integer> validClassIds = new ArrayList<>();
            for (String className : classNamesEdit.getText().toString().split(",")) {
                className = className.trim();
                if (!className.isEmpty()) {
                    if (!className.startsWith("Class ")) {
                        className = "Class " + className;
                    }
                    Cursor cursor = db.rawQuery("SELECT class_id FROM classes WHERE class_name = ?", new String[]{className});
                    if (cursor.moveToFirst()) {
                        validClassIds.add(cursor.getInt(0));
                    } else {
                        Toast.makeText(this, "Class not found: " + className, Toast.LENGTH_SHORT).show();
                        cursor.close();
                        return;
                    }
                    cursor.close();
                }
            }

            // ✅ Section input: allow "1-A" or "Class 1-A"
            List<Integer> validSectionIds = new ArrayList<>();
            for (String input : classSectionPairsEdit.getText().toString().split(",")) {
                input = input.trim();
                if (!input.isEmpty()) {
                    String[] parts = input.split("-");
                    if (parts.length != 2) {
                        Toast.makeText(this, "Invalid class-section format: " + input, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String classPart = parts[0].trim();
                    if (!classPart.startsWith("Class ")) {
                        classPart = "Class " + classPart;
                    }
                    String sectionPart = parts[1].trim();

                    Cursor classCursor = db.rawQuery("SELECT class_id FROM classes WHERE class_name = ?", new String[]{classPart});
                    if (!classCursor.moveToFirst()) {
                        Toast.makeText(this, "Class not found: " + classPart, Toast.LENGTH_SHORT).show();
                        classCursor.close();
                        return;
                    }

                    int classId = classCursor.getInt(0);
                    classCursor.close();

                    Cursor sectionCursor = db.rawQuery(
                            "SELECT section_id FROM sections WHERE section_name = ? AND class_id = ?",
                            new String[]{sectionPart, String.valueOf(classId)});
                    if (sectionCursor.moveToFirst()) {
                        validSectionIds.add(sectionCursor.getInt(0));
                    } else {
                        Toast.makeText(this, "Section not found for " + input, Toast.LENGTH_SHORT).show();
                        sectionCursor.close();
                        return;
                    }
                    sectionCursor.close();
                }
            }

            // ✅ Groups
            List<Integer> validGroupIds = new ArrayList<>();
            for (String gid : groupIdsEdit.getText().toString().split(",")) {
                gid = gid.trim();
                if (!gid.isEmpty()) {
                    Cursor cursor = db.rawQuery("SELECT group_id FROM student_groups WHERE group_id = ?",
                            new String[]{gid});
                    if (cursor.moveToFirst()) {
                        validGroupIds.add(cursor.getInt(0));
                    } else {
                        Toast.makeText(this, "Group ID not found: " + gid, Toast.LENGTH_SHORT).show();
                        cursor.close();
                        return;
                    }
                    cursor.close();
                }
            }

            // ✅ Insert into notices table
            ContentValues notice = new ContentValues();
            notice.put("admin_id", adminId);
            notice.put("title", title);
            notice.put("description", desc);
            notice.put("created_at", createdAt);
            long noticeId = db.insert("notices", null, notice);

            if (noticeId == -1) {
                Toast.makeText(this, "Failed to create notice", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sendToAllCheck.isChecked()) {
                ContentValues all = new ContentValues();
                all.put("notice_id", noticeId);
                db.insert("notice_to_all", null, all);
            }

            for (int cid : validClassIds) {
                ContentValues row = new ContentValues();
                row.put("notice_id", noticeId);
                row.put("class_id", cid);
                db.insert("notice_to_classes", null, row);
            }

            for (int sid : validSectionIds) {
                ContentValues row = new ContentValues();
                row.put("notice_id", noticeId);
                row.put("section_id", sid);
                db.insert("notice_to_sections", null, row);
            }

            for (int sid : validStudentIds) {
                ContentValues row = new ContentValues();
                row.put("notice_id", noticeId);
                row.put("student_id", sid);
                db.insert("notice_to_individuals", null, row);
            }

            for (int gid : validGroupIds) {
                ContentValues row = new ContentValues();
                row.put("notice_id", noticeId);
                row.put("group_id", gid);
                db.insert("notice_to_groups", null, row);
            }

            Toast.makeText(this, "Notice sent successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
