package com.example.project_donate_flood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
// Import for BottomNavigationView


import com.example.project_donate_flood.databinding.ActivityMainBinding;
import com.example.project_donate_flood.fragment.AddPageFragment;
import com.example.project_donate_flood.fragment.HomePageFragment;
import com.example.project_donate_flood.fragment.SettingPageFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    FirebaseAuth mAuth; // Declared but not used in the provided snippet, ensure it's used if needed
    private static final int REQUEST_CODE_NOTIFICATION = 101;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.signout) {
            mAuth.signOut();
            Intent intent = new Intent(this, Login_page.class);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setTitle("");
        // Use setOnItemSelectedListener for handling fragment transactions
        // Make sure your binding.bottomNavigation is a BottomNavigationView
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null; // Renamed for clarity

            if (itemId == R.id.homeFragment) {
                selectedFragment = new HomePageFragment();
            } else if (itemId == R.id.addPageFragment) {
                selectedFragment = new AddPageFragment();
            } else if (itemId == R.id.settingFragment) {
                selectedFragment = new SettingPageFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true; // Event handled, item should be selected
            }

            return false; // Event not handled (or no fragment for this item), item might not be selected
        });

        // Set the default selected item
        if (savedInstanceState == null) {
            // Load the default fragment
            binding.bottomNavigation.setSelectedItemId(R.id.homeFragment);
        }
        checkAndRequestNotificationPermission();
        initFcmAndSubscribeTopic();
    }
    private void checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {  // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_NOTIFICATION);
            }
        }
    }
    private void initFcmAndSubscribeTopic() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Log.d("FCM", "Device Token: " + token);
                });

        // Subscribe to topic for broadcast
        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FCM", "Subscribed to topic: all");
                    } else {
                        Log.d("FCM", "Topic subscription failed.");
                    }
                });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_NOTIFICATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission denied. Notification won't appear", Toast.LENGTH_SHORT).show();
            }
        }
    }
}