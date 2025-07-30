// package com.example.stuadminlogin.activities;

// import android.app.AlertDialog;
// import android.content.ContentValues;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.text.TextUtils;
// import android.view.LayoutInflater;
// import android.widget.*;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
// import android.view.View;


// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.ClassListAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.ClassModel;

// import java.util.ArrayList;
// import java.util.List;

// public class ManageClassesActivity extends AppCompatActivity {
//     DatabaseHelper dbHelper;
//     RecyclerView recyclerView;
//     Button btnAdd, btnSearch;
//     List<ClassModel> classList;
//     ClassListAdapter adapter;
//     TextView textNoClasses;


//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_manage_classes);

//         dbHelper = new DatabaseHelper(this);
//         recyclerView = findViewById(R.id.recyclerViewClasses);
//         btnAdd = findViewById(R.id.btnAddClass);
//         btnSearch = findViewById(R.id.btnSearchClass);
//         textNoClasses = findViewById(R.id.textNoClasses);


//         recyclerView.setLayoutManager(new LinearLayoutManager(this));
//         loadClasses(null, null);

//         btnAdd.setOnClickListener(v -> showClassDialog(null));
//         btnSearch.setOnClickListener(v -> showFilterDialog());
//     }

//     private void showClassDialog(ClassModel editModel) {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         builder.setTitle(editModel == null ? "Add Class" : "Edit Class");

//         LinearLayout layout = new LinearLayout(this);
//         layout.setOrientation(LinearLayout.VERTICAL);
//         layout.setPadding(20, 20, 20, 20);

//         EditText nameInput = new EditText(this);
//         nameInput.setHint("Class Name");
//         layout.addView(nameInput);

//         EditText codeInput = new EditText(this);
//         codeInput.setHint("Class Code");
//         layout.addView(codeInput);

//         if (editModel != null) {
//             nameInput.setText(editModel.getName());
//             codeInput.setText(editModel.getCode());
//         }

//         builder.setView(layout);

//         builder.setPositiveButton("Save", (dialog, which) -> {
//             String name = nameInput.getText().toString().trim();
//             String code = codeInput.getText().toString().trim();

//             if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code)) {
//                 Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
//                 return;
//             }

//             SQLiteDatabase db = dbHelper.getWritableDatabase();
//             ContentValues values = new ContentValues();
//             values.put("class_name", name);
//             values.put("class_code", code);

//             if (editModel == null) {
//                 db.insert("classes", null, values);
//             } else {
//                 db.update("classes", values, "class_id=?", new String[]{String.valueOf(editModel.getId())});
//             }

//             loadClasses(null, null);
//         });

//         builder.setNegativeButton("Cancel", null);
//         builder.show();
//     }

//     private void showFilterDialog() {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         LayoutInflater inflater = getLayoutInflater();
//         final View dialogView = inflater.inflate(R.layout.dialog_filter_class, null);
//         builder.setView(dialogView);
//         builder.setTitle("Filter Classes");

//         final EditText nameFilter = dialogView.findViewById(R.id.editFilterName);
//         final EditText codeFilter = dialogView.findViewById(R.id.editFilterCode);
//         Button btnApply = dialogView.findViewById(R.id.btnApplyFilter);

//         final AlertDialog dialog = builder.create();
//         btnApply.setOnClickListener(v -> {
//             loadClasses(nameFilter.getText().toString().trim(), codeFilter.getText().toString().trim());
//             dialog.dismiss();
//         });

//         dialog.show();
//     }

//     private void loadClasses(String nameFilter, String codeFilter) {
//         classList = new ArrayList<>();
//         SQLiteDatabase db = dbHelper.getReadableDatabase();
//         String query = "SELECT * FROM classes WHERE 1=1";
//         List<String> args = new ArrayList<>();

//         if (!TextUtils.isEmpty(nameFilter)) {
//             query += " AND class_name LIKE ?";
//             args.add("%" + nameFilter + "%");
//         }

//         if (!TextUtils.isEmpty(codeFilter)) {
//             query += " AND class_code LIKE ?";
//             args.add("%" + codeFilter + "%");
//         }

//         Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));
//         while (cursor.moveToNext()) {
//             classList.add(new ClassModel(
//                 cursor.getInt(0),
//                 cursor.getString(1),
//                 cursor.getString(2)
//             ));
//         }
//         cursor.close();

//         if (classList.isEmpty()) {
//     textNoClasses.setVisibility(View.VISIBLE);
//     recyclerView.setVisibility(View.GONE);
// } else {
//     textNoClasses.setVisibility(View.GONE);
//     recyclerView.setVisibility(View.VISIBLE);
// }

//         adapter = new ClassListAdapter(this, classList, new ClassListAdapter.OnClassActionListener() {
//             @Override
//             public void onEdit(ClassModel model) {
//                 showClassDialog(model);
//             }

//             @Override
//             public void onDelete(ClassModel model) {
//                 db.delete("classes", "class_id=?", new String[]{String.valueOf(model.getId())});
//                 loadClasses(null, null);
//             }
//         });

//         recyclerView.setAdapter(adapter);
//     }
// }



// package com.example.stuadminlogin.activities;

// import android.app.AlertDialog;
// import android.content.ContentValues;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.text.TextUtils;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.widget.Button;
// import android.widget.EditText;
// import android.widget.TextView;
// import android.widget.Toast;

// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.ClassListAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.ClassModel;
// import com.google.android.material.textfield.TextInputEditText;

// import java.util.ArrayList;
// import java.util.List;

// public class ManageClassesActivity extends AppCompatActivity {
//     DatabaseHelper dbHelper;
//     RecyclerView recyclerView;
//     Button btnAdd, btnSearch;
//     List<ClassModel> classList;
//     ClassListAdapter adapter;
//     TextView textNoClasses;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_manage_classes);

//         dbHelper = new DatabaseHelper(this);
//         recyclerView = findViewById(R.id.recyclerViewClasses);
//         btnAdd = findViewById(R.id.btnAddClass);
//         btnSearch = findViewById(R.id.btnSearchClass);
//         textNoClasses = findViewById(R.id.textNoClasses);

//         recyclerView.setLayoutManager(new LinearLayoutManager(this));
//         loadClasses(null, null);

//         btnAdd.setOnClickListener(v -> showClassDialog(null));
//         btnSearch.setOnClickListener(v -> showFilterDialog());
//     }

//     private void showClassDialog(ClassModel editModel) {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_class, null);
//         builder.setView(dialogView);

//         TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
//         TextInputEditText nameInput = dialogView.findViewById(R.id.editClassName);
//         TextInputEditText codeInput = dialogView.findViewById(R.id.editClassCode);
//         Button btnSave = dialogView.findViewById(R.id.btnSave);
//         Button btnCancel = dialogView.findViewById(R.id.btnCancel);

//         if (editModel != null) {
//             dialogTitle.setText("Edit Class");
//             nameInput.setText(editModel.getName());
//             codeInput.setText(editModel.getCode());
//         }

//         AlertDialog dialog = builder.create();
        
//         btnSave.setOnClickListener(v -> {
//             String name = nameInput.getText().toString().trim();
//             String code = codeInput.getText().toString().trim();

//             if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code)) {
//                 Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
//                 return;
//             }

//             SQLiteDatabase db = dbHelper.getWritableDatabase();
//             ContentValues values = new ContentValues();
//             values.put("class_name", name);
//             values.put("class_code", code);

//             if (editModel == null) {
//                 db.insert("classes", null, values);
//             } else {
//                 db.update("classes", values, "class_id=?", new String[]{String.valueOf(editModel.getId())});
//             }

//             loadClasses(null, null);
//             dialog.dismiss();
//         });

//         btnCancel.setOnClickListener(v -> dialog.dismiss());
//         dialog.show();
//     }

//     private void showFilterDialog() {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         LayoutInflater inflater = getLayoutInflater();
//         final View dialogView = inflater.inflate(R.layout.dialog_filter_class, null);
//         builder.setView(dialogView);
//         builder.setTitle("Filter Classes");

//         final EditText nameFilter = dialogView.findViewById(R.id.editFilterName);
//         final EditText codeFilter = dialogView.findViewById(R.id.editFilterCode);
//         Button btnApply = dialogView.findViewById(R.id.btnApplyFilter);

//         final AlertDialog dialog = builder.create();
//         btnApply.setOnClickListener(v -> {
//             loadClasses(nameFilter.getText().toString().trim(), codeFilter.getText().toString().trim());
//             dialog.dismiss();
//         });

//         dialog.show();
//     }

//     private void loadClasses(String nameFilter, String codeFilter) {
//         classList = new ArrayList<>();
//         SQLiteDatabase db = dbHelper.getReadableDatabase();
//         String query = "SELECT * FROM classes WHERE 1=1";
//         List<String> args = new ArrayList<>();

//         if (!TextUtils.isEmpty(nameFilter)) {
//             query += " AND class_name LIKE ?";
//             args.add("%" + nameFilter + "%");
//         }

//         if (!TextUtils.isEmpty(codeFilter)) {
//             query += " AND class_code LIKE ?";
//             args.add("%" + codeFilter + "%");
//         }

//         Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));
//         while (cursor.moveToNext()) {
//             classList.add(new ClassModel(
//                 cursor.getInt(0),
//                 cursor.getString(1),
//                 cursor.getString(2)
//             ));
//         }
//         cursor.close();

//         if (classList.isEmpty()) {
//             textNoClasses.setVisibility(View.VISIBLE);
//             recyclerView.setVisibility(View.GONE);
//         } else {
//             textNoClasses.setVisibility(View.GONE);
//             recyclerView.setVisibility(View.VISIBLE);
//         }

//         adapter = new ClassListAdapter(this, classList, new ClassListAdapter.OnClassActionListener() {
//             @Override
//             public void onEdit(ClassModel model) {
//                 showClassDialog(model);
//             }

//             @Override
//             public void onDelete(ClassModel model) {
//                 dbHelper.getWritableDatabase().delete("classes", "class_id=?", 
//                     new String[]{String.valueOf(model.getId())});
//                 loadClasses(null, null);
//             }
//         });

//         recyclerView.setAdapter(adapter);
//     }
// }

// package com.example.stuadminlogin.activities;

// import android.app.AlertDialog;
// import android.content.ContentValues;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.text.TextUtils;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.widget.Button;
// import android.widget.EditText;
// import android.widget.TextView;
// import android.widget.Toast;

// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.adapters.ClassListAdapter;
// import com.example.stuadminlogin.database.DatabaseHelper;
// import com.example.stuadminlogin.models.ClassModel;

// import java.util.ArrayList;
// import java.util.List;

// public class ManageClassesActivity extends AppCompatActivity {
//     DatabaseHelper dbHelper;
//     RecyclerView recyclerView;
//     Button btnAdd, btnSearch;
//     List<ClassModel> classList;
//     ClassListAdapter adapter;
//     TextView textNoClasses;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_manage_classes);

//         dbHelper = new DatabaseHelper(this);
//         recyclerView = findViewById(R.id.recyclerViewClasses);
//         btnAdd = findViewById(R.id.btnAddClass);
//         btnSearch = findViewById(R.id.btnSearchClass);
//         textNoClasses = findViewById(R.id.textNoClasses);

//         recyclerView.setLayoutManager(new LinearLayoutManager(this));
//         loadClasses(null, null);

//         btnAdd.setOnClickListener(v -> showClassDialog(null));
//         btnSearch.setOnClickListener(v -> showFilterDialog());
//     }

//     private void showClassDialog(ClassModel editModel) {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_class, null);
//         builder.setView(dialogView);
//         builder.setTitle(editModel == null ? "Add Class" : "Edit Class");

//         EditText nameInput = dialogView.findViewById(R.id.etClassName);
//         EditText codeInput = dialogView.findViewById(R.id.etClassCode);

//         if (editModel != null) {
//             nameInput.setText(editModel.getName());
//             codeInput.setText(editModel.getCode());
//         }

//         builder.setPositiveButton("Save", (dialog, which) -> {
//             String name = nameInput.getText().toString().trim();
//             String code = codeInput.getText().toString().trim();

//             if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code)) {
//                 Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
//                 return;
//             }

//             SQLiteDatabase db = dbHelper.getWritableDatabase();
//             ContentValues values = new ContentValues();
//             values.put("class_name", name);
//             values.put("class_code", code);

//             if (editModel == null) {
//                 db.insert("classes", null, values);
//             } else {
//                 db.update("classes", values, "class_id=?", new String[]{String.valueOf(editModel.getId())});
//             }

//             loadClasses(null, null);
//         });

//         builder.setNegativeButton("Cancel", null);
//         builder.create().show();
//     }

//     private void showFilterDialog() {
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_filter_class, null);
//         builder.setView(dialogView);
//         builder.setTitle("Filter Classes");

//         EditText nameFilter = dialogView.findViewById(R.id.editFilterName);
//         EditText codeFilter = dialogView.findViewById(R.id.editFilterCode);
//         Button btnApply = dialogView.findViewById(R.id.btnApplyFilter);

//         AlertDialog dialog = builder.create();
//         btnApply.setOnClickListener(v -> {
//             loadClasses(nameFilter.getText().toString().trim(), codeFilter.getText().toString().trim());
//             dialog.dismiss();
//         });

//         dialog.show();
//     }

//     private void loadClasses(String nameFilter, String codeFilter) {
//         classList = new ArrayList<>();
//         SQLiteDatabase db = dbHelper.getReadableDatabase();
//         String query = "SELECT * FROM classes WHERE 1=1";
//         List<String> args = new ArrayList<>();

//         if (!TextUtils.isEmpty(nameFilter)) {
//             query += " AND class_name LIKE ?";
//             args.add("%" + nameFilter + "%");
//         }

//         if (!TextUtils.isEmpty(codeFilter)) {
//             query += " AND class_code LIKE ?";
//             args.add("%" + codeFilter + "%");
//         }

//         Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));
//         while (cursor.moveToNext()) {
//             classList.add(new ClassModel(
//                 cursor.getInt(0),
//                 cursor.getString(1),
//                 cursor.getString(2)
//             ));
//         }
//         cursor.close();

//         if (classList.isEmpty()) {
//             textNoClasses.setVisibility(View.VISIBLE);
//             recyclerView.setVisibility(View.GONE);
//         } else {
//             textNoClasses.setVisibility(View.GONE);
//             recyclerView.setVisibility(View.VISIBLE);
//         }

//         adapter = new ClassListAdapter(this, classList, new ClassListAdapter.OnClassActionListener() {
//             @Override
//             public void onEdit(ClassModel model) {
//                 showClassDialog(model);
//             }

//             @Override
//             public void onDelete(ClassModel model) {
//                 dbHelper.getWritableDatabase().delete("classes", "class_id=?", new String[]{String.valueOf(model.getId())});
//                 loadClasses(null, null);
//             }
//         });

//         recyclerView.setAdapter(adapter);
//     }
// }


package com.example.stuadminlogin.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.adapters.ClassListAdapter;
import com.example.stuadminlogin.database.DatabaseHelper;
import com.example.stuadminlogin.models.ClassModel;

import java.util.ArrayList;
import java.util.List;

public class ManageClassesActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    Button btnAdd, btnSearch;
    List<ClassModel> classList;
    ClassListAdapter adapter;
    TextView textNoClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);

        // --- Toolbar Setup ---
        Toolbar toolbar = findViewById(R.id.toolbar); // Find the Toolbar by its ID
        setSupportActionBar(toolbar); // Set it as the Activity's support action bar

        // Optional: Customize Toolbar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Manage Classes"); // Set your desired title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Ensure back button is clickable
        }
        // --- End Toolbar Setup ---

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewClasses);
        btnAdd = findViewById(R.id.btnAddClass);
        btnSearch = findViewById(R.id.btnSearchClass);
        textNoClasses = findViewById(R.id.textNoClasses);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadClasses(null, null);

        btnAdd.setOnClickListener(v -> showClassDialog(null));
        btnSearch.setOnClickListener(v -> showFilterDialog());
    }

    // Handle back button press on the Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showClassDialog(ClassModel editModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_class, null);
        builder.setView(dialogView);
        builder.setTitle(editModel == null ? "Add Class" : "Edit Class");

        EditText nameInput = dialogView.findViewById(R.id.etClassName);
        EditText codeInput = dialogView.findViewById(R.id.etClassCode);

        if (editModel != null) {
            nameInput.setText(editModel.getName());
            codeInput.setText(editModel.getCode());
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            String code = codeInput.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code)) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                return; // Do not dismiss dialog if fields are empty
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("class_name", name);
            values.put("class_code", code);

            if (editModel == null) {
                db.insert("classes", null, values);
            } else {
                db.update("classes", values, "class_id=?", new String[]{String.valueOf(editModel.getId())});
            }

            loadClasses(null, null); // Reload all classes after save
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_filter_class, null);
        builder.setView(dialogView);
        builder.setTitle("Filter Classes");

        EditText nameFilter = dialogView.findViewById(R.id.editFilterName);
        EditText codeFilter = dialogView.findViewById(R.id.editFilterCode);
        Button btnApply = dialogView.findViewById(R.id.btnApplyFilter); // Get button from dialog_filter_class.xml

        AlertDialog dialog = builder.create(); // Create the dialog first
        
        // Set click listener for the "Apply Filter" button within the dialog
        btnApply.setOnClickListener(v -> {
            loadClasses(nameFilter.getText().toString().trim(), codeFilter.getText().toString().trim());
            dialog.dismiss(); // Dismiss the dialog after applying filter
        });

        dialog.show();
    }

    private void loadClasses(String nameFilter, String codeFilter) {
        classList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT class_id, class_name, class_code FROM classes WHERE 1=1"; // Specify columns to avoid issues with order
        List<String> args = new ArrayList<>();

        if (!TextUtils.isEmpty(nameFilter)) {
            query += " AND class_name LIKE ?";
            args.add("%" + nameFilter + "%");
        }

        if (!TextUtils.isEmpty(codeFilter)) {
            query += " AND class_code LIKE ?";
            args.add("%" + codeFilter + "%");
        }

        Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));
        while (cursor.moveToNext()) {
            classList.add(new ClassModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("class_id")), // Use column names for robustness
                cursor.getString(cursor.getColumnIndexOrThrow("class_name")),
                cursor.getString(cursor.getColumnIndexOrThrow("class_code"))
            ));
        }
        cursor.close();

        if (classList.isEmpty()) {
            textNoClasses.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textNoClasses.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter = new ClassListAdapter(this, classList, new ClassListAdapter.OnClassActionListener() {
            @Override
            public void onEdit(ClassModel model) {
                showClassDialog(model);
            }

            @Override
            public void onDelete(ClassModel model) {
                dbHelper.getWritableDatabase().delete("classes", "class_id=?", new String[]{String.valueOf(model.getId())});
                loadClasses(null, null); // Reload all classes after delete
            }
        });

        recyclerView.setAdapter(adapter);
    }
}