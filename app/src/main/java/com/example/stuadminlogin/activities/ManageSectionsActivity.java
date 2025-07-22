package com.example.stuadminlogin.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.SectionListAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.SectionModel;

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
    finish(); // avoids crash
    return;
}

        className = getIntent().getStringExtra("class_name");

        recyclerView = findViewById(R.id.recyclerViewSections);
        btnAdd = findViewById(R.id.btnAddSection);
        btnSearch = findViewById(R.id.btnSearchSection);
        textHeader = findViewById(R.id.textViewSelectedClass);


        textNoData = findViewById(R.id.textNoSectionsFound); // Add this TextView in XML

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textHeader.setText("Sections for " + (className != null ? className : ("Class ID: " + classId)));

        btnAdd.setOnClickListener(v -> showSectionDialog(null));
        btnSearch.setOnClickListener(v -> showSearchDialog());

        loadSections(null);
    }

    private void showSectionDialog(SectionModel editModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(editModel == null ? "Add Section" : "Edit Section");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 20, 20, 20);

        EditText sectionInput = new EditText(this);
        sectionInput.setHint("Section Name");
        layout.addView(sectionInput);

        if (editModel != null) {
            sectionInput.setText(editModel.getName());
        }

        builder.setView(layout);

        builder.setPositiveButton("Save", (dialog, which) -> {
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
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search Sections");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 20, 20, 20);

        EditText searchInput = new EditText(this);
        searchInput.setHint("Section Name");
        layout.addView(searchInput);

        builder.setView(layout);

        builder.setPositiveButton("Search", (dialog, which) -> {
            loadSections(searchInput.getText().toString().trim());
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
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
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        int cId = cursor.getInt(2);

        sectionList.add(new SectionModel(id, name, cId));
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
            db.delete("sections", "section_id=?", new String[]{String.valueOf(model.getId())});
            loadSections(null);
        }
    });

    recyclerView.setAdapter(adapter);
}
}
