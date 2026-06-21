package com.google.android.youtube.pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // SharedPreferences for Auto-Login
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Yeh aapki Welcome Screen (activity_main.xml) ko load karega
        setContentView(R.layout.activity_main);

        // 1. SharedPreferences Initialize karna (Login data save karne ke liye)
        prefs = getSharedPreferences("LibraryApp", MODE_PRIVATE);

        // 2. Foreground Service Start karna (Background Notifications ke liye)
        startNotificationService();

        // 3. Check karna ki user pehle se login hai ya nahi
        checkLoginStatus();
    }

    // Yeh function humari Notification Service ko zinda rakhega
    private void startNotificationService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    // Traffic Police: Decide karega kahan jana hai
    private void checkLoginStatus() {
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        
        if (isLoggedIn) {
            // Agar login hai, toh seedha Dashboard par bhej do (Aage banayenge)
            Toast.makeText(this, "Welcome back to Krishna Library!", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            // startActivity(intent);
            // finish();
        } else {
            // Agar login nahi hai, toh Login Screen par hi rukne do
            Toast.makeText(this, "Please Login to continue.", Toast.LENGTH_SHORT).show();
        }
    }

    // Permission handle karne ke liye (Aage Camera/Scanner me kaam aayega)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) { // Camera Permission
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera access is required for QR Scanner", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Hum service ko stop nahi karenge taaki app band hone ke baad bhi notice aayein
    }
}
