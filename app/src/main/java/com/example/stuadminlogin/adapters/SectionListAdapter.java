package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
//import com.example.stuadminlogin.activities.ManageStudentsActivity;
import com.example.stuadminlogin.models.SectionModel;

import java.util.List;

public class SectionListAdapter extends RecyclerView.Adapter<SectionListAdapter.SectionViewHolder> {
    

    public interface OnSectionActionListener {
        void onEdit(SectionModel model);
        void onDelete(SectionModel model);
    }

    private List<SectionModel> sectionList;
    private OnSectionActionListener listener;

    public SectionListAdapter(List<SectionModel> sectionList, OnSectionActionListener listener) {
        this.sectionList = sectionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
    SectionModel section = sectionList.get(position);
    
      // Add "Section " prefix to the section name
    String sectionName = "Section " + section.getName();
    holder.textSectionName.setText(sectionName);

    holder.btnEdit.setOnClickListener(v -> listener.onEdit(section));
    holder.btnDelete.setOnClickListener(v -> listener.onDelete(section));

    // ✅ Open ViewSectionDetailsActivity when item is clicked
    holder.itemView.setOnClickListener(v -> {
        Context context = v.getContext();
        Intent intent = new Intent(context, com.example.stuadminlogin.activities.ViewSectionDetailsActivity.class);
        intent.putExtra("section_id", section.getId());
        intent.putExtra("class_id", section.getClassId()); // changed getClassName() to getClassId()
        context.startActivity(intent);
    });
}


    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    static class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView textSectionName;
        Button btnEdit, btnDelete;

        SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            textSectionName = itemView.findViewById(R.id.textSectionName);
            btnEdit = itemView.findViewById(R.id.btnEditSection);
            btnDelete = itemView.findViewById(R.id.btnDeleteSection);
        }
    }
}
