package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View; // Add this import statement
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.ParentAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.ParentModel;

import java.util.List;

public class ManageParentsActivity extends AppCompatActivity {

    private int studentId;
    private DatabaseHelper dbHelper;
    private ListView parentsListView;
    private ParentAdapter parentAdapter;
    private List<ParentModel> parentsList;
    private TextView tvNoParentsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_parents);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Manage Parents");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new DatabaseHelper(this);

        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Student ID not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        parentsListView = findViewById(R.id.parents_list_view);
        Button btnAddParent = findViewById(R.id.btn_add_parent);
        tvNoParentsMessage = findViewById(R.id.tv_no_parents_message);

        loadParents();

        btnAddParent.setOnClickListener(v -> {
            Intent intent = new Intent(ManageParentsActivity.this, AddParentActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
        });

        parentsListView.setOnItemClickListener((parent, view, position, id) -> {
            ParentModel selectedParent = parentsList.get(position);
            showParentOptionsDialog(selectedParent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadParents();
    }

    private void loadParents() {
        parentsList = dbHelper.getParentsByStudentId(studentId);
        parentAdapter = new ParentAdapter(this, parentsList);
        parentsListView.setAdapter(parentAdapter);

        if (parentsList.isEmpty()) {
            tvNoParentsMessage.setVisibility(View.VISIBLE); // Now 'View' will be recognized
            parentsListView.setVisibility(View.GONE);
        } else {
            tvNoParentsMessage.setVisibility(View.GONE);
            parentsListView.setVisibility(View.VISIBLE);
        }
    }

    private void showParentOptionsDialog(ParentModel parent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Parent Options");
        String[] options = {"Edit Parent Details", "Remove Link to Student"};

        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0: // Edit Parent Details
                    Intent intent = new Intent(ManageParentsActivity.this, AddParentActivity.class);
                    intent.putExtra("student_id", studentId);
                    intent.putExtra("parent_id", parent.getParentId());
                    startActivity(intent);
                    break;
                case 1: // Remove Link to Student (soft delete for the link)
                    removeParentLink(parent.getParentId());
                    break;
            }
        });
        builder.show();
    }

    private void removeParentLink(int parentId) {
        boolean isRemoved = dbHelper.removeParentStudentLink(parentId, studentId);
        if (isRemoved) {
            Toast.makeText(this, "Parent link removed.", Toast.LENGTH_SHORT).show();
            loadParents();
        } else {
            Toast.makeText(this, "Failed to remove parent link.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}