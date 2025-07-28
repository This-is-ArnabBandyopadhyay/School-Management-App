package com.example.stuadminlogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.GroupMembersAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.GroupModel;
import com.example.stuadminlogin.models.StudentModel;
import java.util.List;

public class GroupMembersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GroupMembersAdapter adapter;
    private DatabaseHelper db;
    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        db = new DatabaseHelper(this);
        groupId = getIntent().getIntExtra("group_id", -1);
        
        if (groupId == -1) {
            finish();
            return;
        }

        TextView tvGroupName = findViewById(R.id.tv_group_name);
        Button btnAddMembers = findViewById(R.id.btn_add_members);
        recyclerView = findViewById(R.id.rv_members);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GroupModel group = db.getGroupWithMembers(groupId);
        if (group != null) {
            tvGroupName.setText(group.getGroupName());
            adapter = new GroupMembersAdapter(this, group.getMembers(), db, groupId);
            recyclerView.setAdapter(adapter);
        }

        btnAddMembers.setOnClickListener(v -> {
            Intent intent = new Intent(GroupMembersActivity.this, AddMembersActivity.class);
            intent.putExtra("group_id", groupId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMembers();
    }

    private void refreshMembers() {
        GroupModel group = db.getGroupWithMembers(groupId);
        if (group != null) {
            adapter.updateMembers(group.getMembers());
        }
    }
}