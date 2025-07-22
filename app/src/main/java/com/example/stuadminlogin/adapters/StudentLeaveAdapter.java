package com.example.stuadminlogin.adapters;

import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.models.LeaveApplication;
import java.util.List;
import com.example.stuadminlogin.R;

public class StudentLeaveAdapter extends RecyclerView.Adapter<StudentLeaveAdapter.VH> {
    private List<LeaveApplication> list;
    public StudentLeaveAdapter(List<LeaveApplication> list) { this.list = list; }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_student_leave, parent, false));
    }

    @Override
    public void onBindViewHolder(VH h, int pos) {
        LeaveApplication la = list.get(pos);
        h.tvDates.setText(la.getFromDate() + " → " + la.getToDate());
        h.tvReason.setText(la.getReason());
        h.tvStatus.setText(la.getStatus());
        h.tvAppliedAt.setText(la.getAppliedAt());

        if (!la.getStatus().equals("Pending")) {
            h.adminInfo.setVisibility(View.VISIBLE);
            h.adminInfo.setText("By " + la.getAdminName() +
                " at " + la.getReviewedAt() + ":\n" + la.getAdminResponse());
        } else {
            h.adminInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvDates, tvReason, tvStatus, tvAppliedAt, adminInfo;
        VH(View v) {
            super(v);
            tvDates = v.findViewById(R.id.tvDates);
            tvReason = v.findViewById(R.id.tvReason);
            tvStatus = v.findViewById(R.id.tvStatus);
            tvAppliedAt = v.findViewById(R.id.tvAppliedAt);
            adminInfo = v.findViewById(R.id.tvAdminInfo);
        }
    }
}
