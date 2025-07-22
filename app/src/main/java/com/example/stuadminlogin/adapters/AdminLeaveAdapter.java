package com.example.stuadminlogin.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.models.LeaveApplication;
import java.util.List;
import com.example.stuadminlogin.R;

public class AdminLeaveAdapter extends RecyclerView.Adapter<AdminLeaveAdapter.VH> {
    public interface Callback {
        void onRespond(int leaveId, String responseText, boolean approve);
    }

    private List<LeaveApplication> list;
    private Callback cb;

    public AdminLeaveAdapter(List<LeaveApplication> list, Callback cb) {
        this.list = list;
        this.cb = cb;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_admin_leave, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH h, int pos) {
        LeaveApplication la = list.get(pos);
        h.tvName.setText("Name: " + la.getStudentName());
        h.tvRoll.setText("Roll No: " + la.getRollNo());
        h.tvClassSec.setText("Class: " + la.getStudentClass() + " - " + la.getSection());
        h.tvDates.setText("Dates: " + la.getFromDate() + " → " + la.getToDate());
        h.tvReason.setText("Reason: " + la.getReason());
        h.tvAppliedAt.setText("Applied At: " + la.getAppliedAt());

        h.btnApprove.setOnClickListener(v -> promptResponse(la.getLeaveId(), true, h.itemView.getContext()));
        h.btnReject.setOnClickListener(v -> promptResponse(la.getLeaveId(), false, h.itemView.getContext()));
    }

    private void promptResponse(int leaveId, boolean approve, Context ctx) {
        AlertDialog.Builder b = new AlertDialog.Builder(ctx);
        b.setTitle(approve ? "Approve with response" : "Reject with response");
        final EditText input = new EditText(ctx);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        b.setView(input);
        b.setPositiveButton("Submit", (d, w) -> cb.onRespond(leaveId, input.getText().toString(), approve));
        b.setNegativeButton("Cancel", null);
        b.show();
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvRoll, tvClassSec, tvDates, tvReason, tvAppliedAt;
        Button btnApprove, btnReject;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvRoll = v.findViewById(R.id.tvRoll);
            tvClassSec = v.findViewById(R.id.tvClassSec);
            tvDates = v.findViewById(R.id.tvDates);
            tvReason = v.findViewById(R.id.tvReason);
            tvAppliedAt = v.findViewById(R.id.tvAppliedAt);
            btnApprove = v.findViewById(R.id.btnApprove);
            btnReject = v.findViewById(R.id.btnReject);
        }
    }
}
