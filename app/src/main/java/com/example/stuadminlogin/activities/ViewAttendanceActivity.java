// package com.example.stuadminlogin.activities;

// import android.os.Bundle;
// import android.text.Editable;
// import android.text.TextWatcher;
// import android.widget.EditText;
// import android.widget.ImageButton;
// import android.widget.Toast;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.StudentAttendanceAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.Attendance;
// import java.util.ArrayList;
// import java.util.List;
// import android.util.Log;

// public class ViewAttendanceActivity extends AppCompatActivity {
//     private RecyclerView recyclerView;
//     private StudentAttendanceAdapter adapter;
//     private DatabaseHelper db;
//     private int studentId;
//     private List<Attendance> originalAttendanceList = new ArrayList<>(); // Initialize here
//     private EditText searchView;
//     private ImageButton btnFilter;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_view_attendance);

//         recyclerView = findViewById(R.id.recyclerViewAttendance);
//         searchView = findViewById(R.id.searchView);
//         btnFilter = findViewById(R.id.btnFilter);
//         db = new DatabaseHelper(this);

//         studentId = getIntent().getIntExtra("student_id", -1);
//         if (studentId == -1) {
//             Toast.makeText(this, "Invalid student ID!", Toast.LENGTH_SHORT).show();
//             finish();
//             return;
//         }

//         // Load data
//         loadAttendanceData();

//         // Setup search functionality
//         setupSearch();
        
//         // Setup filter button
//         setupFilterButton();
//     }

//     private void loadAttendanceData() {
//         originalAttendanceList.clear();
//         originalAttendanceList.addAll(db.getAttendanceByStudent(studentId));
//         adapter = new StudentAttendanceAdapter(this, new ArrayList<>(originalAttendanceList));
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));
//         recyclerView.setAdapter(adapter);
//     }

//     private void setupSearch() {
//         searchView.addTextChangedListener(new TextWatcher() {
//             @Override
//             public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

//             @Override
//             public void onTextChanged(CharSequence s, int start, int before, int count) {
//                 filterAttendanceRecords(s.toString());
//             }

//             @Override
//             public void afterTextChanged(Editable s) {}
//         });
//     }

//     private void setupFilterButton() {
//         btnFilter.setOnClickListener(v -> {
//             String currentQuery = searchView.getText().toString().trim();
//             if (adapter.getItemCount() == originalAttendanceList.size()) {
//                 // Show only absent records
//                 filterRecords(currentQuery, "Absent");
//                 btnFilter.setImageResource(android.R.drawable.ic_menu_revert);
//             } else {
//                 // Show all records matching current search
//                 filterAttendanceRecords(currentQuery);
//                 btnFilter.setImageResource(android.R.drawable.ic_menu_sort_by_size);
//             }
//         });
//     }

//     private void filterAttendanceRecords(String query) {
//         filterRecords(query, null);
//     }

//     private void filterRecords(String query, String statusFilter) {
//         List<Attendance> filteredList = new ArrayList<>();
//         String searchQuery = (query == null) ? "" : query.toLowerCase().trim();

//         for (Attendance record : originalAttendanceList) {
//             boolean matchesSearch = searchQuery.isEmpty() ||
//                     adapter.formatDateWithDay(record.getDate()).toLowerCase().contains(searchQuery) ||
//                     record.getStatus().toLowerCase().contains(searchQuery);

//             boolean matchesStatus = statusFilter == null || 
//                     record.getStatus().equalsIgnoreCase(statusFilter);

//             if (matchesSearch && matchesStatus) {
//                 filteredList.add(record);
//             }
//         }

//         adapter.updateList(filteredList);
//     }

//     @Override
//     protected void onResume() {
//         super.onResume();
//         // Refresh data when returning to activity
//         loadAttendanceData();
//     }
// }



package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.StudentAttendanceAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.Attendance;
import java.util.ArrayList;
import java.util.List;
import android.util.Log; // Keep if still used, otherwise can be removed

public class ViewAttendanceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAttendanceAdapter adapter;
    private DatabaseHelper db;
    private int studentId;
    private List<Attendance> originalAttendanceList = new ArrayList<>();
    private EditText searchView;
    private ImageButton btnFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Attendance Records"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        recyclerView = findViewById(R.id.recyclerViewAttendance);
        searchView = findViewById(R.id.searchView);
        btnFilter = findViewById(R.id.btnFilter);
        db = new DatabaseHelper(this);

        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Invalid student ID! Cannot display attendance.", Toast.LENGTH_SHORT).show();
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
            if (adapter.getItemCount() == originalAttendanceList.size() && !currentQuery.isEmpty()) {
                // If currently showing all records (matching search) and there's a search query,
                // apply "Absent" filter on top of the search.
                filterRecords(currentQuery, "Absent");
                btnFilter.setImageResource(android.R.drawable.ic_menu_revert); // Change icon to indicate filter is active
            } else if (adapter.getItemCount() == originalAttendanceList.size()) {
                // If currently showing all records (no search or search result is all),
                // apply "Absent" filter directly.
                filterRecords(null, "Absent"); // Pass null for query as we only filter by status
                btnFilter.setImageResource(android.R.drawable.ic_menu_revert);
            }
            else {
                // If a filter is currently applied (either 'Absent' or from a previous state)
                // or if the list is already filtered, revert to showing all records matching current search query.
                filterAttendanceRecords(currentQuery); // Revert to only search filter
                btnFilter.setImageResource(android.R.drawable.ic_menu_sort_by_size); // Change icon back to sort
            }
            // If the query is empty and we filter for absent, and then click again, it should show all
            if (currentQuery.isEmpty() && adapter.getItemCount() < originalAttendanceList.size()) { // Corrected method call{
                 filterAttendanceRecords(""); // Revert to showing all original records
                 btnFilter.setImageResource(android.R.drawable.ic_menu_sort_by_size);
            }
        });
    }


    private void filterAttendanceRecords(String query) {
        filterRecords(query, null); // Call the generic filter with no status filter
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
        // Ensure filter icon is reset if we left and came back
        btnFilter.setImageResource(android.R.drawable.ic_menu_sort_by_size);
        // Clear search view on resume if desired, or keep its state
        searchView.setText("");
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }
}