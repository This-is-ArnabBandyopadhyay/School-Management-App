package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.StudentModel;
import java.util.List;

public class GroupMembersAdapter extends RecyclerView.Adapter<GroupMembersAdapter.MemberViewHolder> {
    private Context context;
    private List<StudentModel> members;
    private DatabaseHelper db;
    private int groupId;

    public GroupMembersAdapter(Context context, List<StudentModel> members, DatabaseHelper db, int groupId) {
        this.context = context;
        this.members = members;
        this.db = db;
        this.groupId = groupId;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        StudentModel student = members.get(position);
        holder.tvStudentName.setText(student.getName());
        holder.tvRollNo.setText("Roll: " + student.getRollNo());

        holder.btnRemove.setOnClickListener(v -> {
            int removed = db.removeStudentFromGroup(groupId, student.getStudentId());
            if (removed > 0) {
                members.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Student removed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to remove student", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void updateMembers(List<StudentModel> newMembers) {
        members = newMembers;
        notifyDataSetChanged();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvRollNo;
        Button btnRemove;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_student_name);
            tvRollNo = itemView.findViewById(R.id.tv_roll_no);
            btnRemove = itemView.findViewById(R.id.btn_remove_member);
        }
    }
}