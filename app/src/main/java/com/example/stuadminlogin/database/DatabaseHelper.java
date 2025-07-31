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
import java.util.Map;
import java.util.HashMap;



import com.example.stuadminlogin.models.GroupModel;
import com.example.stuadminlogin.models.StudentModel;
import com.example.stuadminlogin.models.Notice;
import com.example.stuadminlogin.models.LeaveApplication;
import com.example.stuadminlogin.models.Attendance;
import com.example.stuadminlogin.models.Holiday;
import com.example.stuadminlogin.models.ParentModel; // Make sure to import your Parent model






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
                                "password TEXT NOT NULL, " +
                                "email_id TEXT, " + // Optional: for email ID
                                "phone_no TEXT, " + // Optional: for phone number
                                "address TEXT, " + // Optional: for address
                                "dob TEXT, " + // Optional: for date of birth
                                "date_of_joining TEXT, " + // Optional: for date of joining
                                "profile_photo_uri TEXT, " + // Optional: for profile photo
                                "last_login TEXT, " + // Optional: for last login timestamp
                                "created_at TEXT)");

                db.execSQL("INSERT INTO admins (username, full_name, password, email_id, phone_no, address, dob, date_of_joining, profile_photo_uri, last_login, created_at) "
                                +
                                "VALUES ('admin1', 'Admin One', 'admin123', 'admin1@example.com', '1234567890', 'Address 1', '1990-01-01', '2020-01-01', 'uri1', datetime('now'), datetime('now'))");
                db.execSQL("INSERT INTO admins (username, full_name, password, email_id, phone_no, address, dob, date_of_joining, profile_photo_uri, last_login, created_at) "
                                +
                                "VALUES ('admin2', 'Admin Two', 'admin456', 'admin2@example.com', '0987654321', 'Address 2', '1992-02-02', '2020-02-02', 'uri2', datetime('now'), datetime('now'))");

                // 🟢 Classes Table with class_code
db.execSQL("CREATE TABLE classes (" +
            "class_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "class_name TEXT NOT NULL, " +
            "class_code TEXT NOT NULL UNIQUE)");

// Dummy classes
db.execSQL("INSERT INTO classes (class_name, class_code) VALUES ('Class 10', 'C10')");
db.execSQL("INSERT INTO classes (class_name, class_code) VALUES ('Class 11', 'C11')");
db.execSQL("INSERT INTO classes (class_name, class_code) VALUES ('Class 12', 'C12')");

// 🟠 Sections Table (comes after classes since it references class_id)
db.execSQL("CREATE TABLE sections (" +
            "section_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "class_id INTEGER NOT NULL, " +
            "section_name TEXT NOT NULL, " +
            "FOREIGN KEY(class_id) REFERENCES classes(class_id))");

// Dummy sections
// Section 1: Class 10, Section A
db.execSQL("INSERT INTO sections (class_id, section_name) VALUES (1, 'A')");
// Section 2: Class 10, Section B
db.execSQL("INSERT INTO sections (class_id, section_name) VALUES (1, 'B')");
// Section 3: Class 11, Section A
db.execSQL("INSERT INTO sections (class_id, section_name) VALUES (2, 'A')");

                // 🔵 Students Table (comes after sections and classes since it references section_id and class_id)
// Added UNIQUE constraints for roll_no and registration_no
db.execSQL("CREATE TABLE students (" +
            "student_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "roll_no TEXT UNIQUE, " + // Added UNIQUE
            "registration_no TEXT UNIQUE, " + // Added UNIQUE
            "name TEXT, " +
            "email TEXT, " +
            "phone_no TEXT, " +
            "fathername TEXT, " +
            "mothername TEXT, " +
            "dob TEXT, " +
            "password TEXT NOT NULL, " +
            "address TEXT, " +
            "admission_date TEXT, " +
            "class_id INTEGER NOT NULL, " +
            "section_id INTEGER NOT NULL, " +
            "profile_photo_uri TEXT, " +
            "last_login TEXT, " +
            "created_at TEXT, " +
            "FOREIGN KEY(class_id) REFERENCES classes(class_id), " +
            "FOREIGN KEY(section_id) REFERENCES sections(section_id))");

// Dummy students with updated fathername/mothername to match parents
//John Doe (student_id 1, Class 10, Section A) - Parents: David Johnson, Alice Johnson
db.execSQL("INSERT INTO students " +
            "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, address, admission_date, class_id, section_id, profile_photo_uri, last_login) " +
            "VALUES ('001', 'REG123', 'John Doe', 'john@example.com', '9876543210', 'David Johnson', 'Alice Johnson', '2008-10-05', 'student123', datetime('now'), '123 Oak St', '2020-01-01', 1, 1, 'uri1', datetime('now'))");

// Jane Smith (student_id 2, Class 10, Section A) - Parents: David Johnson, Alice Johnson
db.execSQL("INSERT INTO students " +
            "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, address, admission_date, class_id, section_id, profile_photo_uri, last_login) " +
            "VALUES ('002', 'REG124', 'Jane Smith', 'jane@example.com', '9123456789', 'David Johnson', 'Alice Johnson', '2009-06-12', 'student456', datetime('now'), '123 Oak St', '2020-01-01', 1, 1, 'uri2', datetime('now'))");

// Amit Roy (student_id 3, Class 10, Section B) - Parents: Robert Williams, Brenda Williams
db.execSQL("INSERT INTO students " +
            "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, address, admission_date, class_id, section_id, profile_photo_uri, last_login) " +
            "VALUES ('003', 'REG125', 'Amit Roy', 'amit@example.com', '9123456701', 'Robert Williams', 'Brenda Williams', '2008-02-14', 'student789', datetime('now'), '456 Pine Ave', '2020-01-01', 1, 2, 'uri3', datetime('now'))");

// Sara Khan (student_id 4, Class 10, Section B) - Parents: Robert Williams, Brenda Williams
db.execSQL("INSERT INTO students " +
            "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, address, admission_date, class_id, section_id, profile_photo_uri, last_login) " +
            "VALUES ('004', 'REG126', 'Sara Khan', 'sara@example.com', '9123456702', 'Robert Williams', 'Brenda Williams', '2008-07-20', 'student101', datetime('now'), '456 Pine Ave', '2020-01-01', 1, 2, 'uri4', datetime('now'))");

// Ravi Verma (student_id 5, Class 11, Section A) - Parents: Michael Davis, Catherine Davis
db.execSQL("INSERT INTO students " +
            "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, address, admission_date, class_id, section_id, profile_photo_uri, last_login) " +
            "VALUES ('005', 'REG127', 'Ravi Verma', 'ravi@example.com', '9123456703', 'Michael Davis', 'Catherine Davis', '2009-03-08', 'student102', datetime('now'), '789 Elm St', '2020-01-01', 2, 3, 'uri5', datetime('now'))");

// Nisha Patel (student_id 6, Class 11, Section A) - Parents: Michael Davis, Catherine Davis
db.execSQL("INSERT INTO students " +
            "(roll_no, registration_no, name, email, phone_no, fathername, mothername, dob, password, created_at, address, admission_date, class_id, section_id, profile_photo_uri, last_login) " +
            "VALUES ('006', 'REG128', 'Nisha Patel', 'nisha@example.com', '9123456704', 'Michael Davis', 'Catherine Davis', '2009-11-18', 'student103', datetime('now'), '789 Elm St', '2020-01-01', 2, 3, 'uri6', datetime('now'))");

                                // 🔵 Parents Table
       // 🔵 Parents Table (Updated to include more specific names for surname matching)
db.execSQL("CREATE TABLE parents (" +
            "parent_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "email TEXT UNIQUE NOT NULL, " +
            "password TEXT NOT NULL, " +
            "name TEXT, " +
            "phone_no TEXT, " +
            "created_at TEXT, " +
            "last_login TEXT, " +
            "profile_photo_uri TEXT)");

// Dummy parents (passwords should be hashed in a real application)
// Family 1: Johnson family (Parents of John Doe, Jane Smith)
db.execSQL("INSERT INTO parents (email, password, name, phone_no, created_at, last_login, profile_photo_uri) VALUES ('david.johnson@example.com', 'parent123', 'David Johnson', '9988776655', datetime('now'), datetime('now'), NULL)"); // parent_id 1
db.execSQL("INSERT INTO parents (email, password, name, phone_no, created_at, last_login, profile_photo_uri) VALUES ('alice.johnson@example.com', 'parent123', 'Alice Johnson', '9988776656', datetime('now'), datetime('now'), NULL)"); // parent_id 2

// Family 2: Williams family (Parents of Amit Roy, Sara Khan)
db.execSQL("INSERT INTO parents (email, password, name, phone_no, created_at, last_login, profile_photo_uri) VALUES ('robert.williams@example.com', 'parent456', 'Robert Williams', '9988776644', datetime('now'), datetime('now'), NULL)"); // parent_id 3
db.execSQL("INSERT INTO parents (email, password, name, phone_no, created_at, last_login, profile_photo_uri) VALUES ('brenda.williams@example.com', 'parent456', 'Brenda Williams', '9988776645', datetime('now'), datetime('now'), NULL)"); // parent_id 4

// Family 3: Davis family (Parents of Ravi Verma, Nisha Patel)
db.execSQL("INSERT INTO parents (email, password, name, phone_no, created_at, last_login, profile_photo_uri) VALUES ('michael.davis@example.com', 'parent789', 'Michael Davis', '9988776633', datetime('now'), datetime('now'), NULL)"); // parent_id 5
db.execSQL("INSERT INTO parents (email, password, name, phone_no, created_at, last_login, profile_photo_uri) VALUES ('catherine.davis@example.com', 'parent789', 'Catherine Davis', '9988776634', datetime('now'), datetime('now'), NULL)"); // parent_id 6


        // 🔵 Parent-Student Linking Table
db.execSQL("CREATE TABLE parent_student_link (" +
            "link_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "parent_id INTEGER NOT NULL, " +
            "student_id INTEGER NOT NULL, " +
            "FOREIGN KEY(parent_id) REFERENCES parents(parent_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(student_id) REFERENCES students(student_id) ON DELETE CASCADE, " +
            "UNIQUE(parent_id, student_id))");

// Dummy parent-student links
// John Doe (student_id 1) and Jane Smith (student_id 2) are children of David Johnson (parent_id 1) and Alice Johnson (parent_id 2)
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (1, 1)"); // David Johnson -> John Doe
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (2, 1)"); // Alice Johnson -> John Doe
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (1, 2)"); // David Johnson -> Jane Smith
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (2, 2)"); // Alice Johnson -> Jane Smith

// Amit Roy (student_id 3) and Sara Khan (student_id 4) are children of Robert Williams (parent_id 3) and Brenda Williams (parent_id 4)
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (3, 3)"); // Robert Williams -> Amit Roy
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (4, 3)"); // Brenda Williams -> Amit Roy
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (3, 4)"); // Robert Williams -> Sara Khan
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (4, 4)"); // Brenda Williams -> Sara Khan

// Ravi Verma (student_id 5) and Nisha Patel (student_id 6) are children of Michael Davis (parent_id 5) and Catherine Davis (parent_id 6)
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (5, 5)"); // Michael Davis -> Ravi Verma
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (6, 5)"); // Catherine Davis -> Ravi Verma
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (5, 6)"); // Michael Davis -> Nisha Patel
db.execSQL("INSERT INTO parent_student_link (parent_id, student_id) VALUES (6, 6)"); // Catherine Davis -> Nisha Patel


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

                // ** 🟢 Notice to Parents **
db.execSQL("CREATE TABLE notice_to_parents (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "notice_id INTEGER NOT NULL, " +
        "parent_id INTEGER NOT NULL, " +
        "FOREIGN KEY(notice_id) REFERENCES notices(notice_id), " +
        "FOREIGN KEY(parent_id) REFERENCES parents(parent_id))");

// Dummy data for notice_to_parents
// Assuming notice_id 1 is 'Holiday Announcement'
// Assuming parent_id 1 is 'Alice Johnson'
// Assuming parent_id 2 is 'Bob Williams'
db.execSQL("INSERT INTO notice_to_parents (notice_id, parent_id) VALUES (1, 1)");
db.execSQL("INSERT INTO notice_to_parents (notice_id, parent_id) VALUES (2, 2)");
db.execSQL("INSERT INTO notice_to_parents (notice_id, parent_id) VALUES (1, 3)"); // Example: send holiday notice to parent 3 as well

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
                                "group_name TEXT NOT NULL UNIQUE)");

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
                // Inside DatabaseHelper.java, in the onCreate method:

// Inside DatabaseHelper.java, in the onCreate method:

// 🟢 Queries Table (Students/Parents raise queries)
db.execSQL("CREATE TABLE queries (" +
                "query_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_id INTEGER, " + // This is for direct student queries
                "parent_id INTEGER, " +  // This is for parent queries
                "linked_student_id INTEGER, " + // NEW: To link parent query to a specific child
                "query_text TEXT NOT NULL, " +
                "response_status TEXT NOT NULL, " +
                "generated_at TEXT NOT NULL, " +
                "FOREIGN KEY(student_id) REFERENCES students(student_id), " +
                "FOREIGN KEY(parent_id) REFERENCES parents(parent_id), " +
                "FOREIGN KEY(linked_student_id) REFERENCES students(student_id))"); // NEW FK constraint

// Update your dummy data inserts for queries to include linked_student_id:
// For student queries: linked_student_id should be NULL
// For parent queries: linked_student_id can be NULL (general query) or a student_id (query about a specific child)
db.execSQL("INSERT INTO queries (student_id, parent_id, linked_student_id, query_text, response_status, generated_at) VALUES " +
                "(1, NULL, NULL, 'When is the science exam?', 'Pending', datetime('now'))," +
                "(2, NULL, NULL, 'Can I change my section?', 'Pending', datetime('now'))," +
                "(NULL, 1, 1, 'Query about John Does progress report.', 'Pending', datetime('now'))," + // Parent 1 about Student 1
                "(NULL, 1, 2, 'Query about Jane Smiths attendance.', 'Pending', datetime('now'))," + // Parent 1 about Student 2
                "(NULL, 2, NULL, 'General query about school events.', 'Pending', datetime('now'))," + // Parent 2 general query
                "(3, NULL, NULL, 'How to join the science club?', 'Responded', datetime('now'))");

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

                db.execSQL("CREATE TABLE holidays (" +
                                "holiday_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "title TEXT NOT NULL, " + // e.g. "Independence Day"
                                "description TEXT, " + // e.g. "Celebration of Indian Independence"
                                "holiday_date TEXT NOT NULL, " + // Stored in YYYY-MM-DD format
                                "created_by_admin_id INTEGER NOT NULL, " + // Foreign key for admin
                                "created_at TEXT NOT NULL DEFAULT (datetime('now')), " +
                                "FOREIGN KEY(created_by_admin_id) REFERENCES admins(admin_id))");

                db.execSQL("INSERT INTO holidays (title, description, holiday_date, created_by_admin_id) VALUES " +
                                "('Independence Day', 'Celebration of Indian Independence', '2025-08-15', 1), " +
                                "('Gandhi Jayanti', 'Birthday of Mahatma Gandhi', '2025-10-02', 2), " +
                                "('Christmas', 'Christmas Day holiday', '2025-12-25', 1)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS query_responses");
                db.execSQL("DROP TABLE IF EXISTS queries");
                db.execSQL("DROP TABLE IF EXISTS notice_to_all");
                db.execSQL("DROP TABLE IF EXISTS notice_to_groups");
                db.execSQL("DROP TABLE IF EXISTS group_members");
                db.execSQL("DROP TABLE IF EXISTS student_groups");
                db.execSQL("DROP TABLE IF EXISTS notice_to_parents");
                db.execSQL("DROP TABLE IF EXISTS notice_to_individuals");
                db.execSQL("DROP TABLE IF EXISTS notices");
                db.execSQL("DROP TABLE IF EXISTS sections");
                db.execSQL("DROP TABLE IF EXISTS classes");
                db.execSQL("DROP TABLE IF EXISTS admins");
                db.execSQL("DROP TABLE IF EXISTS students");
                db.execSQL("DROP TABLE IF EXISTS parent_student_link"); // New table to drop
                db.execSQL("DROP TABLE IF EXISTS parents"); 
                db.execSQL("DROP TABLE IF EXISTS notice_to_classes");
                db.execSQL("DROP TABLE IF EXISTS notice_to_sections");
                db.execSQL("DROP TABLE IF EXISTS leave_applications");
                db.execSQL("DROP TABLE IF EXISTS attendance"); // Drop attendance records table if it exists
                db.execSQL("DROP TABLE IF EXISTS holidays"); // Drop holidays table if it exists

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
                                student.setAddress(cursor.getString(cursor.getColumnIndex("address"))); // ✅
                                student.setAdmissionDate(cursor.getString(cursor.getColumnIndex("admission_date"))); // ✅
                                student.setProfilePhotoUri(
                                                cursor.getString(cursor.getColumnIndex("profile_photo_uri"))); // ✅
                                student.setLastLogin(cursor.getString(cursor.getColumnIndex("last_login"))); // ✅
                                student.setSectionId(cursor.getInt(cursor.getColumnIndex("section_id")));
                                student.setClassId(cursor.getInt(cursor.getColumnIndex("class_id"))); // ✅ Add class ID

                                studentList.add(student);

                        } while (cursor.moveToNext());
                }

                cursor.close();
                db.close();
                return studentList;
        }


        // Inside DatabaseHelper.java

public String getClassNameById(int classId) {
    SQLiteDatabase db = this.getReadableDatabase();
    String className = null;
    Cursor cursor = db.query("classes", new String[]{"class_name"},
            "class_id = ?", new String[]{String.valueOf(classId)},
            null, null, null);
    if (cursor != null) {
        if (cursor.moveToFirst()) {
            className = cursor.getString(cursor.getColumnIndexOrThrow("class_name"));
        }
        cursor.close();
    }
    db.close();
    return className;
}

public String getSectionNameById(int sectionId) {
    SQLiteDatabase db = this.getReadableDatabase();
    String sectionName = null;
    Cursor cursor = db.query("sections", new String[]{"section_name"},
            "section_id = ?", new String[]{String.valueOf(sectionId)},
            null, null, null);
    if (cursor != null) {
        if (cursor.moveToFirst()) {
            sectionName = cursor.getString(cursor.getColumnIndexOrThrow("section_name"));
        }
        cursor.close();
    }
    db.close();
    return sectionName;
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
                values.put("address", student.getAddress()); // ✅
                values.put("admission_date", student.getAdmissionDate()); // ✅
                values.put("mothername", student.getMotherName());
                values.put("dob", student.getDob());
                values.put("password", student.getPassword());
                values.put("profile_photo_uri", student.getProfilePhotoUri() != null ? student.getProfilePhotoUri() : ""); // ✅
                values.put("last_login", student.getLastLogin() != null ? student.getLastLogin() : "");

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
                                        c.getInt(0), // leave_id
                                        c.getInt(1), // student_id
                                        c.getString(2), // student_name
                                        c.getString(3), // roll_no
                                        c.getString(4), // class_name
                                        c.getString(5), // section_name
                                        c.getString(6), // from_date
                                        c.getString(7), // to_date
                                        c.getString(8), // reason
                                        c.getString(9), // status
                                        c.getString(10) // applied_at
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
                cv.put("reviewed_at",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date())); // ensured via SQL
                int count = db.update("leave_applications", cv, "leave_id = ?",
                                new String[] { String.valueOf(leaveId) });
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
                cv.put("applied_at",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
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
                                new String[] { String.valueOf(studentId), date });

                boolean success;
                if (cursor != null && cursor.moveToFirst()) {
                        // Update existing record
                        int attendanceId = cursor.getInt(0);
                        success = db.update("attendance", values, "attendance_id = ?",
                                        new String[] { String.valueOf(attendanceId) }) > 0;
                } else {
                        // Insert new record
                        success = db.insert("attendance", null, values) != -1;
                }

                if (cursor != null)
                        cursor.close();
                return success;
        }

        public boolean insertOrUpdateAttendance(int studentId, String date, String status, Integer adminId) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put("student_id", studentId);
    values.put("date", date);
    values.put("status", status);
    values.put("updated_by_admin_id", adminId);
    values.put("updated_at", getCurrentDateTime());

    boolean success;

    // Check if attendance already exists
    Cursor cursor = db.rawQuery("SELECT attendance_id FROM attendance WHERE student_id = ? AND date = ?",
            new String[]{String.valueOf(studentId), date});

    if (cursor.moveToFirst()) {
        // Update existing record
        int attendanceId = cursor.getInt(0);
        success = db.update("attendance", values, "attendance_id = ?", new String[]{String.valueOf(attendanceId)}) > 0;
    } else {
        // Insert new record
        success = db.insert("attendance", null, values) != -1;
    }

    cursor.close();
    db.close(); // ✅ important!
    return success;
}


public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                        "SELECT attendance_id, student_id, date, status FROM attendance WHERE student_id = ? ORDER BY date ASC",
                        new String[] { String.valueOf(studentId) });

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
        
public Map<Integer, String> getAttendanceForDate(String date) {
    Map<Integer, String> map = new HashMap<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT student_id, status FROM attendance WHERE date = ?", new String[]{date});
    if (cursor.moveToFirst()) {
        do {
            int studentId = cursor.getInt(0);
            String status = cursor.getString(1);
            map.put(studentId, status);
        } while (cursor.moveToNext());
    }
    cursor.close();
    return map;
}




        private String getCurrentDateTime() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                return sdf.format(new Date());
        }

        public boolean insertHoliday(String title, String description, String date, int adminId) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("description", description);
                values.put("holiday_date", date);
                values.put("created_by_admin_id", adminId);
                values.put("created_at",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));

                long result = db.insert("holidays", null, values);
                return result != -1;
        }

        public boolean updateHoliday(int id, String title, String description, String date) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("description", description);
                values.put("holiday_date", date);

                int result = db.update("holidays", values, "holiday_id=?", new String[] { String.valueOf(id) });
                return result > 0;
        }

        public boolean deleteHoliday(int id) {
                SQLiteDatabase db = this.getWritableDatabase();
                int result = db.delete("holidays", "holiday_id=?", new String[] { String.valueOf(id) });
                return result > 0;
        }

        public List<Holiday> getAllHolidays() {
                List<Holiday> list = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM holidays ORDER BY holiday_date ASC", null);

                while (c.moveToNext()) {
                        int id = c.getInt(c.getColumnIndexOrThrow("holiday_id"));
                        String title = c.getString(c.getColumnIndexOrThrow("title"));
                        String desc = c.getString(c.getColumnIndexOrThrow("description"));
                        String date = c.getString(c.getColumnIndexOrThrow("holiday_date"));
                        String createdAt = c.getString(c.getColumnIndexOrThrow("created_at"));

                        list.add(new Holiday(id, title, desc, date, createdAt, ""));
                        ;
                }

                c.close();
                return list;
        }

        // Inside DatabaseHelper.java
// Group related methods
public long createGroup(String groupName) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("group_name", groupName);
    long id = db.insert("student_groups", null, values);
    db.close();
    return id;
}

public List<GroupModel> getAllGroupsWithMembers() {
    List<GroupModel> groups = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    
    // Get all groups
    Cursor groupCursor = db.query("student_groups", 
            new String[]{"group_id", "group_name"}, 
            null, null, null, null, null);
    
    if (groupCursor.moveToFirst()) {
        do {
            GroupModel group = new GroupModel();
            group.setGroupId(groupCursor.getInt(0));
            group.setGroupName(groupCursor.getString(1));
            
            // Get members for this group
            List<StudentModel> members = getGroupMembers(group.getGroupId());
            group.setMembers(members);
            
            groups.add(group);
        } while (groupCursor.moveToNext());
    }
    groupCursor.close();
    db.close();
    return groups;
}

public GroupModel getGroupWithMembers(int groupId) {
    SQLiteDatabase db = this.getReadableDatabase();
    GroupModel group = null;
    
    Cursor groupCursor = db.query("student_groups", 
            new String[]{"group_id", "group_name"}, 
            "group_id=?", new String[]{String.valueOf(groupId)}, 
            null, null, null);
    
    if (groupCursor.moveToFirst()) {
        group = new GroupModel();
        group.setGroupId(groupCursor.getInt(0));
        group.setGroupName(groupCursor.getString(1));
        
        // Get members for this group
        List<StudentModel> members = getGroupMembers(groupId);
        group.setMembers(members);
    }
    groupCursor.close();
    db.close();
    return group;
}

private List<StudentModel> getGroupMembers(int groupId) {
    List<StudentModel> members = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    
    String query = "SELECT s.student_id, s.roll_no, s.registration_no, s.name, " +
            "s.email, s.phone_no, s.fathername, s.mothername, s.dob, s.password, " +
            "s.created_at FROM students s INNER JOIN group_members gm ON " +
            "s.student_id = gm.student_id WHERE gm.group_id = ?";
    
    Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(groupId)});
    
    if (cursor.moveToFirst()) {
        do {
            StudentModel student = new StudentModel();
            student.setStudentId(cursor.getInt(0));
            student.setRollNo(cursor.getString(1));
            student.setRegistrationNo(cursor.getString(2));
            student.setName(cursor.getString(3));
            student.setEmail(cursor.getString(4));
            student.setPhoneNo(cursor.getString(5));
            student.setFatherName(cursor.getString(6));
            student.setMotherName(cursor.getString(7));
            student.setDob(cursor.getString(8));
            student.setPassword(cursor.getString(9));
            student.setCreatedAt(cursor.getString(10));
            
            members.add(student);
        } while (cursor.moveToNext());
    }
    cursor.close();
    return members;
}

public int updateGroup(GroupModel group) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("group_name", group.getGroupName());
    
    int rowsAffected = db.update("student_groups", values, 
            "group_id=?", new String[]{String.valueOf(group.getGroupId())});
    db.close();
    return rowsAffected;
}

public int deleteGroup(int groupId) {
    SQLiteDatabase db = this.getWritableDatabase();
    
    // First delete all members from this group
    db.delete("group_members", "group_id=?", new String[]{String.valueOf(groupId)});
    
    // Then delete the group
    int rowsAffected = db.delete("student_groups", "group_id=?", new String[]{String.valueOf(groupId)});
    db.close();
    return rowsAffected;
}

public long addStudentToGroup(int groupId, int studentId) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("group_id", groupId);
    values.put("student_id", studentId);
    
    // Check if student is already in the group
    Cursor cursor = db.query("group_members", 
            new String[]{"id"}, 
            "group_id=? AND student_id=?", 
            new String[]{String.valueOf(groupId), String.valueOf(studentId)}, 
            null, null, null);
    
    if (cursor.getCount() > 0) {
        cursor.close();
        db.close();
        return -1; // Already exists
    }
    cursor.close();
    
    long id = db.insert("group_members", null, values);
    db.close();
    return id;
}

public int removeStudentFromGroup(int groupId, int studentId) {
    SQLiteDatabase db = this.getWritableDatabase();
    int rowsAffected = db.delete("group_members", 
            "group_id=? AND student_id=?", 
            new String[]{String.valueOf(groupId), String.valueOf(studentId)});
    db.close();
    return rowsAffected;
}

public List<StudentModel> getAllStudents() {
        List<StudentModel> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("students", null, null, null, null, null, "name ASC");

        if (cursor.moveToFirst()) {
                do {
                        StudentModel student = new StudentModel();
                        student.setStudentId(cursor.getInt(0));
                        student.setRollNo(cursor.getString(1));
                        student.setRegistrationNo(cursor.getString(2));
                        student.setName(cursor.getString(3));
                        student.setEmail(cursor.getString(4));
                        student.setPhoneNo(cursor.getString(5));
                        student.setFatherName(cursor.getString(6));
                        student.setMotherName(cursor.getString(7));
                        student.setDob(cursor.getString(8));
                        student.setPassword(cursor.getString(9));
                        student.setAddress(cursor.getString(10));
                        student.setAdmissionDate(cursor.getString(11));
                        student.setClassId(cursor.getInt(12));
                        student.setSectionId(cursor.getInt(13));
                        students.add(student);
                } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
}

// --- Parent Management Methods ---

    /**
     * Adds a new parent to the database.
     *
     * @param parent The ParentModel object containing details.
     * @return The row ID of the newly inserted parent, or -1 if an error occurred.
     */
    public long addParent(ParentModel parent) { // Changed Parent to ParentModel
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", parent.getEmail());
        values.put("password", parent.getPassword());
        values.put("name", parent.getName());
        values.put("phone_no", parent.getPhoneNo());
        values.put("created_at", getCurrentDateTime());
        values.put("profile_photo_uri", parent.getProfilePhotoUri()); // Add this field

        long id = db.insert("parents", null, values);
        db.close();
        return id;
    }

    /**
     * Retrieves a parent by their ID.
     *
     * @param parentId The ID of the parent to retrieve.
     * @return The ParentModel object if found, otherwise null.
     */
    public ParentModel getParentById(int parentId) { // Changed Parent to ParentModel
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("parents",
                new String[]{"parent_id", "email", "password", "name", "phone_no", "created_at", "last_login", "profile_photo_uri"},
                "parent_id" + "=?",
                new String[]{String.valueOf(parentId)},
                null, null, null, null);

        ParentModel parent = null; // Changed Parent to ParentModel
        if (cursor != null && cursor.moveToFirst()) {
            parent = new ParentModel( // Changed Parent to ParentModel
                    cursor.getInt(cursor.getColumnIndexOrThrow("parent_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phone_no")),
                    cursor.getString(cursor.getColumnIndexOrThrow("created_at")),
                    cursor.getString(cursor.getColumnIndexOrThrow("last_login")),
                    cursor.getString(cursor.getColumnIndexOrThrow("profile_photo_uri"))
            );
            cursor.close();
        }
        db.close();
        return parent;
    }

    /**
     * Retrieves a parent's ID by their email address.
     *
     * @param email The email address of the parent.
     * @return The parent_id if found, or -1 if not found.
     */
    public long getParentIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("parents",
                new String[]{"parent_id"},
                "email" + "=?",
                new String[]{email},
                null, null, null);

        long parentId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            parentId = cursor.getInt(cursor.getColumnIndexOrThrow("parent_id"));
            cursor.close();
        }
        db.close();
        return parentId;
    }

    /**
     * Updates an existing parent's details.
     *
     * @param parent The ParentModel object with updated details.
     * @param updatePassword True if the password should be updated, false otherwise.
     * @return True if the update was successful, false otherwise.
     */
    public boolean updateParent(ParentModel parent, boolean updatePassword) { // Changed Parent to ParentModel
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", parent.getName());
        values.put("email", parent.getEmail());
        values.put("phone_no", parent.getPhoneNo());
        values.put("profile_photo_uri", parent.getProfilePhotoUri()); // Add this field
        if (updatePassword && parent.getPassword() != null && !parent.getPassword().isEmpty()) {
            values.put("password", parent.getPassword());
        }

        int rowsAffected = db.update("parents", values, "parent_id" + " = ?",
                new String[]{String.valueOf(parent.getParentId())});
        db.close();
        return rowsAffected > 0;
    }

    /**
     * Links a parent to a student in the parent_student_link table.
     *
     * @param parentId The ID of the parent.
     * @param studentId The ID of the student.
     * @return True if the link was successfully created, false otherwise (e.g., if link already exists).
     */
    public boolean linkParentToStudent(int parentId, int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("parent_id", parentId);
        values.put("student_id", studentId);

        // Check if the link already exists to avoid duplicate entries
        Cursor cursor = db.query("parent_student_link",
                new String[]{"link_id"},
                "parent_id" + " = ? AND " + "student_id" + " = ?",
                new String[]{String.valueOf(parentId), String.valueOf(studentId)},
                null, null, null);

        boolean linkExists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }

        if (!linkExists) {
            long id = db.insert("parent_student_link", null, values);
            db.close();
            return id != -1;
        } else {
            db.close();
            return false; // Link already exists
        }
    }

    /**
     * Retrieves all parents linked to a specific student.
     *
     * @param studentId The ID of the student.
     * @return A list of ParentModel objects associated with the student.
     */
    public List<ParentModel> getParentsByStudentId(int studentId) { // Changed Parent to ParentModel
        List<ParentModel> parentsList = new ArrayList<>(); // Changed Parent to ParentModel
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT P.parent_id, P.email, P.password, P.name, P.phone_no, P.created_at, P.last_login, P.profile_photo_uri"
                + " FROM parents P"
                + " INNER JOIN parent_student_link PSL ON P.parent_id = PSL.parent_id"
                + " WHERE PSL.student_id = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(studentId)});

        if (cursor.moveToFirst()) {
            do {
                ParentModel parent = new ParentModel( // Changed Parent to ParentModel
                        cursor.getInt(cursor.getColumnIndexOrThrow("parent_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phone_no")),
                        cursor.getString(cursor.getColumnIndexOrThrow("created_at")),
                        cursor.getString(cursor.getColumnIndexOrThrow("last_login")),
                        cursor.getString(cursor.getColumnIndexOrThrow("profile_photo_uri"))
                );
                parentsList.add(parent);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return parentsList;
    }

    /**
     * Removes the link between a specific parent and a student.
     * This does NOT delete the parent's record from the parents table.
     *
     * @param parentId The ID of the parent.
     * @param studentId The ID of the student.
     * @return True if the link was successfully removed, false otherwise.
     */
    public boolean removeParentStudentLink(int parentId, int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("parent_student_link",
                "parent_id" + " = ? AND " + "student_id" + " = ?",
                new String[]{String.valueOf(parentId), String.valueOf(studentId)});
        db.close();
        return rowsAffected > 0;
    }

    /**
     * Deletes a parent completely from the database.
     * Use with caution, as this will also remove all links to students due to ON DELETE CASCADE.
     *
     * @param parentId The ID of the parent to delete.
     * @return True if the parent was successfully deleted, false otherwise.
     */
    public boolean deleteParent(int parentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("parents", "parent_id" + " = ?",
                new String[]{String.valueOf(parentId)});
        db.close();
        return rowsAffected > 0;
    }
}

