package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import com.example.stuadminlogin.activities.ManageSectionsActivity;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.models.ClassModel;

import java.util.List;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder> {
    private List<ClassModel> classList;
    private Context context;
    private OnClassActionListener listener;

    public interface OnClassActionListener {
        void onEdit(ClassModel model);
        void onDelete(ClassModel model);
    }

    public ClassListAdapter(Context context, List<ClassModel> list, OnClassActionListener listener) {
        this.context = context;
        this.classList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassModel model = classList.get(position);
        holder.name.setText(model.getName());
        holder.code.setText("Code: " + model.getCode());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(model));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(model));

        holder.itemView.setOnClickListener(v -> {
    Intent intent = new Intent(context, ManageSectionsActivity.class);
    intent.putExtra("class_id", model.getId());
    intent.putExtra("class_name", model.getName()); // changed getClassName() to getName()
    context.startActivity(intent);
});

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView name, code;
        Button btnEdit, btnDelete;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textClassName);
            code = itemView.findViewById(R.id.textClassCode);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
