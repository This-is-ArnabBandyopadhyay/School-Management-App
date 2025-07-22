package com.example.stuadminlogin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.models.Query;
import java.util.List;

public class PendingQueryAdapter extends RecyclerView.Adapter<PendingQueryAdapter.ViewHolder> {

    private List<Query> queryList;

    public PendingQueryAdapter(List<Query> queryList) {
        this.queryList = queryList;
    }

    @NonNull
    @Override
    public PendingQueryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_query, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Query query = queryList.get(position);
        holder.queryText.setText("Query: " + query.getQueryText());
        holder.generatedAt.setText("Asked on: " + query.getGeneratedAt());
    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView queryText, generatedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            queryText = itemView.findViewById(R.id.tvQueryText);
            generatedAt = itemView.findViewById(R.id.tvGeneratedAt);
        }
    }
}
