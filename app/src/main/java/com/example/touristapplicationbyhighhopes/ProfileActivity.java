package com.example.touristapplicationbyhighhopes;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName, tvProfileAddress, tvProfilePhone;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileAddress = findViewById(R.id.tvProfileAddress);
        tvProfilePhone = findViewById(R.id.tvProfilePhone);
        btnLogout = findViewById(R.id.btnLogout);

        // Set user information (dummy data for now, replace with actual data)
        tvProfileName.setText("John Doe");
        tvProfileAddress.setText("1234 Main St, Anytown, USA");
        tvProfilePhone.setText("(123) 456-7890");

        // Set click listener for logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show confirmation dialog
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Navigate to login page
                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}
