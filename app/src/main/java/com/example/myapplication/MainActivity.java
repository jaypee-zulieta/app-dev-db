package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        binding.saveDataMaterialButton.setOnClickListener(v -> {
            String name = Objects.requireNonNull(binding.completeNameTextInputEditText.getText()).toString().trim();
            String age = Objects.requireNonNull(binding.ageTextInputEditText.getText()).toString().trim();
            String email = Objects.requireNonNull(binding.emailTextInputEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordTextInputEditText.getText()).toString().trim();

            if(name.isEmpty() || age.isEmpty() || email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            else createAccount(new User(UUID.randomUUID().toString(), name, Integer.parseInt(age), email, password));

        });




        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
      } );
    }

    private void saveData(User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(user.getId()).setValue(user).addOnSuccessListener(u -> {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to save data: \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void createAccount(User user) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth
                .createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Account not created", Toast.LENGTH_SHORT).show();
                });
    }
}
