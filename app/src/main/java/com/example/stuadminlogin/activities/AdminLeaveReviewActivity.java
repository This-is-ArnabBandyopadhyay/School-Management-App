// package com.example.stuadminlogin.activities;

// import android.os.Bundle;
// import android.widget.Toast;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
// import com.example.stuadminlogin.adapters.AdminLeaveAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.LeaveApplication;
// import com.example.stuadminlogin.R;
// import java.util.List;

// public class AdminLeaveReviewActivity extends AppCompatActivity implements AdminLeaveAdapter.Callback {

//     private RecyclerView rv;
//     private DatabaseHelper db;
//     private List<LeaveApplication> list;

//     private int currentAdminId;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_admin_leave_review);

//         currentAdminId = getIntent().getIntExtra("admin_id", -1);
//         if (currentAdminId == -1) {
//             Toast.makeText(this, "Admin ID not received", Toast.LENGTH_SHORT).show();
//             finish();
//             return;
//         }

//         db = new DatabaseHelper(this);
//         rv = findViewById(R.id.recyclerAdminLeaves);
//         rv.setLayoutManager(new LinearLayoutManager(this));
//         loadList();
//     }

//     private void loadList() {
//         list = db.getPendingLeavesWithStudent();
//         AdminLeaveAdapter adapter = new AdminLeaveAdapter(list, this);
//         rv.setAdapter(adapter);
//     }

//     @Override
//     public void onRespond(int leaveId, String responseText, boolean approve) {
//         boolean ok = db.respondToLeave(leaveId, currentAdminId,
//                 responseText, approve ? "Approved" : "Rejected");
//         if (ok) {
//             Toast.makeText(this, "Responded successfully.", Toast.LENGTH_SHORT).show();
//             loadList();
//         } else {
//             Toast.makeText(this, "Error updating record.", Toast.LENGTH_SHORT).show();
//         }
//     }
// }



package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.adapters.AdminLeaveAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.LeaveApplication;
import com.example.stuadminlogin.R;
import java.util.List;

public class AdminLeaveReviewActivity extends AppCompatActivity implements AdminLeaveAdapter.Callback {

    private RecyclerView rv;
    private DatabaseHelper db;
    private List<LeaveApplication> list;

    private int currentAdminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_leave_review);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Optional: Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pending Leave Applications"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        currentAdminId = getIntent().getIntExtra("admin_id", -1);
        if (currentAdminId == -1) {
            Toast.makeText(this, "Admin ID not received", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = new DatabaseHelper(this);
        rv = findViewById(R.id.recyclerAdminLeaves);
        rv.setLayoutManager(new LinearLayoutManager(this));
        loadList();
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadList() {
        list = db.getPendingLeavesWithStudent();
        AdminLeaveAdapter adapter = new AdminLeaveAdapter(list, this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onRespond(int leaveId, String responseText, boolean approve) {
        boolean ok = db.respondToLeave(leaveId, currentAdminId,
                responseText, approve ? "Approved" : "Rejected");
        if (ok) {
            Toast.makeText(this, "Responded successfully.", Toast.LENGTH_SHORT).show();
            loadList();
        } else {
            Toast.makeText(this, "Error updating record.", Toast.LENGTH_SHORT).show();
        }
    }
}