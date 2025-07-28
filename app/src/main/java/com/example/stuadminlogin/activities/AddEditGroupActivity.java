package com.example.stuadminlogin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.GroupModel;

public class AddEditGroupActivity extends AppCompatActivity {
    private EditText etGroupName;
    private Button btnSave;
    private DatabaseHelper db;
    private GroupModel existingGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_group);

        db = new DatabaseHelper(this);
        etGroupName = findViewById(R.id.et_group_name);
        btnSave = findViewById(R.id.btn_save_group);

        int groupId = getIntent().getIntExtra("group_id", -1);
        if (groupId != -1) {
            existingGroup = db.getGroupWithMembers(groupId);
            if (existingGroup != null) {
                etGroupName.setText(existingGroup.getGroupName());
                setTitle("Edit Group");
            }
        } else {
            setTitle("Add New Group");
        }

        btnSave.setOnClickListener(v -> saveGroup());
    }

    private void saveGroup() {
        String groupName = etGroupName.getText().toString().trim();
        
        if (groupName.isEmpty()) {
            Toast.makeText(this, "Group name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (existingGroup != null) {
            // Update existing group
            existingGroup.setGroupName(groupName);
            db.updateGroup(existingGroup);
            Toast.makeText(this, "Group updated", Toast.LENGTH_SHORT).show();
        } else {
            // Create new group
            long id = db.createGroup(groupName);
            if (id == -1) {
                Toast.makeText(this, "Failed to create group", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Group created", Toast.LENGTH_SHORT).show();
        }
        
        finish();
    }
}