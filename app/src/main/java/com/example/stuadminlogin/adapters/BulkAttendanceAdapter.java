// package com.example.stuadminlogin.adapters;

// import android.view.*;
// import android.widget.*;
// import androidx.annotation.NonNull;
// import androidx.recyclerview.widget.RecyclerView;
// import android.graphics.Color;
// import java.util.HashMap;
// import java.util.List;


// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.models.StudentModel;

// import java.util.*;

// public class BulkAttendanceAdapter extends RecyclerView.Adapter<BulkAttendanceAdapter.ViewHolder> {

//     private List<StudentModel> studentList;
//     private Map<Integer, String> attendanceMap = new HashMap<>();

//    public BulkAttendanceAdapter(Context context, List<StudentModel> students, Map<Integer, String> existingAttendance) {
//     this.context = context;
//     this.students = students;
//     this.attendanceMap = new HashMap<>(existingAttendance); // pre-fill map
// }


//     public Map<Integer, String> getAttendanceMap() {
//         return attendanceMap;
//     }

//     @NonNull
//     @Override
//     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bulk_attendance, parent, false);
//         return new ViewHolder(view);
//     }

//     @Override
// public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//     StudentModel student = studentList.get(position);
//     holder.tvStudentName.setText(student.getName());

//     // Reset selection state initially
//     resetButtonStyles(holder);

//     // Restore previously selected status (if any)
//     String currentStatus = attendanceMap.get(student.getStudentId());
//     if (currentStatus != null) {
//         setSelectedButtonStyle(holder, currentStatus);
//     }

//     // Set click listeners for buttons
//     holder.btnPresent.setOnClickListener(v -> {
//         attendanceMap.put(student.getStudentId(), "Present");
//         setSelectedButtonStyle(holder, "Present");
//     });

//     holder.btnAbsent.setOnClickListener(v -> {
//         attendanceMap.put(student.getStudentId(), "Absent");
//         setSelectedButtonStyle(holder, "Absent");
//     });

//     holder.btnLeave.setOnClickListener(v -> {
//         attendanceMap.put(student.getStudentId(), "Leave");
//         setSelectedButtonStyle(holder, "Leave");
//     });
// }

// private void resetButtonStyles(ViewHolder holder) {
//     holder.btnPresent.setBackgroundColor(Color.LTGRAY);
//     holder.btnAbsent.setBackgroundColor(Color.LTGRAY);
//     holder.btnLeave.setBackgroundColor(Color.LTGRAY);
// }

// private void setSelectedButtonStyle(ViewHolder holder, String status) {
//     resetButtonStyles(holder); // First reset all
//     switch (status) {
//         case "Present":
//             holder.btnPresent.setBackgroundColor(Color.GREEN);
//             break;
//         case "Absent":
//             holder.btnAbsent.setBackgroundColor(Color.RED);
//             break;
//         case "Leave":
//             holder.btnLeave.setBackgroundColor(Color.YELLOW);
//             break;
//     }
// }


//     @Override
//     public int getItemCount() {
//         return studentList.size();
//     }

//     static class ViewHolder extends RecyclerView.ViewHolder {
//         TextView tvStudentName;
//         Button btnPresent, btnAbsent, btnLeave;

//         ViewHolder(@NonNull View itemView) {
//             super(itemView);
//             tvStudentName = itemView.findViewById(R.id.tvStudentName);
//             btnPresent = itemView.findViewById(R.id.btnPresent);
//             btnAbsent = itemView.findViewById(R.id.btnAbsent);
//             btnLeave = itemView.findViewById(R.id.btnLeave);
//         }
//     }
// }



package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.models.StudentModel;

import java.util.*;

public class BulkAttendanceAdapter extends RecyclerView.Adapter<BulkAttendanceAdapter.ViewHolder> {

    private List<StudentModel> students;
    private Context context;
    private Map<Integer, String> attendanceMap;

    public BulkAttendanceAdapter(Context context, List<StudentModel> students, Map<Integer, String> existingAttendance) {
        this.context = context;
        this.students = students;
        this.attendanceMap = new HashMap<>(existingAttendance); // Copy for internal use
    }

    public Map<Integer, String> getAttendanceMap() {
        return attendanceMap;
    }

    @NonNull
    @Override
    public BulkAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bulk_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BulkAttendanceAdapter.ViewHolder holder, int position) {
        StudentModel student = students.get(position);
        holder.tvStudentName.setText(student.getName());

        resetButtonStyles(holder);

        String currentStatus = attendanceMap.get(student.getStudentId());
        if (currentStatus != null) {
            setSelectedButtonStyle(holder, currentStatus);
        }

        holder.btnPresent.setOnClickListener(v -> {
            attendanceMap.put(student.getStudentId(), "Present");
            setSelectedButtonStyle(holder, "Present");
        });

        holder.btnAbsent.setOnClickListener(v -> {
            attendanceMap.put(student.getStudentId(), "Absent");
            setSelectedButtonStyle(holder, "Absent");
        });

        holder.btnLeave.setOnClickListener(v -> {
            attendanceMap.put(student.getStudentId(), "Leave");
            setSelectedButtonStyle(holder, "Leave");
        });
    }

    private void resetButtonStyles(ViewHolder holder) {
        holder.btnPresent.setBackgroundColor(Color.LTGRAY);
        holder.btnAbsent.setBackgroundColor(Color.LTGRAY);
        holder.btnLeave.setBackgroundColor(Color.LTGRAY);
    }

    private void setSelectedButtonStyle(ViewHolder holder, String status) {
        resetButtonStyles(holder);
        switch (status) {
            case "Present":
                holder.btnPresent.setBackgroundColor(Color.GREEN);
                break;
            case "Absent":
                holder.btnAbsent.setBackgroundColor(Color.RED);
                break;
            case "Leave":
                holder.btnLeave.setBackgroundColor(Color.YELLOW);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName;
        Button btnPresent, btnAbsent, btnLeave;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            btnPresent = itemView.findViewById(R.id.btnPresent);
            btnAbsent = itemView.findViewById(R.id.btnAbsent);
            btnLeave = itemView.findViewById(R.id.btnLeave);
        }
    }
}


