// package com.example.stuadminlogin.activities;

// import android.app.AlertDialog;
// import android.content.ContentValues;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.text.TextUtils;
// import android.view.View;
// import android.widget.*;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.SectionListAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.SectionModel;

// import java.util.ArrayList;
// import java.util.List;

// public class ManageSectionsActivity extends AppCompatActivity {

//     private RecyclerView recyclerView;
//     private Button btnAdd, btnSearch;
//     private DatabaseHelper dbHelper;
//     private List<SectionModel> sectionList;
//     private SectionListAdapter adapter;
//     private int classId;
//     private TextView textHeader, textNoData;
//     private String className;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_manage_sections);

//         dbHelper = new DatabaseHelper(this);
//         classId = getIntent().getIntExtra("class_id", -1);
// if (classId == -1) {
//     Toast.makeText(this, "Missing class_id", Toast.LENGTH_SHORT).show();
//     finish(); // avoids crash
//     return;
// }

//         className = getIntent().getStringExtra("class_name");

//         recyclerView = findViewById(R.id.recyclerViewSections);
//         btnAdd = findViewById(R.id.btnAddSection);
//         btnSearch = findViewById(R.id.btnSearchSection);
//         textHeader = findViewById(R.id.textViewSelectedClass);


//         textNoData = findViewById(R.id.textNoSectionsFound); // Add this TextView in XML

//         recyclerView.setLayoutManager(new LinearLayoutManager(this));
//         textHeader.setText("Sections for " + (className != null ? className : ("Class ID: " + classId)));

//         btnAdd.setOnClickListener(v -> showSectionDialog(null));
//         btnSearch.setOnClickListener(v -> showSearchDialog());

//         loadSections(null);
//     }

//     private void showSectionDialog(SectionModel editModel) {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         builder.setTitle(editModel == null ? "Add Section" : "Edit Section");

//         LinearLayout layout = new LinearLayout(this);
//         layout.setOrientation(LinearLayout.VERTICAL);
//         layout.setPadding(20, 20, 20, 20);

//         EditText sectionInput = new EditText(this);
//         sectionInput.setHint("Section Name");
//         layout.addView(sectionInput);

//         if (editModel != null) {
//             sectionInput.setText(editModel.getName());
//         }

//         builder.setView(layout);

//         builder.setPositiveButton("Save", (dialog, which) -> {
//             String name = sectionInput.getText().toString().trim();
//             if (TextUtils.isEmpty(name)) {
//                 Toast.makeText(this, "Section name required", Toast.LENGTH_SHORT).show();
//                 return;
//             }

//             SQLiteDatabase db = dbHelper.getWritableDatabase();
//             ContentValues values = new ContentValues();
//             values.put("section_name", name);
//             values.put("class_id", classId);

//             if (editModel == null) {
//                 db.insert("sections", null, values);
//             } else {
//                 db.update("sections", values, "section_id=?", new String[]{String.valueOf(editModel.getId())});
//             }

//             loadSections(null);
//         });

//         builder.setNegativeButton("Cancel", null);
//         builder.show();
//     }

//     private void showSearchDialog() {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         builder.setTitle("Search Sections");

//         LinearLayout layout = new LinearLayout(this);
//         layout.setOrientation(LinearLayout.VERTICAL);
//         layout.setPadding(20, 20, 20, 20);

//         EditText searchInput = new EditText(this);
//         searchInput.setHint("Section Name");
//         layout.addView(searchInput);

//         builder.setView(layout);

//         builder.setPositiveButton("Search", (dialog, which) -> {
//             loadSections(searchInput.getText().toString().trim());
//         });

//         builder.setNegativeButton("Cancel", null);
//         builder.show();
//     }

//     private void loadSections(String nameFilter) {
//     sectionList = new ArrayList<>();
//     SQLiteDatabase db = dbHelper.getReadableDatabase();

//     String query = "SELECT section_id, section_name, class_id FROM sections WHERE class_id=?";
//     List<String> args = new ArrayList<>();
//     args.add(String.valueOf(classId));

//     if (!TextUtils.isEmpty(nameFilter)) {
//         query += " AND section_name LIKE ?";
//         args.add("%" + nameFilter + "%");
//     }

//     Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));

//     while (cursor.moveToNext()) {
//         int id = cursor.getInt(0);
//         String name = cursor.getString(1);
//         int cId = cursor.getInt(2);

//         sectionList.add(new SectionModel(id, name, cId));
//     }

//     cursor.close();

//     if (sectionList.isEmpty()) {
//         recyclerView.setVisibility(View.GONE);
//         textNoData.setVisibility(View.VISIBLE);
//     } else {
//         recyclerView.setVisibility(View.VISIBLE);
//         textNoData.setVisibility(View.GONE);
//     }

//     adapter = new SectionListAdapter(sectionList, new SectionListAdapter.OnSectionActionListener() {
//         @Override
//         public void onEdit(SectionModel model) {
//             showSectionDialog(model);
//         }

//         @Override
//         public void onDelete(SectionModel model) {
//             db.delete("sections", "section_id=?", new String[]{String.valueOf(model.getId())});
//             loadSections(null);
//         }
//     });

//     recyclerView.setAdapter(adapter);
// }
// }




package com.example.stuadminlogin.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.SectionListAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.SectionModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ManageSectionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnAdd, btnSearch;
    private DatabaseHelper dbHelper;
    private List<SectionModel> sectionList;
    private SectionListAdapter adapter;
    private int classId;
    private TextView textHeader, textNoData;
    private String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sections);

        dbHelper = new DatabaseHelper(this);
        classId = getIntent().getIntExtra("class_id", -1);
        if (classId == -1) {
            Toast.makeText(this, "Missing class_id", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        className = getIntent().getStringExtra("class_name");

        recyclerView = findViewById(R.id.recyclerViewSections);
        btnAdd = findViewById(R.id.btnAddSection);
        btnSearch = findViewById(R.id.btnSearchSection);
        textHeader = findViewById(R.id.textViewSelectedClass);
        textNoData = findViewById(R.id.textNoSectionsFound);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textHeader.setText("Sections for " + (className != null ? className : ("Class ID: " + classId)));

        btnAdd.setOnClickListener(v -> showSectionDialog(null));
        btnSearch.setOnClickListener(v -> showSearchDialog());

        loadSections(null);
    }

    private void showSectionDialog(SectionModel editModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_section, null);
        builder.setView(dialogView);

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        TextInputEditText sectionInput = dialogView.findViewById(R.id.editSectionName);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        if (editModel != null) {
            dialogTitle.setText("Edit Section");
            sectionInput.setText(editModel.getName());
        }

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(v -> {
            String name = sectionInput.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Section name required", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("section_name", name);
            values.put("class_id", classId);

            if (editModel == null) {
                db.insert("sections", null, values);
            } else {
                db.update("sections", values, "section_id=?", new String[]{String.valueOf(editModel.getId())});
            }

            loadSections(null);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_filter_section, null);
        builder.setView(dialogView);
        builder.setTitle("Search Sections");

        TextInputEditText searchInput = dialogView.findViewById(R.id.editSearchSection);
        Button btnSearch = dialogView.findViewById(R.id.btnSearch);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog dialog = builder.create();

        btnSearch.setOnClickListener(v -> {
            loadSections(searchInput.getText().toString().trim());
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void loadSections(String nameFilter) {
        sectionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT section_id, section_name, class_id FROM sections WHERE class_id=?";
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(classId));

        if (!TextUtils.isEmpty(nameFilter)) {
            query += " AND section_name LIKE ?";
            args.add("%" + nameFilter + "%");
        }

        Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));

        while (cursor.moveToNext()) {
            sectionList.add(new SectionModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2)
            ));
        }
        cursor.close();

        if (sectionList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textNoData.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textNoData.setVisibility(View.GONE);
        }

        adapter = new SectionListAdapter(sectionList, new SectionListAdapter.OnSectionActionListener() {
            @Override
            public void onEdit(SectionModel model) {
                showSectionDialog(model);
            }

            @Override
            public void onDelete(SectionModel model) {
                new AlertDialog.Builder(ManageSectionsActivity.this)
                    .setTitle("Confirm Delete")
                    .setMessage("Are you sure you want to delete this section?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        dbHelper.getWritableDatabase().delete("sections", 
                            "section_id=?", new String[]{String.valueOf(model.getId())});
                        loadSections(null);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            }
        });

        recyclerView.setAdapter(adapter);
    }
}
