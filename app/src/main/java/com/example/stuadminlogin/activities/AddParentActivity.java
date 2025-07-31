package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.ParentModel; // Corrected: Using ParentModel

public class AddParentActivity extends AppCompatActivity {

    private EditText etParentName, etParentEmail, etParentPhone, etParentPassword;
    private Button btnSaveParent;
    private DatabaseHelper dbHelper;
    private int studentId;
    private int parentIdToEdit = -1; // -1 for adding new, >0 for editing existing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add/Edit Parent");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new DatabaseHelper(this);

        etParentName = findViewById(R.id.et_parent_name);
        etParentEmail = findViewById(R.id.et_parent_email);
        etParentPhone = findViewById(R.id.et_parent_phone);
        etParentPassword = findViewById(R.id.et_parent_password);
        btnSaveParent = findViewById(R.id.btn_save_parent);

        studentId = getIntent().getIntExtra("student_id", -1);
        parentIdToEdit = getIntent().getIntExtra("parent_id", -1);

        if (parentIdToEdit != -1) {
            // Load parent details for editing
            ParentModel parent = dbHelper.getParentById(parentIdToEdit); // Corrected: Using ParentModel
            if (parent != null) {
                etParentName.setText(parent.getName());
                etParentEmail.setText(parent.getEmail());
                etParentPhone.setText(parent.getPhoneNo());
                // Do not pre-fill password for security reasons in a real app,
                // or handle it carefully (e.g., allow changing only).
                etParentPassword.setHint("Enter new password (optional)");
            } else {
                Toast.makeText(this, "Parent not found for editing.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        btnSaveParent.setOnClickListener(v -> saveParent());
    }

    private void saveParent() {
        String name = etParentName.getText().toString().trim();
        String email = etParentEmail.getText().toString().trim();
        String phone = etParentPhone.getText().toString().trim();
        String password = etParentPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please fill all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (parentIdToEdit == -1) {
            // Adding or Linking a new parent
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Password is required for new parents.", Toast.LENGTH_SHORT).show();
                return;
            }

            long existingParentId = dbHelper.getParentIdByEmail(email); // Check if parent exists by email
            if (existingParentId != -1) {
                // Parent exists, link them to the student
                boolean linked = dbHelper.linkParentToStudent((int) existingParentId, studentId);
                if (linked) {
                    Toast.makeText(this, "Parent already exists and linked to student.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to link existing parent.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Parent does not exist, create new parent and link
                // Corrected: Using ParentModel constructor
                ParentModel newParent = new ParentModel(-1, email, password, name, phone, null, null, null);
                long newParentRowId = dbHelper.addParent(newParent);
                if (newParentRowId != -1) {
                    boolean linked = dbHelper.linkParentToStudent((int) newParentRowId, studentId);
                    if (linked) {
                        Toast.makeText(this, "New parent added and linked to student.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "New parent added, but failed to link.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Failed to add new parent.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            // Editing existing parent details
            // Corrected: Using ParentModel constructor
            ParentModel updatedParent = new ParentModel(parentIdToEdit, email, password, name, phone, null, null, null);
            boolean updated = dbHelper.updateParent(updatedParent, !TextUtils.isEmpty(password)); // Pass flag if password updated
            if (updated) {
                Toast.makeText(this, "Parent details updated.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update parent details.", Toast.LENGTH_SHORT).show();
            }
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