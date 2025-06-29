package com.example.stuadminlogin;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReadNotices extends AppCompatActivity {

    TextView noticeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notices);

        noticeText = findViewById(R.id.noticeText);

        String notice = getIntent().getStringExtra("NOTICE_MESSAGE");

        if (notice != null && !notice.isEmpty()) {
            noticeText.setText(notice);
        } else {
            noticeText.setText("No new notices.");
        }
    }
}
