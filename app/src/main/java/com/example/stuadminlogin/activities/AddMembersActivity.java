// package com.example.stuadminlogin.activities;

// import android.os.Bundle;
// import android.view.View;
// import android.widget.Button;
// import android.widget.EditText;
// import android.widget.Toast;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.StudentSelectionAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.StudentModel;
// import java.util.ArrayList;
// import java.util.List;

// public class AddMembersActivity extends AppCompatActivity {
//     private RecyclerView recyclerView;
//     private StudentSelectionAdapter adapter;
//     private DatabaseHelper db;
//     private int groupId;
//     private List<StudentModel> selectedStudents = new ArrayList<>();

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_add_members);

//         db = new DatabaseHelper(this);
//         groupId = getIntent().getIntExtra("group_id", -1);
        
//         if (groupId == -1) {
//             finish();
//             return;
//         }

//         EditText etSearch = findViewById(R.id.et_search);
//         Button btnAddSelected = findViewById(R.id.btn_add_selected);
//         recyclerView = findViewById(R.id.rv_students);
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));

//         List<StudentModel> allStudents = db.getAllStudents();
//         adapter = new StudentSelectionAdapter(this, allStudents);
//         recyclerView.setAdapter(adapter);

//         etSearch.addTextChangedListener(new android.text.TextWatcher() {
//             @Override
//             public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

//             @Override
//             public void onTextChanged(CharSequence s, int start, int before, int count) {
//                 adapter.getFilter().filter(s);
//             }

//             @Override
//             public void afterTextChanged(android.text.Editable s) {}
//         });

//         btnAddSelected.setOnClickListener(v -> {
//             selectedStudents = adapter.getSelectedStudents();
//             if (selectedStudents.isEmpty()) {
//                 Toast.makeText(this, "No students selected", Toast.LENGTH_SHORT).show();
//                 return;
//             }

//             for (StudentModel student : selectedStudents) {
//                 db.addStudentToGroup(groupId, student.getStudentId());
//             }

//             Toast.makeText(this, "Students added to group", Toast.LENGTH_SHORT).show();
//             finish();
//         });
//     }
// }

package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.StudentSelectionAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.StudentModel;
import java.util.ArrayList;
import java.util.List;

public class AddMembersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentSelectionAdapter adapter;
    private DatabaseHelper db;
    private int groupId;
    private List<StudentModel> selectedStudents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Optional: Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Group Members"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        db = new DatabaseHelper(this);
        groupId = getIntent().getIntExtra("group_id", -1);

        if (groupId == -1) {
            finish();
            return;
        }

        EditText etSearch = findViewById(R.id.et_search);
        Button btnAddSelected = findViewById(R.id.btn_add_selected);
        recyclerView = findViewById(R.id.rv_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<StudentModel> allStudents = db.getAllStudents();
        adapter = new StudentSelectionAdapter(this, allStudents);
        recyclerView.setAdapter(adapter);

        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        btnAddSelected.setOnClickListener(v -> {
            selectedStudents = adapter.getSelectedStudents();
            if (selectedStudents.isEmpty()) {
                Toast.makeText(this, "No students selected", Toast.LENGTH_SHORT).show();
                return;
            }

            for (StudentModel student : selectedStudents) {
                db.addStudentToGroup(groupId, student.getStudentId());
            }

            Toast.makeText(this, "Students added to group", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}