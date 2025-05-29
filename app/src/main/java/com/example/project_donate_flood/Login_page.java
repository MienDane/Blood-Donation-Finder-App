package com.example.project_donate_flood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_donate_flood.databinding.ActivityLoginPageBinding;
import com.example.project_donate_flood.service.IUserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_page extends AppCompatActivity {
    private ActivityLoginPageBinding binding;
    IUserService userservice;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.LoginBtn.setOnClickListener(v -> {
            onLoginClicked();
        });
        binding.registerBtn.setOnClickListener(v -> {
            onRegisterClicked();
        });
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        String email = data.getStringExtra("email");
                        String password = data.getStringExtra("password");

                        binding.inputEmail.setText(email);
                        binding.inputPassword.setText(password);
                    }
                }
        );
    }

    private void onRegisterClicked() {
        Intent intent = new Intent(this, Register_page.class);
        activityResultLauncher.launch(intent);
    }


    private void onLoginClicked() {
        binding.pbLoginProgress.setVisibility(View.VISIBLE);
        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            binding.pbLoginProgress.setVisibility(View.INVISIBLE);
                            reload();
                        }else {
                            Toast.makeText(Login_page.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            binding.pbLoginProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void reload() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}