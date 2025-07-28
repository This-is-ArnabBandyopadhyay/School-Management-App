package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.activities.GroupMembersActivity;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.GroupModel;
import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {
    private Context context;
    private List<GroupModel> groups;
    private DatabaseHelper db;

    public GroupListAdapter(Context context, List<GroupModel> groups, DatabaseHelper db) {
        this.context = context;
        this.groups = groups;
        this.db = db;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupModel group = groups.get(position);
        holder.tvGroupName.setText(group.getGroupName());
        holder.tvMemberCount.setText(group.getMembers() != null ? 
            group.getMembers().size() + " members" : "0 members");

        holder.btnViewMembers.setOnClickListener(v -> {
            Intent intent = new Intent(context, GroupMembersActivity.class);
            intent.putExtra("group_id", group.getGroupId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            int deleted = db.deleteGroup(group.getGroupId());
            if (deleted > 0) {
                groups.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Group deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to delete group", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void updateGroups(List<GroupModel> newGroups) {
        groups = newGroups;
        notifyDataSetChanged();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView tvGroupName, tvMemberCount;
        Button btnViewMembers, btnDelete;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tv_group_name);
            tvMemberCount = itemView.findViewById(R.id.tv_member_count);
            btnViewMembers = itemView.findViewById(R.id.btn_view_members);
            btnDelete = itemView.findViewById(R.id.btn_delete_group);
        }
    }
}