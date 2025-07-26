// package com.example.stuadminlogin.activities;

// import android.app.Activity;
// import android.content.Intent;
// import android.content.SharedPreferences;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.util.Log;
// import android.view.View;
// import android.widget.*;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// public class LoginActivity extends Activity {
//     EditText username, password;
//     Button loginButton, viewDataButton;
//     RadioGroup roleGroup;
//     DatabaseHelper dbHelper;
//     SharedPreferences sharedPreferences;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_login);

//         username = findViewById(R.id.username);
//         password = findViewById(R.id.password);
//         loginButton = findViewById(R.id.loginButton);
//         viewDataButton = findViewById(R.id.viewDataButton);
//         roleGroup = findViewById(R.id.roleGroup);
//         dbHelper = new DatabaseHelper(this);
//         sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

//         viewDataButton.setOnClickListener(v -> {
//             startActivity(new Intent(LoginActivity.this, DatabaseViewerActivity.class));
//         });

//         loginButton.setOnClickListener(v -> {
//             int selectedId = roleGroup.getCheckedRadioButtonId();
//             String user = username.getText().toString();
//             String pass = password.getText().toString();

//             SQLiteDatabase db = dbHelper.getReadableDatabase();

//             if (selectedId == R.id.radio_admin) {
//                 Cursor cursorAdmin = db.rawQuery("SELECT * FROM admins WHERE username=? AND password=?", new String[]{user, pass});
//                 if (cursorAdmin.moveToFirst()) {
//                     int adminId = cursorAdmin.getInt(cursorAdmin.getColumnIndex("admin_id"));

//                     // Save to SharedPreferences
//                     SharedPreferences.Editor editor = sharedPreferences.edit();
//                     editor.putInt("admin_id", adminId);
//                     editor.apply();

//                     // Pass via intent too
//                     Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
//                     intent.putExtra("admin_id", adminId);
//                     startActivity(intent);
//                 } else {
//                     Toast.makeText(this, "Invalid Admin credentials", Toast.LENGTH_SHORT).show();
//                 }
//                 cursorAdmin.close();
//             } else if (selectedId == R.id.radio_student) {
//                 Cursor cursorStudent = db.rawQuery("SELECT * FROM students WHERE roll_no=? AND password=?", new String[]{user, pass});
//                 if (cursorStudent.moveToFirst()) {
//                     int studentId = cursorStudent.getInt(cursorStudent.getColumnIndex("student_id"));

//                     // Save to SharedPreferences
//                     SharedPreferences.Editor editor = sharedPreferences.edit();
//                     editor.putInt("student_id", studentId);
//                     editor.apply();

//                     // Pass via intent too
//                     Intent intent = new Intent(LoginActivity.this, StudentDashboardActivity.class);
//                     intent.putExtra("student_id", studentId);
//                     startActivity(intent);
//                 } else {
//                     Toast.makeText(this, "Invalid Student credentials", Toast.LENGTH_SHORT).show();
//                 }
//                 cursorStudent.close();
//             } else {
//                 Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
//             }

//             db.close();
//         });
//     }
// }



package com.example.stuadminlogin.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

public class LoginActivity extends Activity {
    EditText username, password;
    Button loginButton, viewDataButton;
    RadioGroup roleGroup;
    DatabaseHelper dbHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        viewDataButton = findViewById(R.id.viewDataButton);
        roleGroup = findViewById(R.id.roleGroup);
        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // 🔍 View Database Debug
        viewDataButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, DatabaseViewerActivity.class));
        });

        // 🔐 Login Button Logic
        loginButton.setOnClickListener(v -> {
    int selectedId = roleGroup.getCheckedRadioButtonId();
    String user = username.getText().toString().trim();
    String pass = password.getText().toString().trim();

    if (selectedId == -1) {
        Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
        return;
    }

    SQLiteDatabase db = dbHelper.getWritableDatabase(); // 🔄 Use writable since we'll update

    String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

    if (selectedId == R.id.radio_admin) {
        try (Cursor cursorAdmin = db.rawQuery("SELECT * FROM admins WHERE username=? AND password=?", new String[]{user, pass})) {
            if (cursorAdmin != null && cursorAdmin.moveToFirst()) {
                int adminId = cursorAdmin.getInt(cursorAdmin.getColumnIndexOrThrow("admin_id"));

                // ✅ Update last_login
                db.execSQL("UPDATE admins SET last_login=? WHERE admin_id=?", new Object[]{currentDateTime, adminId});

                // Save to SharedPreferences
                sharedPreferences.edit().putInt("admin_id", adminId).apply();

                // Start Admin Dashboard
                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                intent.putExtra("admin_id", adminId);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid Admin credentials", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("LoginError", "Admin login failed", e);
            Toast.makeText(this, "Admin login error!", Toast.LENGTH_SHORT).show();
        }

    } else if (selectedId == R.id.radio_student) {
        try (Cursor cursorStudent = db.rawQuery("SELECT * FROM students WHERE roll_no=? AND password=?", new String[]{user, pass})) {
            if (cursorStudent != null && cursorStudent.moveToFirst()) {
                int studentId = cursorStudent.getInt(cursorStudent.getColumnIndexOrThrow("student_id"));

                // ✅ Update last_login
                db.execSQL("UPDATE students SET last_login=? WHERE student_id=?", new Object[]{currentDateTime, studentId});

                // Save to SharedPreferences
                sharedPreferences.edit().putInt("student_id", studentId).apply();

                // Start Student Dashboard
                Intent intent = new Intent(LoginActivity.this, StudentDashboardActivity.class);
                intent.putExtra("student_id", studentId);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid Student credentials", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("LoginError", "Student login failed", e);
            Toast.makeText(this, "Student login error!", Toast.LENGTH_SHORT).show();
        }
    }

    db.close();
});

    }
}
