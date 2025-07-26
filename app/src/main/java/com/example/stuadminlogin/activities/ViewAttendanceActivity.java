package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.StudentAttendanceAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.Attendance;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class ViewAttendanceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAttendanceAdapter adapter;
    private DatabaseHelper db;
    private int studentId;
    private List<Attendance> originalAttendanceList = new ArrayList<>(); // Initialize here
    private EditText searchView;
    private ImageButton btnFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        recyclerView = findViewById(R.id.recyclerViewAttendance);
        searchView = findViewById(R.id.searchView);
        btnFilter = findViewById(R.id.btnFilter);
        db = new DatabaseHelper(this);

        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Invalid student ID!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load data
        loadAttendanceData();

        // Setup search functionality
        setupSearch();
        
        // Setup filter button
        setupFilterButton();
    }

    private void loadAttendanceData() {
        originalAttendanceList.clear();
        originalAttendanceList.addAll(db.getAttendanceByStudent(studentId));
        adapter = new StudentAttendanceAdapter(this, new ArrayList<>(originalAttendanceList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAttendanceRecords(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupFilterButton() {
        btnFilter.setOnClickListener(v -> {
            String currentQuery = searchView.getText().toString().trim();
            if (adapter.getItemCount() == originalAttendanceList.size()) {
                // Show only absent records
                filterRecords(currentQuery, "Absent");
                btnFilter.setImageResource(android.R.drawable.ic_menu_revert);
            } else {
                // Show all records matching current search
                filterAttendanceRecords(currentQuery);
                btnFilter.setImageResource(android.R.drawable.ic_menu_sort_by_size);
            }
        });
    }

    private void filterAttendanceRecords(String query) {
        filterRecords(query, null);
    }

    private void filterRecords(String query, String statusFilter) {
        List<Attendance> filteredList = new ArrayList<>();
        String searchQuery = (query == null) ? "" : query.toLowerCase().trim();

        for (Attendance record : originalAttendanceList) {
            boolean matchesSearch = searchQuery.isEmpty() ||
                    adapter.formatDateWithDay(record.getDate()).toLowerCase().contains(searchQuery) ||
                    record.getStatus().toLowerCase().contains(searchQuery);

            boolean matchesStatus = statusFilter == null || 
                    record.getStatus().equalsIgnoreCase(statusFilter);

            if (matchesSearch && matchesStatus) {
                filteredList.add(record);
            }
        }

        adapter.updateList(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when returning to activity
        loadAttendanceData();
    }
}