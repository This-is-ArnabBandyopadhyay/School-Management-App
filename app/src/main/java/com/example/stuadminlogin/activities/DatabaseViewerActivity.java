// package com.example.stuadminlogin.activities;

// import android.app.Activity;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.widget.ScrollView;
// import android.widget.TextView;

// import com.example.stuadminlogin.database.DatabaseHelper;

// public class DatabaseViewerActivity extends Activity {

//     TextView dataOutput;
//     DatabaseHelper dbHelper;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);

//         ScrollView scrollView = new ScrollView(this);
//         dataOutput = new TextView(this);
//         scrollView.addView(dataOutput);
//         setContentView(scrollView);

//         dbHelper = new DatabaseHelper(this);
//         SQLiteDatabase db = dbHelper.getReadableDatabase();
//         StringBuilder sb = new StringBuilder();

//         // ✅ Admins
//         sb.append("------ ADMINS TABLE ------\n");
//         Cursor c = db.rawQuery("SELECT * FROM admins", null);
//         while (c.moveToNext()) {
//             sb.append("ID: ").append(c.getInt(c.getColumnIndex("admin_id")))
//               .append("\nUsername: ").append(c.getString(c.getColumnIndex("username")))
//               .append("\nFull Name: ").append(c.getString(c.getColumnIndex("full_name")))
//               .append("\nPassword: ").append(c.getString(c.getColumnIndex("password")))
//               .append("\nCreated At: ").append(c.getString(c.getColumnIndex("created_at"))).append("\n\n");
//         }
//         c.close();

//         // ✅ Students
//         sb.append("------ STUDENTS TABLE ------\n");
//         c = db.rawQuery("SELECT * FROM students", null);
//         while (c.moveToNext()) {
//             sb.append("ID: ").append(c.getInt(c.getColumnIndex("student_id")))
//               .append("\nRoll No: ").append(c.getString(c.getColumnIndex("roll_no")))
//               .append("\nName: ").append(c.getString(c.getColumnIndex("name")))
//               .append("\nClass: ").append(c.getString(c.getColumnIndex("class")))
//               .append("\nSection: ").append(c.getString(c.getColumnIndex("section")))
//               .append("\nEmail: ").append(c.getString(c.getColumnIndex("email")))
//               .append("\nPhone: ").append(c.getString(c.getColumnIndex("phone_no")))
//               .append("\nFather: ").append(c.getString(c.getColumnIndex("fathername")))
//               .append("\nMother: ").append(c.getString(c.getColumnIndex("mothername")))
//               .append("\nDOB: ").append(c.getString(c.getColumnIndex("dob")))
//               .append("\nPassword: ").append(c.getString(c.getColumnIndex("password")))
//               .append("\nCreated At: ").append(c.getString(c.getColumnIndex("created_at"))).append("\n\n");
//         }
//         c.close();

//         // ✅ Notices
//         sb.append("------ NOTICES TABLE ------\n");
//         c = db.rawQuery("SELECT * FROM notices", null);
//         while (c.moveToNext()) {
//             sb.append("Notice ID: ").append(c.getInt(c.getColumnIndex("notice_id")))
//               .append("\nAdmin ID: ").append(c.getInt(c.getColumnIndex("admin_id")))
//               .append("\nTitle: ").append(c.getString(c.getColumnIndex("title")))
//               .append("\nDescription: ").append(c.getString(c.getColumnIndex("description")))
//               .append("\nCreated At: ").append(c.getString(c.getColumnIndex("created_at"))).append("\n\n");
//         }
//         c.close();

//         // ✅ Notice to Individuals
//         sb.append("------ NOTICE TO INDIVIDUALS ------\n");
//         c = db.rawQuery("SELECT * FROM notice_to_individuals", null);
//         while (c.moveToNext()) {
//             sb.append("ID: ").append(c.getInt(c.getColumnIndex("id")))
//               .append("\nNotice ID: ").append(c.getInt(c.getColumnIndex("notice_id")))
//               .append("\nStudent ID: ").append(c.getInt(c.getColumnIndex("student_id"))).append("\n\n");
//         }
//         c.close();

//         // ✅ Student Groups
//         sb.append("------ STUDENT GROUPS ------\n");
//         c = db.rawQuery("SELECT * FROM student_groups", null);
//         while (c.moveToNext()) {
//             sb.append("Group ID: ").append(c.getInt(c.getColumnIndex("group_id")))
//               .append("\nGroup Name: ").append(c.getString(c.getColumnIndex("group_name"))).append("\n\n");
//         }
//         c.close();

//         // ✅ Group Members
//         sb.append("------ GROUP MEMBERS ------\n");
//         c = db.rawQuery("SELECT * FROM group_members", null);
//         while (c.moveToNext()) {
//             sb.append("ID: ").append(c.getInt(c.getColumnIndex("id")))
//               .append("\nGroup ID: ").append(c.getInt(c.getColumnIndex("group_id")))
//               .append("\nStudent ID: ").append(c.getInt(c.getColumnIndex("student_id"))).append("\n\n");
//         }
//         c.close();

//         // ✅ Notice to Groups
//         sb.append("------ NOTICE TO GROUPS ------\n");
//         c = db.rawQuery("SELECT * FROM notice_to_groups", null);
//         while (c.moveToNext()) {
//             sb.append("ID: ").append(c.getInt(c.getColumnIndex("id")))
//               .append("\nNotice ID: ").append(c.getInt(c.getColumnIndex("notice_id")))
//               .append("\nGroup ID: ").append(c.getInt(c.getColumnIndex("group_id"))).append("\n\n");
//         }
//         c.close();

//         // ✅ Notice to All
//         sb.append("------ NOTICE TO ALL ------\n");
//         c = db.rawQuery("SELECT * FROM notice_to_all", null);
//         while (c.moveToNext()) {
//             sb.append("ID: ").append(c.getInt(c.getColumnIndex("id")))
//               .append("\nNotice ID: ").append(c.getInt(c.getColumnIndex("notice_id"))).append("\n\n");
//         }
//         c.close();

//         db.close();
//         dataOutput.setText(sb.toString());
//     }
// }



package com.example.stuadminlogin.activities;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.stuadminlogin.database.DatabaseHelper;

public class DatabaseViewerActivity extends Activity {

    TextView dataOutput;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        dataOutput = new TextView(this);
        scrollView.addView(dataOutput);
        setContentView(scrollView);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        StringBuilder sb = new StringBuilder();

        try {
            Cursor c;

            // ===== ADMINS =====
            sb.append("------ ADMINS TABLE ------\n");
            c = db.rawQuery("SELECT * FROM admins", null);
            while (c.moveToNext()) {
                sb.append("ID: ").append(c.getInt(c.getColumnIndex("admin_id")))
                  .append("\nUsername: ").append(c.getString(c.getColumnIndex("username")))
                  .append("\nFull Name: ").append(c.getString(c.getColumnIndex("full_name")))
                  .append("\nPassword: ").append(c.getString(c.getColumnIndex("password")))
                  .append("\nCreated At: ").append(c.getString(c.getColumnIndex("created_at"))).append("\n\n");
            }
            c.close();

            // ===== CLASSES =====
            sb.append("------ CLASSES TABLE ------\n");
            c = db.rawQuery("SELECT * FROM classes", null);
            while (c.moveToNext()) {
                sb.append("Class ID: ").append(c.getInt(c.getColumnIndex("class_id")))
                  .append("\nClass Name: ").append(c.getString(c.getColumnIndex("class_name")))
                  .append("\nClass Code: ").append(c.getString(c.getColumnIndex("class_code"))).append("\n\n");
            }
            c.close();

            // ===== SECTIONS =====
            sb.append("------ SECTIONS TABLE ------\n");
            c = db.rawQuery("SELECT * FROM sections", null);
            while (c.moveToNext()) {
                sb.append("Section ID: ").append(c.getInt(c.getColumnIndex("section_id")))
                  .append("\nClass ID: ").append(c.getInt(c.getColumnIndex("class_id")))
                  .append("\nSection Name: ").append(c.getString(c.getColumnIndex("section_name"))).append("\n\n");
            }
            c.close();

            // ===== STUDENTS =====
sb.append("------ STUDENTS TABLE ------\n");

 c = db.rawQuery(
    "SELECT students.student_id, students.roll_no, students.registration_no, students.name, students.email, students.phone_no, " +
    "students.fathername, students.mothername, students.dob, students.password, students.created_at, " +
    "sections.section_name AS section_name, classes.class_name AS class_name " +
    "FROM students " +
    "JOIN sections ON students.section_id = sections.section_id " +
    "JOIN classes ON students.class_id = classes.class_id", null);

while (c.moveToNext()) {
    sb.append("ID: ").append(c.getInt(c.getColumnIndexOrThrow("student_id")))
      .append("\nRoll No: ").append(c.getString(c.getColumnIndexOrThrow("roll_no")))
      .append("\nRegistration No: ").append(c.getString(c.getColumnIndexOrThrow("registration_no")))
      .append("\nName: ").append(c.getString(c.getColumnIndexOrThrow("name")))
      .append("\nEmail: ").append(c.getString(c.getColumnIndexOrThrow("email")))
      .append("\nPhone No: ").append(c.getString(c.getColumnIndexOrThrow("phone_no")))
      .append("\nFather Name: ").append(c.getString(c.getColumnIndexOrThrow("fathername")))
      .append("\nMother Name: ").append(c.getString(c.getColumnIndexOrThrow("mothername")))
      .append("\nDOB: ").append(c.getString(c.getColumnIndexOrThrow("dob")))
      .append("\nPassword: ").append(c.getString(c.getColumnIndexOrThrow("password")))
      .append("\nClass Name: ").append(c.getString(c.getColumnIndexOrThrow("class_name")))
      .append("\nSection Name: ").append(c.getString(c.getColumnIndexOrThrow("section_name")))
      .append("\nCreated At: ").append(c.getString(c.getColumnIndexOrThrow("created_at")))
      .append("\n\n");
}

c.close(); // Don't forget to close the cursor

            
            // ===== NOTICES =====
            sb.append("------ NOTICES TABLE ------\n");
            c = db.rawQuery("SELECT * FROM notices", null);
            while (c.moveToNext()) {
                sb.append("Notice ID: ").append(c.getInt(c.getColumnIndex("notice_id")))
                  .append("\nAdmin ID: ").append(c.getInt(c.getColumnIndex("admin_id")))
                  .append("\nTitle: ").append(c.getString(c.getColumnIndex("title")))
                  .append("\nDescription: ").append(c.getString(c.getColumnIndex("description")))
                  .append("\nCreated At: ").append(c.getString(c.getColumnIndex("created_at"))).append("\n\n");
            }
            c.close();

            // ===== NOTICE TO CLASSES =====
        sb.append("------ NOTICE TO CLASSES ------\n");
        c = db.rawQuery(
                "SELECT ntc.id, ntc.notice_id, c.class_name " +
                "FROM notice_to_classes ntc " +
                "JOIN classes c ON ntc.class_id = c.class_id", null);
        while (c.moveToNext()) {
            sb.append("Mapping ID: ").append(c.getInt(c.getColumnIndexOrThrow("id")))
              .append("\nNotice ID: ").append(c.getInt(c.getColumnIndexOrThrow("notice_id")))
              .append("\nClass Name: ").append(c.getString(c.getColumnIndexOrThrow("class_name")))
              .append("\n\n");
        }
        c.close();

        // ===== NOTICE TO SECTIONS =====
        sb.append("------ NOTICE TO SECTIONS ------\n");
        c = db.rawQuery(
                "SELECT nts.id, nts.notice_id, s.section_name, c.class_name " +
                "FROM notice_to_sections nts " +
                "JOIN sections s ON nts.section_id = s.section_id " +
                "JOIN classes c ON s.class_id = c.class_id", null);
        while (c.moveToNext()) {
            sb.append("Mapping ID: ").append(c.getInt(c.getColumnIndexOrThrow("id")))
              .append("\nNotice ID: ").append(c.getInt(c.getColumnIndexOrThrow("notice_id")))
              .append("\nSection Name: ").append(c.getString(c.getColumnIndexOrThrow("section_name")))
              .append("\nClass Name: ").append(c.getString(c.getColumnIndexOrThrow("class_name")))
              .append("\n\n");
        }
        c.close();

            // ===== NOTICE TO INDIVIDUALS =====
            sb.append("------ NOTICE TO INDIVIDUALS ------\n");
            c = db.rawQuery("SELECT * FROM notice_to_individuals", null);
            while (c.moveToNext()) {
                sb.append("ID: ").append(c.getInt(c.getColumnIndex("id")))
                  .append("\nNotice ID: ").append(c.getInt(c.getColumnIndex("notice_id")))
                  .append("\nStudent ID: ").append(c.getInt(c.getColumnIndex("student_id"))).append("\n\n");
            }
            c.close();

            // ===== NOTICE TO GROUPS =====
            sb.append("------ NOTICE TO GROUPS ------\n");
            c = db.rawQuery("SELECT * FROM notice_to_groups", null);
            while (c.moveToNext()) {
                sb.append("ID: ").append(c.getInt(c.getColumnIndex("id")))
                  .append("\nNotice ID: ").append(c.getInt(c.getColumnIndex("notice_id")))
                  .append("\nGroup ID: ").append(c.getInt(c.getColumnIndex("group_id"))).append("\n\n");
            }
            c.close();

            // ===== NOTICE TO ALL =====
            sb.append("------ NOTICE TO ALL ------\n");
            c = db.rawQuery("SELECT * FROM notice_to_all", null);
            while (c.moveToNext()) {
                sb.append("ID: ").append(c.getInt(c.getColumnIndex("id")))
                  .append("\nNotice ID: ").append(c.getInt(c.getColumnIndex("notice_id"))).append("\n\n");
            }
            c.close();

            // ===== STUDENT GROUPS =====
            sb.append("------ STUDENT GROUPS ------\n");
            c = db.rawQuery("SELECT * FROM student_groups", null);
            while (c.moveToNext()) {
                sb.append("Group ID: ").append(c.getInt(c.getColumnIndex("group_id")))
                  .append("\nGroup Name: ").append(c.getString(c.getColumnIndex("group_name"))).append("\n\n");
            }
            c.close();

            // ===== GROUP MEMBERS =====
            sb.append("------ GROUP MEMBERS ------\n");
            c = db.rawQuery("SELECT * FROM group_members", null);
            while (c.moveToNext()) {
                sb.append("ID: ").append(c.getInt(c.getColumnIndex("id")))
                  .append("\nGroup ID: ").append(c.getInt(c.getColumnIndex("group_id")))
                  .append("\nStudent ID: ").append(c.getInt(c.getColumnIndex("student_id"))).append("\n\n");
            }
            c.close();

            // ===== QUERIES =====
            sb.append("------ QUERIES TABLE ------\n");
            c = db.rawQuery("SELECT * FROM queries", null);
            while (c.moveToNext()) {
                sb.append("Query ID: ").append(c.getInt(c.getColumnIndex("query_id")))
                  .append("\nStudent ID: ").append(c.getInt(c.getColumnIndex("student_id")))
                  .append("\nQuery Text: ").append(c.getString(c.getColumnIndex("query_text")))
                  .append("\nStatus: ").append(c.getString(c.getColumnIndex("response_status")))
                  .append("\nGenerated At: ").append(c.getString(c.getColumnIndex("generated_at"))).append("\n\n");
            }
            c.close();

            // ===== QUERY RESPONSES =====
            sb.append("------ QUERY RESPONSES TABLE ------\n");
            c = db.rawQuery("SELECT r.response_id, r.query_id, r.admin_id, a.full_name, r.response_text, r.responded_at " +
                            "FROM query_responses r LEFT JOIN admins a ON r.admin_id = a.admin_id", null);
            while (c.moveToNext()) {
                sb.append("Response ID: ").append(c.getInt(c.getColumnIndex("response_id")))
                  .append("\nQuery ID: ").append(c.getInt(c.getColumnIndex("query_id")))
                  .append("\nAdmin ID: ").append(c.getInt(c.getColumnIndex("admin_id")))
                  .append("\nAdmin Name: ").append(c.getString(c.getColumnIndex("full_name")))
                  .append("\nResponse: ").append(c.getString(c.getColumnIndex("response_text")))
                  .append("\nResponded At: ").append(c.getString(c.getColumnIndex("responded_at"))).append("\n\n");
            }
            c.close();

            // ===== LEAVE APPLICATIONS =====
sb.append("------ LEAVE APPLICATIONS TABLE ------\n");
c = db.rawQuery(
        "SELECT l.leave_id, l.student_id, s.name AS student_name, l.from_date, l.to_date, l.reason, " +
        "l.status, l.admin_id, a.full_name AS admin_name, l.admin_response, l.applied_at, l.reviewed_at " +
        "FROM leave_applications l " +
        "LEFT JOIN students s ON l.student_id = s.student_id " +
        "LEFT JOIN admins a ON l.admin_id = a.admin_id", null);

while (c.moveToNext()) {
    sb.append("Leave ID: ").append(c.getInt(c.getColumnIndexOrThrow("leave_id")))
      .append("\nStudent ID: ").append(c.getInt(c.getColumnIndexOrThrow("student_id")))
      .append("\nStudent Name: ").append(c.getString(c.getColumnIndexOrThrow("student_name")))
      .append("\nFrom Date: ").append(c.getString(c.getColumnIndexOrThrow("from_date")))
      .append("\nTo Date: ").append(c.getString(c.getColumnIndexOrThrow("to_date")))
      .append("\nReason: ").append(c.getString(c.getColumnIndexOrThrow("reason")))
      .append("\nStatus: ").append(c.getString(c.getColumnIndexOrThrow("status")))
      .append("\nAdmin ID: ").append(c.isNull(c.getColumnIndexOrThrow("admin_id")) ? "N/A" : c.getInt(c.getColumnIndexOrThrow("admin_id")))
      .append("\nAdmin Name: ").append(c.getString(c.getColumnIndexOrThrow("admin_name")))
      .append("\nAdmin Response: ").append(c.getString(c.getColumnIndexOrThrow("admin_response")))
      .append("\nApplied At: ").append(c.getString(c.getColumnIndexOrThrow("applied_at")))
      .append("\nReviewed At: ").append(c.getString(c.getColumnIndexOrThrow("reviewed_at")))
      .append("\n\n");
}
c.close();

// ===== ATTENDANCE TABLE =====
sb.append("------ ATTENDANCE TABLE ------\n");
c = db.rawQuery(
        "SELECT a.attendance_id, a.student_id, s.name AS student_name, a.date, a.status, " +
        "a.updated_by_admin_id, ad.full_name AS admin_name, a.updated_at " +
        "FROM attendance a " +
        "LEFT JOIN students s ON a.student_id = s.student_id " +
        "LEFT JOIN admins ad ON a.updated_by_admin_id = ad.admin_id " +
        "ORDER BY a.date DESC", null);

while (c.moveToNext()) {
    sb.append("Attendance ID: ").append(c.getInt(c.getColumnIndexOrThrow("attendance_id")))
      .append("\nStudent ID: ").append(c.getInt(c.getColumnIndexOrThrow("student_id")))
      .append("\nStudent Name: ").append(c.getString(c.getColumnIndexOrThrow("student_name")))
      .append("\nDate: ").append(c.getString(c.getColumnIndexOrThrow("date")))
      .append("\nStatus: ").append(c.getString(c.getColumnIndexOrThrow("status")))
      .append("\nUpdated By Admin ID: ").append(c.isNull(c.getColumnIndexOrThrow("updated_by_admin_id")) ? "N/A" : c.getInt(c.getColumnIndexOrThrow("updated_by_admin_id")))
      .append("\nAdmin Name: ").append(c.getString(c.getColumnIndexOrThrow("admin_name")))
      .append("\nUpdated At: ").append(c.getString(c.getColumnIndexOrThrow("updated_at")))
      .append("\n\n");
}
c.close();




        } catch (Exception e) {
            sb.append("ERROR: ").append(e.getMessage());
        } finally {
            db.close();
        }

        dataOutput.setText(sb.toString());
    }
}


