// === activities/EditAdminActivity.java ===
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
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
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
        String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        if (admin != null) {
            etUsername.setText(admin.getUsername());
            etFullName.setText(admin.getFullName());
            etPassword.setText(admin.getPassword());
        }

        new AlertDialog.Builder(this)
                .setTitle(admin == null ? "Add Admin" : "Edit Admin")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    ContentValues values = new ContentValues();
                    values.put("username", etUsername.getText().toString());
                    values.put("full_name", etFullName.getText().toString());
                    values.put("password", etPassword.getText().toString());

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
