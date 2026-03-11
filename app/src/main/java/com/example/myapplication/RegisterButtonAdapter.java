package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.UUID;

public class RegisterButtonAdapter {
    private final View view;

    public RegisterButtonAdapter(ActivityMainBinding binding, Button button) {
        this.view = binding.getRoot().getRootView();
        initialize(binding, button);
    }

    private void initialize(ActivityMainBinding binding, Button button) {
        button.setOnClickListener(v -> {
            String name = Objects.requireNonNull(binding.completeNameTextInputEditText.getText()).toString().trim();
            String age = Objects.requireNonNull(binding.ageTextInputEditText.getText()).toString().trim();
            String email = Objects.requireNonNull(binding.emailTextInputEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordTextInputEditText.getText()).toString().trim();

            if(name.isEmpty() || age.isEmpty() || email.isEmpty() || password.isEmpty())
                Toast.makeText(v.getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            else saveData(new User(UUID.randomUUID().toString(), name, Integer.parseInt(age), email, password));
        });
    }

    private void saveData(User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(user.getId()).setValue(user).addOnSuccessListener(u -> {
            Toast.makeText(this.view.getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this.view.getContext(), "Failed to save data: \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }



}
