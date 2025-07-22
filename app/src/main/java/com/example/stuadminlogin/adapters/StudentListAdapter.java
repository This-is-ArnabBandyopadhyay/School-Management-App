package com.example.stuadminlogin.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.StudentModel;
import android.content.Intent;
import com.example.stuadminlogin.activities.ViewStudentDetailsActivity;


import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> implements Filterable {

    Context context;
    List<StudentModel> studentList;
    List<StudentModel> studentListFull;
    DatabaseHelper db;

    public StudentListAdapter(Context context, List<StudentModel> studentList, DatabaseHelper db) {
        this.context = context;
        this.studentList = studentList;
        this.db = db;
        this.studentListFull = new ArrayList<>(studentList);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentModel student = studentList.get(position);

        holder.name.setText(student.getName());
        holder.roll.setText("Roll: " + student.getRollNo());
        holder.email.setText("Email: " + student.getEmail());

        holder.edit.setOnClickListener(v -> showEditDialog(student, position));
        holder.delete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this student?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteStudent(student.getStudentId());
                        studentList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Student deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void updateList(List<StudentModel> updatedList) {
        studentList = updatedList;
        studentListFull = new ArrayList<>(updatedList);
        notifyDataSetChanged();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
    TextView name, roll, email;
    Button edit, delete;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_student_name);
        roll = itemView.findViewById(R.id.tv_student_roll);
        email = itemView.findViewById(R.id.tv_student_email);
        edit = itemView.findViewById(R.id.btn_edit_student);
        delete = itemView.findViewById(R.id.btn_delete_student);

        itemView.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                StudentModel student = studentList.get(position);
                Intent intent = new Intent(context, ViewStudentDetailsActivity.class);
                intent.putExtra("student_id", student.getStudentId());
                context.startActivity(intent);
            }
        });
    }
}

    @Override
    public Filter getFilter() {
        return studentFilter;
    }

    private final Filter studentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StudentModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(studentListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (StudentModel student : studentListFull) {
                    if (student.matches(filterPattern)) {
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
            studentList.clear();
            studentList.addAll((List<StudentModel>) results.values);
            notifyDataSetChanged();
        }
    };

    private void showEditDialog(StudentModel student, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_student, null);
        builder.setView(view);

        EditText roll = view.findViewById(R.id.et_roll);
        EditText reg = view.findViewById(R.id.et_reg);
        EditText name = view.findViewById(R.id.et_name);
        EditText email = view.findViewById(R.id.et_email);
        EditText phone = view.findViewById(R.id.et_phone);
        EditText father = view.findViewById(R.id.et_father);
        EditText mother = view.findViewById(R.id.et_mother);
        EditText dob = view.findViewById(R.id.et_dob);
        EditText password = view.findViewById(R.id.et_password);

        // Pre-fill existing data
        roll.setText(student.getRollNo());
        reg.setText(student.getRegistrationNo());
        name.setText(student.getName());
        email.setText(student.getEmail());
        phone.setText(student.getPhoneNo());
        father.setText(student.getFatherName());
        mother.setText(student.getMotherName());
        dob.setText(student.getDob());
        password.setText(student.getPassword());

        builder.setPositiveButton("Update", (dialog, which) -> {
            student.setRollNo(roll.getText().toString());
            student.setRegistrationNo(reg.getText().toString());
            student.setName(name.getText().toString());
            student.setEmail(email.getText().toString());
            student.setPhoneNo(phone.getText().toString());
            student.setFatherName(father.getText().toString());
            student.setMotherName(mother.getText().toString());
            student.setDob(dob.getText().toString());
            student.setPassword(password.getText().toString());

            db.updateStudent(student);
            notifyItemChanged(position);
            Toast.makeText(context, "Student updated", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
