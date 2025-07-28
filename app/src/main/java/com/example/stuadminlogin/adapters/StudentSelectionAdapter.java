package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.models.StudentModel;
import java.util.ArrayList;
import java.util.List;

public class StudentSelectionAdapter extends RecyclerView.Adapter<StudentSelectionAdapter.StudentViewHolder> implements Filterable {
    private Context context;
    private List<StudentModel> students;
    private List<StudentModel> studentsFull;
    private List<StudentModel> selectedStudents = new ArrayList<>();

    public StudentSelectionAdapter(Context context, List<StudentModel> students) {
        this.context = context;
        this.students = students;
        this.studentsFull = new ArrayList<>(students);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_selection, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentModel student = students.get(position);
        holder.tvStudentName.setText(student.getName());
        holder.tvRollNo.setText("Roll: " + student.getRollNo());

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selectedStudents.contains(student));
        
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedStudents.add(student);
            } else {
                selectedStudents.remove(student);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public List<StudentModel> getSelectedStudents() {
        return selectedStudents;
    }

    @Override
    public Filter getFilter() {
        return studentFilter;
    }

    private Filter studentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StudentModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(studentsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (StudentModel student : studentsFull) {
                    if (student.getName().toLowerCase().contains(filterPattern) || 
                        student.getRollNo().toLowerCase().contains(filterPattern)) {
                        filteredList.add(student);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            students.clear();
            students.addAll((List<StudentModel>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvRollNo;
        CheckBox checkBox;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_student_name);
            tvRollNo = itemView.findViewById(R.id.tv_roll_no);
            checkBox = itemView.findViewById(R.id.cb_select);
        }
    }
}