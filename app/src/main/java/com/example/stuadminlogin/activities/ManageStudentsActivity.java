package com.example.stuadminlogin.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.StudentListAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class ManageStudentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentListAdapter adapter;
    private List<StudentModel> studentList;
    private DatabaseHelper db;
    private int sectionId, classId;
    private Button addStudentButton, filterButton, backButton;
    private static final String TAG = "ManageStudentsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        db = new DatabaseHelper(this);

        // Fetch section ID passed from previous activity
        sectionId = getIntent().getIntExtra("section_id", -1);
        classId = getIntent().getIntExtra("class_id", -1);

        if (sectionId == -1) {
            Toast.makeText(this, "Invalid section ID passed", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Section ID not passed or invalid");
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recycler_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addStudentButton = findViewById(R.id.btn_add_student);
        filterButton = findViewById(R.id.btn_filter_student);
        backButton = findViewById(R.id.btn_back_to_sections);

        studentList = db.getStudentsBySection(sectionId);
        adapter = new StudentListAdapter(this, studentList, db);
        recyclerView.setAdapter(adapter);

        addStudentButton.setOnClickListener(v -> showAddStudentDialog());
        filterButton.setOnClickListener(v -> showFilterDialog());
        backButton.setOnClickListener(v -> finish());
    }

    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null);
        builder.setView(view);

        EditText roll = view.findViewById(R.id.et_roll);
        EditText reg = view.findViewById(R.id.et_reg);
        EditText name = view.findViewById(R.id.et_name);
        EditText email = view.findViewById(R.id.et_email);
        EditText phone = view.findViewById(R.id.et_phone);
        EditText address = view.findViewById(R.id.et_address);
        EditText admissionDate = view.findViewById(R.id.et_admission_date);
        EditText father = view.findViewById(R.id.et_father);
        EditText mother = view.findViewById(R.id.et_mother);
        EditText dob = view.findViewById(R.id.et_dob);
        EditText password = view.findViewById(R.id.et_password);

        builder.setPositiveButton("Add", (dialog, which) -> {
            try {
                StudentModel student = new StudentModel();
                student.setSectionId(sectionId);
                student.setClassId(classId);
                student.setRollNo(roll.getText().toString().trim());
                student.setRegistrationNo(reg.getText().toString().trim());
                student.setName(name.getText().toString().trim());
                student.setEmail(email.getText().toString().trim());
                student.setPhoneNo(phone.getText().toString().trim());
                student.setAddress(address.getText().toString().trim());
                student.setAdmissionDate(admissionDate.getText().toString().trim());
                student.setFatherName(father.getText().toString().trim());
                student.setMotherName(mother.getText().toString().trim());
                student.setDob(dob.getText().toString().trim());
                student.setPassword(password.getText().toString().trim());

                boolean inserted = db.insertStudent(student);
                if (inserted) {
                    Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
                    refreshStudentList();
                } else {
                    Toast.makeText(this, "Error adding student", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error adding student", e);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_filter_student, null);
        builder.setView(view);

        EditText filterName = view.findViewById(R.id.filterName);
        EditText filterRollNo = view.findViewById(R.id.filterRollNo);
        EditText filterRegNo = view.findViewById(R.id.filterRegNo);
        EditText filterEmail = view.findViewById(R.id.filterEmail);
        EditText filterPhone = view.findViewById(R.id.filterPhone);
        EditText filterAddress = view.findViewById(R.id.filterAddress);
        EditText filterAdmissionDate = view.findViewById(R.id.filterAdmissionDate);
        EditText filterFatherName = view.findViewById(R.id.filterFatherName);
        EditText filterMotherName = view.findViewById(R.id.filterMotherName);
        EditText filterDob = view.findViewById(R.id.filterDob);
        Button btnClearFilters = view.findViewById(R.id.btnClearFilters);

        AlertDialog dialog = builder.create();

        btnClearFilters.setOnClickListener(v -> {
            filterName.setText("");
            filterRollNo.setText("");
            filterRegNo.setText("");
            filterEmail.setText("");
            filterPhone.setText("");
            filterAddress.setText("");
            filterAdmissionDate.setText("");
            filterFatherName.setText("");
            filterMotherName.setText("");
            filterDob.setText("");
        });

        builder.setPositiveButton("Filter", (d, which) -> {
            try {
                List<StudentModel> filteredList = new ArrayList<>();

                for (StudentModel student : db.getStudentsBySection(sectionId)) {
                    boolean matches = true;
                    
                    if (!TextUtils.isEmpty(filterName.getText())) {
                        matches = matches && student.getName().toLowerCase()
                            .contains(filterName.getText().toString().trim().toLowerCase());
                    }
                    if (!TextUtils.isEmpty(filterRollNo.getText())) {
                        matches = matches && student.getRollNo().equalsIgnoreCase(
                            filterRollNo.getText().toString().trim());
                    }
                    if (!TextUtils.isEmpty(filterRegNo.getText())) {
                        matches = matches && student.getRegistrationNo().equalsIgnoreCase(
                            filterRegNo.getText().toString().trim());
                    }
                    if (!TextUtils.isEmpty(filterEmail.getText())) {
                        matches = matches && student.getEmail().toLowerCase()
                            .contains(filterEmail.getText().toString().trim().toLowerCase());
                    }
                    if (!TextUtils.isEmpty(filterPhone.getText())) {
                        matches = matches && student.getPhoneNo().contains(
                            filterPhone.getText().toString().trim());
                    }
                    if (!TextUtils.isEmpty(filterAddress.getText())) {
                        matches = matches && student.getAddress().toLowerCase()
                            .contains(filterAddress.getText().toString().trim().toLowerCase());
                    }
                    if (!TextUtils.isEmpty(filterAdmissionDate.getText())) {
                        matches = matches && student.getAdmissionDate().equals(
                            filterAdmissionDate.getText().toString().trim());
                    }
                    if (!TextUtils.isEmpty(filterFatherName.getText())) {
                        matches = matches && student.getFatherName().toLowerCase()
                            .contains(filterFatherName.getText().toString().trim().toLowerCase());
                    }
                    if (!TextUtils.isEmpty(filterMotherName.getText())) {
                        matches = matches && student.getMotherName().toLowerCase()
                            .contains(filterMotherName.getText().toString().trim().toLowerCase());
                    }
                    if (!TextUtils.isEmpty(filterDob.getText())) {
                        matches = matches && student.getDob().equals(
                            filterDob.getText().toString().trim());
                    }

                    if (matches) {
                        filteredList.add(student);
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(this, "No students match the filters", Toast.LENGTH_SHORT).show();
                }
                adapter.updateList(filteredList);
            } catch (Exception e) {
                Toast.makeText(this, "Error during filtering: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Filtering error", e);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void refreshStudentList() {
        studentList = db.getStudentsBySection(sectionId);
        adapter.updateList(studentList);
        adapter.notifyDataSetChanged();
    }
}