// File: RespondToQueryActivity.java
package com.example.stuadminlogin.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import java.text.SimpleDateFormat;
import java.util.*;

public class RespondToQueryActivity extends AppCompatActivity {

    EditText responseInput;
    Button submitResponseBtn;
    int queryId, adminId;
    String respondedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respond_to_query);

        responseInput = findViewById(R.id.response_input);
        submitResponseBtn = findViewById(R.id.submit_response_btn);

        queryId = getIntent().getIntExtra("query_id", -1);
        adminId = getIntent().getIntExtra("admin_id", -1);
        respondedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        submitResponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String responseText = responseInput.getText().toString().trim();
                if (!responseText.isEmpty() && queryId != -1 && adminId != -1) {
                    DatabaseHelper dbHelper = new DatabaseHelper(RespondToQueryActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("query_id", queryId);
                    values.put("admin_id", adminId);
                    values.put("response_text", responseText);
                    values.put("responded_at", respondedAt);

                    long result = db.insert("query_responses", null, values);

                    if (result != -1) {
                        db.execSQL("UPDATE queries SET response_status = 'Responded' WHERE query_id = " + queryId);
                        Toast.makeText(RespondToQueryActivity.this, "Response submitted.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RespondToQueryActivity.this, "Failed to submit response.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}