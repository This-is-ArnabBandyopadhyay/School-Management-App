// package com.example.stuadminlogin.activities;

// import android.app.AlertDialog;
// import android.content.*;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.text.InputType;
// import android.view.View;
// import android.widget.*;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.HolidayAdapter;
// import com.example.stuadminlogin.models.Holiday;
// import com.example.stuadminlogin.database.DatabaseHelper;

// import java.text.SimpleDateFormat;
// import java.util.*;

// public class ManageHolidaysActivity extends AppCompatActivity {

//     private RecyclerView recyclerView;
//     private DatabaseHelper helper;
//     private List<Holiday> holidayList;
//     private HolidayAdapter adapter;
//     private int adminId = 1; // Assume session or shared pref

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_manage_holidays);

//         helper = new DatabaseHelper(this);
//         recyclerView = findViewById(R.id.holidayRecycler);
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));
//         holidayList = new ArrayList<>();

//         adapter = new HolidayAdapter(holidayList, new HolidayAdapter.HolidayCallback() {
//             @Override
//             public void onEdit(Holiday h) {
//                 showAddEditDialog(h);
//             }

//             @Override
//             public void onDelete(Holiday h) {
//                 helper.deleteHoliday(h.getId());
//                 loadData();
//             }
//         }, true);

//         recyclerView.setAdapter(adapter);

//         findViewById(R.id.btnAddHoliday).setOnClickListener(v -> showAddEditDialog(null));
//         loadData();
//     }

//     private void loadData() {
//         holidayList.clear();
//         SQLiteDatabase db = helper.getReadableDatabase();
//         Cursor c = db.rawQuery("SELECT h.holiday_id, h.title, h.description, h.holiday_date, h.created_at, a.full_name " +
//                 "FROM holidays h JOIN admins a ON h.created_by_admin_id = a.admin_id", null);

//         while (c.moveToNext()) {
//             holidayList.add(new Holiday(
//                     c.getInt(0),
//                     c.getString(1),
//                     c.getString(2),
//                     c.getString(3),
//                     c.getString(4),
//                     c.getString(5)
//             ));
//         }
//         c.close();
//         adapter.notifyDataSetChanged();
//     }

//     private void showAddEditDialog(Holiday existing) {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         builder.setTitle(existing == null ? "Add Holiday" : "Edit Holiday");

//         LinearLayout layout = new LinearLayout(this);
//         layout.setOrientation(LinearLayout.VERTICAL);

//         EditText etTitle = new EditText(this);
//         etTitle.setHint("Title");
//         layout.addView(etTitle);

//         EditText etDesc = new EditText(this);
//         etDesc.setHint("Description");
//         layout.addView(etDesc);

//         EditText etDate = new EditText(this);
//         etDate.setHint("YYYY-MM-DD");
//         etDate.setInputType(InputType.TYPE_CLASS_DATETIME);
//         layout.addView(etDate);

//         if (existing != null) {
//             etTitle.setText(existing.getTitle());
//             etDesc.setText(existing.getDescription());
//             etDate.setText(existing.getHolidayDate());
//         }

//         builder.setView(layout);
//         builder.setPositiveButton("Save", (dialog, which) -> {
//             String title = etTitle.getText().toString();
//             String desc = etDesc.getText().toString();
//             String date = etDate.getText().toString();

//             if (existing == null) {
//                 helper.insertHoliday(title, desc, date, adminId);
//             } else {
//                 helper.updateHoliday(existing.getId(), title, desc, date);
//             }
//             loadData();
//         });
//         builder.setNegativeButton("Cancel", null);
//         builder.show();
//     }
// }



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
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import android.app.DatePickerDialog; // Add this line
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

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Optional: Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Manage Holidays"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

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

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadData() {
        holidayList.clear();
        SQLiteDatabase db = helper.getReadableDatabase();
        // Use column names in Cursor.getColumnIndexOrThrow for robustness
        Cursor c = db.rawQuery("SELECT h.holiday_id, h.title, h.description, h.holiday_date, h.created_at, a.full_name " +
                "FROM holidays h JOIN admins a ON h.created_by_admin_id = a.admin_id", null);

        while (c.moveToNext()) {
            holidayList.add(new Holiday(
                c.getInt(c.getColumnIndexOrThrow("holiday_id")),
                c.getString(c.getColumnIndexOrThrow("title")),
                c.getString(c.getColumnIndexOrThrow("description")),
                c.getString(c.getColumnIndexOrThrow("holiday_date")),
                c.getString(c.getColumnIndexOrThrow("created_at")),
                c.getString(c.getColumnIndexOrThrow("full_name"))
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
        layout.setPadding(50, 20, 50, 20); // Add padding to the dialog layout itself

        EditText etTitle = new EditText(this);
        etTitle.setHint("Title");
        layout.addView(etTitle);

        EditText etDesc = new EditText(this);
        etDesc.setHint("Description");
        layout.addView(etDesc);

        EditText etDate = new EditText(this);
        etDate.setHint("YYYY-MM-DD");
        etDate.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE); // More specific date input type
        // Optional: Add a DatePickerDialog for easier date selection
        etDate.setFocusable(false); // Make it non-editable to force date picker
        etDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            if (existing != null && !existing.getHolidayDate().isEmpty()) {
                try {
                    Date existingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(existing.getHolidayDate());
                    if (existingDate != null) cal.setTime(existingDate);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                etDate.setText(selectedDate);
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });


        layout.addView(etDate);

        if (existing != null) {
            etTitle.setText(existing.getTitle());
            etDesc.setText(existing.getDescription());
            etDate.setText(existing.getHolidayDate());
        }

        builder.setView(layout);
        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
                // To prevent dialog from dismissing, you would typically use a custom dialog or override the positive button listener.
                // For simplicity here, we'll let it dismiss and inform the user.
                return;
            }

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