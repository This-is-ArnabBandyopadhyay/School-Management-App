package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
