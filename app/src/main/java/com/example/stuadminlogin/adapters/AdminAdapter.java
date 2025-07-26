package com.example.stuadminlogin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.models.Admin;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {

    private List<Admin> adminList;
    private OnAdminActionListener listener;

    public interface OnAdminActionListener {
        void onEdit(Admin admin);
        void onDelete(Admin admin);
    }

    public AdminAdapter(List<Admin> adminList, OnAdminActionListener listener) {
        this.adminList = adminList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        Admin admin = adminList.get(position);
        holder.fullName.setText(admin.getFullName());
        holder.username.setText("Username: " + admin.getUsername());
        holder.email.setText("Email: " + admin.getEmailId());

        holder.editButton.setOnClickListener(v -> listener.onEdit(admin));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(admin));
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, username, email;
        Button editButton, deleteButton;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.tvFullName);
            username = itemView.findViewById(R.id.tvUsername);
            email = itemView.findViewById(R.id.tvEmail);
            editButton = itemView.findViewById(R.id.btnEdit);
            deleteButton = itemView.findViewById(R.id.btnDelete);
        }
    }
}