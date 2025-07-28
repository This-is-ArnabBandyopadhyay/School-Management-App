package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.GroupListAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.GroupModel;
import java.util.List;

public class GroupManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GroupListAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);

        db = new DatabaseHelper(this);
        
        Button btnAddGroup = findViewById(R.id.btn_add_group);
        recyclerView = findViewById(R.id.rv_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddGroup.setOnClickListener(v -> {
            Intent intent = new Intent(GroupManagementActivity.this, AddEditGroupActivity.class);
            startActivity(intent);
        });

        loadGroups();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGroups();
    }

    private void loadGroups() {
        List<GroupModel> groups = db.getAllGroupsWithMembers();
        adapter = new GroupListAdapter(this, groups, db);
        recyclerView.setAdapter(adapter);
    }
}