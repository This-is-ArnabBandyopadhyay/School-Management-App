package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.models.StudentModel;

import java.util.List;

public class StudentListAdapterForParent extends ArrayAdapter<StudentModel> {

    private Context context;
    private List<StudentModel> studentList;

    public StudentListAdapterForParent(Context context, List<StudentModel> studentList) {
        super(context, 0, studentList);
        this.context = context;
        this.studentList = studentList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_student_for_parent, parent, false);
        }

        StudentModel student = studentList.get(position);

        TextView studentNameTextView = convertView.findViewById(R.id.studentNameTextView);
        TextView studentDetailsTextView = convertView.findViewById(R.id.studentDetailsTextView);

        studentNameTextView.setText(student.getName());
        String details = "Roll No: " + student.getRollNo() +
                         " | Class: " + student.getStudentClass() +
                         " " + student.getSection();
        studentDetailsTextView.setText(details);

        return convertView;
    }
}