package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.models.Attendance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.ViewHolder> {
    private final List<Attendance> list;
    private final LayoutInflater inflater;

    public StudentAttendanceAdapter(Context context, List<Attendance> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_attendance_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attendance a = list.get(position);
        String formattedDate = formatDateWithDay(a.getDate());
        holder.date.setText(formattedDate);
        holder.status.setText(a.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 👉 Date formatter: "2025-08-14" → "14 August 2025 (Thursday)"
    public String formatDateWithDay(String inputDate) {
    try {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = inputFormat.parse(inputDate);

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy (EEEE)", Locale.getDefault());
        return outputFormat.format(date);
    } catch (ParseException e) {
        e.printStackTrace();
        return inputDate; // fallback
    }
}

    // Add this to your StudentAttendanceAdapter class
public void updateList(List<Attendance> newList) {
    this.list.clear();
    if (newList != null) {
        this.list.addAll(newList);
    }
    notifyDataSetChanged();
}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.attendance_date);
            status = itemView.findViewById(R.id.attendance_status);
        }
    }
}
