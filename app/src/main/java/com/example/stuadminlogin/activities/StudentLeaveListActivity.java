// package com.example.stuadminlogin.activities;

// import android.content.Intent;
// import android.os.Bundle;
// import android.widget.Button;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.*;
// import com.example.stuadminlogin.adapters.StudentLeaveAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.LeaveApplication;
// import com.example.stuadminlogin.R;
// import java.util.List;

// public class StudentLeaveListActivity extends AppCompatActivity {
//     private RecyclerView rv;
//     private Button btnNew;
//     private DatabaseHelper db;

//     // logged-in student ID
//     private int currentStudentId;

//     @Override
//     protected void onCreate(Bundle s) {
//     super.onCreate(s);
//     setContentView(R.layout.activity_student_leave_list);

//     // ✅ Get student ID from Intent
//     currentStudentId = getIntent().getIntExtra("student_id", -1);
//     if (currentStudentId == -1) {
//         // student ID not found — optionally handle error
//         finish();
//         return;
//     }

//     db = new DatabaseHelper(this);
//     rv = findViewById(R.id.recyclerStudentLeaves);
//     rv.setLayoutManager(new LinearLayoutManager(this));
//     // btnNew = findViewById(R.id.btnNewLeave);

//     // btnNew.setOnClickListener(v -> {
//     //     // ✅ Pass student ID to the form as well
//     //     Intent intent = new Intent(this, LeaveApplicationFormActivity.class);
//     //     intent.putExtra("student_id", currentStudentId);
//     //     startActivity(intent);
//     // });
// }

//     @Override
//     protected void onResume() {
//         super.onResume();
//         List<LeaveApplication> list = db.getLeavesByStudent(currentStudentId);
//         rv.setAdapter(new StudentLeaveAdapter(list));
//     }
// }


package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;         // Import Menu
import android.view.MenuItem;      // Import MenuItem
import android.widget.Button;    // Keep if you uncomment btnNew later, otherwise can remove
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.*;
import android.widget.Toast; // Add this line
import com.example.stuadminlogin.adapters.StudentLeaveAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.LeaveApplication;
import com.example.stuadminlogin.R;
import java.util.List;

public class StudentLeaveListActivity extends AppCompatActivity {
    private RecyclerView rv;
    // private Button btnNew; // This button is being replaced by a Toolbar action
    private DatabaseHelper db;

    // logged-in student ID
    private int currentStudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Renamed 's' to 'savedInstanceState' for clarity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_leave_list);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Leave Applications"); // Set Toolbar title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Make it clickable
        }
        // --- End Toolbar Setup ---

        // ✅ Get student ID from Intent
        currentStudentId = getIntent().getIntExtra("student_id", -1);
        if (currentStudentId == -1) {
            // student ID not found — optionally handle error, e.g., show a toast and finish
            Toast.makeText(this, "Student ID not found. Please log in again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        db = new DatabaseHelper(this);
        rv = findViewById(R.id.recyclerStudentLeaves);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // The btnNew logic is moved to the Toolbar's menu item
        // btnNew = findViewById(R.id.btnNewLeave); // Commented out or removed as per XML change

        // btnNew.setOnClickListener(v -> {
        //     // ✅ Pass student ID to the form as well
        //     Intent intent = new Intent(this, LeaveApplicationFormActivity.class);
        //     intent.putExtra("student_id", currentStudentId);
        //     startActivity(intent);
        // });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load data specific to the current student
        List<LeaveApplication> list = db.getLeavesByStudent(currentStudentId);
        rv.setAdapter(new StudentLeaveAdapter(list));
    }

    // --- Toolbar Menu Methods ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_leave_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_new_leave) {
            // Logic for "New Leave Application"
            Intent intent = new Intent(this, LeaveApplicationFormActivity.class);
            intent.putExtra("student_id", currentStudentId);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) { // Handle the back button in the Toolbar
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Handle the back button click specifically if not handled by onOptionsItemSelected
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // This will navigate back to the previous activity
        return true;
    }
}