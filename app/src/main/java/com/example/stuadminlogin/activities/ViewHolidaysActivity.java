package com.example.stuadminlogin.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.example.stuadminlogin.R;
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

        recyclerView = findViewById(R.id.viewHolidayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        helper = new DatabaseHelper(this);
        holidayList = new ArrayList<>();
        HolidayAdapter adapter = new HolidayAdapter(holidayList, null, false);
        recyclerView.setAdapter(adapter);

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
}
