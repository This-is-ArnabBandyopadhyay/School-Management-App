// package com.example.stuadminlogin.activities;

// import android.app.AlertDialog;
// import android.os.Bundle;
// import android.text.TextUtils;
// import android.util.Log;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.widget.*;

// import androidx.annotation.Nullable;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.StudentListAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.StudentModel;

// import java.util.ArrayList;
// import java.util.List;

// public class ManageStudentsActivity extends AppCompatActivity {

//     private RecyclerView recyclerView;
//     private StudentListAdapter adapter;
//     private List<StudentModel> studentList;
//     private DatabaseHelper db;
//     private int sectionId, classId;
//     private Button addStudentButton, filterButton, backButton;
//     private static final String TAG = "ManageStudentsActivity";

//     @Override
//     protected void onCreate(@Nullable Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_manage_students);

//         db = new DatabaseHelper(this);

//         // Fetch section ID passed from previous activity
//         sectionId = getIntent().getIntExtra("section_id", -1);
//         classId = getIntent().getIntExtra("class_id", -1);

//         if (sectionId == -1) {
//             Toast.makeText(this, "Invalid section ID passed", Toast.LENGTH_SHORT).show();
//             Log.e(TAG, "Section ID not passed or invalid");
//             finish();
//             return;
//         }

//         recyclerView = findViewById(R.id.recycler_students);
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));

//         addStudentButton = findViewById(R.id.btn_add_student);
//         filterButton = findViewById(R.id.btn_filter_student);
//         backButton = findViewById(R.id.btn_back_to_sections);

//         studentList = db.getStudentsBySection(sectionId);
//         adapter = new StudentListAdapter(this, studentList, db);
//         recyclerView.setAdapter(adapter);

//         addStudentButton.setOnClickListener(v -> showAddStudentDialog());
//         filterButton.setOnClickListener(v -> showFilterDialog());
//         backButton.setOnClickListener(v -> finish());
//     }

//     private void showAddStudentDialog() {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null);
//         builder.setView(view);

//         EditText roll = view.findViewById(R.id.et_roll);
//         EditText reg = view.findViewById(R.id.et_reg);
//         EditText name = view.findViewById(R.id.et_name);
//         EditText email = view.findViewById(R.id.et_email);
//         EditText phone = view.findViewById(R.id.et_phone);
//         EditText address = view.findViewById(R.id.et_address);
//         EditText admissionDate = view.findViewById(R.id.et_admission_date);
//         EditText father = view.findViewById(R.id.et_father);
//         EditText mother = view.findViewById(R.id.et_mother);
//         EditText dob = view.findViewById(R.id.et_dob);
//         EditText password = view.findViewById(R.id.et_password);

//         builder.setPositiveButton("Add", (dialog, which) -> {
//             try {
//                 StudentModel student = new StudentModel();
//                 student.setSectionId(sectionId);
//                 student.setClassId(classId);
//                 student.setRollNo(roll.getText().toString().trim());
//                 student.setRegistrationNo(reg.getText().toString().trim());
//                 student.setName(name.getText().toString().trim());
//                 student.setEmail(email.getText().toString().trim());
//                 student.setPhoneNo(phone.getText().toString().trim());
//                 student.setAddress(address.getText().toString().trim());
//                 student.setAdmissionDate(admissionDate.getText().toString().trim());
//                 student.setFatherName(father.getText().toString().trim());
//                 student.setMotherName(mother.getText().toString().trim());
//                 student.setDob(dob.getText().toString().trim());
//                 student.setPassword(password.getText().toString().trim());

//                 boolean inserted = db.insertStudent(student);
//                 if (inserted) {
//                     Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
//                     refreshStudentList();
//                 } else {
//                     Toast.makeText(this, "Error adding student", Toast.LENGTH_SHORT).show();
//                 }
//             } catch (Exception e) {
//                 Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                 Log.e(TAG, "Error adding student", e);
//             }
//         });

//         builder.setNegativeButton("Cancel", null);
//         builder.show();
//     }

//     private void showFilterDialog() {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         View view = LayoutInflater.from(this).inflate(R.layout.dialog_filter_student, null);
//         builder.setView(view);

//         EditText filterName = view.findViewById(R.id.filterName);
//         EditText filterRollNo = view.findViewById(R.id.filterRollNo);
//         EditText filterRegNo = view.findViewById(R.id.filterRegNo);
//         EditText filterEmail = view.findViewById(R.id.filterEmail);
//         EditText filterPhone = view.findViewById(R.id.filterPhone);
//         EditText filterAddress = view.findViewById(R.id.filterAddress);
//         EditText filterAdmissionDate = view.findViewById(R.id.filterAdmissionDate);
//         EditText filterFatherName = view.findViewById(R.id.filterFatherName);
//         EditText filterMotherName = view.findViewById(R.id.filterMotherName);
//         EditText filterDob = view.findViewById(R.id.filterDob);
//         Button btnClearFilters = view.findViewById(R.id.btnClearFilters);

//         AlertDialog dialog = builder.create();

//         btnClearFilters.setOnClickListener(v -> {
//             filterName.setText("");
//             filterRollNo.setText("");
//             filterRegNo.setText("");
//             filterEmail.setText("");
//             filterPhone.setText("");
//             filterAddress.setText("");
//             filterAdmissionDate.setText("");
//             filterFatherName.setText("");
//             filterMotherName.setText("");
//             filterDob.setText("");
//         });

//         builder.setPositiveButton("Filter", (d, which) -> {
//             try {
//                 List<StudentModel> filteredList = new ArrayList<>();

//                 for (StudentModel student : db.getStudentsBySection(sectionId)) {
//                     boolean matches = true;
                    
//                     if (!TextUtils.isEmpty(filterName.getText())) {
//                         matches = matches && student.getName().toLowerCase()
//                             .contains(filterName.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterRollNo.getText())) {
//                         matches = matches && student.getRollNo().equalsIgnoreCase(
//                             filterRollNo.getText().toString().trim());
//                     }
//                     if (!TextUtils.isEmpty(filterRegNo.getText())) {
//                         matches = matches && student.getRegistrationNo().equalsIgnoreCase(
//                             filterRegNo.getText().toString().trim());
//                     }
//                     if (!TextUtils.isEmpty(filterEmail.getText())) {
//                         matches = matches && student.getEmail().toLowerCase()
//                             .contains(filterEmail.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterPhone.getText())) {
//                         matches = matches && student.getPhoneNo().contains(
//                             filterPhone.getText().toString().trim());
//                     }
//                     if (!TextUtils.isEmpty(filterAddress.getText())) {
//                         matches = matches && student.getAddress().toLowerCase()
//                             .contains(filterAddress.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterAdmissionDate.getText())) {
//                         matches = matches && student.getAdmissionDate().equals(
//                             filterAdmissionDate.getText().toString().trim());
//                     }
//                     if (!TextUtils.isEmpty(filterFatherName.getText())) {
//                         matches = matches && student.getFatherName().toLowerCase()
//                             .contains(filterFatherName.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterMotherName.getText())) {
//                         matches = matches && student.getMotherName().toLowerCase()
//                             .contains(filterMotherName.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterDob.getText())) {
//                         matches = matches && student.getDob().equals(
//                             filterDob.getText().toString().trim());
//                     }

//                     if (matches) {
//                         filteredList.add(student);
//                     }
//                 }

//                 if (filteredList.isEmpty()) {
//                     Toast.makeText(this, "No students match the filters", Toast.LENGTH_SHORT).show();
//                 }
//                 adapter.updateList(filteredList);
//             } catch (Exception e) {
//                 Toast.makeText(this, "Error during filtering: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                 Log.e(TAG, "Filtering error", e);
//             }
//         });

//         builder.setNegativeButton("Cancel", null);
//         builder.show();
//     }

//     private void refreshStudentList() {
//         studentList = db.getStudentsBySection(sectionId);
//         adapter.updateList(studentList);
//         adapter.notifyDataSetChanged();
//     }
// }



// package com.example.stuadminlogin.activities;

// import android.app.AlertDialog;
// import android.os.Bundle;
// import android.text.TextUtils;
// import android.util.Log;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.widget.*;

// import androidx.annotation.Nullable;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.appcompat.widget.Toolbar; // Import Toolbar
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.StudentListAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.StudentModel;

// import java.util.ArrayList;
// import java.util.List;

// public class ManageStudentsActivity extends AppCompatActivity {

//     private RecyclerView recyclerView;
//     private StudentListAdapter adapter;
//     private List<StudentModel> studentList;
//     private DatabaseHelper db;
//     private int sectionId, classId;
//     private Button addStudentButton, filterButton;
//     // Removed: private Button backButton; // Replaced by Toolbar's back arrow
//     private static final String TAG = "ManageStudentsActivity";

//     @Override
//     protected void onCreate(@Nullable Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_manage_students);

//         db = new DatabaseHelper(this);

//         // Fetch section ID and class ID passed from previous activity
//         sectionId = getIntent().getIntExtra("section_id", -1);
//         classId = getIntent().getIntExtra("class_id", -1);

//         if (sectionId == -1) {
//             Toast.makeText(this, "Invalid section ID passed", Toast.LENGTH_SHORT).show();
//             Log.e(TAG, "Section ID not passed or invalid");
//             finish();
//             return;
//         }

//         // --- Toolbar Setup ---
//         Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
//         setSupportActionBar(toolbar); // Set it as the Activity's support action bar

//         // Optional: Customize Toolbar title and back button
//         if (getSupportActionBar() != null) {
//             // You might want to fetch section name and class name from the DB for a better title
//             // For now, using IDs:
//             getSupportActionBar().setTitle("Students for Section " + sectionId + " (Class " + classId + ")");
//             getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
//             getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
//         }
//         // --- End Toolbar Setup ---


//         recyclerView = findViewById(R.id.recycler_students);
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));

//         addStudentButton = findViewById(R.id.btn_add_student);
//         filterButton = findViewById(R.id.btn_filter_student);
//         // Removed: backButton = findViewById(R.id.btn_back_to_sections); // Replaced by Toolbar

//         studentList = db.getStudentsBySection(sectionId);
//         adapter = new StudentListAdapter(this, studentList, db); // Assuming StudentListAdapter constructor is compatible
//         recyclerView.setAdapter(adapter);

//         addStudentButton.setOnClickListener(v -> showAddStudentDialog());
//         filterButton.setOnClickListener(v -> showFilterDialog());
//         // Removed: backButton.setOnClickListener(v -> finish()); // Replaced by Toolbar's back navigation
//     }

//     // Handle back button press on the Toolbar
//     @Override
//     public boolean onSupportNavigateUp() {
//         onBackPressed(); // This will navigate back to the previous activity (ManageSectionsActivity)
//         return true;
//     }

//     private void showAddStudentDialog() {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null);
//         builder.setView(view);

//         EditText roll = view.findViewById(R.id.et_roll);
//         EditText reg = view.findViewById(R.id.et_reg);
//         EditText name = view.findViewById(R.id.et_name);
//         EditText email = view.findViewById(R.id.et_email);
//         EditText phone = view.findViewById(R.id.et_phone);
//         EditText address = view.findViewById(R.id.et_address);
//         EditText admissionDate = view.findViewById(R.id.et_admission_date);
//         EditText father = view.findViewById(R.id.et_father);
//         EditText mother = view.findViewById(R.id.et_mother);
//         EditText dob = view.findViewById(R.id.et_dob);
//         EditText password = view.findViewById(R.id.et_password);

//         builder.setPositiveButton("Add", (dialog, which) -> {
//             try {
//                 // Basic validation for critical fields
//                 if (TextUtils.isEmpty(roll.getText()) || TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(password.getText())) {
//                     Toast.makeText(this, "Roll No, Name, and Password are required!", Toast.LENGTH_SHORT).show();
//                     return; // Prevent dialog dismissal on empty required fields
//                 }

//                 StudentModel student = new StudentModel();
//                 student.setSectionId(sectionId);
//                 student.setClassId(classId); // Ensure classId is also set for the student
//                 student.setRollNo(roll.getText().toString().trim());
//                 student.setRegistrationNo(reg.getText().toString().trim());
//                 student.setName(name.getText().toString().trim());
//                 student.setEmail(email.getText().toString().trim());
//                 student.setPhoneNo(phone.getText().toString().trim());
//                 student.setAddress(address.getText().toString().trim());
//                 student.setAdmissionDate(admissionDate.getText().toString().trim());
//                 student.setFatherName(father.getText().toString().trim());
//                 student.setMotherName(mother.getText().toString().trim());
//                 student.setDob(dob.getText().toString().trim());
//                 student.setPassword(password.getText().toString().trim());

//                 boolean inserted = db.insertStudent(student);
//                 if (inserted) {
//                     Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
//                     refreshStudentList();
//                 } else {
//                     Toast.makeText(this, "Error adding student (e.g., duplicate Roll/Reg No)", Toast.LENGTH_SHORT).show();
//                 }
//             } catch (Exception e) {
//                 Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                 Log.e(TAG, "Error adding student", e);
//             }
//         });

//         builder.setNegativeButton("Cancel", null);
//         builder.show();
//     }

//     private void showFilterDialog() {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         View view = LayoutInflater.from(this).inflate(R.layout.dialog_filter_student, null);
//         builder.setView(view);
//         builder.setTitle("Filter Students"); // Set dialog title here

//         EditText filterName = view.findViewById(R.id.filterName);
//         EditText filterRollNo = view.findViewById(R.id.filterRollNo);
//         EditText filterRegNo = view.findViewById(R.id.filterRegNo);
//         EditText filterEmail = view.findViewById(R.id.filterEmail);
//         EditText filterPhone = view.findViewById(R.id.filterPhone);
//         EditText filterAddress = view.findViewById(R.id.filterAddress);
//         EditText filterAdmissionDate = view.findViewById(R.id.filterAdmissionDate);
//         EditText filterFatherName = view.findViewById(R.id.filterFatherName);
//         EditText filterMotherName = view.findViewById(R.id.filterMotherName);
//         EditText filterDob = view.findViewById(R.id.filterDob);
//         Button btnClearFilters = view.findViewById(R.id.btnClearFilters); // Added this button in the dialog

//         AlertDialog dialog = builder.create(); // Create dialog instance

//         btnClearFilters.setOnClickListener(v -> {
//             filterName.setText("");
//             filterRollNo.setText("");
//             filterRegNo.setText("");
//             filterEmail.setText("");
//             filterPhone.setText("");
//             filterAddress.setText("");
//             filterAdmissionDate.setText("");
//             filterFatherName.setText("");
//             filterMotherName.setText("");
//             filterDob.setText("");
//             // No need to dismiss here, user might want to apply empty filters
//         });

//         dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Filter", (d, which) -> { // Use dialog.setButton
//             try {
//                 List<StudentModel> filteredList = new ArrayList<>();
//                 List<StudentModel> allStudentsInSection = db.getStudentsBySection(sectionId); // Get fresh list

//                 for (StudentModel student : allStudentsInSection) {
//                     boolean matches = true;

//                     // Apply filters based on input. Case-insensitive for names/emails/addresses
//                     if (!TextUtils.isEmpty(filterName.getText())) {
//                         matches = matches && student.getName().toLowerCase()
//                             .contains(filterName.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterRollNo.getText())) {
//                         matches = matches && student.getRollNo().equalsIgnoreCase(
//                             filterRollNo.getText().toString().trim());
//                     }
//                     if (!TextUtils.isEmpty(filterRegNo.getText())) {
//                         matches = matches && student.getRegistrationNo().equalsIgnoreCase(
//                             filterRegNo.getText().toString().trim());
//                     }
//                     if (!TextUtils.isEmpty(filterEmail.getText())) {
//                         matches = matches && student.getEmail().toLowerCase()
//                             .contains(filterEmail.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterPhone.getText())) {
//                         matches = matches && student.getPhoneNo().contains( // phone could be partial match
//                             filterPhone.getText().toString().trim());
//                     }
//                     if (!TextUtils.isEmpty(filterAddress.getText())) {
//                         matches = matches && student.getAddress().toLowerCase()
//                             .contains(filterAddress.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterAdmissionDate.getText())) {
//                         matches = matches && student.getAdmissionDate().equals(
//                             filterAdmissionDate.getText().toString().trim());
//                     }
//                     if (!TextUtils.isEmpty(filterFatherName.getText())) {
//                         matches = matches && student.getFatherName().toLowerCase()
//                             .contains(filterFatherName.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterMotherName.getText())) {
//                         matches = matches && student.getMotherName().toLowerCase()
//                             .contains(filterMotherName.getText().toString().trim().toLowerCase());
//                     }
//                     if (!TextUtils.isEmpty(filterDob.getText())) {
//                         matches = matches && student.getDob().equals(
//                             filterDob.getText().toString().trim());
//                     }

//                     if (matches) {
//                         filteredList.add(student);
//                     }
//                 }

//                 if (filteredList.isEmpty()) {
//                     Toast.makeText(this, "No students match the filters", Toast.LENGTH_SHORT).show();
//                 }
//                 adapter.updateList(filteredList); // Update RecyclerView with filtered list
//                 // No need to dismiss here as the system will do it after the button click
//             } catch (Exception e) {
//                 Toast.makeText(this, "Error during filtering: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                 Log.e(TAG, "Filtering error", e);
//             }
//         });

//         dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (d, which) -> dialog.dismiss()); // Use dialog.setButton
//         dialog.show();
//     }

//     private void refreshStudentList() {
//         studentList.clear(); // Clear existing list
//         studentList.addAll(db.getStudentsBySection(sectionId)); // Populate with fresh data
//         adapter.notifyDataSetChanged(); // Notify adapter of data change
//     }
// }



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
import androidx.appcompat.widget.Toolbar; // Import Toolbar
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
    private Button addStudentButton, filterButton;
    private static final String TAG = "ManageStudentsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        db = new DatabaseHelper(this);

        // Fetch section ID and class ID passed from previous activity
        sectionId = getIntent().getIntExtra("section_id", -1);
        classId = getIntent().getIntExtra("class_id", -1);

        if (sectionId == -1) {
            Toast.makeText(this, "Invalid section ID passed", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Section ID not passed or invalid");
            finish();
            return;
        }

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fetch section name and class name
        String sectionName = db.getSectionNameById(sectionId);
        String className = db.getClassNameById(classId);

        if (getSupportActionBar() != null) {
            String title = "Students";
            if (sectionName != null && className != null) {
                title = "Students : Section " + sectionName + "(" + className + ")";
            } else if (sectionName != null) {
                title = "Students in " + sectionName;
            } else if (className != null) {
                title = "Students in Class " + className;
            }
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // --- End Toolbar Setup ---

        recyclerView = findViewById(R.id.recycler_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addStudentButton = findViewById(R.id.btn_add_student);
        filterButton = findViewById(R.id.btn_filter_student);

        studentList = db.getStudentsBySection(sectionId);
        adapter = new StudentListAdapter(this, studentList, db);
        recyclerView.setAdapter(adapter);

        addStudentButton.setOnClickListener(v -> showAddStudentDialog());
        filterButton.setOnClickListener(v -> showFilterDialog());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                if (TextUtils.isEmpty(roll.getText()) || TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(this, "Roll No, Name, and Password are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                    Toast.makeText(this, "Error adding student (e.g., duplicate Roll/Reg No)", Toast.LENGTH_SHORT).show();
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
        builder.setTitle("Filter Students");

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

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Filter", (d, which) -> {
            try {
                List<StudentModel> filteredList = new ArrayList<>();
                List<StudentModel> allStudentsInSection = db.getStudentsBySection(sectionId);

                for (StudentModel student : allStudentsInSection) {
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

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (d, which) -> dialog.dismiss());
        dialog.show();
    }

    private void refreshStudentList() {
        studentList.clear();
        studentList.addAll(db.getStudentsBySection(sectionId));
        adapter.notifyDataSetChanged();
    }
}