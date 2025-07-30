// package com.example.stuadminlogin.activities;

// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.*;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.HolidayAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.Holiday;

// import java.util.*;

// public class ViewHolidaysActivity extends AppCompatActivity {

//     private RecyclerView recyclerView;
//     private List<Holiday> holidayList;
//     private DatabaseHelper helper;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_view_holidays);

//         recyclerView = findViewById(R.id.viewHolidayRecycler);
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));

//         helper = new DatabaseHelper(this);
//         holidayList = new ArrayList<>();
//         HolidayAdapter adapter = new HolidayAdapter(holidayList, null, false);
//         recyclerView.setAdapter(adapter);

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
// }



package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.*;
import com.example.stuadminlogin.R;
import android.widget.Toast; // Add this line
import com.example.stuadminlogin.adapters.HolidayAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.Holiday;

import java.util.*;

public class ViewHolidaysActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Holiday> holidayList;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_holidays);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Academic Holidays"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        recyclerView = findViewById(R.id.viewHolidayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        helper = new DatabaseHelper(this);
        holidayList = new ArrayList<>();
        // Note: 'null' for OnHolidayActionListener might cause issues if it's actually used.
        // Assuming it's fine for simple viewing.
        HolidayAdapter adapter = new HolidayAdapter(holidayList, null, false);
        recyclerView.setAdapter(adapter);

        // Use try-with-resources for SQLiteDatabase and Cursor for automatic closing
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor c = db.rawQuery("SELECT h.holiday_id, h.title, h.description, h.holiday_date, h.created_at, a.full_name " +
                     "FROM holidays h JOIN admins a ON h.created_by_admin_id = a.admin_id ORDER BY h.holiday_date ASC", null)) { // Added ORDER BY

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
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading holidays: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        adapter.notifyDataSetChanged();
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }
}