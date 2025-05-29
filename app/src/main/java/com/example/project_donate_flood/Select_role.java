package com.example.project_donate_flood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_donate_flood.databinding.ActivitySelectRoleBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Select_role extends AppCompatActivity {
    private ActivitySelectRoleBinding binding;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

       binding = ActivitySelectRoleBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

       mAuth = FirebaseAuth.getInstance();

       binding.arrowBecomeDonor.setOnClickListener(view -> {
           Intent become_donor = new Intent(this, Donor_homepage.class);
           startActivity(become_donor);

       });
       binding.arrowLookingDonor.setOnClickListener(view -> {
           Intent looking_donor = new Intent(this, Receipient_homepage.class);
           startActivity(looking_donor);
       });
        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutUser();
            }
        });
    }
    private void signOutUser() {
        mAuth.signOut();

        // Navigate to Login Page
        Intent intent = new Intent(Select_role.this, Login_page.class); // Assuming Login_page is your login Activity
        // Flags to clear the back stack and start fresh, ensuring the user cannot go back to this activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Close the Select_role activity
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // If not signed in, redirect to login page.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User is not signed in, redirect to Login_page
            Intent intent = new Intent(Select_role.this, Login_page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}