package com.example.project_donate_flood;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_donate_flood.databinding.ActivityGetStartPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Get_start_page extends AppCompatActivity {
    private ActivityGetStartPageBinding binding;

//    FirebaseAuth mAuth;
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
//    }
//    private void reload() {
//        Intent intent = new Intent(this, Select_role.class);
//        startActivity(intent);
//        finish();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityGetStartPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.button2.setOnClickListener(view -> {
            Intent register_page = new Intent(this, Register_page.class);
            startActivity(register_page);
        });
        binding.button3.setOnClickListener(view -> {
            Intent login_page = new Intent(this, Login_page.class);
            startActivity(login_page);
        });


    }
}