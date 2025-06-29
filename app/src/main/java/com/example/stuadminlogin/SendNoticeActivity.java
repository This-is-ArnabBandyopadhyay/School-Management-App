package com.example.stuadminlogin;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendNoticeActivity extends AppCompatActivity {

    EditText noticeInput;
    Button submitNoticeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendnotice);
        submitNoticeButton= findViewById(R.id.submitNoticeButton);

        noticeInput = findViewById(R.id.noticeInput);
        submitNoticeButton.setOnClickListener(v -> {
            String notice = noticeInput.getText().toString().trim();

            if (!notice.isEmpty()) {
                Intent intent = new Intent(SendNoticeActivity.this, ReadNotices.class);
                intent.putExtra("NOTICE_MESSAGE", notice);
                startActivity(intent);
            } else {
                Toast.makeText(SendNoticeActivity.this, "Please enter a notice", Toast.LENGTH_SHORT).show();
            }
        });
    }}