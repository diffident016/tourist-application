package com.example.touristapplicationbyhighhopes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // Initialize views
        EditText etComment = findViewById(R.id.etComment);
        Button btnSubmitComment = findViewById(R.id.btnSubmitComment);

        // Set click listener for submit button
        btnSubmitComment.setOnClickListener(v -> {
            String comment = etComment.getText().toString();
            if (comment.isEmpty()) {
                Toast.makeText(CommentActivity.this, "Please enter a comment", Toast.LENGTH_SHORT).show();
            } else {
                // Handle the comment submission logic here
                Toast.makeText(CommentActivity.this, "Comment submitted: " + comment, Toast.LENGTH_SHORT).show();
                // Optionally, close the activity
                finish();
            }
        });
    }
}
