package com.google.android.youtube.pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends Activity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        prefs = getSharedPreferences("LibraryApp", Context.MODE_PRIVATE);

        // AppConfig file se naam yahan automatically aayega
        TextView tvLibName = findViewById(R.id.tvDashboardLibName);
        tvLibName.setText(AppConfig.LIBRARY_NAME);

        // Student ka naam Login system se laayenge
        TextView tvStudent = findViewById(R.id.tvWelcomeStudent);
        String username = prefs.getString("username", "Student");
        tvStudent.setText("Welcome, " + username);

        // Grid Cards
        LinearLayout cardAttendance = findViewById(R.id.cardAttendance);
        LinearLayout cardFees = findViewById(R.id.cardFees);
        LinearLayout cardNotices = findViewById(R.id.cardNotices);
        LinearLayout cardProfile = findViewById(R.id.cardProfile);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Abhi sirf Toast lagaya hai, aage asli pages connect karenge
        cardAttendance.setOnClickListener(v -> Toast.makeText(DashboardActivity.this, "Opening Attendance...", Toast.LENGTH_SHORT).show());
        cardFees.setOnClickListener(v -> Toast.makeText(DashboardActivity.this, "Opening Fees...", Toast.LENGTH_SHORT).show());
        cardNotices.setOnClickListener(v -> Toast.makeText(DashboardActivity.this, "Opening Notices...", Toast.LENGTH_SHORT).show());
        cardProfile.setOnClickListener(v -> Toast.makeText(DashboardActivity.this, "Opening Profile...", Toast.LENGTH_SHORT).show());

        // Logout Logic
        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}
