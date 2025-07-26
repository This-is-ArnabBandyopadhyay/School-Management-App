package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.models.Holiday;

import java.util.List;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.VH> {

    public interface HolidayCallback {
        void onEdit(Holiday holiday);
        void onDelete(Holiday holiday);
    }

    private final List<Holiday> list;
    private final HolidayCallback callback;
    private final boolean isAdmin;

    public HolidayAdapter(List<Holiday> list, HolidayCallback callback, boolean isAdmin) {
        this.list = list;
        this.callback = callback;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holiday, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        Holiday holiday = list.get(i);
        h.title.setText(holiday.getTitle());
        h.description.setText(holiday.getDescription());
        h.date.setText("Date: " + holiday.getHolidayDate());

        if (isAdmin) {
            h.btnEdit.setVisibility(View.VISIBLE);
            h.btnDelete.setVisibility(View.VISIBLE);
        }

        h.btnEdit.setOnClickListener(v -> callback.onEdit(holiday));
        h.btnDelete.setOnClickListener(v -> callback.onDelete(holiday));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, description, date;
        Button btnEdit, btnDelete;

        public VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.holidayTitle);
            description = itemView.findViewById(R.id.holidayDescription);
            date = itemView.findViewById(R.id.holidayDate);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
