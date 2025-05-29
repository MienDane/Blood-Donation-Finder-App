package com.example.project_donate_flood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_donate_flood.databinding.ActivityRegisterPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_page extends AppCompatActivity {
    private ActivityRegisterPageBinding binding;
    private FirebaseAuth mAuth;
    private Spinner bloodTypeSpinner; // Declare the Spinner

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityRegisterPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Inflate the layout and set the content view

        bloodTypeSpinner = findViewById(R.id.bloodTypeSpinner); // Now find the Spinner after setting the content view

        String[] bloodTypes = getResources().getStringArray(R.array.blood_type);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodTypeSpinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();

        binding.registerButton.setOnClickListener(v -> {
            onSignUpClicked();
        });
        binding.loginTextView.setOnClickListener(v -> {
            onAlreadyHaveAccountClicked();
        });
    }

    private void  onSignUpClicked() {
        String email =  binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();
        String confirmPassword = binding.confirmPassword.getText().toString();

        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Confirm password doesn't match!", Toast.LENGTH_SHORT).show();
        }else {
            binding.pbLoginProgress.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                binding.pbLoginProgress.setVisibility(View.INVISIBLE);
                                Intent loginIntent = new Intent(Register_page.this, Login_page.class);
                                loginIntent.putExtra("email", email);
                                loginIntent.putExtra("password", password);
                                setResult(RESULT_OK, loginIntent);
                                finish();
                            } else {
                                Toast.makeText(Register_page.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                binding.pbLoginProgress.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        }
    }

    private void onAlreadyHaveAccountClicked() {
        Intent intent = new Intent(this, Login_page.class);
        startActivity(intent);
    }
}