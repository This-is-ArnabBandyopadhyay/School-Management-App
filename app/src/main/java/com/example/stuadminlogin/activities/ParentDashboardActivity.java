// package com.example.stuadminlogin.activities;

// import android.app.Activity;
// import android.app.AlertDialog;
// import android.content.Intent;
// import android.content.SharedPreferences;
// import android.database.Cursor;
// import android.database.sqlite.SQLiteDatabase;
// import android.os.Bundle;
// import android.widget.Button;
// import android.widget.TextView;
// import android.widget.Toast;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// public class ParentDashboardActivity extends Activity {

//     // Declare all buttons and TextView
//     Button btnViewMyStudents, parentViewNoticesButton, parentBtnViewHolidays,
//             parentBtnSubmitQuery, parentBtnViewQueries, parentBtnMyProfile,
//             parentBtnLogout;
//     TextView parentWelcomeText;
//     int loggedInParentId;
//     DatabaseHelper dbHelper;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_parent_dashboard);

//         dbHelper = new DatabaseHelper(this);

//         // Initialize Views
//         parentWelcomeText = findViewById(R.id.parentWelcomeText);
//         btnViewMyStudents = findViewById(R.id.btnViewMyStudents);
//         parentViewNoticesButton = findViewById(R.id.parentViewNoticesButton);
//         parentBtnViewHolidays = findViewById(R.id.parentBtnViewHolidays);
//         parentBtnSubmitQuery = findViewById(R.id.parentBtnSubmitQuery);
//         parentBtnViewQueries = findViewById(R.id.parentBtnViewQueries);
//         parentBtnMyProfile = findViewById(R.id.parentBtnMyProfile);
//         parentBtnLogout = findViewById(R.id.parentBtnLogout);

//         // Retrieve parent_id
//         loggedInParentId = getIntent().getIntExtra("parent_id", -1);
//         if (loggedInParentId == -1) {
//             SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//             loggedInParentId = sharedPreferences.getInt("parent_id", -1);
//         }

//         if (loggedInParentId == -1) {
//             Toast.makeText(this, "Parent not identified. Please log in again.", Toast.LENGTH_LONG).show();
//             finish();
//             return;
//         }

//         // Fetch and display parent name
//         displayParentName();

//         // Set OnClickListeners for all buttons

//         btnViewMyStudents.setOnClickListener(v -> {
//             Intent intent = new Intent(ParentDashboardActivity.this, ViewStudentsForParentActivity.class);
//             intent.putExtra("parent_id", loggedInParentId);
//             startActivity(intent);
//         });

//         // --- UPDATED CODE FOR PARENT NOTICES ---
//         parentViewNoticesButton.setOnClickListener(v -> {
//             Intent intent = new Intent(ParentDashboardActivity.this, ParentViewNoticesActivity.class);
//             intent.putExtra("parent_id", loggedInParentId); // Pass the parent ID
//             startActivity(intent);
//         });
//         // --- END UPDATED CODE ---

//         parentBtnViewHolidays.setOnClickListener(v -> {
//             Intent intent = new Intent(ParentDashboardActivity.this, ViewHolidaysActivity.class);
//             startActivity(intent);
//         });

//         parentBtnSubmitQuery.setOnClickListener(v -> {
//     // Launch SubmitQueryActivity for parents
//     Intent submitQueryIntent = new Intent(ParentDashboardActivity.this, SubmitQueryActivity.class);
//     submitQueryIntent.putExtra("parent_id", loggedInParentId); // Pass the parent ID
//     startActivity(submitQueryIntent);
// });

//         parentBtnViewQueries.setOnClickListener(v -> {
//             // Similar to submit, you might need to view queries for a specific child
//             // or queries submitted by the parent themselves.
//             Toast.makeText(this, "View Queries functionality needs child selection or parent-specific queries.", Toast.LENGTH_LONG).show();
//             // Example if it's parent's own queries:
//             // Intent viewQueriesIntent = new Intent(ParentDashboardActivity.this, ViewParentQueriesActivity.class); // You might need a new activity for parent's queries
//             // viewQueriesIntent.putExtra("parent_id", loggedInParentId);
//             // startActivity(viewQueriesIntent);
//         });

//         parentBtnMyProfile.setOnClickListener(v -> {
//             // This would lead to the parent's own profile, not a student's profile.
//             // You'll need a new ParentProfileActivity.
//             Toast.makeText(this, "My Profile functionality needs a ParentProfileActivity.", Toast.LENGTH_LONG).show();
//             // Example:
//             // Intent intent = new Intent(ParentDashboardActivity.this, ParentProfileActivity.class); // Create this new activity
//             // intent.putExtra("parent_id", loggedInParentId);
//             // startActivity(intent);
//         });

//         parentBtnLogout.setOnClickListener(v -> {
//             new AlertDialog.Builder(ParentDashboardActivity.this)
//                     .setTitle("Logout")
//                     .setMessage("Are you sure you want to log out?")
//                     .setPositiveButton("Yes", (dialog, which) -> {
//                         SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//                         sharedPreferences.edit().clear().apply();

//                         Intent intent = new Intent(ParentDashboardActivity.this, LoginActivity.class);
//                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                         startActivity(intent);
//                         finish();
//                     })
//                     .setNegativeButton("Cancel", null)
//                     .show();
//         });
//     }

//     private void displayParentName() {
//         SQLiteDatabase db = dbHelper.getReadableDatabase();
//         String query = "SELECT name FROM parents WHERE parent_id = ?";
//         Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(loggedInParentId)});

//         if (cursor != null && cursor.moveToFirst()) {
//             String parentName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
//             parentWelcomeText.setText("Welcome back, " + parentName);
//             cursor.close();
//         } else {
//             parentWelcomeText.setText("Welcome back, Parent");
//             if (cursor != null) {
//                 cursor.close();
//             }
//         }
//     }

//     @Override
//     protected void onDestroy() {
//         dbHelper.close();
//         super.onDestroy();
//     }
// }



package com.example.stuadminlogin.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

public class ParentDashboardActivity extends Activity {

    // Declare all buttons and TextView
    Button btnViewMyStudents, parentViewNoticesButton, parentBtnViewHolidays,
            parentBtnSubmitQuery, parentBtnViewPendingQueries, parentBtnViewRespondedQueries, // Updated buttons
            parentBtnMyProfile, parentBtnLogout;
    TextView parentWelcomeText;
    int loggedInParentId;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);

        dbHelper = new DatabaseHelper(this);

        // Initialize Views
        parentWelcomeText = findViewById(R.id.parentWelcomeText);
        btnViewMyStudents = findViewById(R.id.btnViewMyStudents);
        parentViewNoticesButton = findViewById(R.id.parentViewNoticesButton);
        parentBtnViewHolidays = findViewById(R.id.parentBtnViewHolidays);
        parentBtnSubmitQuery = findViewById(R.id.parentBtnSubmitQuery);
        parentBtnViewPendingQueries = findViewById(R.id.parentBtnViewPendingQueries); // Initialize new button
        parentBtnViewRespondedQueries = findViewById(R.id.parentBtnViewRespondedQueries); // Initialize new button
        parentBtnMyProfile = findViewById(R.id.parentBtnMyProfile);
        parentBtnLogout = findViewById(R.id.parentBtnLogout);

        // Retrieve parent_id
        loggedInParentId = getIntent().getIntExtra("parent_id", -1);
        if (loggedInParentId == -1) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            loggedInParentId = sharedPreferences.getInt("parent_id", -1);
        }

        if (loggedInParentId == -1) {
            Toast.makeText(this, "Parent not identified. Please log in again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Fetch and display parent name
        displayParentName();

        // Set OnClickListeners for all buttons

        btnViewMyStudents.setOnClickListener(v -> {
            Intent intent = new Intent(ParentDashboardActivity.this, ViewStudentsForParentActivity.class);
            intent.putExtra("parent_id", loggedInParentId);
            startActivity(intent);
        });

        parentViewNoticesButton.setOnClickListener(v -> {
            Intent intent = new Intent(ParentDashboardActivity.this, ParentViewNoticesActivity.class);
            intent.putExtra("parent_id", loggedInParentId);
            startActivity(intent);
        });

        parentBtnViewHolidays.setOnClickListener(v -> {
            Intent intent = new Intent(ParentDashboardActivity.this, ViewHolidaysActivity.class);
            startActivity(intent);
        });

        parentBtnSubmitQuery.setOnClickListener(v -> {
            Intent submitQueryIntent = new Intent(ParentDashboardActivity.this, SubmitQueryActivity.class);
            submitQueryIntent.putExtra("parent_id", loggedInParentId);
            startActivity(submitQueryIntent);
        });

        // NEW Listener for View My Pending Queries
        parentBtnViewPendingQueries.setOnClickListener(v -> {
            Intent viewQueriesIntent = new Intent(ParentDashboardActivity.this, ParentViewQueriesActivity.class);
            viewQueriesIntent.putExtra("parent_id", loggedInParentId);
            viewQueriesIntent.putExtra("query_status", "Pending"); // Pass status
            startActivity(viewQueriesIntent);
        });

        // NEW Listener for View My Responded Queries
        parentBtnViewRespondedQueries.setOnClickListener(v -> {
            Intent viewQueriesIntent = new Intent(ParentDashboardActivity.this, ParentViewQueriesActivity.class);
            viewQueriesIntent.putExtra("parent_id", loggedInParentId);
            viewQueriesIntent.putExtra("query_status", "Responded"); // Pass status
            startActivity(viewQueriesIntent);
        });

         // --- UPDATED CODE FOR MY PROFILE ---
        parentBtnMyProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ParentDashboardActivity.this, ParentProfileActivity.class); // Launch ParentProfileActivity
            intent.putExtra("parent_id", loggedInParentId); // Pass the logged-in parent's ID
            startActivity(intent);
        });

        parentBtnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(ParentDashboardActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        sharedPreferences.edit().clear().apply();

                        Intent intent = new Intent(ParentDashboardActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void displayParentName() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT name FROM parents WHERE parent_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(loggedInParentId)});

        if (cursor != null && cursor.moveToFirst()) {
            String parentName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            parentWelcomeText.setText("Welcome back, " + parentName);
            cursor.close();
        } else {
            parentWelcomeText.setText("Welcome back, Parent");
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}