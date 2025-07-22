package com.example.stuadminlogin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.util.Log;


import com.example.stuadminlogin.models.StudentModel;
import com.example.stuadminlogin.models.Notice;
import com.example.stuadminlogin.models.LeaveApplication;
import com.example.stuadminlogin.models.Attendance;




import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
        public static final String DB_NAME = "school.db";
        public static final int DB_VERSION = 4; // ⬅️ Updated to trigger onUpgrade()

        public DatabaseHelper(Context context) {
                super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                // Admin Table
                db.execSQL("CREATE TABLE admins (" +
                                "admin_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "username TEXT, " +
                                "full_name TEXT, " +
                                "password TEXT, " +
                                "created_at TEXT)");

                db.execSQL("INSERT INTO admins (username, full_name, password, created_at) " +
                                "VALUES ('admin1', 'Admin One', 'admin123', datetime('now'))");
                db.execSQL("INSERT INTO admins (username, full_name, password, created_at) " +
                                "VALUES ('admin2', 'Admin Two', 'admin456', datetime('now'))");

                // 🟢 Classes Table with class_code
                db.execSQL("CREATE TABLE classes (" +
                                "class_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "class_name TEXT NOT NULL, " +
                                "class_code TEXT NOT NULL UNIQUE)");

                // Dummy classes with codes
                db.execSQL("INSERT INTO classes (class_name, class_code) VALUES " +
                                "('Class 1', 'CLS9'), " +
                                "('Class 2', 'CLS10')");

                // 🟢 Sections Table
                db.execSQL("CREATE TABLE sections (" +
                                "section_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "class_id INTEGER NOT NULL, " +
                                "section_name TEXT NOT NULL, " +
                                "FOREIGN KEY(class_id) REFERENCES classes(class_id))");

                // Dummy sections
                db.execSQL("INSERT INTO sections (class_id, section_name) VALUES " +
                                "(1, 'A'), (1, 'B'), (2, 'A'), (2, 'B')");

                // 🔵 Students Table (comes after sections since it references section_id)
                db.execSQL("CREATE TABLE students (" +
                                "student_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "roll_no TEXT, " +
                                "registration_no TEXT, " +
                                "name TEXT, " +
                                "email TEXT, " +
                                "phone_no TEXT, " +
                                "fathername TEXT, " +
                                "mothername TEXT, " +
                                "dob TEXT, " +
                                "password TEXT, " +
                                "class_id INTEGER NOT NULL, " +
                                "section_id INTEGER NOT NULL, " +
                                "created_at TEXT, " +
                                "FOREIGN KEY(class_id) REFERENCES classes(class_id), " + // ✅ Add this line
                                "FOREIGN KEY(section_id) REFERENCES sections(section_id))");

                // Dummy students — ensure section_id matches valid values from the inserted sections above
                db.execSQL("INSERT INTO students " +
                                "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at,class_id, section_id) "
                                +
                                "VALUES ('001', 'REG123', 'John Doe', 'john@example.com', '9876543210', 'Father Name', 'Mother Name', '2008-10-05', 'student123', datetime('now'), 1, 1)");
                db.execSQL("INSERT INTO students " +
                                "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at,class_id, section_id) "
                                +
                                "VALUES ('002', 'REG124', 'Jane Smith', 'jane@example.com', '9123456789', 'Mr. Smith', 'Mrs. Smith', '2009-06-12', 'student456', datetime('now'), 1, 1)");
                db.execSQL("INSERT INTO students " +
                                "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, class_id, section_id) "
                                +
                                "VALUES ('003', 'REG125', 'Amit Roy', 'amit@example.com', '9123456701', 'Mr. Roy', 'Mrs. Roy', '2008-02-14', 'student789', datetime('now'), 1, 2)");
                db.execSQL("INSERT INTO students " +
                                "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, class_id, section_id) "
                                +
                                "VALUES ('004', 'REG126', 'Sara Khan', 'sara@example.com', '9123456702', 'Mr. Khan', 'Mrs. Khan', '2008-07-20', 'student101', datetime('now'), 1, 2)");
                db.execSQL("INSERT INTO students " +
                                "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, class_id, section_id) "
                                +
                                "VALUES ('005', 'REG127', 'Ravi Verma', 'ravi@example.com', '9123456703', 'Mr. Verma', 'Mrs. Verma', '2009-03-08', 'student102', datetime('now'), 2, 1)");
                db.execSQL("INSERT INTO students " +
                                "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, class_id, section_id) "
                                +
                                "VALUES ('006', 'REG128', 'Nisha Patel', 'nisha@example.com', '9123456704', 'Mr. Patel', 'Mrs. Patel', '2009-11-18', 'student103', datetime('now'), 2, 1)");

                // 🟢 Notices Table
                db.execSQL("CREATE TABLE notices (" +
                                "notice_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "admin_id INTEGER, " +
                                "title TEXT NOT NULL, " +
                                "description TEXT NOT NULL, " +
                                "created_at TEXT NOT NULL, " +
                                "FOREIGN KEY(admin_id) REFERENCES admins(admin_id))");

                db.execSQL("INSERT INTO notices (admin_id, title, description, created_at) VALUES " +
                                "(1, 'Holiday Notice', 'School will be closed on Monday.', datetime('now')), " +
                                "(1, 'Group Meeting', 'Science Club meeting at 2 PM.', datetime('now'))");

                // 🟢 Notice to Individuals
                db.execSQL("CREATE TABLE notice_to_individuals (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "notice_id INTEGER NOT NULL, " +
                                "student_id INTEGER NOT NULL, " +
                                "FOREIGN KEY(notice_id) REFERENCES notices(notice_id), " +
                                "FOREIGN KEY(student_id) REFERENCES students(student_id))");

                db.execSQL("INSERT INTO notice_to_individuals (notice_id, student_id) VALUES (1, 1)");

                // 🟢 Notice to Classes
                db.execSQL("CREATE TABLE notice_to_classes (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "notice_id INTEGER NOT NULL, " +
                                "class_id INTEGER NOT NULL, " +
                                "FOREIGN KEY(notice_id) REFERENCES notices(notice_id), " +
                                "FOREIGN KEY(class_id) REFERENCES classes(class_id))");

                db.execSQL("INSERT INTO notice_to_classes (notice_id, class_id) VALUES (1, 2),(2, 1)");

                // 🟢 Notice to Sections
                db.execSQL("CREATE TABLE notice_to_sections (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "notice_id INTEGER NOT NULL, " +
                                "section_id INTEGER NOT NULL, " +
                                "FOREIGN KEY(notice_id) REFERENCES notices(notice_id), " +
                                "FOREIGN KEY(section_id) REFERENCES sections(section_id))");

                db.execSQL("INSERT INTO notice_to_sections (notice_id, section_id) VALUES (1, 1), (2, 2)");

                // 🟢 Student Groups
                db.execSQL("CREATE TABLE student_groups (" +
                                "group_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "group_name TEXT NOT NULL)");

                db.execSQL("INSERT INTO student_groups (group_name) VALUES ('Class 10A'), ('Science Club'), ('Eco Club'),('Math Olympiad')");

                // 🟢 Group Members
                db.execSQL("CREATE TABLE group_members (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "group_id INTEGER NOT NULL, " +
                                "student_id INTEGER NOT NULL, " +
                                "FOREIGN KEY(group_id) REFERENCES student_groups(group_id), " +
                                "FOREIGN KEY(student_id) REFERENCES students(student_id))");

                db.execSQL("INSERT INTO group_members (group_id, student_id) VALUES (1, 1), (2, 2),(1, 3),(1, 4),(2, 5),(2, 6),(3, 1),(3, 5),(4, 2),(4, 4)");

                // 🟢 Notice to Groups
                db.execSQL("CREATE TABLE notice_to_groups (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "notice_id INTEGER NOT NULL, " +
                                "group_id INTEGER NOT NULL, " +
                                "FOREIGN KEY(notice_id) REFERENCES notices(notice_id), " +
                                "FOREIGN KEY(group_id) REFERENCES student_groups(group_id))");

                db.execSQL("INSERT INTO notice_to_groups (notice_id, group_id) VALUES (2, 1)");

                // 🟢 Notice to All
                db.execSQL("CREATE TABLE notice_to_all (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "notice_id INTEGER NOT NULL, " +
                                "FOREIGN KEY(notice_id) REFERENCES notices(notice_id))");

                db.execSQL("INSERT INTO notice_to_all (notice_id) VALUES (2)");

                // 🟢 Queries Table (Students raise queries)
                db.execSQL("CREATE TABLE queries (" +
                                "query_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "student_id INTEGER NOT NULL, " +
                                "query_text TEXT NOT NULL, " +
                                "response_status TEXT NOT NULL, " +
                                "generated_at TEXT NOT NULL, " +
                                "FOREIGN KEY(student_id) REFERENCES students(student_id))");

                // Dummy queries from students
                db.execSQL("INSERT INTO queries (student_id, query_text, response_status, generated_at) VALUES " +
                                "(1, 'When is the science exam?', 'Pending', datetime('now'))," +
                                "(2, 'Can I change my section?', 'Pending', datetime('now'))," +
                                "(3, 'How to join the science club?', 'Responded', datetime('now'))");

                // 🟢 Query Responses Table (Admins respond to queries)
                db.execSQL("CREATE TABLE query_responses (" +
                                "response_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "query_id INTEGER NOT NULL, " +
                                "admin_id INTEGER NOT NULL, " +
                                "response_text TEXT NOT NULL, " +
                                "responded_at TEXT NOT NULL, " +
                                "FOREIGN KEY(query_id) REFERENCES queries(query_id), " +
                                "FOREIGN KEY(admin_id) REFERENCES admins(admin_id))");

                // Dummy responses from admin to query_id = 3
                db.execSQL("INSERT INTO query_responses (query_id, admin_id, response_text, responded_at) VALUES " +
                                "(3, 1, 'You can register for the science club via the notice board.', datetime('now'))");


                                // 🟢 Leave Applications Table
db.execSQL("CREATE TABLE leave_applications (" +
        "leave_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "student_id INTEGER NOT NULL, " +
        "from_date TEXT NOT NULL, " +
        "to_date TEXT NOT NULL, " +
        "reason TEXT NOT NULL, " +
        "status TEXT NOT NULL DEFAULT 'Pending', " + // values: Pending, Approved, Rejected
        "admin_id INTEGER, " + // set when responded
        "admin_response TEXT, " +
        "applied_at TEXT NOT NULL, " +
        "reviewed_at TEXT, " +
        "FOREIGN KEY(student_id) REFERENCES students(student_id), " +
                "FOREIGN KEY(admin_id) REFERENCES admins(admin_id))");
        
                db.execSQL("INSERT INTO leave_applications " +
        "(student_id, from_date, to_date, reason, status, applied_at) VALUES " +
        "(1, '2025-07-25', '2025-07-28', 'Family function', 'Pending', datetime('now'))");



                                // 🟢 Attendance Table
db.execSQL("CREATE TABLE attendance (" +
        "attendance_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "student_id INTEGER NOT NULL, " +
        "date TEXT NOT NULL, " + // Format: YYYY-MM-DD
        "status TEXT CHECK(status IN ('Present', 'Absent', 'Leave')) NOT NULL, " +
        "updated_by_admin_id INTEGER, " +
        "updated_at TEXT NOT NULL DEFAULT (datetime('now')), " +
        "FOREIGN KEY(student_id) REFERENCES students(student_id), " +
        "FOREIGN KEY(updated_by_admin_id) REFERENCES admins(admin_id), " +
        "UNIQUE(student_id, date))"); // To prevent duplicate entries for same date


// Dummy Attendance Records
db.execSQL("INSERT INTO attendance (student_id, date, status, updated_by_admin_id) VALUES " +
        "(1, '2025-07-20', 'Present', 1), " +
        "(1, '2025-07-21', 'Absent', 1), " +
        "(2, '2025-07-20', 'Leave', 1), " +
        "(2, '2025-07-21', 'Present', 2)");



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS query_responses");
                db.execSQL("DROP TABLE IF EXISTS queries");
                db.execSQL("DROP TABLE IF EXISTS notice_to_all");
                db.execSQL("DROP TABLE IF EXISTS notice_to_groups");
                db.execSQL("DROP TABLE IF EXISTS group_members");
                db.execSQL("DROP TABLE IF EXISTS student_groups");
                db.execSQL("DROP TABLE IF EXISTS notice_to_individuals");
                db.execSQL("DROP TABLE IF EXISTS notices");
                db.execSQL("DROP TABLE IF EXISTS sections");
                db.execSQL("DROP TABLE IF EXISTS classes");
                db.execSQL("DROP TABLE IF EXISTS admins");
                db.execSQL("DROP TABLE IF EXISTS students");
                db.execSQL("DROP TABLE IF EXISTS notice_to_classes");
                db.execSQL("DROP TABLE IF EXISTS notice_to_sections");
                db.execSQL("DROP TABLE IF EXISTS leave_applications");
                db.execSQL("DROP TABLE IF EXISTS attendance"); // Drop attendance records table if it exists
        
                onCreate(db);
        }

        public List<StudentModel> getStudentsBySection(int sectionId) {
                List<StudentModel> studentList = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM students WHERE section_id = ?",
                                new String[] { String.valueOf(sectionId) });

                if (cursor.moveToFirst()) {
                        do {
                                StudentModel student = new StudentModel();
                                student.setStudentId(cursor.getInt(cursor.getColumnIndex("student_id")));
                                student.setRollNo(cursor.getString(cursor.getColumnIndex("roll_no")));
                                student.setRegistrationNo(cursor.getString(cursor.getColumnIndex("registration_no")));
                                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                                student.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                                student.setPhoneNo(cursor.getString(cursor.getColumnIndex("phone_no")));
                                student.setFatherName(cursor.getString(cursor.getColumnIndex("fathername")));
                                student.setMotherName(cursor.getString(cursor.getColumnIndex("mothername")));
                                student.setDob(cursor.getString(cursor.getColumnIndex("dob")));
                                student.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                                student.setSectionId(cursor.getInt(cursor.getColumnIndex("section_id")));
                                studentList.add(student);
                        } while (cursor.moveToNext());
                }

                cursor.close();
                db.close();
                return studentList;
        }

        public boolean insertStudent(StudentModel student) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("roll_no", student.getRollNo());
                values.put("registration_no", student.getRegistrationNo());
                values.put("name", student.getName());
                values.put("email", student.getEmail());
                values.put("phone_no", student.getPhoneNo());
                values.put("fathername", student.getFatherName());
                values.put("mothername", student.getMotherName());
                values.put("dob", student.getDob());
                values.put("password", student.getPassword());
                values.put("created_at",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                values.put("section_id", student.getSectionId()); // assume it's already an int
                values.put("class_id", student.getClassId()); // ✅ Add class ID

                long result = db.insert("students", null, values);
                db.close();
                return result != -1;
        }

        public void updateStudent(StudentModel student) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("roll_no", student.getRollNo());
                values.put("registration_no", student.getRegistrationNo());
                values.put("name", student.getName());
                values.put("email", student.getEmail());
                values.put("phone_no", student.getPhoneNo());
                values.put("fathername", student.getFatherName());
                values.put("mothername", student.getMotherName());
                values.put("dob", student.getDob());
                values.put("password", student.getPassword());

                db.update("students", values, "student_id = ?",
                                new String[] { String.valueOf(student.getStudentId()) });
        }

        public void deleteStudent(int studentId) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.delete("students", "student_id = ?", new String[] { String.valueOf(studentId) });
        }

        public List<Notice> getNoticesForStudent(int studentId) {
                List<Notice> notices = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();

                String query = "SELECT DISTINCT n.notice_id, n.title, n.description, n.created_at " +
                                "FROM notices n " +
                                "LEFT JOIN notice_to_individuals ni ON n.notice_id = ni.notice_id " +
                                "LEFT JOIN students s ON s.student_id = ? " +
                                "LEFT JOIN notice_to_classes nc ON n.notice_id = nc.notice_id AND nc.class_id = s.class_id "
                                +
                                "LEFT JOIN notice_to_sections ns ON n.notice_id = ns.notice_id AND ns.section_id = s.section_id "
                                +
                                "LEFT JOIN group_members gm ON gm.student_id = s.student_id " +
                                "LEFT JOIN notice_to_groups ng ON n.notice_id = ng.notice_id AND ng.group_id = gm.group_id "
                                +
                                "LEFT JOIN notice_to_all na ON n.notice_id = na.notice_id " +
                                "WHERE ni.student_id = s.student_id OR " +
                                "      nc.class_id = s.class_id OR " +
                                "      ns.section_id = s.section_id OR " +
                                "      gm.student_id = s.student_id OR " +
                                "      na.notice_id IS NOT NULL";

                Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(studentId) });
                if (cursor.moveToFirst()) {
                        do {
                                int id = cursor.getInt(0);
                                String title = cursor.getString(1);
                                String desc = cursor.getString(2);
                                String date = cursor.getString(3);
                                notices.add(new Notice(id, title, desc, date));
                        } while (cursor.moveToNext());
                }
                cursor.close();
                return notices;
        }

 // 2.1 Admin: Fetch pending leave apps
public List<LeaveApplication> getPendingLeavesWithStudent() {
    List<LeaveApplication> list = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    String sql = "SELECT l.leave_id, l.student_id, s.name, s.roll_no, c.class_name, sec.section_name, " +
                 "l.from_date, l.to_date, l.reason, l.status, l.applied_at " +
                 "FROM leave_applications l " +
                 "JOIN students s ON l.student_id = s.student_id " +
                 "JOIN classes c ON s.class_id = c.class_id " +
                 "JOIN sections sec ON s.section_id = sec.section_id " +
                 "WHERE l.status = 'Pending' ORDER BY l.applied_at DESC";

    Cursor c = db.rawQuery(sql, null);
    while (c.moveToNext()) {
        list.add(new LeaveApplication(
            c.getInt(0),      // leave_id
            c.getInt(1),      // student_id
            c.getString(2),   // student_name
            c.getString(3),   // roll_no
            c.getString(4),   // class_name
            c.getString(5),   // section_name
            c.getString(6),   // from_date
            c.getString(7),   // to_date
            c.getString(8),   // reason
            c.getString(9),   // status
            c.getString(10)   // applied_at
        ));
    }
    c.close();
    return list;
}


// 2.2 Admin: Approve/Reject update
public boolean respondToLeave(int leaveId, int adminId, String responseText, String status) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put("admin_id", adminId);
    cv.put("admin_response", responseText);
    cv.put("status", status);
    cv.put("reviewed_at",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date())); // ensured via SQL
    int count = db.update("leave_applications", cv, "leave_id = ?", new String[]{String.valueOf(leaveId)});
    return count > 0;
}

// 2.3 Student: Submit leave
public boolean submitLeave(int studentId, String fromDate, String toDate, String reason) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put("student_id", studentId);
    cv.put("from_date", fromDate);
    cv.put("to_date", toDate);
    cv.put("reason", reason);
    cv.put("status", "Pending");
    cv.put("applied_at",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
    long id = db.insert("leave_applications", null, cv);
    return id != -1;
}

// 2.4 Student: Get all leave by student ordered with pending first
public List<LeaveApplication> getLeavesByStudent(int studentId) {
        List<LeaveApplication> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT l.leave_id, l.from_date, l.to_date, l.reason, l.status, l.applied_at, " +
                        "a.full_name, l.admin_response, l.reviewed_at " +
                        "FROM leave_applications l " +
                        "LEFT JOIN admins a ON l.admin_id = a.admin_id " +
                        "WHERE l.student_id = ? " +
                        "ORDER BY CASE WHEN l.status = 'Pending' THEN 0 ELSE 1 END, l.applied_at DESC";
        Cursor c = db.rawQuery(sql, new String[] { String.valueOf(studentId) });
        while (c.moveToNext()) {
                list.add(new LeaveApplication(
                                c.getInt(0),
                                c.getString(1),
                                c.getString(2),
                                c.getString(3),
                                c.getString(4),
                                c.getString(5),
                                c.getString(6),
                                c.getString(7),
                                c.getString(8)));
        }
        c.close();
        return list;
}

public List<LeaveApplication> getAllLeaveApplicationsWithStudentDetails() {
        List<LeaveApplication> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Join leave_applications with students, classes, and sections
        Cursor cursor = db.rawQuery(
                        "SELECT la.*, s.name AS student_name, s.roll_no, c.class_name, sec.section_name " +
                                        "FROM leave_applications la " +
                                        "JOIN students s ON la.student_id = s.student_id " +
                                        "JOIN classes c ON s.class_id = c.class_id " +
                                        "JOIN sections sec ON s.section_id = sec.section_id " +
                                        "ORDER BY la.applied_at DESC",
                        null);

        if (cursor.moveToFirst()) {
                do {
                        LeaveApplication la = new LeaveApplication(
                                        cursor.getInt(cursor.getColumnIndexOrThrow("leave_id")),
                                        cursor.getInt(cursor.getColumnIndexOrThrow("student_id")),
                                        cursor.getString(cursor.getColumnIndexOrThrow("from_date")),
                                        cursor.getString(cursor.getColumnIndexOrThrow("to_date")),
                                        cursor.getString(cursor.getColumnIndexOrThrow("reason")),
                                        cursor.getString(cursor.getColumnIndexOrThrow("status")),
                                        cursor.getInt(cursor.getColumnIndexOrThrow("admin_id")),
                                        cursor.getString(cursor.getColumnIndexOrThrow("admin_response")),
                                        cursor.getString(cursor.getColumnIndexOrThrow("applied_at")),
                                        cursor.getString(cursor.getColumnIndexOrThrow("reviewed_at")));

                        // ✅ Corrected column names
                        la.setStudentName(cursor.getString(cursor.getColumnIndexOrThrow("student_name")));
                        la.setRollNo(cursor.getString(cursor.getColumnIndexOrThrow("roll_no")));
                        la.setStudentClass(cursor.getString(cursor.getColumnIndexOrThrow("class_name")));
                        la.setSection(cursor.getString(cursor.getColumnIndexOrThrow("section_name")));

                        list.add(la);
                } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
}

public boolean markOrUpdateAttendance(int studentId, String date, String status, int adminId) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put("student_id", studentId);
    values.put("date", date);
    values.put("status", status);
    values.put("updated_by_admin_id", adminId);
    values.put("updated_at", getCurrentDateTime());

    // Check if attendance already exists for that student and date
    Cursor cursor = db.rawQuery("SELECT attendance_id FROM attendance WHERE student_id = ? AND date = ?",
            new String[]{String.valueOf(studentId), date});

    boolean success;
    if (cursor != null && cursor.moveToFirst()) {
        // Update existing record
        int attendanceId = cursor.getInt(0);
        success = db.update("attendance", values, "attendance_id = ?",
                new String[]{String.valueOf(attendanceId)}) > 0;
    } else {
        // Insert new record
        success = db.insert("attendance", null, values) != -1;
    }

    if (cursor != null)
        cursor.close();
    return success;
}


public List<Attendance> getAttendanceByStudent(int studentId) {
    List<Attendance> records = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.rawQuery(
            "SELECT attendance_id, student_id, date, status FROM attendance WHERE student_id = ? ORDER BY date ASC",
            new String[]{String.valueOf(studentId)});

    if (cursor != null) {
        while (cursor.moveToNext()) {
            int attendanceId = cursor.getInt(0);
            int sId = cursor.getInt(1);
            String date = cursor.getString(2);
            String status = cursor.getString(3);
            records.add(new Attendance(attendanceId, sId, date, status));
        }
        cursor.close();
    }

    return records;
}


private String getCurrentDateTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    return sdf.format(new Date());
}


}