package com.example.stuadminlogin.activities;

import android.app.AlertDialog;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.HolidayAdapter;
import com.example.stuadminlogin.models.Holiday;
import com.example.stuadminlogin.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.*;

public class ManageHolidaysActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper helper;
    private List<Holiday> holidayList;
    private HolidayAdapter adapter;
    private int adminId = 1; // Assume session or shared pref

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_holidays);

        helper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.holidayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        holidayList = new ArrayList<>();

        adapter = new HolidayAdapter(holidayList, new HolidayAdapter.HolidayCallback() {
            @Override
            public void onEdit(Holiday h) {
                showAddEditDialog(h);
            }

            @Override
            public void onDelete(Holiday h) {
                helper.deleteHoliday(h.getId());
                loadData();
            }
        }, true);

        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnAddHoliday).setOnClickListener(v -> showAddEditDialog(null));
        loadData();
    }

    private void loadData() {
        holidayList.clear();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT h.holiday_id, h.title, h.description, h.holiday_date, h.created_at, a.full_name " +
                "FROM holidays h JOIN admins a ON h.created_by_admin_id = a.admin_id", null);

        while (c.moveToNext()) {
            holidayList.add(new Holiday(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5)
            ));
        }
        c.close();
        adapter.notifyDataSetChanged();
    }

    private void showAddEditDialog(Holiday existing) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(existing == null ? "Add Holiday" : "Edit Holiday");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        EditText etTitle = new EditText(this);
        etTitle.setHint("Title");
        layout.addView(etTitle);

        EditText etDesc = new EditText(this);
        etDesc.setHint("Description");
        layout.addView(etDesc);

        EditText etDate = new EditText(this);
        etDate.setHint("YYYY-MM-DD");
        etDate.setInputType(InputType.TYPE_CLASS_DATETIME);
        layout.addView(etDate);

        if (existing != null) {
            etTitle.setText(existing.getTitle());
            etDesc.setText(existing.getDescription());
            etDate.setText(existing.getHolidayDate());
        }

        builder.setView(layout);
        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = etTitle.getText().toString();
            String desc = etDesc.getText().toString();
            String date = etDate.getText().toString();

            if (existing == null) {
                helper.insertHoliday(title, desc, date, adminId);
            } else {
                helper.updateHoliday(existing.getId(), title, desc, date);
            }
            loadData();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
