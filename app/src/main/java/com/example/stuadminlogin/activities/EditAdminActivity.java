// // === activities/EditAdminActivity.java ===
// package com.example.stuadminlogin.activities;

// import android.content.ContentValues;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.view.View;
// import android.widget.*;
// import androidx.annotation.Nullable;
// import androidx.appcompat.app.AlertDialog;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
// import androidx.fragment.app.DialogFragment;
// import java.text.SimpleDateFormat;
// import java.util.*;



// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.AdminAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.Admin;

// import java.util.ArrayList;
// import java.util.List;

// public class EditAdminActivity extends AppCompatActivity implements AdminAdapter.OnAdminActionListener, FilterDialogFragment.FilterDialogListener {

//     private RecyclerView recyclerView;
//     private Button btnSearch, btnAddAdmin;
//     private AdminAdapter adapter;
//     private List<Admin> adminList;
//     private DatabaseHelper dbHelper;

//     @Override
//     protected void onCreate(@Nullable Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_edit_admin);

//         recyclerView = findViewById(R.id.recyclerViewAdmins);
//         btnSearch = findViewById(R.id.btnSearch);
//         btnAddAdmin = findViewById(R.id.btnAddAdmin);

//         dbHelper = new DatabaseHelper(this);
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));

//         loadAdmins(null, null);

//         btnSearch.setOnClickListener(v -> {
//             DialogFragment filterDialog = new FilterDialogFragment();
//             filterDialog.show(getSupportFragmentManager(), "filter_dialog");
//         });

//         btnAddAdmin.setOnClickListener(v -> showAdminForm(null));
//     }

//     private void loadAdmins(@Nullable String column, @Nullable String keyword) {
//         adminList = new ArrayList<>();
//         SQLiteDatabase db = dbHelper.getReadableDatabase();
//         String query = "SELECT * FROM admins";
//         String[] args = null;

//         if (column != null && keyword != null && !keyword.isEmpty()) {
//             query += " WHERE " + column + " LIKE ?";
//             args = new String[]{"%" + keyword + "%"};
//         }

//         Cursor cursor = db.rawQuery(query, args);
//         if (cursor.moveToFirst()) {
//             do {
//                 Admin admin = new Admin(
//     cursor.getInt(cursor.getColumnIndexOrThrow("admin_id")),
//     cursor.getString(cursor.getColumnIndexOrThrow("username")),
//     cursor.getString(cursor.getColumnIndexOrThrow("full_name")),
//     cursor.getString(cursor.getColumnIndexOrThrow("password")),
//     cursor.getString(cursor.getColumnIndexOrThrow("created_at")),
//     cursor.getString(cursor.getColumnIndexOrThrow("email_id")),
//     cursor.getString(cursor.getColumnIndexOrThrow("phone_no")),
//     cursor.getString(cursor.getColumnIndexOrThrow("address")),
//     cursor.getString(cursor.getColumnIndexOrThrow("dob")),
//     cursor.getString(cursor.getColumnIndexOrThrow("date_of_joining")),
//     cursor.getString(cursor.getColumnIndexOrThrow("profile_photo_uri")),
//     cursor.getString(cursor.getColumnIndexOrThrow("last_login"))
// );

//                 adminList.add(admin);
//             } while (cursor.moveToNext());
//         }
//         cursor.close();

//         adapter = new AdminAdapter(adminList, this);
//         recyclerView.setAdapter(adapter);
//     }

//     @Override
//     public void onEdit(Admin admin) {
//         showAdminForm(admin);
//     }

//     @Override
//     public void onDelete(Admin admin) {
//         new AlertDialog.Builder(this)
//                 .setTitle("Delete Admin")
//                 .setMessage("Are you sure?")
//                 .setPositiveButton("Yes", (d, w) -> {
//                     SQLiteDatabase db = dbHelper.getWritableDatabase();
//                     db.delete("admins", "admin_id=?", new String[]{String.valueOf(admin.getAdminId())});
//                     loadAdmins(null, null);
//                 })
//                 .setNegativeButton("Cancel", null)
//                 .show();
//     }

//     private void showAdminForm(@Nullable Admin admin) {
//         View dialogView = getLayoutInflater().inflate(R.layout.dialog_admin_form, null);
//         EditText etUsername = dialogView.findViewById(R.id.etUsername);
//         EditText etFullName = dialogView.findViewById(R.id.etFullName);
//         EditText etPassword = dialogView.findViewById(R.id.etPassword);
//         EditText etEmailId = dialogView.findViewById(R.id.etEmailId);
// EditText etPhoneNo = dialogView.findViewById(R.id.etPhoneNo);
// EditText etAddress = dialogView.findViewById(R.id.etAddress);
// EditText etDob = dialogView.findViewById(R.id.etDob);
// EditText etDateOfJoining = dialogView.findViewById(R.id.etDateOfJoining);
// //EditText etProfilePhotoUri = dialogView.findViewById(R.id.etProfilePhotoUri);
// //EditText etLastLogin = dialogView.findViewById(R.id.etLastLogin); // Optional

//         String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

//         if (admin != null) {
//             etUsername.setText(admin.getUsername());
//             etFullName.setText(admin.getFullName());
//             etPassword.setText(admin.getPassword());
//             etEmailId.setText(admin.getEmailId());
//             etPhoneNo.setText(admin.getPhoneNo());
//             etAddress.setText(admin.getAddress());
//             etDob.setText(admin.getDob());
//             etDateOfJoining.setText(admin.getDateOfJoining());
//     //etProfilePhotoUri.setText(admin.getProfilePhotoUri());
//         }

//         new AlertDialog.Builder(this)
//                 .setTitle(admin == null ? "Add Admin" : "Edit Admin")
//                 .setView(dialogView)
//                 .setPositiveButton("Save", (dialog, which) -> {
//                     ContentValues values = new ContentValues();
//                     values.put("username", etUsername.getText().toString().trim());
//                     values.put("full_name", etFullName.getText().toString());
//                     values.put("username", etUsername.getText().toString());
// values.put("full_name", etFullName.getText().toString());
// values.put("password", etPassword.getText().toString().trim());
// values.put("email_id", etEmailId.getText().toString());
// values.put("phone_no", etPhoneNo.getText().toString());
// values.put("address", etAddress.getText().toString());
// values.put("dob", etDob.getText().toString());
// values.put("date_of_joining", etDateOfJoining.getText().toString());

//                     values.put("password", etPassword.getText().toString());

//                     SQLiteDatabase db = dbHelper.getWritableDatabase();
//                     if (admin == null) {
//                         values.put("created_at", createdAt);
//                         db.insert("admins", null, values);
//                     } else {
//                         db.update("admins", values, "admin_id=?", new String[]{String.valueOf(admin.getAdminId())});
//                     }
//                     loadAdmins(null, null);
//                 })
//                 .setNegativeButton("Cancel", null)
//                 .show();
//     }

//     @Override
//     public void onFilterSelected(String column, String value) {
//         loadAdmins(column, value);
//     }
// }



package com.example.stuadminlogin.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.DialogFragment;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.AdminAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.Admin;

import java.util.ArrayList;
import java.util.List;

public class EditAdminActivity extends AppCompatActivity implements AdminAdapter.OnAdminActionListener, FilterDialogFragment.FilterDialogListener {

    private RecyclerView recyclerView;
    private Button btnSearch, btnAddAdmin;
    private AdminAdapter adapter;
    private List<Admin> adminList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Optional: Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Manage Admins"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        recyclerView = findViewById(R.id.recyclerViewAdmins);
        btnSearch = findViewById(R.id.btnSearch);
        btnAddAdmin = findViewById(R.id.btnAddAdmin);

        dbHelper = new DatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAdmins(null, null);

        btnSearch.setOnClickListener(v -> {
            DialogFragment filterDialog = new FilterDialogFragment();
            filterDialog.show(getSupportFragmentManager(), "filter_dialog");
        });

        btnAddAdmin.setOnClickListener(v -> showAdminForm(null));
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadAdmins(@Nullable String column, @Nullable String keyword) {
        adminList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM admins";
        String[] args = null;

        if (column != null && keyword != null && !keyword.isEmpty()) {
            query += " WHERE " + column + " LIKE ?";
            args = new String[]{"%" + keyword + "%"};
        }

        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                Admin admin = new Admin(
                    cursor.getInt(cursor.getColumnIndexOrThrow("admin_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("full_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("created_at")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phone_no")),
                    cursor.getString(cursor.getColumnIndexOrThrow("address")),
                    cursor.getString(cursor.getColumnIndexOrThrow("dob")),
                    cursor.getString(cursor.getColumnIndexOrThrow("date_of_joining")),
                    cursor.getString(cursor.getColumnIndexOrThrow("profile_photo_uri")),
                    cursor.getString(cursor.getColumnIndexOrThrow("last_login"))
                );

                adminList.add(admin);
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new AdminAdapter(adminList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEdit(Admin admin) {
        showAdminForm(admin);
    }

    @Override
    public void onDelete(Admin admin) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Admin")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", (d, w) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("admins", "admin_id=?", new String[]{String.valueOf(admin.getAdminId())});
                    loadAdmins(null, null);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showAdminForm(@Nullable Admin admin) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_admin_form, null);
        EditText etUsername = dialogView.findViewById(R.id.etUsername);
        EditText etFullName = dialogView.findViewById(R.id.etFullName);
        EditText etPassword = dialogView.findViewById(R.id.etPassword);
        EditText etEmailId = dialogView.findViewById(R.id.etEmailId);
        EditText etPhoneNo = dialogView.findViewById(R.id.etPhoneNo);
        EditText etAddress = dialogView.findViewById(R.id.etAddress);
        EditText etDob = dialogView.findViewById(R.id.etDob);
        EditText etDateOfJoining = dialogView.findViewById(R.id.etDateOfJoining);
        //EditText etProfilePhotoUri = dialogView.findViewById(R.id.etProfilePhotoUri);
        //EditText etLastLogin = dialogView.findViewById(R.id.etLastLogin); // Optional

        String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        if (admin != null) {
            etUsername.setText(admin.getUsername());
            etFullName.setText(admin.getFullName());
            etPassword.setText(admin.getPassword());
            etEmailId.setText(admin.getEmailId());
            etPhoneNo.setText(admin.getPhoneNo());
            etAddress.setText(admin.getAddress());
            etDob.setText(admin.getDob());
            etDateOfJoining.setText(admin.getDateOfJoining());
            //etProfilePhotoUri.setText(admin.getProfilePhotoUri());
        }

        new AlertDialog.Builder(this)
                .setTitle(admin == null ? "Add Admin" : "Edit Admin")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    ContentValues values = new ContentValues();
                    values.put("username", etUsername.getText().toString().trim());
                    values.put("full_name", etFullName.getText().toString());
                    // This was duplicated in your original code, removed the redundant one
                    values.put("password", etPassword.getText().toString().trim());
                    values.put("email_id", etEmailId.getText().toString());
                    values.put("phone_no", etPhoneNo.getText().toString());
                    values.put("address", etAddress.getText().toString());
                    values.put("dob", etDob.getText().toString());
                    values.put("date_of_joining", etDateOfJoining.getText().toString());

                    // This was duplicated and potentially overwriting password, removed
                    // values.put("password", etPassword.getText().toString());

                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    if (admin == null) {
                        values.put("created_at", createdAt);
                        db.insert("admins", null, values);
                    } else {
                        db.update("admins", values, "admin_id=?", new String[]{String.valueOf(admin.getAdminId())});
                    }
                    loadAdmins(null, null);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onFilterSelected(String column, String value) {
        loadAdmins(column, value);
    }
}